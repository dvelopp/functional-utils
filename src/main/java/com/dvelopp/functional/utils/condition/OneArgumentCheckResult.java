package com.dvelopp.functional.utils.condition;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.dvelopp.functional.utils.condition.AbstractCheckResult.ConditionResult.*;
import static java.util.Objects.requireNonNull;

/**
 * @since 1.0
 */
public class OneArgumentCheckResult<T, R> extends AbstractCheckResult<Predicate<T>, R, OneArgumentCheckResult<T, R>> {

    private T arg;

    OneArgumentCheckResult(Predicate<T> condition, T arg) {
        setCondition(condition);
        this.arg = requireNonNull(arg);
    }

    public OneArgumentCheckResult<T, R> isTrue(Consumer<T> closure) {
        return performIfConditionIs(closure, TRUE);
    }

    public OneArgumentCheckResult<T, R> isFalse(Consumer<T> closure) {
        return performIfConditionIs(closure, FALSE);
    }

    public <RR extends R> OneArgumentCheckResult<T, RR> isTrueMap(Function<T, RR> function) {
        return performIfConditionIs(function, TRUE);
    }

    public <RR extends R> OneArgumentCheckResult<T, RR> isFalseMap(Function<T, RR> function) {
        return performIfConditionIs(function, FALSE);
    }

    private OneArgumentCheckResult<T, R> performIfConditionIs(Consumer<T> closure,
                                                              ConditionResult expectedConditionValue) {
        if (needToExecuteCondition()) {
            requireNonNull(closure);
            if (expectedConditionValue == performCheck()) {
                conditionResult = EXECUTED;
                closure.accept(arg);
            }
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    private <RR extends R> OneArgumentCheckResult<T, RR> performIfConditionIs(Function<T, RR> function,
                                                                              ConditionResult expectedConditionValue) {
        if (needToExecuteCondition()) {
            requireNonNull(function);
            if (expectedConditionValue == performCheck()) {
                conditionResult = EXECUTED;
                valueToReturn = function.apply(arg);
            }
        }
        return (OneArgumentCheckResult<T, RR>) this;
    }

    @Override
    protected ConditionResult performCheck() {
        if (conditionResult != NONE) {
            return conditionResult;
        }
        try {
            return conditionResult = getCondition().test(arg) ? TRUE : FALSE;
        } catch (Exception e) {
            exception = e;
            return conditionResult = EXCEPTION;
        }
    }


}