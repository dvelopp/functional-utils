package com.dvelopp.functional.utils.condition;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.dvelopp.functional.utils.ObjectUtils.requireNonNull;

/**
 * Utility class to build readable builder-based conditions using java 8 features like predicates/suppliers etc.
 * @since 1.0
 */
public final class CheckUtils {

    private CheckUtils() {
        throw new UnsupportedOperationException();
    }

    public static <R> NoArgumentsCheckResult<R> inCase(Supplier<Boolean> condition) {
        return new NoArgumentsCheckResult<>(requireNonNull(condition));
    }

    public static <R> NoArgumentsCheckResult<R> inCase(Boolean condition) {
        return new NoArgumentsCheckResult<>(() -> requireNonNull(condition));
    }

    public static <T, R> OneArgumentCheckResult<T, R> inCase(Predicate<T> condition, T arg) {
        return new OneArgumentCheckResult<>(requireNonNull(condition), arg);
    }

}
