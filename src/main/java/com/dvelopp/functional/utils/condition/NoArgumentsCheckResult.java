package com.dvelopp.functional.utils.condition;

import java.util.function.Supplier;

import static com.dvelopp.functional.utils.condition.AbstractCheckResult.ConditionResult.*;

public class NoArgumentsCheckResult<R> extends AbstractCheckResult<Supplier<Boolean>, R, NoArgumentsCheckResult<R>> {

    NoArgumentsCheckResult(Supplier<Boolean> condition) {
        setCondition(condition);
    }

    @Override
    protected ConditionResult performCheck() {
        if (conditionResult != NONE) {
            return conditionResult;
        }
        try {
            return conditionResult = getCondition().get() ? TRUE : FALSE;
        } catch (Exception e) {
            exception = e;
            return conditionResult = EXCEPTION;
        }
    }

}