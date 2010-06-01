package com.googlecode.linkedlisp.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Sequence implements List<Number> {

    private BigDecimal start;
    private BigDecimal increment;
    private BigDecimal stop;

    public Sequence (Number start, Number stop, Number increment) {
        this.start = getValue(start);
        this.stop = getValue(stop);
        this.increment = getValue(increment);
    }
    
    @Override
    public boolean add(Number o) {
        return false;
    }

    @Override
    public void add(int index, Number element) {
    }

    @Override
    public boolean addAll(Collection<? extends Number> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Number> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Number get(int index) {
        return start.add(increment.multiply(BigDecimal.valueOf(index)));
    }

    @Override
    public int indexOf(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<Number> iterator() {
        return listIterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        BigDecimal value = getValue((Number)o);
        return value.divideToIntegralValue(increment).intValue();
    }

    @Override
    public ListIterator<Number> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<Number> listIterator(final int index) {
        return new ListIterator<Number>() {

            BigDecimal current = (BigDecimal) get(index);
            
            @Override
            public boolean hasNext() {
                return current.doubleValue() < stop.doubleValue();
            }

            @Override
            public Number next() {
                BigDecimal result = current;
                current = current.add(increment);
                return result;
            }

            @Override
            public void remove() {
            }

            @Override
            public void add(Number arg0) {
            }

            @Override
            public boolean hasPrevious() {
                return current.doubleValue() > start.doubleValue();
            }

            @Override
            public int nextIndex() {
                return indexOf(current) + 1;
            }

            @Override
            public Number previous() {
                BigDecimal result = current;
                current = current.subtract(increment);
                return result;
            }

            @Override
            public int previousIndex() {
                return indexOf(current) - 1;
            }

            @Override
            public void set(Number arg0) {
            }
            
        };        
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Number remove(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Number set(int index, Number element) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int size() {
        return start.subtract(stop).divideToIntegralValue(increment).intValue()+1;
    }

    @Override
    public List<Number> subList(int fromIndex, int toIndex) {
        return new Sequence(get(fromIndex), get(toIndex),increment);
    }

    @Override
    public Number[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    public Number[] toArray(Number[] a) {
        int size = size();
        for (int i = 0; i < size; ++i ) {
            a[i] = get(i);
        }
        return a;
    }

    public BigDecimal getValue(Number n) {
        if (n instanceof Double || n instanceof Float) {
            return BigDecimal.valueOf(n.doubleValue());
        } else {
            return BigDecimal.valueOf(n.longValue());
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
