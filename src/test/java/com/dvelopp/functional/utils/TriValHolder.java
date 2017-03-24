package com.dvelopp.functional.utils;

/**
 * Test class that holds pair of values in a way that is easy to use for testing.
 *
 * @param <T> The first value type.
 * @param <K> The second value type.
 * @param <R> The third value type.
 */
public final class TriValHolder<T, K, R> {

    private T val1;
    private K val2;
    private R val3;

    public TriValHolder(T val1, K val2, R val3) {
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
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

    public R getVal3() {
        return val3;
    }

    public void setVal3(R val3) {
        this.val3 = val3;
    }
}
