package com.dvelopp.functional.utils;

/**
 * Test class that holds pair of values in a way that is easy to use for testing using in lambdas.
 *
 * @param <T> first value type
 * @param <K> second value type
 */
public class BiValHolder<T, K> {

    private T val1;
    private K val2;

    public BiValHolder(T val1, K val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public T getVal1() {
        return val1;
    }

    public void setVal1(T val1) {
        this.val1 = val1;
    }

    public K getVal2() {
        return val2;
    }

    public void setVal2(K val2) {
        this.val2 = val2;
    }
}
