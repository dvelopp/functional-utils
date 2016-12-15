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
        return o -> {
        };
    }

    /**
     * Converts BiConsumer to Consumer
     *
     * @param action The BiConsumer action to be converted to consumer.
     * @param arg    The second argument for BiConsumer.
     * @param <T>    The first argument type.
     * @param <R>    The second argument type.
     * @return The new consumer object based on the BiConsumer and the argument that are passed. Argument is used
     * in the BiConsumer method invocation.
     */
    public static <T, R> Consumer<T> consumer(BiConsumer<T, R> action, R arg) {
        requireNonNull(action);
        return e -> action.accept(e, arg);
    }

    /**
     * Converts BiFunction to Function
     *
     * @param action The BiFunction to be converted to a function.
     * @param arg    The second argument for BiFunction.
     * @param <T>    The first argument type.
     * @param <S>    The second argument type.
     * @param <R>    The return type.
     * @return The new function object based on the BiFunction and the argument that are passed. Argument is used
     * in BiFunction method invocation.
     */
    public static <T, R, S> Function<T, R> function(BiFunction<T, S, R> action, S arg) {
        requireNonNull(action);
        return e -> action.apply(e, arg);
    }

}
