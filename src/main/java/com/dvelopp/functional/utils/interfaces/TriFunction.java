package com.dvelopp.functional.utils.interfaces;

@FunctionalInterface
public interface TriFunction<T1, T2, T3, R> {


    /**
     * Applies this function to the given arguments.
     *
     * @param t1 the first input argument.
     * @param t2 the second input argument.
     * @param t3 the third input argument.
     * @return the function result
     */
    R apply(T1 t1, T2 t2, T3 t3);

}
