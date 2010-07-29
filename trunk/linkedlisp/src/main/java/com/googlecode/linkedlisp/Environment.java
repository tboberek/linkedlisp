package com.googlecode.linkedlisp;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.OWLMicroReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

@SuppressWarnings("unchecked")
public class Environment {

    private Model baseModel = null;
    private Environment parentState = null;
    private Map<String, Object> variables = new HashMap<String,Object>();
    private InfModel model;
    
    private Environment parentEnvironment = null;

    public Environment(InfModel m) {
        this.model = m;
    }
    
    public Environment() {
    }
    
    public void setModel(Model m) {
        baseModel = m;
        List<Rule> r = new ArrayList<Rule>(OWLMicroReasoner.loadRules());
        GenericRuleReasoner reasoner = new GenericRuleReasoner(r);
        reasoner.setOWLTranslation(true);
        reasoner.setTransitiveClosureCaching(true);
        model = ModelFactory.createInfModel(reasoner, baseModel);
    }
    
    public InfModel getModel() {
        if (parentState == null)
            return model;
        else return parentEnvironment.getModel();
    }
    
    public Map<String,Object> getVariables() {
        return variables;
    }
    
    public Map<String,String> getPrefixes() {
        return getModel().getNsPrefixMap();
    }

    public void setPrefix(String prefix, String uri) {
        try {
            getModel().setNsPrefix(prefix, uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Environment getParentEnvironment() {
        return parentEnvironment;
    }
    
    public Environment getParentState() {
        return parentState;
    }
    
    public String processPrefix(String s) {
        String[] parsed = s.split(":",2);
        if (parsed.length > 1 ) {
            if (getPrefixes().containsKey(parsed[0]))
                return getPrefixes().get(parsed[0])+parsed[1];
            else if (parentState != null 
                && parentEnvironment.getPrefixes().containsKey(parsed[0]))
                return parentEnvironment.getPrefixes().get(parsed[0])+parsed[1];
        }
        return s;
    }
    
    public Object getVariable(String name) {
        Object value = null;
        if (getVariables().containsKey(name)) {
            value = getVariables().get(name);
//            if (value instanceof Symbol && parentState != null) {
//                String nameInParent = value.toString();
//                value = parentState.getVariable(nameInParent);            
//            } else if (value instanceof Expression) {
//                Environment forEval = this;
//                if (this.parentState != null) forEval = this.parentState;
//                value = ((Expression)value).evaluate(forEval);
//                getVariables().put(name, value);
//            }
        } else if (parentEnvironment != null){
            value = parentEnvironment.getVariable(name);
        }

        return value;
    }

	public Object setVariable(String name, Object value) throws Exception {
	    if (parentEnvironment != null 
	            && parentEnvironment.getVariable(name) != null) {
	        parentEnvironment.setVariable(name, value);
	    } else {
	        getVariables().put(name, value);
	    }
		return value;
	}
    
	
	
    public Environment copyForCall(List params, List paramNames, Environment parentEnvironment) {
        Environment copy =  new Environment(model);
        copy.parentState = this;
        copy.parentEnvironment = parentEnvironment;

        if (params != null && paramNames != null) {
            for (int i=0; i< params.size() && i < paramNames.size(); ++i) {
                Object parameter = resolve(params.get(i));
                copy.variables.put(paramNames.get(i).toString(), parameter);
            }
        }
        
        return copy;
    }

    public Environment copyForScope(List mappings, Environment parentEnvironment) throws Exception {
        Environment copy =  new Environment(model);
        copy.parentState = this;
        copy.parentEnvironment = parentEnvironment;

        for (Object mapping : mappings) {
            List m = (List)mapping;
            copy.variables.put(m.get(0).toString(), resolve(m.get(1)));
        }
        return copy;
    }

    public Object resolve(Object o) {
        Object result = o;
        if (o instanceof Symbol) {
            result = ((Symbol)o).evaluate(this);
        }
        if (result instanceof URI) {
            result = model.createResource(model.expandPrefix(result.toString().trim()));
        }
        return result;
    }
    
    public String resolveAsString(Object o) {
        Object oPrime = resolve(o);
        if (oPrime == null) return null;
        if (oPrime instanceof String) return (String)o;
        else return oPrime.toString();
    }

    public Double resolveAsFloat(Object o) {
        Object f = resolve(o);
        if (f == null) {
            return null;
        } else if (f instanceof Number) {
            return ((Number)f).doubleValue();
        } else {
            return Double.valueOf(f.toString().trim());
        }
    }
    
    public Long resolveAsInteger(Object o) {
        Object f = resolve(o);
        if (f == null) {
            return null;
        } else if (f instanceof Number) {
            return ((Number)f).longValue();
        } else {
            return Long.valueOf(f.toString().trim());
        }
    }
    
    public Function resolveAsFunction(Object o) {
        return (Function) resolve(o);
    }
    
    public void registerRule(Rule rule) {
        GenericRuleReasoner reasoner = (GenericRuleReasoner)model.getReasoner();
        List<Rule> rules = new ArrayList<Rule>(reasoner.getRules());
        rules.add(rule);
        reasoner.setRules(rules);
        model = ModelFactory.createInfModel(reasoner, baseModel);
    }
    
    public Object evaluate(Object o) throws Exception {
        if (o instanceof List) {
            List l = (List)o;
            if (l.size() == 0) return null;

            Function f = resolveAsFunction(l.get(0));

            Object first = l.get(0);
            String name = "UNKNOWN";
            if(first instanceof Symbol) {
                name = first.toString();
            }
            
            if(f == null)
                throw new RuntimeException("function not found: " + first);

            try {
                return f.execute(this, getRest(l));
//            } catch (LispRuntimeException e) {
                // add linkedlisp names as we're unwinding the java stack
 //               e.addToStacktrace(name);
//                throw e;
            } catch (Exception e) {
                throw new RuntimeException(name, e);
//                throw new LispRuntimeException(e, name);
            }
        } else {
            return resolve(o);
        }
    }

    public List getRest(List l) {
        List result = new ArrayList(l);
        result.remove(0);
        return result;
    }

    public Resource resolveAsResource(Object o) {
        Object oPrime = resolve(o);
        if (oPrime instanceof Resource) return (Resource)oPrime;
        else if (oPrime == null) return null;
        else {
            return model.createResource(model.expandPrefix(oPrime.toString().trim()));
        }
    }

    public List resolveAsList(Object o) {
        Object object = resolve(o);
        return (List)object;
    }

}
