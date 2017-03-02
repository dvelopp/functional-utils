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

    static <T1, T2, T3> TriFunction<T1, T2, T3, T1> firstArgIdentity() {
        return (t1, t2, t3) -> t1;
    }

    static <T1, T2, T3> TriFunction<T1, T2, T3, T2> secondArgIdentity() {
        return (t1, t2, t3) -> t2;
    }

    static <T1, T2, T3> TriFunction<T1, T2, T3, T3> thirdArgIdentity() {
        return (t1, t2, t3) -> t3;
    }
}
