package com.dvelopp.functional.utils.interfaces;

@FunctionalInterface
public interface TriPredicate<T1, T2, T3> {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param t1 the first input argument.
     * @param t2 the second input argument.
     * @param t3 the third input argument.
     * @return {@code true} if the input arguments match the predicate,
     * otherwise {@code false}
     */
    boolean test(T1 t1, T2 t2, T3 t3);
}
