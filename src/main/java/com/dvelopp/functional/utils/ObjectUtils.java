package com.dvelopp.functional.utils;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ObjectUtils {

    public static <T> T[] requireNonNull(T... objectsToCheck) {
        Stream.of(objectsToCheck).forEach(Objects::requireNonNull);
        return objectsToCheck;
    }

    public static <T> T[] requireNonNull(String message, T... objectsToCheck) {
        Stream.of(objectsToCheck).forEach(o -> Objects.requireNonNull(o, message));
        return objectsToCheck;
    }

    public static <T> T[] requireNonNull(Supplier<String> messageSupplier, T... objectsToCheck) {
        Stream.of(objectsToCheck).forEach(o -> Objects.requireNonNull(o, messageSupplier));
        return objectsToCheck;
    }
}
