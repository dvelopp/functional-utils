package com.dvelopp.functional.utils;

import java.util.*;
import java.util.function.Supplier;

import static com.dvelopp.functional.utils.FunctionUtils.with;
import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

/**
 * Provides utils to simplify working with the objects.
 *
 * @since 1.0
 */
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

    public static <T> List<T> requireNonNull(Map<T, String> notNullCheckMap) {
        for (Map.Entry<T, String> entry : notNullCheckMap.entrySet()) {
            Objects.requireNonNull(entry.getKey(), entry.getValue());
        }
        return notNullCheckMap.keySet().stream().collect(toList());
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> T[] requireNonNull(T firstObjectToCheck, T... otherObjectsToCheck) {
        List<T> allObjects = with(new ArrayList<>(), it -> it.add(firstObjectToCheck));
        if (otherObjectsToCheck != null && otherObjectsToCheck.length > 0) {
            allObjects.addAll(asList(otherObjectsToCheck));
        }
        return (T[]) requireNonNull(allObjects).stream().map(identity()).toArray(Object[]::new);
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
