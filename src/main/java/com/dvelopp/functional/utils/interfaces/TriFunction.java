package com.dvelopp.functional.utils.interfaces;

import java.util.function.Function;

/**
 * Represents a function that accepts three arguments and produces a result.
 * This is the three-arity specialization of {@link Function}.
 *
 * @param <T1> The type of the first argument to the function.
 * @param <T2> The type of the second argument to the function.
 * @param <T3> The type of the third argument to the function.
 * @param <R>  The type of the result of the function.
 * @since 1.3
 */
@FunctionalInterface
public interface TriFunction<T1, T2, T3, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param firstArgument  The first input argument.
     * @param secondArgument The second input argument.
     * @param thirdArgument  The third input argument.
     * @return The function result.
     */
    R apply(T1 firstArgument, T2 secondArgument, T3 thirdArgument);

    /**
     * Returns a TriFunction that always returns its first input argument.
     *
     * @param <T1> The type of the input and output objects to the function.
     * @param <T2> Second arg type.
     * @param <T3> Third arg type.
     * @return A function that always returns its first input argument.
     */
    static <T1, T2, T3> TriFunction<T1, T2, T3, T1> firstArgIdentity() {
        return (firstArgument, secondArgument, thirdArgument) -> firstArgument;
    }

    /**
     * Short form of this#firstArgIdentity().
     *
     * @see this#firstArgIdentity()
     */
    static <T1, T2, T3> TriFunction<T1, T2, T3, T1> identity() {
        return firstArgIdentity();
    }

    /**
     * Returns a TriFunction that always returns its second input argument.
     *
     * @param <T1> First arg type.
     * @param <T2> The type of the input and output objects to the function.
     * @param <T3> Third arg type.
     * @return A function that always returns its second input argument.
     */
    static <T1, T2, T3> TriFunction<T1, T2, T3, T2> secondArgIdentity() {
        return (firstArgument, secondArgument, thirdArgument) -> secondArgument;
    }

    /**
     * Returns a TriFunction that always returns its third input argument.
     *
     * @param <T1> First arg type.
     * @param <T2> Second arg type.
     * @param <T3> The type of the input and output objects to the function.
     * @return A function that always returns its third input argument.
     */
    static <T1, T2, T3> TriFunction<T1, T2, T3, T3> thirdArgIdentity() {
        return (firstArgument, secondArgument, thirdArgument) -> thirdArgument;
    }
}
