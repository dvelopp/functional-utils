package com.dvelopp.functional.utils.condition;

import java.util.function.Supplier;

import static com.dvelopp.functional.utils.condition.AbstractCheckResult.ConditionResult.*;
import static java.util.Objects.requireNonNull;

/**
 * The parent class for result of the check performed in {@link CheckUtils}.
 * Provides with the default methods to be used by all types of check results as well as the general concept.
 * The main advantage of those methods that they provide fluent generics that transforms the result of the method
 * invocation to the correct check result objects with corresponding types parametrization.
 *
 * @param <T> The condition to be calculated.
 * @param <R> The return value type.
 * @param <S> The type of returned instance of {@link AbstractCheckResult}. This type is used to always know what child
 *            class we work with. The main advantage of it is the fact that we don't need to cast the result even when
 *            the child doesn't provide its own implementation of the methods.
 */
public abstract class AbstractCheckResult<T, R, S extends AbstractCheckResult<T, R, S>> {

    /**
     * Condition to be calculated to get a {@link ConditionResult object}.
     */
    private T condition;
    /**
     * Indicates whether condition was calculated and if it was, then it's not calculated more. Except for performance
     * optimization, it also allows to indicate the result of condition to take specific action for different states of
     * the {@link ConditionResult object}.
     */
    ConditionResult conditionResult = NONE;
    /**
     * Used to return values from the statement. An alternative for the ternary operator.
     */
    R valueToReturn;
    /**
     * Useful to get information about the exception and to take specific actions when it happens.
     */
    Exception exception;

    /**
     * Performs check of the condition based on the implementation.
     *
     * @return the conditional result object. Used as builder pattern. Other actions can be added to the returned
     * {@link ConditionResult} object.
     */
    protected abstract ConditionResult performCheck();

    /**
     * Returns specific value if the condition result is {@link Boolean#TRUE}.
     *
     * @param objectToReturn The object that is returned in case of the conditional result is {@link Boolean#TRUE}.
     * @param <RR>           The return value of the new {@link AbstractCheckResult} object.
     * @param <SS>           The result type of implementation of {@link AbstractCheckResult}. Used to work with the
     *                       type as with the implementation, not an abstract type.
     * @return The result implementation of {@link AbstractCheckResult} object.
     */
    public <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> isTrue(RR objectToReturn) {
        return performIfConditionIs(objectToReturn, TRUE);
    }

    /**
     * Returns specific value if the condition result is {@link Boolean#FALSE}.
     *
     * @param objectToReturn The object that is returned in case of the conditional result is {@link Boolean#FALSE}.
     * @param <RR>           The return value of the new {@link AbstractCheckResult} object.
     * @param <SS>           The result type of implementation of {@link AbstractCheckResult}. Used to work with the
     *                       type as with the implementation, not an abstract type.
     * @return The result implementation of {@link AbstractCheckResult} object.
     */
    public <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> isFalse(RR objectToReturn) {
        return performIfConditionIs(objectToReturn, FALSE);
    }

    /**
     * Performs a specific action if the condition result is {@link Boolean#TRUE}.
     *
     * @param closure The object that is returned in case of the conditional result is {@link Boolean#TRUE}.
     * @return The result implementation of {@link AbstractCheckResult} object.
     */
    public S isTrue(Runnable closure) {
        return performIfConditionIs(closure, TRUE);
    }

    /**
     * Performs a specific action if the condition result is {@link Boolean#FALSE}.
     *
     * @param closure The object that is returned in case of the conditional result is {@link Boolean#FALSE}.
     * @return The result implementation of {@link AbstractCheckResult} object.
     */
    public S isFalse(Runnable closure) {
        return performIfConditionIs(closure, FALSE);
    }

    /**
     * Performs a specific action if the condition calculation throws an exception.
     *
     * @param closure The object that is returned in case of exception.
     * @return The result implementation of {@link AbstractCheckResult} object.
     */
    public S isException(Runnable closure) {
        return performIfConditionIs(closure, EXCEPTION);
    }

    /**
     * Returns specific value if the condition result is {@link Boolean#TRUE}.
     *
     * @param closure The supplier to produce an object that is returned in case of the
     *                conditional result is {@link Boolean#TRUE}.
     * @param <RR>    The return value of the new {@link AbstractCheckResult} object.
     * @param <SS>    The result type of implementation of {@link AbstractCheckResult}. Used to work with the
     *                type as with the implementation, not an abstract type.
     * @return The result implementation of {@link AbstractCheckResult} object.
     */
    public <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> isTrueGet(
            Supplier<RR> closure) {
        return performIfConditionIs(closure, TRUE);
    }

    /**
     * Returns specific value if the condition result is {@link Boolean#FALSE}.
     *
     * @param closure The supplier to produce an object that is returned in case of the
     *                conditional result is {@link Boolean#FALSE}.
     * @param <RR>    The return value of the new {@link AbstractCheckResult} object.
     * @param <SS>    The result type of implementation of {@link AbstractCheckResult}. Used to work with the
     *                type as with the implementation, not an abstract type.
     * @return The result implementation of {@link AbstractCheckResult} object.
     */
    public <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> isFalseGet(
            Supplier<RR> closure) {
        return performIfConditionIs(closure, FALSE);
    }

    @SuppressWarnings("unchecked")
    private <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS>
        performIfConditionIs(
            RR objectToReturn, ConditionResult expectedConditionValue) {
        if (needToExecuteCondition()) {
            if (expectedConditionValue == performCheck()) {
                conditionResult = EXECUTED;
                valueToReturn = objectToReturn;
            }
        }
        return (AbstractCheckResult<T, RR, SS>) this;
    }

    @SuppressWarnings("unchecked")
    private S performIfConditionIs(Runnable closure, ConditionResult expectedConditionValue) {
        if (needToExecuteCondition()) {
            requireNonNull(closure);
            if (expectedConditionValue == performCheck()) {
                conditionResult = EXECUTED;
                closure.run();
            }
        }
        return (S) this;
    }

    @SuppressWarnings("unchecked")
    private <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS>
        performIfConditionIs(
            Supplier<RR> closure, ConditionResult expectedConditionValue) {
        if (needToExecuteCondition()) {
            requireNonNull(closure);
            if (expectedConditionValue == performCheck()) {
                conditionResult = EXECUTED;
                valueToReturn = closure.get();
            }
        }
        return (AbstractCheckResult<T, RR, SS>) this;
    }

    boolean needToExecuteCondition() {
        return conditionResult != EXECUTED;
    }

    void setCondition(T condition) {
        this.condition = requireNonNull(condition);
    }

    T getCondition() {
        return condition;
    }

    /**
     * Get a value of the condition execution. Can be null in case the listed actions don't produce the value.
     *
     * @return the value calculated in the conditions.
     */
    public R value() {
        return valueToReturn;
    }

    /**
     * Indicates the state of the condition calculation.
     */
    public enum ConditionResult {
        TRUE,
        FALSE,
        EXCEPTION, //State after exception during the execution
        EXECUTED, //State after success execution
        NONE //Initial state
    }
}
