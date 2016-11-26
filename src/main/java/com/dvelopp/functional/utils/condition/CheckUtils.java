package com.dvelopp.functional.utils.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Utility class to build readable builder-based conditions using java 8 features
 */
public final class CheckUtils {

    public static <T, R> OneArgumentCheckResult<T, R> inCase(Predicate<T> condition, T arg) {
        return new OneArgumentCheckResult<>(condition, arg);
    }

    public static NoArgumentsCheckResult inCase(Supplier<Boolean> condition) {
        return new NoArgumentsCheckResult(condition);
    }

    public static NoArgumentsCheckResult inCase(Boolean condition) {
        return new NoArgumentsCheckResult(() -> condition);
    }

}
