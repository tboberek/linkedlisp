package com.googlecode.linkedlisp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListExpression implements Expression, Iterable<Expression> {

    private List<Expression> values = new LinkedList<Expression>();
    
    public ListExpression() {
    }
    
    public ListExpression(List<Expression> values) {
        this.values = values;
    }
    
    public ListExpression(Expression first, ListExpression rest) {
        values.add(first);
        if (rest != null)
            values.addAll(rest.getValues());
    }
    
    private List<Expression> getValues() {
        return values;
    }

    @Override
    public Object evaluate(State s) throws Exception {
        if (values.size() == 0) return null;
        Expression first = getFirst();
        Function f = (Function)first.evaluate(s);
        
        if(f == null)
        	throw new RuntimeException("function not found: " + first);
        
        return f.execute(s, getRest());
    }

    @Override
    public Object getValue() {
        List<Object> result = new ArrayList<Object>();
        for (Expression value : values) {
            result.add(value.getValue());
        }
        return result;
    }

    public void append(Expression exp) {
        values.add(values.size(),exp);
    }
    
    public Expression getFirst() {
        if (values.size() > 0)
            return values.get(0);
        else return null;
    }
    
    public ListExpression getRest() {
        if (values.size() > 1) {
            List<Expression> result = new ArrayList<Expression>(values);
            result.remove(0);
            return new ListExpression(result);
        } else return null;
    }
    
    public Expression get(int index) {
        return values.get(index);
    }
    
    public int size() {
        return values.size();
    }

    @Override
    public Iterator<Expression> iterator() {
        return values.iterator();
    }
    
    @Override
	public String toString() {
        return getValue().toString();
    }
}
