package com.dvelopp.functional.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.dvelopp.functional.utils.FunctionUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class FunctionUtilsTest {

    @Test
    public void not_CheckEmptyListWhenListIsNotEmpty_NegatedPredicateSaysThatListIsEmpty() {
        List<String> notEmptyList = asList("NotEmptyList");

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
    public void with_ModifyObjectWithFirstValue_ObjectHasBeenModified() {
        BiValHolder<String, String> testObject = new BiValHolder<>("initialFirstValue", "initialSecondValue");

        with(testObject, o -> o.setVal1("modifiedFirstValue"));

        assertThat(testObject.getVal1()).isEqualTo("modifiedFirstValue");
    }

    @Test(expected = NullPointerException.class)
    public void with_NullClosure_NPEHasBeenThrown() {
        with(mock(Testee.class), null);
    }

    @Test(expected = NullPointerException.class)
    public void with_NullSelfLink_NPEHasBeenThrown() {
        with(null, emptyConsumer());
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
    public void function_BiFunctionThatAddsSecArgumentToFirst_ArgumentsHaveBeenAddedToEachOther() {
        Function<Integer, Integer> function = function(Math::addExact, 100);
        Integer actualResult = function.apply(1);

        assertThat(actualResult).isEqualTo(101);
    }

    @Test
    public void consumer_BiFunctionThatCheckWhetherFirstArgumentIsBiggerThanTheSecond_CheckWorksForLowerAndGreaterOnes() {
        final boolean[] result = {false};
        Consumer<Integer> consumer = consumer((o1, o2) -> result[0] = o1 > o2, 100);

        consumer.accept(101);
        assertThat(result[0]).isTrue();
        consumer.accept(99);
        assertThat(result[0]).isFalse();
    }

}