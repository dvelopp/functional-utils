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

    /**
     * Returns a TriFunction that always returns its first input argument.
     *
     * @param <T1> the type of the input and output objects to the function.
     * @param <T2> second arg type.
     * @param <T3> third arg type.
     * @return a function that always returns its first input argument.
     */
    static <T1, T2, T3> TriFunction<T1, T2, T3, T1> firstArgIdentity() {
        return (t1, t2, t3) -> t1;
    }

    /**
     * Returns a TriFunction that always returns its second input argument.
     *
     * @param <T1> first arg type.
     * @param <T2> the type of the input and output objects to the function.
     * @param <T3> third arg type.
     * @return a function that always returns its second input argument.
     */
    static <T1, T2, T3> TriFunction<T1, T2, T3, T2> secondArgIdentity() {
        return (t1, t2, t3) -> t2;
    }

    /**
     * Returns a TriFunction that always returns its second input argument.
     *
     * @param <T1> first arg type.
     * @param <T2> second arg type.
     * @param <T3> the type of the input and output objects to the function.
     * @return a function that always returns its second input argument.
     */
    static <T1, T2, T3> TriFunction<T1, T2, T3, T3> thirdArgIdentity() {
        return (t1, t2, t3) -> t3;
    }
}
