package com.dvelopp.functional.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Supplier;

import static com.dvelopp.functional.utils.FunctionUtils.*;
import static com.dvelopp.functional.utils.condition.CheckUtils.inCase;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CheckUtilsTest {

    @Mock private Testee testee;

    @Test
    public void inCaseIsTrue_TrueCaseForSupplier_ClosureHasBeenExecuted() {
        inCase(trueSupplier()).isTrue(this::closureWithoutReturn);

        assertClosureWasExecuted();
    }

    @Test
    public void inCaseIsTrue_FalseCaseForSupplier_ClosureHasBeenExecuted() {
        inCase(falseSupplier()).isTrue(this::closureWithoutReturn);

        assertClosureWasNotExecuted();
    }

    @Test
    public void inCaseIsTrue_ExceptionInTheConditionCaseForSupplier_ClosureForExceptionCaseHasBeenExecuted() {
        inCase(() -> {
            throw new IllegalArgumentException();
        }).isException(this::closureWithoutReturn);

        assertClosureWasExecuted();
    }

    @Test(expected = IllegalArgumentException.class)
    public void inCaseIsTrue_ExceptionInTheClosureCaseForSupplier_IllegalArgumentExceptionHasBeenThrown() {
        inCase(trueSupplier()).isTrue(() -> {
            throw new IllegalArgumentException();
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void inCaseIsTrue_ExceptionInTheClosureCaseForFunction_IllegalArgumentExceptionHasBeenThrown() {
        inCase(identityPredicate(), true).isTrueMap((o) -> {
            throw new IllegalArgumentException();
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void inCaseIsTrue_ExceptionInTheClosureCaseForConsumer_IllegalArgumentExceptionHasBeenThrown() {
        inCase(identityPredicate(), true).isTrue((o) -> {
            throw new IllegalArgumentException();
        });
    }

    @Test
    public void inCaseIsFalse_TrueCaseForSupplier_ClosureHasBeenExecuted() {
        inCase(trueSupplier()).isFalse(this::closureWithoutReturn);

        assertClosureWasNotExecuted();
    }

    @Test
    public void inCaseIsFalse_FalseCaseForSupplier_ClosureHasBeenExecuted() {
        inCase(falseSupplier()).isFalse(this::closureWithoutReturn);

        assertClosureWasExecuted();
    }

    @Test
    public void inCaseIsFalse_ExceptionInTheConditionCaseForSupplier_ClosureForExceptionCaseHasBeenExecuted() {
        inCase(() -> {
            throw new IllegalArgumentException();
        }).isException(this::closureWithoutReturn);

        assertClosureWasExecuted();
    }

    @Test(expected = IllegalArgumentException.class)
    public void inCaseIsFalse_ExceptionInTheClosureCaseForSupplier_IllegalArgumentExceptionHasBeenThrown() {
        inCase(falseSupplier()).isFalse(() -> {
            throw new IllegalArgumentException();
        });
    }

    @Test
    public void inCaseIsTrue_TrueCaseForBoolean_ClosureHasBeenExecuted() {
        inCase(true).isTrue(this::closureWithoutReturn);

        assertClosureWasExecuted();
    }

    @Test
    public void inCaseIsTrue_FalseCaseForBoolean_ClosureHasBeenExecuted() {
        inCase(false).isTrue(this::closureWithoutReturn);

        assertClosureWasNotExecuted();
    }

    @Test
    public void inCaseIsFalse_TrueCaseForBoolean_ClosureHasBeenExecuted() {
        inCase(trueSupplier()).isFalse(this::closureWithoutReturn);

        assertClosureWasNotExecuted();
    }

    @Test
    public void inCaseIsFalse_FalseCaseForBoolean_ClosureHasBeenExecuted() {
        inCase(falseSupplier()).isFalse(this::closureWithoutReturn);

        assertClosureWasExecuted();
    }

    @Test
    public void inCaseIsTrue_TrueCaseForPredicate_ConsumerClosureHasBeenExecuted() {
        inCase(identityPredicate(), true).isTrue((o) -> closureWithoutReturn());

        assertClosureWasExecuted();
    }

    @Test
    public void inCaseIsTrue_TrueCaseForPredicate_RunnableClosureHasBeenExecuted() {
        inCase(identityPredicate(), true).isTrue(this::closureWithoutReturn);

        assertClosureWasExecuted();
    }

    @Test
    public void inCaseIsTrue_FalseCaseForPredicate_ConsumerClosureHasNotBeenExecuted() {
        inCase(identityPredicate(), false).isTrue((o) -> closureWithoutReturn());

        assertClosureWasNotExecuted();
    }

    @Test
    public void inCaseIsTrue_FalseCaseForPredicate_RunnableClosureHasNotBeenExecuted() {
        inCase(identityPredicate(), false).isTrue(this::closureWithoutReturn);

        assertClosureWasNotExecuted();
    }

    @Test
    public void inCaseIsTrue_TrueCaseForSupplier_SupplierHasBeenExecutedAndReturnedExpectedVal() {
        Integer expectedVal = 5;

        Integer actualVal = inCase(identityPredicate(), true).isTrueGet(() -> closureWithReturn(expectedVal)).value();

        assertClosureWasExecuted();
        assertThat(actualVal).isEqualTo(expectedVal);
    }

    @Test
    public void inCaseIsTrue_FalseCaseForSupplier_SupplierHasNotBeenExecutedAndReturnedNull() {
        Integer actualVal = inCase(identityPredicate(), false).isTrueGet(() -> closureWithReturn(5)).value();

        assertClosureWasNotExecuted();
        assertThat(actualVal).isNull();
    }

    @Test
    public void inCaseIsFalse_TrueCaseForSupplier_SupplierHasNotBeenExecutedAndReturnedNull() {
        Integer actualVal = inCase(identityPredicate(), true).isFalseGet(() -> closureWithReturn(5)).value();

        assertClosureWasNotExecuted();
        assertThat(actualVal).isNull();
    }

    @Test
    public void inCaseIsFalse_FalseCaseForSupplier_SupplierHasBeenExecutedAndReturnedExpectedVal() {
        Integer expectedVal = 5;

        Integer actualVal = inCase(identityPredicate(), false).isFalseGet(() -> closureWithReturn(5)).value();

        assertClosureWasExecuted();
        assertThat(actualVal).isEqualTo(expectedVal);
    }

    @Test
    public void inCaseIsTrueOrFalse_TrueCaseForSupplier_SupplierHasBeenExecutedAndReturnedExpectedDefinedValForTrue() {
        Integer expectedValInTrueDefinition = 1;
        Integer expectedValInFalseDefinition = 2;

        Integer actualVal = inCase(identityPredicate(), true)
                .isTrueGet(() -> closureWithReturn(expectedValInTrueDefinition))
                .isFalseGet(() -> closureWithReturn(expectedValInFalseDefinition))
                .value();

        assertClosureWasExecuted();
        assertThat(actualVal).isEqualTo(expectedValInTrueDefinition);
    }

    @Test
    public void inCaseIsTrueOrFalseOrException_ExceptionCaseAndClosureInside_ClosureHasBeenExecutedForExceptionCase() {
        inCase(()-> {throw new IllegalArgumentException();})
                .isTrue(() -> {})
                .isFalse(() -> {})
                .isException(this::closureWithoutReturn);

        assertClosureWasExecuted();
    }

    @Test
    public void inCaseIsTrueOrFalseOrException_ExceptionCaseAndClosureOutside_ClosureHasNotBeenExecutedForExceptionCase() {
        inCase(()-> {throw new IllegalArgumentException();})
                .isTrue(this::closureWithoutReturn)
                .isFalse(this::closureWithoutReturn)
                .isException(() -> {});

        assertClosureWasNotExecuted();
    }

    @Test
    public void inCaseIsTrueOrFalse_FalseCaseForSupplier_SupplierHasBeenExecutedAndReturnedExpectedDefinedValForFalse() {
        Integer expectedValInTrueDefinition = 1;
        Integer expectedValInFalseDefinition = 2;

        Integer actualVal = inCase(identityPredicate(), false)
                .isTrueGet(() -> closureWithReturn(expectedValInTrueDefinition))
                .isFalseGet(() -> closureWithReturn(expectedValInFalseDefinition))
                .value();

        assertClosureWasExecuted();
        assertThat(actualVal).isEqualTo(expectedValInFalseDefinition);
    }

    @Test
    public void inCaseIsTrue_TrueCaseForFunction_FunctionHasBeenExecutedAndReturnedExpectedVal() {
        Integer expectedVal = 5;

        Integer actualVal = inCase(identityPredicate(), true).isTrueMap(o -> closureWithReturn(expectedVal)).value();

        assertClosureWasExecuted();
        assertThat(actualVal).isEqualTo(expectedVal);
    }

    @Test
    public void inCaseIsTrue_FalseCaseForFunction_FunctionHasNotBeenExecutedAndReturnedNull() {
        Integer actualVal = inCase(identityPredicate(), false).isTrueMap(o -> closureWithReturn(5)).value();

        assertClosureWasNotExecuted();
        assertThat(actualVal).isNull();
    }

    @Test
    public void inCaseIsFalse_TrueCaseForFunction_FunctionHasNotBeenExecutedAndReturnedNull() {
        Integer actualVal = inCase(identityPredicate(), true).isFalseMap(o -> closureWithReturn(5)).value();

        assertClosureWasNotExecuted();
        assertThat(actualVal).isNull();
    }

    @Test
    public void inCaseIsFalse_FalseCaseForFunction_FunctionHasBeenExecutedAndReturnedExpectedVal() {
        Integer expectedVal = 5;

        Integer actualVal = inCase(identityPredicate(), false).isFalseMap(o -> closureWithReturn(5)).value();

        assertClosureWasExecuted();
        assertThat(actualVal).isEqualTo(expectedVal);
    }

    @Test
    public void inCaseIsTrueOrFalse_TrueCaseForFunction_FunctionHasBeenExecutedAndReturnedExpectedDefinedValForTrue() {
        Integer expectedValInTrueDefinition = 1;
        Integer expectedValInFalseDefinition = 2;

        Integer actualVal = inCase(identityPredicate(), true)
                .isTrueMap(o -> closureWithReturn(expectedValInTrueDefinition))
                .isFalseMap(o -> closureWithReturn(expectedValInFalseDefinition))
                .value();

        assertClosureWasExecuted();
        assertThat(actualVal).isEqualTo(expectedValInTrueDefinition);
    }

    @Test
    public void inCaseIsTrueOrFalse_FalseCaseForFunction_FunctionHasBeenExecutedAndReturnedExpectedDefinedValForFalse() {
        Integer expectedValInTrueDefinition = 1;
        Integer expectedValInFalseDefinition = 2;

        Integer actualVal = inCase(identityPredicate(), false)
                .isTrueMap(o -> closureWithReturn(expectedValInTrueDefinition))
                .isFalseMap(o -> closureWithReturn(expectedValInFalseDefinition))
                .value();

        assertClosureWasExecuted();
        assertThat(actualVal).isEqualTo(expectedValInFalseDefinition);
    }


    @Test
    public void inCaseIsTrue_TrueCaseForExactValue_ExpectedValHasBeenReturned() {
        Integer expectedVal = 5;

        Integer actualVal = inCase(identityPredicate(), true).isTrue(expectedVal).value();

        assertThat(actualVal).isEqualTo(expectedVal);
    }

    @Test
    public void inCaseIsTrue_FalseCaseForExactValue_NullHasBeenReturned() {
        Integer actualVal = inCase(identityPredicate(), false).isTrue(5).value();

        assertThat(actualVal).isNull();
    }

    @Test
    public void inCaseIsFalse_TrueCaseForExactValue_NullHasBeenReturned() {
        Integer actualVal = inCase(identityPredicate(), true).isFalse(5).value();

        assertThat(actualVal).isNull();
    }

    @Test
    public void inCaseIsFalse_FalseCaseForExactValue_ExpectedValHasBeenReturned() {
        Integer expectedVal = 5;

        Integer actualVal = inCase(identityPredicate(), false).isFalse(expectedVal).value();

        assertThat(actualVal).isEqualTo(expectedVal);
    }

    @Test
    public void inCaseIsTrueThreeTimes_TrueCaseForExactValue_FirstTrueValHasBeenReturned() {
        Integer first = 1;
        Integer second = 2;
        Integer third = 3;

        Integer actualVal = inCase(identityPredicate(), true)
                .isTrue(first)
                .isTrue(second)
                .isTrue(third)
                .value();

        assertThat(actualVal).isEqualTo(first);
    }

    @Test
    public void inCaseIsFalseThreeTimes_FalseCaseForExactValue_FirstFalseValHasBeenReturned() {
        Integer first = 1;
        Integer second = 2;
        Integer third = 3;

        Integer actualVal = inCase(identityPredicate(), false)
                .isFalse(first)
                .isFalse(second)
                .isFalse(third)
                .value();

        assertThat(actualVal).isEqualTo(first);
    }

    @Test
    public void inCaseIsTrueOrFalse_TrueCaseForExactValue_ExpectedDefinedValForTrueHasBeenReturned() {
        Integer expectedValInTrueDefinition = 1;
        Integer expectedValInFalseDefinition = 2;

        Integer actualVal = inCase(identityPredicate(), true)
                .isTrue(expectedValInTrueDefinition)
                .isFalse(expectedValInFalseDefinition)
                .value();

        assertThat(actualVal).isEqualTo(expectedValInTrueDefinition);
    }

    @Test
    public void inCaseIsTrueOrFalse_FalseCaseForExactValue_ExpectedDefinedValForFalseHasBeenReturned() {
        Integer expectedValInTrueDefinition = 1;
        Integer expectedValInFalseDefinition = 2;

        Integer actualVal = inCase(identityPredicate(), false)
                .isTrue(expectedValInTrueDefinition)
                .isFalse(expectedValInFalseDefinition)
                .value();

        assertThat(actualVal).isEqualTo(expectedValInFalseDefinition);
    }

    @Test
    public void inCaseIsTrue_ExceptionInTheConditionCaseForPredicate_ClosureForExceptionCaseHasBeenExecuted() {
        inCase(() -> {
            throw new IllegalArgumentException();
        }).isException(this::closureWithoutReturn);

        assertClosureWasExecuted();
    }

    @Test(expected = IllegalArgumentException.class)
    public void inCaseIsTrue_ExceptionInTheClosureForPredicate_IllegalArgumentExceptionHasBeenThrown() {
        inCase(identityPredicate(), true).isTrue((o) -> {
            throw new IllegalArgumentException();
        });
    }

    @Test
    public void inCaseIsFalse_TrueCaseForPredicate_ConsumerClosureHasBeenExecuted() {
        inCase(identityPredicate(), true).isFalse((o) -> closureWithoutReturn());

        assertClosureWasNotExecuted();
    }

    @Test
    public void inCaseIsFalse_TrueCaseForPredicate_RunnableClosureHasBeenExecuted() {
        inCase(identityPredicate(), true).isFalse(this::closureWithoutReturn);

        assertClosureWasNotExecuted();
    }

    @Test
    public void inCaseIsFalse_FalseCaseForPredicate_ConsumerClosureHasNotBeenExecuted() {
        inCase(identityPredicate(), false).isFalse((o) -> closureWithoutReturn());

        assertClosureWasExecuted();
    }

    @Test
    public void inCaseIsFalse_FalseCaseForPredicate_RunnableClosureHasNotBeenExecuted() {
        inCase(identityPredicate(), false).isFalse(this::closureWithoutReturn);

        assertClosureWasExecuted();
    }

    @Test
    public void inCaseIsFalse_ExceptionInTheConditionCaseForPredicate_ClosureForExceptionCaseHasBeenExecuted() {
        inCase(() -> {
            throw new IllegalArgumentException();
        }).isException(this::closureWithoutReturn);

        assertClosureWasExecuted();
    }

    @Test(expected = IllegalArgumentException.class)
    public void inCaseIsFalse_ExceptionInTheClosureForPredicate_IllegalArgumentExceptionHasBeenThrown() {
        inCase(identityPredicate(), false).isFalse((o) -> {
            throw new IllegalArgumentException();
        });
    }

    @Test(expected = NullPointerException.class)
    public void check_CaseForSupplierWithNullSupplier_NullPointerExceptionHasBeenThrown() {
        inCase((Supplier<Boolean>) null);
    }

    @Test(expected = NullPointerException.class)
    public void check_CaseForPredicateWithNullPredicate_NullPointerExceptionHasBeenThrown() {
        inCase(null, new Object());
    }

    @Test(expected = NullPointerException.class)
    public void check_CaseForPredicateWithNullArgument_NullPointerExceptionHasBeenThrown() {
        inCase(truePredicate(), null);
    }

    @Test(expected = NullPointerException.class)
    public void check_CaseForSupplierWithNullClosure_NullPointerExceptionHasBeenThrown() {
        inCase(trueSupplier()).isTrue(null);
    }

    @Test(expected = NullPointerException.class)
    public void check_CaseForPredicateWithNullClosure_NullPointerExceptionHasBeenThrown() {
        inCase(identityPredicate(), true).isTrue((Runnable) null);
    }

    @Test(expected = NullPointerException.class)
    public void check_CaseForPredicateWithNullFunction_NullPointerExceptionHasBeenThrown() {
        inCase(identityPredicate(), true).isTrueMap(null);
    }

    @Test
    public void check_CaseForExactValueWithNullExactValue_NoExceptionHasBeenThrown() {
        Object nullObject = null;

        inCase(identityPredicate(), true).isTrue(nullObject);
    }

    /**
     * Mock for closure without return. It could be any method.
     * We just need it in order to know whether closureWithoutReturn was executed or not.
     */
    private void closureWithoutReturn() {
        testee.voidMethod();
    }

    /**
     * Mock for closure with return. It could be any method.
     * We just need it in order to know whether closureWithoutReturn was executed or not and test return value.
     */
    private Integer closureWithReturn(Integer objectToReturn) {
        testee.voidMethod();
        return objectToReturn;
    }

    private void assertClosureWasExecuted() {
        verify(testee).voidMethod();
    }

    private void assertClosureWasNotExecuted() {
        verify(testee, times(0)).voidMethod();
    }

}