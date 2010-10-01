package com.googlecode.linkedlisp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.googlecode.linkedlisp.functions.Evaluate;

public class ListExpression extends ArrayList<Object> {

    public ListExpression() {
        super();
    }
    
    public ListExpression(List values) {
        super(values);
    }
    
    public ListExpression(Object first, List rest) {
        add(first);
        if (rest != null)
            addAll(rest);
    }
    
    public void append(Object exp) {
        add(size(),exp);
    }
    
    public Object getFirst() {
        if (size() > 0)
            return get(0);
        else return null;
    }
    
    public ListExpression getRest() {
        if (size() > 1) {
            ListExpression result = new ListExpression(this);
            result.remove(0);
            return result;
        } else return null;
    }
    
}
