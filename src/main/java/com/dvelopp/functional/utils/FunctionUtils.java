package com.dvelopp.functional.utils;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.dvelopp.functional.utils.ObjectUtils.requireNonNull;
import static java.util.Objects.requireNonNull;

public final class FunctionUtils {

    /**
     * Negate given predicate
     *
     * @param predicate given predicate value to negate
     * @param <T>       predicate type
     * @return negated predicate
     */
    public static <T> Predicate<T> not(Predicate<T> predicate) {
        requireNonNull(predicate);
        return t -> !predicate.test(t);
    }

    /**
     * Execute closure on the object
     *
     * @param self    - object to work with
     * @param closure - actions to do on the object
     * @param <T>     object type
     * @return modified object
     */
    public static <T> T with(T self, Consumer<T> closure) {
        requireNonNull(self, closure);
        closure.accept(self);
        return self;
    }

    public static Supplier<Boolean> trueSupplier() {
        return () -> true;
    }

    public static Supplier<Boolean> falseSupplier() {
        return () -> false;
    }

    public static Predicate<Boolean> identityPredicate() {
        return o -> o;
    }

    public static Predicate<Boolean> truePredicate() {
        return o -> true;
    }

    public static Predicate<Boolean> falsePredicate() {
        return o -> false;
    }

}
