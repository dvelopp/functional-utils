package com.dvelopp.functional.utils;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.dvelopp.functional.utils.FunctionUtils.consumer;
import static com.dvelopp.functional.utils.FunctionUtils.emptyConsumer;
import static com.dvelopp.functional.utils.FunctionUtils.exceptionConsumer;
import static com.dvelopp.functional.utils.FunctionUtils.exceptionFunction;
import static com.dvelopp.functional.utils.FunctionUtils.exceptionPredicate;
import static com.dvelopp.functional.utils.FunctionUtils.exceptionRunnable;
import static com.dvelopp.functional.utils.FunctionUtils.exceptionSupplier;
import static com.dvelopp.functional.utils.FunctionUtils.falsePredicate;
import static com.dvelopp.functional.utils.FunctionUtils.falseSupplier;
import static com.dvelopp.functional.utils.FunctionUtils.function;
import static com.dvelopp.functional.utils.FunctionUtils.identityPredicate;
import static com.dvelopp.functional.utils.FunctionUtils.not;
import static com.dvelopp.functional.utils.FunctionUtils.nullSuppler;
import static com.dvelopp.functional.utils.FunctionUtils.truePredicate;
import static com.dvelopp.functional.utils.FunctionUtils.trueSupplier;
import static com.dvelopp.functional.utils.FunctionUtils.with;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class FunctionUtilsTest {

    @Test
    public void not_CheckEmptyListWhenListIsNotEmpty_NegatedPredicateSaysThatListIsEmpty() {
        List<String> notEmptyList = singletonList("NotEmptyList");

        Predicate<Collection> isEmptyPredicate = Collection::isEmpty;
        Predicate<Collection> isNotEmptyPredicate = not(isEmptyPredicate);

        assertThat(isEmptyPredicate.test(notEmptyList)).isFalse();
        assertThat(isNotEmptyPredicate.test(notEmptyList)).isTrue();
    }

    @Test
    public void not_CheckEmptyListWhenListIsEmpty_NegatedPredicateSaysThatListIsNotEmpty() {
        List<String> emptyList = emptyList();

        Predicate<Collection> isEmptyPredicate = Collection::isEmpty;
        Predicate<Collection> isNotEmptyPredicate = not(isEmptyPredicate);

        assertThat(isEmptyPredicate.test(emptyList)).isTrue();
        assertThat(isNotEmptyPredicate.test(emptyList)).isFalse();
    }

    @Test(expected = NullPointerException.class)
    public void not_NullPredicate_NPEHasBeenThrown() {
        not(null);
    }

    @Test
    public void not_ExceptionInPredicate_ExceptionHasNotBeenThrownSinceNotDoesNotExecutePredicateFunction() {
        not(exceptionPredicate());
    }

    @Test
    public void with_ModifyObjectWithFirstValue_ObjectHasBeenModified() {
        BiValHolder<String, String> testObject = new BiValHolder<>("initialFirstValue", "initialSecondValue");

        with(testObject, o -> o.setVal1("modifiedFirstValue"));

        assertThat(testObject.getVal1()).isEqualTo("modifiedFirstValue");
    }

    @SuppressWarnings("unchecked")
    @Test(expected = NullPointerException.class)
    public void with_NullClosure_NPEHasBeenThrown() {
        with(mock(Testee.class), null, (Consumer[]) null);
    }

    @Test(expected = NullPointerException.class)
    public void with_NullSelfLink_NPEHasBeenThrown() {
        with(null, emptyConsumer());
    }

    @Test
    public void with_CheckReturnValueSingleClosure_ReturnedTheSameObjectThatIsPassedAsArg() {
        BiValHolder<String, String> testObject = new BiValHolder<>("initialFirstValue", "initialSecondValue");

        BiValHolder<String, String> actualReturnValue = with(testObject, o -> o.setVal1("modifiedFirstValue"));

        assertThat(actualReturnValue).isEqualTo(testObject);
    }

    @Test
    public void with_CheckReturnValueForSeveralClosures_ReturnedTheSameObjectThatIsPassedAsArg() {
        BiValHolder<String, String> testObject = new BiValHolder<>("initialFirstValue", "initialSecondValue");

        BiValHolder<String, String> actualReturnValue = with(testObject, o -> o.setVal1("modifiedFirstValue"),
                o -> o.setVal1("modifiedFirstValue"));

        assertThat(actualReturnValue).isEqualTo(testObject);
    }

    @Test
    public void trueSupplier_TrueValueHasBeenProduced() {
        Supplier<Boolean> trueSupplier = trueSupplier();

        Boolean actualValue = trueSupplier.get();

        assertThat(actualValue).isTrue();
    }

    @Test
    public void falseSupplier_FalseValueHasBeenProduced() {
        Supplier<Boolean> falseSupplier = falseSupplier();

        Boolean actualValue = falseSupplier.get();

        assertThat(actualValue).isFalse();
    }

    @Test
    public void identityPredicate_TrueValue_PredicateProducesTrue() {
        assertThat(identityPredicate().test(true)).isTrue();
    }

    @Test
    public void identityPredicate_FalseValue_PredicateProducesFalse() {
        assertThat(identityPredicate().test(false)).isFalse();
    }

    @Test
    public void truePredicate_TrueValue_PredicateProducesTrue() {
        assertThat(truePredicate().test(true)).isTrue();
    }

    @Test
    public void truePredicate_FalseValue_PredicateProducesTrue() {
        assertThat(truePredicate().test(false)).isTrue();
    }

    @Test
    public void falsePredicate_TrueValue_PredicateProducesFalse() {
        assertThat(falsePredicate().test(true)).isFalse();
    }

    @Test
    public void falsePredicate_FalseValue_PredicateProducesFalse() {
        assertThat(falsePredicate().test(false)).isFalse();
    }

    @Test
    public void function_FirstCaseBiFunctionThatAddsSecArgumentToFirst_ArgumentsHaveBeenAddedToEachOther() {
        Function<Integer, Integer> function = function(Math::addExact, 100);

        Integer actualResult = function.apply(1);

        assertThat(actualResult).isEqualTo(101);
    }

    @Test
    public void function_SecondCaseBiFunctionThatAddsSecArgumentToFirst_ArgumentsHaveBeenAddedToEachOther() {
        Function<Integer, Integer> function = function(Math::addExact, 100);

        Integer actualResult = function.apply(3);

        assertThat(actualResult).isEqualTo(103);
    }

    @Test
    public void consumer_BiFunctionThatCheckWhetherFirstArgumentIsBiggerThanTheSecond_CheckWorksForLowerAndGreaterOnes() {
        final boolean[] result = {false};
        int greaterValue = 1;
        int lowerValue = -1;
        Consumer<Integer> consumer = consumer((o1, o2) -> result[0] = o1 > o2, 0);

        consumer.accept(greaterValue);
        assertThat(result[0]).isTrue();
        consumer.accept(lowerValue);
        assertThat(result[0]).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionPredicate_IllegalArgumentException_IllegalArgumentExceptionHasBeenThrown() {
        exceptionPredicate(IllegalArgumentException::new).test(true);
    }

    @Test(expected = NullPointerException.class)
    public void exceptionPredicate_NPE_NPEHasBeenThrown() {
        exceptionPredicate(NullPointerException::new).test(true);
    }

    @Test(expected = NullPointerException.class)
    public void exceptionPredicate_NullArgument_NPEHasBeenThrown() {
        exceptionPredicate(null).test(true);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionPredicate_NoParameters_IllegalStateExceptionHasBeenThrown() {
        exceptionPredicate().test(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionSupplier_IllegalArgumentException_IllegalArgumentExceptionHasBeenThrown() {
        exceptionSupplier(IllegalArgumentException::new).get();
    }

    @Test(expected = NullPointerException.class)
    public void exceptionSupplier_NPE_NPEHasBeenThrown() {
        exceptionSupplier(NullPointerException::new).get();
    }

    @Test(expected = NullPointerException.class)
    public void exceptionSupplier_NullArgument_NPEHasBeenThrown() {
        exceptionSupplier(null).get();
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionSupplier_NoParameters_IllegalStateExceptionHasBeenThrown() {
        exceptionSupplier().get();
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionConsumer_NullArgument_IllegalStateExceptionHasBeenThrown() {
        exceptionConsumer().accept(null);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionConsumer_NotNullArgument_IllegalStateExceptionHasBeenThrown() {
        exceptionConsumer().accept(new Object());
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionFunction_NullArgument_IllegalStateExceptionHasBeenThrown() {
        exceptionFunction().apply(null);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionFunction_NotNullArgument_IllegalStateExceptionHasBeenThrown() {
        exceptionFunction().apply(new Object());
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionRunnable_NoParameters_IllegalStateExceptionHasBeenThrown() {
        exceptionRunnable().run();
    }

    @Test
    public void nullSupplier_NotParametrized_NullHasBeenSupplied() {
        Object actualSuppliedValue = nullSuppler().get();

        assertThat(actualSuppliedValue).isNull();
    }

    @Test
    public void nullSupplier_Parametrized_NullHasBeenSupplied() {
        Testee actualSuppliedValue = FunctionUtils.<Testee>nullSuppler().get();

        assertThat(actualSuppliedValue).isNull();
    }

}