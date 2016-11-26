package com.dvelopp.functional.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.dvelopp.functional.utils.ObjectUtils.requireNonNull;

public class ObjectUtilsTest {

    @Rule public ExpectedException expectedException = ExpectedException.none();

    private final String TEST_EXCEPTION_MESSAGE = "message";

    @Test
    public void requireNonNull_TwoNotNullObjects_Success() {
        Object o1 = new Object();
        Object o2 = new Object();

        requireNonNull(o1, o2);
    }

    @Test
    public void requireNonNull_TwoNullObjects_NPEHasBeenThrown() {
        Object o1 = null;
        Object o2 = null;
        expectedException.expect(NullPointerException.class);

        requireNonNull(o1, o2);
    }

    @Test
    public void requireNonNull_OneNullObjectAndOneNotNullObject_NPEHasBeenThrown() {
        Object o1 = new Object();
        Object o2 = null;
        expectedException.expect(NullPointerException.class);

        requireNonNull(o1, o2);
    }

    @Test
    public void requireNonNull_OneNullObjectAndOneNotNullObjectReversed_NPEHasBeenThrown() {
        Object o1 = null;
        Object o2 = new Object();
        expectedException.expect(NullPointerException.class);

        requireNonNull(o1, o2);
    }

    @Test
    public void requireNonNullWithMessage_TwoNotNullObjects_Success() {
        Object o1 = new Object();
        Object o2 = new Object();

        requireNonNull(TEST_EXCEPTION_MESSAGE, o1, o2);
    }

    @Test
    public void requireNonNullWithMessage_TwoNullObjects_NPEHasBeenThrownWithTestMessage() {
        Object o1 = null;
        Object o2 = null;
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(TEST_EXCEPTION_MESSAGE, o1, o2);
    }

    @Test
    public void requireNonNullWithMessage_OneNullObjectAndOneNotNullObject_NPEHasBeenThrownWithTestMessage() {
        Object o1 = new Object();
        Object o2 = null;
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(TEST_EXCEPTION_MESSAGE, o1, o2);
    }

    @Test
    public void requireNonNullWithMessage_OneNullObjectAndOneNotNullObjectReversed_NPEHasBeenThrownWithTestMessage() {
        Object o1 = null;
        Object o2 = new Object();
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(TEST_EXCEPTION_MESSAGE, o1, o2);
    }

    @Test
    public void requireNonNullWithMessageSupplier_TwoNotNullObjects_Success() {
        Object o1 = new Object();
        Object o2 = new Object();

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, o1, o2);
    }

    @Test
    public void requireNonNullWithMessageSupplier_TwoNullObjects_NPEHasBeenThrownWithTestMessage() {
        Object o1 = null;
        Object o2 = null;
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, o1, o2);
    }

    @Test
    public void requireNonNullWithMessageSupplier_OneNullObjectAndOneNotNullObject_NPEHasBeenThrownWithTestMessage() {
        Object o1 = new Object();
        Object o2 = null;
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, o1, o2);
    }

    @Test
    public void requireNonNullWithMessageSupplier_OneNullObjectAndOneNotNullObjectReversed_NPEHasBeenThrownWithTestMessage() {
        Object o1 = null;
        Object o2 = new Object();
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, o1, o2);
    }

    @Test
    public void requireNonNullWithMessageSupplier_IllegalArgumentExceptionInSupplier_IllegalArgumentExceptionHasBeenThrown() {
        Object o1 = null;
        Object o2 = new Object();
        expectedException.expect(IllegalArgumentException.class);

        requireNonNull(() -> {
            throw new IllegalArgumentException();
        }, o1, o2);
    }
}