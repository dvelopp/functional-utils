package com.dvelopp.functional.utils.interfaces;

import java.util.function.Consumer;

/**
 * Represents an operation that accepts three input arguments and returns no
 * result.  This is the three-arity specialization of {@link Consumer}.
 * Unlike most other functional interfaces, {@code ThreeConsumer} is expected
 * to operate via side-effects.
 *
 * @param <T1> The type of the first argument to the operation.
 * @param <T2> The type of the second argument to the operation.
 * @param <T3> The type of the third argument to the operation.
 * @since 1.3
 */
@FunctionalInterface
public interface TriConsumer<T1, T2, T3> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t1 the first input argument.
     * @param t2 the second input argument.
     * @param t3 the third input argument.
     */
    void accept(T1 t1, T2 t2, T3 t3);

}
