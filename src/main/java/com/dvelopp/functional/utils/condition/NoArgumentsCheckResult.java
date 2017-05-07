package com.dvelopp.functional.utils.condition;

import java.util.function.Supplier;

import static com.dvelopp.functional.utils.condition.AbstractCheckResult.ConditionResult.EXCEPTION;
import static com.dvelopp.functional.utils.condition.AbstractCheckResult.ConditionResult.FALSE;
import static com.dvelopp.functional.utils.condition.AbstractCheckResult.ConditionResult.NONE;
import static com.dvelopp.functional.utils.condition.AbstractCheckResult.ConditionResult.TRUE;

/**
 * @since 1.0
 */
public class NoArgumentsCheckResult<R> extends AbstractCheckResult<Supplier<Boolean>, R, NoArgumentsCheckResult<R>> {

    NoArgumentsCheckResult(Supplier<Boolean> condition) {
        setCondition(condition);
    }

    /**
     * Performs selected check and sets conditionResult.
     *
     * @return conditionResult.
     */
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