package com.dvelopp.functional.utils.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.dvelopp.functional.utils.ObjectUtils.requireNonNull;

/**
 * Utility class to build readable builder-based conditions using java 8 features like predicates/suppliers etc.
 */
public final class CheckUtils {

    public static <R> NoArgumentsCheckResult<R> inCase(Supplier<Boolean> condition) {
        requireNonNull(condition);
        return new NoArgumentsCheckResult<>(condition);
    }

    public static <R> NoArgumentsCheckResult<R> inCase(Boolean condition) {
        requireNonNull(condition);
        return new NoArgumentsCheckResult<>(() -> condition);
    }

    public static <T, R> OneArgumentCheckResult<T, R> inCase(Predicate<T> condition, T arg) {
        requireNonNull(condition);
        return new OneArgumentCheckResult<>(condition, arg);
    }

    public static <R> NoArgumentsCheckResult<R> when(Supplier<Boolean> condition) {
        return inCase(condition);
    }

    public static <R> NoArgumentsCheckResult<R> when(Boolean condition) {
        return inCase(condition);
    }

    public static <T, R> OneArgumentCheckResult<T, R> when(Predicate<T> condition, T arg) {
        return inCase(condition, arg);
    }

}
