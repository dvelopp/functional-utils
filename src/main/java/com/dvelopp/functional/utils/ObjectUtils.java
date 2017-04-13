package com.dvelopp.functional.utils;

import java.util.*;
import java.util.function.Supplier;

import static java.util.Arrays.asList;

/**
 * Provides utils to simplify working with the objects.
 *
 * @since 1.0
 */
public final class ObjectUtils {

    private ObjectUtils() {
        throw new UnsupportedOperationException();
    }

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

    public static <T> List<T> requireNonNull(Map<T, String> notNullCheckMap) {
        for (Map.Entry<T, String> entry : notNullCheckMap.entrySet()) {
            Objects.requireNonNull(entry.getKey(), entry.getValue());
        }
        return new ArrayList<>(notNullCheckMap.keySet());
    }

    /**
     * Checks that the specified objects are not {@code null}.
     *
     * @param firstObjectToCheck  First or just one object to check.
     * @param otherObjectsToCheck Other objects to check.
     * @param <T>                 The type of the reference.
     * @return array of the checked entries.
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> T[] requireNonNull(T firstObjectToCheck, T... otherObjectsToCheck) {
        ArrayList<T> allObjects = new ArrayList<>();
        allObjects.add(firstObjectToCheck);
        if (otherObjectsToCheck != null && otherObjectsToCheck.length > 0) {
            allObjects.addAll(asList(otherObjectsToCheck));
        }
        return (T[]) requireNonNull(allObjects).toArray();
    }

    /**
     * Checks that the specified objects are not {@code null}.
     *
     * @param message        The exception message in case at least on object is null.
     * @param objectsToCheck The objects to check.
     * @param <T>            The type of the reference.
     * @return array of the checked entries.
     */
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
