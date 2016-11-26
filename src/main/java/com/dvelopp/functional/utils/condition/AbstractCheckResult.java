package com.dvelopp.functional.utils.condition;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public abstract class AbstractCheckResult<T, R, S extends AbstractCheckResult<T, R, S>> {

    private T condition;
    Boolean conditionResult;
    boolean conditionIsMet;
    R valueToReturn;

    protected abstract Boolean performCheck();

    public <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> isTrue(RR objectToReturn) {
        return performIfConditionIs(objectToReturn, true);
    }

    public <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> isFalse(RR objectToReturn) {
        return performIfConditionIs(objectToReturn, false);
    }

    public S isTrue(Runnable closure) {
        return performIfConditionIs(closure, true);
    }

    public S isFalse(Runnable closure) {
        return performIfConditionIs(closure, false);
    }

    public <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> isTrueGet(
            Supplier<RR> closure) {
        return performIfConditionIs(closure, true);
    }

    public <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> isFalseGet(
            Supplier<RR> closure) {
        return performIfConditionIs(closure, false);
    }

    @SuppressWarnings("unchecked")
    private <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> performIfConditionIs(
            RR objectToReturn, boolean expectedConditionValue) {
        if (!conditionIsMet) {
            if (expectedConditionValue == performCheck()) {
                conditionIsMet = true;
                valueToReturn = objectToReturn;
            }
        }
        return (AbstractCheckResult<T, RR, SS>) this;
    }

    @SuppressWarnings("unchecked")
    private S performIfConditionIs(Runnable closure, boolean expectedConditionValue) {
        if (!conditionIsMet) {
            requireNonNull(closure);
            if (expectedConditionValue == performCheck()) {
                conditionIsMet = true;
                closure.run();
            }
        }
        return (S) this;
    }

    @SuppressWarnings("unchecked")
    private <RR extends R, SS extends AbstractCheckResult<T, RR, SS>> AbstractCheckResult<T, RR, SS> performIfConditionIs(
            Supplier<RR> closure, boolean expectedConditionValue) {
        if (!conditionIsMet) {
            requireNonNull(closure);
            if (expectedConditionValue == performCheck()) {
                conditionIsMet = true;
                valueToReturn = closure.get();
            }
        }
        return (AbstractCheckResult<T, RR, SS>) this;
    }

    void setCondition(T condition) {
        requireNonNull(condition);
        this.condition = condition;
    }

    T getCondition() {
        return condition;
    }

    public R value() {
        return valueToReturn;
    }

}
