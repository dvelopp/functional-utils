package com.dvelopp.functional.utils.interfaces;

/**
 * Represents an operation upon three operands of the same type, producing a result
 * of the same type as the operands.  This is a specialization of
 * {@link TriFunction} for the case where the operands and the result are all of
 * the same type.
 *
 * @param <T> the type of the operands and result of the operator.
 * @since 1.3
 */
@FunctionalInterface
public interface TriOperator<T> extends TriFunction<T, T, T, T> {

}
