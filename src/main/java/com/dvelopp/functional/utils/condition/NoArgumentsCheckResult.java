package com.dvelopp.functional.utils.condition;

import java.util.function.Supplier;

public class NoArgumentsCheckResult<R> extends AbstractCheckResult<Supplier<Boolean>, R, NoArgumentsCheckResult<R>> {

    NoArgumentsCheckResult(Supplier<Boolean> condition) {
        setCondition(condition);
    }

    @Override
    protected Boolean performCheck() {
        return getCondition().get();
    }

}