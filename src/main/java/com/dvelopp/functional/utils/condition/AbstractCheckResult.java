package com.dvelopp.functional.utils.condition;

import java.util.function.Supplier;

import static com.dvelopp.functional.utils.condition.AbstractCheckResult.ConditionResult.*;
import static java.util.Objects.requireNonNull;

public abstract class AbstractCheckResult<T, R, S extends AbstractCheckResult<T, R, S>> {

    private T condition;
    ConditionResult conditionResult = NONE;
    R valueToReturn;
    Exception exception;

    protected abstract ConditionResult performCheck();

    public <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> isTrue(RR objectToReturn) {
        return performIfConditionIs(objectToReturn, TRUE);
    }

    public <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> isFalse(RR objectToReturn) {
        return performIfConditionIs(objectToReturn, FALSE);
    }

    public S isTrue(Runnable closure) {
        return performIfConditionIs(closure, TRUE);
    }

    public S isFalse(Runnable closure) {
        return performIfConditionIs(closure, FALSE);
    }

    public S isException(Runnable closure) {
        return performIfConditionIs(closure, EXCEPTION);
    }

    public <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> isTrueGet(
            Supplier<RR> closure) {
        return performIfConditionIs(closure, TRUE);
    }

    public <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> isFalseGet(
            Supplier<RR> closure) {
        return performIfConditionIs(closure, FALSE);
    }

    @SuppressWarnings("unchecked")
    private <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> performIfConditionIs(
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
    private <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> performIfConditionIs(
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

    protected boolean needToExecuteCondition() {
        return conditionResult != EXECUTED;
    }

    void setCondition(T condition) {
        this.condition = requireNonNull(condition);
    }

    T getCondition() {
        return condition;
    }

    public R value() {
        return valueToReturn;
    }

    public enum ConditionResult {
        TRUE, FALSE, EXCEPTION, EXECUTED, NONE
    }
}
