package com.dvelopp.functional.utils;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import static java.util.Arrays.asList;

public class ObjectUtils {

    public static <T> T requireNonNull(T obj) {
        return Objects.requireNonNull(obj);
    }

    public static <T> T requireNonNull(T obj, String message) {
        return Objects.requireNonNull(obj, message);
    }

    public static <T> T requireNonNull(T obj, Supplier<String> messageSupplier) {
        return Objects.requireNonNull(obj, messageSupplier);
    }

    @SafeVarargs
    public static <T> T[] requireNonNull(T... objectsToCheck) {
        requireNonNull(asList(objectsToCheck));
        return objectsToCheck;
    }

    @SafeVarargs
    public static <T> T[] requireNonNull(String message, T... objectsToCheck) {
        requireNonNull(message, asList(objectsToCheck));
        return objectsToCheck;
    }

    @SafeVarargs
    public static <T> T[] requireNonNull(Supplier<String> messageSupplier, T... objectsToCheck) {
        requireNonNull(messageSupplier, asList(objectsToCheck));
        return objectsToCheck;
    }

    public static <T> Collection<T> requireNonNull(Collection<T> objectsToCheck) {
        Objects.requireNonNull(objectsToCheck).forEach(Objects::requireNonNull);
        return objectsToCheck;
    }

    public static <T> Collection<T> requireNonNull(String message, Collection<T> objectsToCheck) {
        Objects.requireNonNull(objectsToCheck).forEach(o -> Objects.requireNonNull(o, message));
        return objectsToCheck;
    }

    public static <T> Collection<T> requireNonNull(Supplier<String> messageSupplier, Collection<T> objectsToCheck) {
        Objects.requireNonNull(objectsToCheck).forEach(o -> Objects.requireNonNull(o, messageSupplier));
        return objectsToCheck;
    }

}
