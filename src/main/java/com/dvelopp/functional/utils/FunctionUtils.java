package com.dvelopp.functional.utils;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.function.*;
import java.util.stream.Stream;

import static com.dvelopp.functional.utils.ObjectUtils.requireNonNull;

/**
 * Contains utils connected to the java.util.function package.
 * Makes it easier to you the components of this package as well as extend its functionality.
 *
 * @since 1.0
 */
public final class FunctionUtils {

    private FunctionUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Negate given predicate.
     *
     * @param predicate The predicate to negated.
     * @param <T>       The predicate type.
     * @return The negated predicate.
     */
    public static <T> Predicate<T> not(Predicate<T> predicate) {
        requireNonNull(predicate);
        return predicate.negate();
    }

    /**
     * Execute closure on the object.
     *
     * @param self    The object to work with.
     * @param closure The actions to do on the object.
     * @param <T>     The object type.
     * @return The modified object.
     */
    public static <T> T with(T self, Consumer<T> closure) {
        requireNonNull(self, closure);
        closure.accept(self);
        return self;
    }

    /**
     * TODO Test it
     */
    public static <T> T with(T self, Consumer<T>... closures) {
        requireNonNull(self, closures);
        if(closures.length == 0){
            throw new NullPointerException();
        }
        Stream.of(closures).forEach(closure -> closure.accept(self));
        return self;
    }

    public static Supplier<Boolean> trueSupplier() {
        return () -> true;
    }

    public static Supplier<Boolean> falseSupplier() {
        return () -> false;
    }

    public static <T> Supplier<T> nullSuppler() {
        return identitySupplier(null);
    }

    public static <T> Supplier<T> identitySupplier(T objectToSupply) {
        return () -> objectToSupply;
    }

    public static Predicate<Boolean> identityPredicate() {
        return o -> o;
    }

    public static <T> Predicate<T> truePredicate() {
        return o -> true;
    }

    public static <T> Predicate<T> falsePredicate() {
        return o -> false;
    }

    public static Runnable emptyRunnable() {
        return () -> {
        };
    }

    public static Supplier<Boolean> exceptionSupplier(Supplier<RuntimeException> exceptionSupplier) {
        requireNonNull(exceptionSupplier);
        return () -> {
            throw exceptionSupplier.get();
        };
    }

    public static <T> Supplier<T> exceptionSupplier() {
        return () -> {
            throwAnExceptionForExceptionalCasesOfFunctionalInterfaces();
            return null;
        };
    }

    public static <T> Consumer<T> exceptionConsumer() {
        return o -> throwAnExceptionForExceptionalCasesOfFunctionalInterfaces();
    }

    public static <T> Predicate<T> exceptionPredicate() {
        return o -> throwAnExceptionForExceptionalCasesOfFunctionalInterfaces();
    }

    public static <T, R> Function<T, R> exceptionFunction() {
        return o -> {
            throwAnExceptionForExceptionalCasesOfFunctionalInterfaces();
            return null;
        };
    }

    public static Runnable exceptionRunnable() {
        return FunctionUtils::throwAnExceptionForExceptionalCasesOfFunctionalInterfaces;
    }

    public static <T> Predicate<T> exceptionPredicate(Supplier<RuntimeException> exceptionSupplier) {
        requireNonNull(exceptionSupplier);
        return o -> {
            throw exceptionSupplier.get();
        };
    }

    public static <T> Consumer<T> emptyConsumer() {
        return o -> {
        };
    }

    private static boolean throwAnExceptionForExceptionalCasesOfFunctionalInterfaces() {
        throw new IllegalStateException("Exception from deliberately forcing exception predicate");
    }

    /**
     * Converts BiConsumer to Consumer.
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
     * Converts BiFunction to Function.
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
