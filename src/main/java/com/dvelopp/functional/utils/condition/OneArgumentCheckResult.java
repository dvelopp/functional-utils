package com.dvelopp.functional.utils.condition;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class OneArgumentCheckResult<T, R> extends AbstractCheckResult<Predicate<T>, R, OneArgumentCheckResult<T, R>> {

    private T arg;

    OneArgumentCheckResult(Predicate<T> condition, T arg) {
        setCondition(condition);
        requireNonNull(arg);
        this.arg = arg;
    }

    public OneArgumentCheckResult<T, R> isTrue(Consumer<T> closure) {
        return performIfConditionIs(closure, true);
    }

    public OneArgumentCheckResult<T, R> isFalse(Consumer<T> closure) {
        return performIfConditionIs(closure, false);
    }

    public <RR extends R> OneArgumentCheckResult<T, RR> isTrueMap(Function<T, RR> function) {
        return performIfConditionIs(function, true);
    }

    public <RR extends R> OneArgumentCheckResult<T, RR> isFalseMap(Function<T, RR> function) {
        return performIfConditionIs(function, false);
    }

    private OneArgumentCheckResult<T, R> performIfConditionIs(Consumer<T> closure, boolean expectedConditionValue) {
        if (!conditionIsMet) {
            requireNonNull(closure);
            if (expectedConditionValue == performCheck()) {
                conditionIsMet = true;
                closure.accept(arg);
            }
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    private <RR extends R> OneArgumentCheckResult<T, RR> performIfConditionIs(Function<T, RR> function,
                                                                              boolean expectedConditionValue) {
        if (!conditionIsMet) {
            requireNonNull(function);
            if (expectedConditionValue == performCheck()) {
                conditionIsMet = true;
                valueToReturn = function.apply(arg);
            }
        }
        return (OneArgumentCheckResult<T, RR>) this;
    }

    @Override
    protected Boolean performCheck() {
        if (conditionResult == null) {
            return conditionResult = getCondition().test(arg);
        }
        return conditionResult;
    }

}