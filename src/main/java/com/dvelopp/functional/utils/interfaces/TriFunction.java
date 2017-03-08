package com.dvelopp.functional.utils.interfaces;

@FunctionalInterface
public interface TriFunction<T1, T2, T3, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param firstArgument the first input argument.
     * @param secondArgument the second input argument.
     * @param thirdArgument the third input argument.
     * @return the function result
     */
    R apply(T1 firstArgument, T2 secondArgument, T3 thirdArgument);

    /**
     * Returns a TriFunction that always returns its first input argument.
     *
     * @param <T1> the type of the input and output objects to the function.
     * @param <T2> second arg type.
     * @param <T3> third arg type.
     * @return a function that always returns its first input argument.
     */
    static <T1, T2, T3> TriFunction<T1, T2, T3, T1> firstArgIdentity() {
        return (firstArgument, secondArgument, thirdArgument) -> firstArgument;
    }

    /**
     * @see this#firstArgIdentity()
     */
    static <T1, T2, T3> TriFunction<T1, T2, T3, T1> identity() {
        return firstArgIdentity();
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
        return (firstArgument, secondArgument, thirdArgument) -> secondArgument;
    }

    /**
     * Returns a TriFunction that always returns its third input argument.
     *
     * @param <T1> first arg type.
     * @param <T2> second arg type.
     * @param <T3> the type of the input and output objects to the function.
     * @return a function that always returns its third input argument.
     */
    static <T1, T2, T3> TriFunction<T1, T2, T3, T3> thirdArgIdentity() {
        return (firstArgument, secondArgument, thirdArgument) -> thirdArgument;
    }
}
