package com.dvelopp.functional.utils;

public class BiValueHolder<T, K> {

    private T firstVal;
    private K secondVal;

    public BiValueHolder(T firstValue, K secondValue) {
        this.firstVal = firstValue;
        this.secondVal = secondValue;
    }

    public T getFirstVal() {
        return firstVal;
    }

    public void setFirstVal(T firstVal) {
        this.firstVal = firstVal;
    }

    public K getSecondVal() {
        return secondVal;
    }

    public void setSecondVal(K secondVal) {
        this.secondVal = secondVal;
    }
}
