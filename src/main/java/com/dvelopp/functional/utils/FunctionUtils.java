package com.dvelopp.functional.utils;

import java.util.function.*;

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
        return predicate.negate();
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

    public static <T> Consumer<T> emptyConsumer() {
        return o -> {};
    }

    /**
     * Converts BiConsumer to Consumer
     *
     * @param action BiConsumer action to be converted to consumer
     * @param arg    second argument for BiConsumer
     * @param <T>    first argument type
     * @param <R>    second argument type
     * @return Consumer object
     */
    public static <T, R> Consumer<T> consumer(BiConsumer<T, R> action, R arg) {
        return e -> action.accept(e, arg);
    }

    /**
     * Converts BiFunction to Function
     *
     * @param action BiConsumer action to be converted to consumer
     * @param arg    second argument for BiFunction
     * @param <T>    first argument type
     * @param <R>    second argument type
     * @return Function object
     */
    public static <T, R, S> Function<T, R> function(BiFunction<T, S, R> action, S arg) {
        return e -> action.apply(e, arg);
    }

}
