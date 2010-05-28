package com.googlecode.linkedlisp;

import java.util.ArrayList;
import java.util.List;

public class ListExpression implements Expression {

    private List<Expression> values = new ArrayList<Expression>();
    
    public ListExpression() {
    }
    
    public ListExpression(List<Expression> values) {
        this.values = values;
    }
    
    @Override
    public Object evaluate(State s) throws Exception {
        
        Expression first = getFirst();
        if (first instanceof ListExpression) {
            return evaluateAsList(s);
        }

        Object evaledFirst = first.evaluate(s);
        if (evaledFirst instanceof Function) {
            Function f = (Function)evaledFirst;
            f.setParameters(getRest());
            return f.evaluate(s);
        } else {
            return evaluateAsList(s);
        }
    }

    private List<Object> evaluateAsList(State s) throws Exception {
        List<Object> result = new ArrayList<Object>();
        for (Expression value : values) {
            try {
                result.add(value.evaluate(s));
            } catch (NoReturnException e) {
                // Just skip, nothing to add.
            }
        }
        return result;
    }
    
    @Override
    public Object getValue(State s) {
        return values;
    }

    public void append(Expression exp) {
        values.add(values.size(),exp);
    }
    
    public Expression getFirst() {
        return values.get(0);
    }
    
    public ListExpression getRest() {
        List<Expression> result = new ArrayList<Expression>(values);
        result.remove(0);
        return new ListExpression(result);
    }
    
    public Expression get(int index) {
        return values.get(index);
    }
    
    public int size() {
        return values.size();
    }
}
