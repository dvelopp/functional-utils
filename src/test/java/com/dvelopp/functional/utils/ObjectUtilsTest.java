package com.dvelopp.functional.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.dvelopp.functional.utils.FunctionUtils.exceptionSupplier;
import static com.dvelopp.functional.utils.ObjectUtils.requireNonNull;
import static java.util.Arrays.asList;

public class ObjectUtilsTest {

    @Rule public ExpectedException expectedException = ExpectedException.none();

    private final String TEST_EXCEPTION_MESSAGE = "message";
    private final Object NOT_NULL_OBJECT = new Object();
    private final Object NULL_OBJECT = null;

    @Test
    public void requireNonNull_ArrayCaseTwoNotNullObjects_Success() {
        requireNonNull(NOT_NULL_OBJECT, NOT_NULL_OBJECT);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void requireNonNull_ArrayCaseTwoNullObjects_NPEHasBeenThrown() {
        expectedException.expect(NullPointerException.class);

        requireNonNull(NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNull_ArrayCaseOneNullObjectAndOneNotNullObject_NPEHasBeenThrown() {
        expectedException.expect(NullPointerException.class);

        requireNonNull(NOT_NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNull_ArrayCaseOneNullObjectAndOneNotNullObjectReversed_NPEHasBeenThrown() {
        expectedException.expect(NullPointerException.class);

        requireNonNull(NULL_OBJECT, NOT_NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessage_ArrayCaseTwoNotNullObjects_Success() {
        requireNonNull(TEST_EXCEPTION_MESSAGE, NOT_NULL_OBJECT, NOT_NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessage_ArrayCaseTwoNullObjects_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(TEST_EXCEPTION_MESSAGE, NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessage_ArrayCaseOneNullObjectAndOneNotNullObject_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(TEST_EXCEPTION_MESSAGE, NOT_NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessage_ArrayCaseOneNullObjectAndOneNotNullObjectReversed_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(TEST_EXCEPTION_MESSAGE, NOT_NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessageSupplier_ArrayCaseTwoNotNullObjects_Success() {
        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, NOT_NULL_OBJECT, NOT_NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessageSupplier_ArrayCaseTwoNullObjects_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessageSupplier_ArrayCaseOneNullObjectAndOneNotNullObject_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, NOT_NULL_OBJECT, NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessageSupplier_ArrayCaseOneNullObjectAndOneNotNullObjectReversed_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, NULL_OBJECT, NOT_NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessageSupplier_ArrayCaseIllegalStateExceptionInSupplier_IllegalStateExceptionHasBeenThrown() {
        expectedException.expect(IllegalStateException.class);

        requireNonNull(exceptionSupplier(), NULL_OBJECT, NOT_NULL_OBJECT);
    }

    @Test
    public void requireNonNull_CollectionCaseTwoNotNullObjects_Success() {
        requireNonNull(asList(NOT_NULL_OBJECT, NOT_NULL_OBJECT));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void requireNonNull_CollectionCaseTwoNullObjects_NPEHasBeenThrown() {
        expectedException.expect(NullPointerException.class);

        requireNonNull(asList(NULL_OBJECT, NULL_OBJECT));
    }

    @Test
    public void requireNonNull_CollectionCaseOneNullObjectAndOneNotNullObject_NPEHasBeenThrown() {
        expectedException.expect(NullPointerException.class);

        requireNonNull(asList(NOT_NULL_OBJECT, NULL_OBJECT));
    }

    @Test
    public void requireNonNull_CollectionCaseOneNullObjectAndOneNotNullObjectReversed_NPEHasBeenThrown() {
        expectedException.expect(NullPointerException.class);

        requireNonNull(asList(NULL_OBJECT, NOT_NULL_OBJECT));
    }

    @Test
    public void requireNonNullWithMessage_CollectionCaseTwoNotNullObjects_Success() {
        requireNonNull(TEST_EXCEPTION_MESSAGE, asList(NOT_NULL_OBJECT, NOT_NULL_OBJECT));
    }

    @Test
    public void requireNonNullWithMessage_CollectionCaseTwoNullObjects_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(TEST_EXCEPTION_MESSAGE, asList(NULL_OBJECT, NULL_OBJECT));
    }

    @Test
    public void requireNonNullWithMessage_CollectionCaseOneNullObjectAndOneNotNullObject_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(TEST_EXCEPTION_MESSAGE, asList(NOT_NULL_OBJECT, NULL_OBJECT));
    }

    @Test
    public void requireNonNullWithMessage_CollectionCaseOneNullObjectAndOneNotNullObjectReversed_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(TEST_EXCEPTION_MESSAGE, asList(NOT_NULL_OBJECT, NULL_OBJECT));
    }

    @Test
    public void requireNonNullWithMessageSupplier_CollectionCaseTwoNotNullObjects_Success() {
        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, asList(NOT_NULL_OBJECT, NOT_NULL_OBJECT));
    }

    @Test
    public void requireNonNullWithMessageSupplier_CollectionCaseTwoNullObjects_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, asList(NULL_OBJECT, NULL_OBJECT));
    }

    @Test
    public void requireNonNullWithMessageSupplier_CollectionCaseOneNullObjectAndOneNotNullObject_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, asList(NOT_NULL_OBJECT, NULL_OBJECT));
    }

    @Test
    public void requireNonNullWithMessageSupplier_CollectionCaseOneNullObjectAndOneNotNullObjectReversed_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(() -> TEST_EXCEPTION_MESSAGE, asList(NULL_OBJECT, NOT_NULL_OBJECT));
    }

    @Test
    public void requireNonNullWithMessageSupplier_CollectionCaseIllegalStateExceptionInSupplier_IllegalStateExceptionHasBeenThrown() {
        expectedException.expect(IllegalStateException.class);
        requireNonNull(new Object());
        requireNonNull(exceptionSupplier(), asList(NULL_OBJECT, NOT_NULL_OBJECT));
    }

    @Test
    public void requireNonNull_SingleObjectCaseNotNullObject_Success() {
        requireNonNull(NOT_NULL_OBJECT);
    }

    @Test
    public void requireNonNull_SingleObjectCaseNullObject_NPEHasBeenThrown() {
        expectedException.expect(NullPointerException.class);

        requireNonNull(NULL_OBJECT);
    }

    @Test
    public void requireNonNullWithMessage_SingleObjectCaseNotNullObject_Success() {
        requireNonNull(NOT_NULL_OBJECT, TEST_EXCEPTION_MESSAGE);
    }

    @Test
    public void requireNonNullWithMessage_SingleObjectCaseNullObject_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(NULL_OBJECT, TEST_EXCEPTION_MESSAGE);
    }

    @Test
    public void requireNonNullWithMessageSupplier_SingleObjectCaseNotNullObject_Success() {
        requireNonNull(NOT_NULL_OBJECT, () -> TEST_EXCEPTION_MESSAGE);
    }

    @Test
    public void requireNonNullWithMessageSupplier_SingleObjectCaseNullObject_NPEHasBeenThrownWithTestMessage() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage(TEST_EXCEPTION_MESSAGE);

        requireNonNull(NULL_OBJECT, () -> TEST_EXCEPTION_MESSAGE);
    }

    @Test
    public void requireNonNullWithMessageSupplier_SingleObjectCaseIllegalArgumentExceptionInSupplier_IllegalStateExceptionHasBeenThrown() {
        expectedException.expect(IllegalStateException.class);

        requireNonNull(NULL_OBJECT, exceptionSupplier());
    }
}