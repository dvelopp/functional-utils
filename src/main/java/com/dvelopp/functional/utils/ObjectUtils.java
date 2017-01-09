package com.dvelopp.functional.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;

public final class ObjectUtils {

    /**
     * Copy of original method so as not to have problems with static import ambiguity.
     */
    public static <T> T requireNonNull(T obj) {
        return Objects.requireNonNull(obj);
    }

    /**
     * Copy of original method so as not to have problems with static import ambiguity.
     */
    public static <T> T requireNonNull(T obj, String message) {
        return Objects.requireNonNull(obj, message);
    }

    /**
     * Copy of original method so as not to have problems with static import ambiguity.
     */
    public static <T> T requireNonNull(T obj, Supplier<String> messageSupplier) {
        return Objects.requireNonNull(obj, messageSupplier);
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> T[] requireNonNull(T firstObjectToCheck, T... otherObjectsToCheck) {
        List<T> allObjects = new ArrayList<>();
        allObjects.add(firstObjectToCheck);
        if (otherObjectsToCheck != null && otherObjectsToCheck.length > 0) {
            allObjects.addAll(asList(otherObjectsToCheck));
        }
        requireNonNull(allObjects);
        return (T[]) allObjects.stream().map(identity()).toArray(Object[]::new);
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
