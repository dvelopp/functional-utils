package com.dvelopp.functional.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.dvelopp.functional.utils.ObjectUtils.requireNonNull;

public class ObjectUtilsTest {

    @Rule public ExpectedException expectedException = ExpectedException.none();

    private final String TEST_EXCEPTION_MESSAGE = "message";
    private final Object NOT_NULL_OBJECT = new Object();
    private final Object NULL_OBJECT = null;


    @Test
    public void requireNonNull_TwoNotNullObjects_Success() {
        requireNonNull(NOT_NULL_OBJECT, NOT_NULL_OBJECT);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void requireNonNull_TwoNullObjects_NPEHasBeenThrown() {
        expectedException.expect(NullPointerException.class);

        requireNonNull(NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNull_OneNullObjectAndOneNotNullObject_NPEHasBeenThrown() {
        expectedException.expect(NullPointerException.class);

        requireNonNull(NOT_NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNull_OneNullObjectAndOneNotNullObjectReversed_NPEHasBeenThrown() {
        expectedException.expect(NullPointerException.class);

        requireNonNull(NULL_OBJECT, NOT_NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessage_TwoNotNullObjects_Success() {
        requireNonNull(TEST_EXCEPTION_MESSAGE, NOT_NULL_OBJECT, NOT_NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessage_TwoNullObjects_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(TEST_EXCEPTION_MESSAGE, NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessage_OneNullObjectAndOneNotNullObject_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(TEST_EXCEPTION_MESSAGE, NOT_NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessage_OneNullObjectAndOneNotNullObjectReversed_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(TEST_EXCEPTION_MESSAGE, NOT_NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessageSupplier_TwoNotNullObjects_Success() {
        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, NOT_NULL_OBJECT, NOT_NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessageSupplier_TwoNullObjects_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessageSupplier_OneNullObjectAndOneNotNullObject_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, NOT_NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessageSupplier_OneNullObjectAndOneNotNullObjectReversed_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, NULL_OBJECT, NOT_NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessageSupplier_IllegalArgumentExceptionInSupplier_IllegalArgumentExceptionHasBeenThrown() {
        expectedException.expect(IllegalArgumentException.class);

        requireNonNull(() -> {
            throw new IllegalArgumentException();
        }, NULL_OBJECT, NOT_NULL_OBJECT);
    }
}