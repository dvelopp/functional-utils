package com.dvelopp.functional.utils.interfaces;

import java.util.function.Predicate;

/**
 * Represents a predicate (boolean-valued function) of three arguments.  This is
 * the three-arity specialization of {@link Predicate}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(Object, Object, Object)}.
 *
 * @param <T1> The type of the first argument to the predicate.
 * @param <T2> The type of the second argument to the predicate.
 * @param <T3> The type of the third argument to the predicate.
 * @since 1.3
 */
@FunctionalInterface
public interface TriPredicate<T1, T2, T3> {

    /**
     * Evaluates this predicate on the given arguments.
     *
     * @param t1 The first input argument.
     * @param t2 The second input argument.
     * @param t3 The third input argument.
     * @return {@code true} if the input arguments match the predicate,
     * otherwise {@code false}
     */
    boolean test(T1 t1, T2 t2, T3 t3);
}
