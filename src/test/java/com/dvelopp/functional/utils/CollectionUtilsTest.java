package com.dvelopp.functional.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.dvelopp.functional.utils.CollectionUtils.*;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CollectionUtilsTest {

    private static final String KEY_1 = "key1";
    private static final String KEY_2 = "key2";
    private static final String KEY_3 = "key3";
    private static final String KEY_4 = "key4";
    private static final String VAL_1 = "val1";
    private static final String VAL_2 = "val2";
    private static final String VAL_3 = "val3";
    private static final String VAL_4 = "val4";

    private BiValHolder<String, String> testObject1 = new BiValHolder<>(KEY_1, VAL_1);
    private BiValHolder<String, String> testObject2 = new BiValHolder<>(KEY_2, VAL_2);
    private BiValHolder<String, String> testObject3 = new BiValHolder<>(KEY_3, VAL_3);
    private BiValHolder<String, String> testObject4 = new BiValHolder<>(KEY_4, VAL_4);
    private final List<BiValHolder<String, String>> validBiValList = new ArrayList<>();
    private final List<BiValHolder<String, String>> nullBiValList = null;

    @Test
    public void forEach_ChangeElementState_StateWasChangedForAllElements() {
        List<BiValHolder<String, String>> list = asList(testObject1, testObject2);
        String expectedNewValue = "changed value";

        forEach(list, BiValHolder::setVal2, expectedNewValue);

        assertThat(testObject1.getVal2()).isEqualTo(expectedNewValue);
        assertThat(testObject2.getVal2()).isEqualTo(expectedNewValue);
    }

    @Test
    public void mapToList_ListWithObjects_ObjectsMappedToTheList() {
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2);

        List<String> mappedObjects = mapToList(testObjects, BiValHolder::getVal2);

        assertThat(mappedObjects).containsOnly(VAL_1, VAL_2);
    }

    @Test
    public void mapToList_SetWithObjects_ObjectsMappedToTheList() {
        Set<BiValHolder<String, String>> testObjects = new HashSet<>(asList(testObject1, testObject2));

        List<String> mappedObjects = mapToList(testObjects, BiValHolder::getVal2);

        assertThat(mappedObjects).contains(VAL_1, VAL_2);
    }

    @Test
    public void map_ListWithObjects_ObjectsMappedToTheList() {
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2);

        List<String> mappedObjects = map(testObjects, BiValHolder::getVal2);

        assertThat(mappedObjects).contains(VAL_1, VAL_2);
    }

    @Test(expected = NullPointerException.class)
    public void mapToList_ListIsNull_NPEHasBeenThrown() {
        mapToList(nullBiValList, BiValHolder::getVal1);
    }

    @Test(expected = NullPointerException.class)
    public void mapToList_MapperIsNull_NPEHasBeenThrown() {
        mapToList(validBiValList, null);
    }

    @Test
    public void mapToSet_SetWithObjects_ObjectsMappedToTheSet() {
        Set<BiValHolder<String, String>> testObjects = new HashSet<>(asList(testObject1, testObject2));

        Set<String> mappedObjects = mapToSet(testObjects, BiValHolder::getVal2);

        assertThat(mappedObjects).contains(VAL_1, VAL_2);
    }

    @Test
    public void mapToSet_ListWithObjects_ObjectsMappedToTheSet() {
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2);

        Set<String> mappedObjects = mapToSet(testObjects, BiValHolder::getVal2);

        assertThat(mappedObjects).contains(VAL_1, VAL_2);
    }

    @Test
    public void map_EmptySet_EmptySetHasBeenCreated() {
        Set<BiValHolder<String, String>> testObjects = new HashSet<>();

        Set<String> mappedObjects = map(testObjects, BiValHolder::getVal2);

        assertThat(mappedObjects).isEmpty();
    }

    @Test
    public void map_SetWithObjects_ObjectsMappedToTheSet() {
        Set<BiValHolder<String, String>> testObjects = new HashSet<>(asList(testObject1, testObject2));

        Set<String> mappedObjects = map(testObjects, BiValHolder::getVal2);

        assertThat(mappedObjects).contains(VAL_1, VAL_2);
    }

    @Test
    public void map_ListCaseBiFunctionAdds100ToEachValue_ListWithSumOfTheValuesHasBeenReturned() {
        List<Integer> testObjects = asList(1, 2, 3);

        List<Integer> mappedObjects = map(testObjects, Math::addExact, 100);

        assertThat(mappedObjects).containsOnly(101, 102, 103);
    }

    @Test
    public void map_SetCaseBiFunctionAdds100ToEachValue_SetWithSumOfTheValuesHasBeenReturned() {
        Set<Integer> testObjects = new HashSet<>(asList(1, 2, 3));

        Set<Integer> mappedObjects = map(testObjects, Math::addExact, 100);

        assertThat(mappedObjects).containsOnly(101, 102, 103);
    }

    @Test
    public void mapToSet_ListCaseBiFunctionAdds100ToEachValue_SetWithSumOfTheValuesHasBeenReturned() {
        List<Integer> testObjects = asList(1, 2, 3);

        Set<Integer> mappedObjects = mapToSet(testObjects, Math::addExact, 100);

        assertThat(mappedObjects).containsOnly(101, 102, 103);
    }

    @Test
    public void mapToList_SetCaseBiFunctionAdds100ToEachValue_ListWithSumOfTheValuesHasBeenReturned() {
        Set<Integer> testObjects = new HashSet<>(asList(1, 2, 3));

        List<Integer> mappedObjects = mapToList(testObjects, Math::addExact, 100);

        assertThat(mappedObjects).containsOnly(101, 102, 103);
    }

    @Test(expected = NullPointerException.class)
    public void mapToSet_SetIsNull_NPEHasBeenThrown() {
        mapToSet(nullBiValList, BiValHolder::getVal1);
    }

    @Test(expected = NullPointerException.class)
    public void mapToSet_MapperIsNull_NPEHasBeenThrown() {
        mapToSet(new HashSet<>(), null);
    }

    @Test
    public void mapToCollection_EmptyList_EmptyCollectionHasBeenCreated() {
        LinkedList<String> mappedObjects = mapToCollection(new HashSet<BiValHolder<String, String>>(),
                BiValHolder::getVal2, LinkedList::new);

        assertThat(mappedObjects).isEmpty();
    }

    @Test
    public void mapToCollection_SetToLinkedList_ObjectsFromTheSetHaveBeenMappedToTheLinkedList() {
        Set<BiValHolder<String, String>> testObjects = new HashSet<>(asList(testObject1, testObject2));

        LinkedList<String> mappedObjects = mapToCollection(testObjects, BiValHolder::getVal2, LinkedList::new);

        assertThat(mappedObjects).contains(VAL_1, VAL_2);
    }

    @Test
    public void mapToCollection_LinkedListToTreeSet_ObjectsFromTheLinkedListHaveBeenMappedToTheHashSet() {
        Set<BiValHolder<String, String>> testObjects = new HashSet<>(asList(testObject1, testObject2));

        Set<String> mappedObjects = mapToCollection(testObjects, BiValHolder::getVal2, HashSet::new);

        assertThat(mappedObjects).contains(VAL_1, VAL_2);
    }

    @Test(expected = NullPointerException.class)
    public void mapToCollection_SetIsNull_NPEHasBeenThrown() {
        Function<BiValHolder<String, String>, String> mapper = BiValHolder::getVal1;
        Supplier<LinkedList<String>> collectionSupplier = LinkedList::new;

        mapToCollection(null, mapper, collectionSupplier);
    }

    @Test(expected = NullPointerException.class)
    public void mapToCollection_MapperIsNull_NPEHasBeenThrown() {
        Set<BiValHolder<String, String>> testObjects = new HashSet<>();
        Supplier<LinkedList<String>> collectionSupplier = LinkedList::new;

        mapToCollection(testObjects, null, collectionSupplier);
    }

    @Test(expected = NullPointerException.class)
    public void mapToCollection_CollectionSupplierIsNull_NPEHasBeenThrown() {
        Set<BiValHolder<String, String>> testObjects = new HashSet<>();
        Function<BiValHolder<String, String>, String> mapper = BiValHolder::getVal1;

        mapToCollection(testObjects, mapper, null);
    }

    @Test
    public void mapToArray_EmptyList_EmptyArrayHasBeenCreated() {
        String[] mappedObjects = mapToArray(Collections.<BiValHolder<String, String>>emptyList(),
                BiValHolder::getVal2, String[]::new);

        assertThat(mappedObjects).isEmpty();
    }

    @Test
    public void mapToArray_ListWithObjects_ObjectsMappedToTheArray() {
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2);

        String[] mappedObjects = mapToArray(testObjects, BiValHolder::getVal2, String[]::new);

        assertThat(mappedObjects).contains(VAL_1, VAL_2);
    }

    @Test
    public void mapToArray_SetWithObjects_ObjectsMappedToTheArray() {
        Set<BiValHolder<String, String>> testObjects = new HashSet<>(asList(testObject1, testObject2));

        String[] mappedObjects = mapToArray(testObjects, BiValHolder::getVal2, String[]::new);

        assertThat(mappedObjects).contains(VAL_1, VAL_2);
    }

    @Test(expected = NullPointerException.class)
    public void mapToArray_CollectionIsNull_NPEHasBeenThrown() {
        final HashSet<BiValHolder<String, String>> objects = null;

        mapToArray(objects, BiValHolder::getVal1, BiValHolder[]::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToArray_MapperIsNull_NPEHasBeenThrown() {
        HashSet<BiValHolder<String, String>> objects = new HashSet<>();

        mapToArray(objects, null, BiValHolder[]::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToArray_ArrayGeneratorIsNull_NPEHasBeenThrown() {
        HashSet<BiValHolder<String, String>> objects = new HashSet<>();

        mapToArray(objects, BiValHolder::getVal1, null);
    }

    @Test
    public void mapToMap_OneObjectAndValidMappers_ObjectHasBeenMappedToMapAccordingToMappers() {
        List<BiValHolder<String, String>> testObjects = singletonList(testObject1);

        Map<String, String> actualMap = mapToMap(testObjects, BiValHolder::getVal1, BiValHolder::getVal2);

        assertThat(actualMap).containsOnly(new SimpleEntry<>(testObject1.getVal1(), testObject1.getVal2()));
    }

    @Test
    public void mapToMap_TwoObjectsAndValidMappers_ObjectsHasBeenMappedToMapAccordingToMappers() {
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2);

        Map<String, String> actualMap = mapToMap(testObjects, BiValHolder::getVal1, BiValHolder::getVal2);

        assertThat(actualMap)
                .containsOnly(new SimpleEntry<>(testObject1.getVal1(), testObject1.getVal2()),
                        new SimpleEntry<>(testObject2.getVal1(), testObject2.getVal2()));
    }

    @Test(expected = IllegalStateException.class)
    public void mapToMap_DuplicateKey_IllegalStateExceptionHasBeenThrown() {
        testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        testObject2 = new BiValHolder<>(KEY_1, VAL_2);
        testObject3 = new BiValHolder<>(KEY_1, VAL_2);
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2, testObject3);

        mapToMap(testObjects, BiValHolder::getVal1, BiValHolder::getVal2);
    }

    @Test
    public void mapToMap_EmptyCollection_EmptyMapHasBeenCreated() {
        Map<String, String> actualMap = mapToMap(validBiValList, BiValHolder::getVal1, BiValHolder::getVal2);

        assertThat(actualMap).isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_NullCollection_NPEHasBeenThrown() {
        mapToMap(nullBiValList, BiValHolder::getVal1, BiValHolder::getVal2);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_NullKeyMapper_NPEHasBeenThrown() {
        mapToMap(validBiValList, null, BiValHolder::getVal1);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_NullValueMapper_NPEHasBeenThrown() {
        mapToMap(validBiValList, BiValHolder::getVal1, null);
    }

    @Test
    public void mapToMap_MergeFunctionAndOneDuplicate_ObjectsHaveBeenMappedToMapAccordingToMappersAndDuplicatesMerged() {
        testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        testObject2 = new BiValHolder<>(KEY_1, VAL_2);
        testObject3 = new BiValHolder<>(KEY_2, VAL_1);
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2, testObject3);

        Map<String, String> actualMap = mapToMap(testObjects,
                BiValHolder::getVal1, BiValHolder::getVal2, this::testMerge);

        SimpleEntry<String, String> mergedEntry = new SimpleEntry<>(testObject1.getVal1(),
                format("%s;%s", testObject1.getVal2(), testObject2.getVal2()));
        SimpleEntry<String, String> uniqueEntry = new SimpleEntry<>(testObject3.getVal1(), testObject3.getVal2());
        assertThat(actualMap).containsOnly(mergedEntry, uniqueEntry);
    }

    @Test
    public void mapToMap_MergeFunctionAndOnlyDuplicates_ObjectsHaveBeenMappedToMapAccordingToMappersAndAllMerged() {
        testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        testObject2 = new BiValHolder<>(KEY_1, VAL_2);
        testObject3 = new BiValHolder<>(KEY_2, VAL_1);
        testObject4 = new BiValHolder<>(KEY_2, VAL_2);
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2, testObject3, testObject4);

        Map<String, String> actualMap = mapToMap(testObjects,
                BiValHolder::getVal1, BiValHolder::getVal2, this::testMerge);

        SimpleEntry<String, String> mergedEntry1 = new SimpleEntry<>(testObject1.getVal1(),
                format("%s;%s", testObject1.getVal2(), testObject2.getVal2()));
        SimpleEntry<String, String> mergedEntry2 = new SimpleEntry<>(testObject3.getVal1(),
                format("%s;%s", testObject3.getVal2(), testObject4.getVal2()));
        assertThat(actualMap).containsOnly(mergedEntry1, mergedEntry2);
    }

    @Test
    public void mapToMap_NoDuplicates_ObjectsHaveBeenMappedToMapAccordingToMappers() {
        testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        testObject2 = new BiValHolder<>(KEY_2, VAL_2);
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2);

        Map<String, String> actualMap = mapToMap(testObjects, BiValHolder::getVal1, BiValHolder::getVal2,
                this::testMerge);

        SimpleEntry<String, String> entry1 = new SimpleEntry<>(testObject1.getVal1(), testObject1.getVal2());
        SimpleEntry<String, String> entry2 = new SimpleEntry<>(testObject2.getVal1(), testObject2.getVal2());
        assertThat(actualMap).containsOnly(entry1, entry2);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_MergeFunctionCaseNullCollection_NPEHasBeenThrown() {
        mapToMap(nullBiValList, BiValHolder::getVal1, BiValHolder::getVal2, this::testMerge);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_MergeFunctionCaseNullKeyMapper_NPEHasBeenThrown() {
        mapToMap(validBiValList, null, BiValHolder::getVal2, this::testMerge);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_MergeFunctionCaseNullValueMapper_NPEHasBeenThrown() {
        mapToMap(validBiValList, BiValHolder::getVal1, null, this::testMerge);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_MergeFunctionCaseNullMergeFunction_NPEHasBeenThrown() {
        final BinaryOperator<String> mergeFunction = null;

        mapToMap(validBiValList, BiValHolder::getVal1, BiValHolder::getVal2, mergeFunction);
    }

    @Test
    public void mapToMap_MapSupplierWithMergeFunction_MapProvidedBySupplierHasBeenFilledWithNewObjects() {
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2);

        Map<String, String> actualMap = mapToMap(testObjects,
                BiValHolder::getVal1, BiValHolder::getVal2, this::testMerge, TestMap::new);

        SimpleEntry<String, String> entry1 = new SimpleEntry<>(testObject1.getVal1(), testObject1.getVal2());
        SimpleEntry<String, String> entry2 = new SimpleEntry<>(testObject2.getVal1(), testObject2.getVal2());
        assertThat(actualMap.getClass().isAssignableFrom(TestMap.class));
        assertThat(actualMap).containsOnly(entry1, entry2);
    }

    @Test
    public void mapToMap_MapSupplierWithMergeFunctionAndOnlyDuplicates_DuplicatesHaveBeenHandledAndAddedToGivenMap() {
        testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        testObject2 = new BiValHolder<>(KEY_1, VAL_2);
        testObject3 = new BiValHolder<>(KEY_2, VAL_1);
        testObject4 = new BiValHolder<>(KEY_2, VAL_2);
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2, testObject3, testObject4);

        Map<String, String> actualMap = mapToMap(testObjects,
                BiValHolder::getVal1, BiValHolder::getVal2, this::testMerge, TestMap::new);

        SimpleEntry<String, String> mergedEntry1 = new SimpleEntry<>(testObject1.getVal1(),
                format("%s;%s", testObject1.getVal2(), testObject2.getVal2()));
        SimpleEntry<String, String> mergedEntry2 = new SimpleEntry<>(testObject3.getVal1(),
                format("%s;%s", testObject3.getVal2(), testObject4.getVal2()));
        assertThat(actualMap).containsOnly(mergedEntry1, mergedEntry2);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_SupplierMapAndMergeFunctionCaseNullCollection_NPEHasBeenThrown() {
        mapToMap(nullBiValList, BiValHolder::getVal1, BiValHolder::getVal2, this::testMerge, TestMap::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_SupplierMapAndMergeFunctionCaseNullKeyMapper_NPEHasBeenThrown() {
        mapToMap(validBiValList, null, BiValHolder::getVal2, this::testMerge, TestMap::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_SupplierMapAndMergeFunctionCaseNullValueMapper_NPEHasBeenThrown() {
        mapToMap(validBiValList, BiValHolder::getVal1, null, this::testMerge, TestMap::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_SupplierMapAndMergeFunctionCaseNullMergeFunction_NPEHasBeenThrown() {
        mapToMap(validBiValList, BiValHolder::getVal1, BiValHolder::getVal2, null, TestMap::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_SupplierMapAndMergeFunctionCaseNullSupplierMapCase_NPEHasBeenThrown() {
        mapToMap(validBiValList, BiValHolder::getVal1, BiValHolder::getVal2, this::testMerge, null);
    }

    @Test
    public void mapToMap_MapSupplier_MapProvidedBySupplierHasBeenFilledWithNewObjects() {
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2);

        Map<String, String> actualMap = mapToMap(testObjects, BiValHolder::getVal1, BiValHolder::getVal2, TestMap::new);

        SimpleEntry<String, String> entry1 = new SimpleEntry<>(testObject1.getVal1(), testObject1.getVal2());
        SimpleEntry<String, String> entry2 = new SimpleEntry<>(testObject2.getVal1(), testObject2.getVal2());
        assertThat(actualMap.getClass().isAssignableFrom(TestMap.class));
        assertThat(actualMap).containsOnly(entry1, entry2);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_MapSupplierCaseNullCollection_NPEHasBeenThrown() {
        mapToMap(nullBiValList, BiValHolder::getVal1, BiValHolder::getVal2, this::testMerge, TestMap::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_MapSupplierCaseNullKeyMapper_NPEHasBeenThrown() {
        mapToMap(validBiValList, null, BiValHolder::getVal2, this::testMerge, TestMap::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_MapSupplierCaseNullValueMapper_NPEHasBeenThrown() {
        mapToMap(validBiValList, BiValHolder::getVal1, null, this::testMerge, TestMap::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_MapSupplierCaseNullSupplierMapCase_NPEHasBeenThrown() {
        final Supplier<Map<String, String>> nullMapSupplier = null;

        mapToMap(validBiValList, BiValHolder::getVal1, BiValHolder::getVal2, nullMapSupplier);
    }

    @Test
    public void groupingBy_ClassifierWithEmptyList_EmptyMapHasBeenCreated() {
        List<BiValHolder<String, String>> testObjects = emptyList();

        Map<String, List<BiValHolder<String, String>>> actualMap = groupingBy(testObjects, BiValHolder::getVal1);

        assertThat(actualMap).isEmpty();
    }

    @Test
    public void groupingBy_ClassifierWithTwoKeysTwoValuesPerEach_MapWithTwoKeysAndTwoValuesForEachHasBeenCreated() {
        testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        testObject2 = new BiValHolder<>(KEY_1, VAL_2);
        testObject3 = new BiValHolder<>(KEY_2, VAL_1);
        testObject4 = new BiValHolder<>(KEY_2, VAL_2);
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2, testObject3, testObject4);

        Map<String, List<BiValHolder<String, String>>> groupedValues = groupingBy(testObjects, BiValHolder::getVal1);

        assertThat(groupedValues).hasSize(2);
        assertThat(groupedValues.get(KEY_1)).containsOnly(testObject1, testObject2);
        assertThat(groupedValues.get(KEY_2)).containsOnly(testObject3, testObject4);
    }

    @Test
    public void groupingBy_ClassifierWithOneKeyAndFourValues_MapWithOneKeyAndFourValuesHasBeenCreated() {
        testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        testObject2 = new BiValHolder<>(KEY_1, VAL_2);
        testObject3 = new BiValHolder<>(KEY_1, VAL_3);
        testObject4 = new BiValHolder<>(KEY_1, VAL_4);
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2, testObject3, testObject4);

        Map<String, List<BiValHolder<String, String>>> groupedValues = groupingBy(testObjects, BiValHolder::getVal1);

        assertThat(groupedValues).hasSize(1);
        assertThat(groupedValues.get(KEY_1)).containsOnly(testObject1, testObject2, testObject3, testObject4);
    }

    @Test
    public void groupingBy_DownstreamCaseWithEmptyList_EmptyMapHasBeenCreated() {
        List<BiValHolder<String, String>> testObjects = emptyList();

        Map<String, List<String>> actualMap =
                groupingBy(testObjects, BiValHolder::getVal1, mapping(BiValHolder::getVal2, toList()));

        assertThat(actualMap).isEmpty();
    }

    @Test
    public void groupingBy_DownstreamCaseWithOneKeyAndTwoValues_OneKeyWithListsOfTwoElementsHasBeenCreated() {
        testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        testObject2 = new BiValHolder<>(KEY_1, VAL_2);
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2);

        Map<String, List<String>> actualMap =
                groupingBy(testObjects, BiValHolder::getVal1, mapping(BiValHolder::getVal2, toList()));

        assertThat(actualMap).hasSize(1);
        assertThat(actualMap.get(KEY_1)).containsOnly(VAL_1, VAL_2);
    }

    @Test
    public void groupingBy_DownstreamCaseWithTwoKeysAndTwoValuesPerEach_TwoKeysWithListsOfTwoElementsHaveBeenCreated() {
        testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        testObject2 = new BiValHolder<>(KEY_1, VAL_2);
        testObject3 = new BiValHolder<>(KEY_2, VAL_3);
        testObject4 = new BiValHolder<>(KEY_2, VAL_4);
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2, testObject3, testObject4);

        Map<String, List<String>> actualMap =
                groupingBy(testObjects, BiValHolder::getVal1, mapping(BiValHolder::getVal2, toList()));

        assertThat(actualMap).hasSize(2);
        assertThat(actualMap.get(KEY_1)).containsOnly(VAL_1, VAL_2);
        assertThat(actualMap.get(KEY_2)).containsOnly(VAL_3, VAL_4);
    }

    @Test
    public void groupingBy_DownstreamCaseWithOneKeyAndFourValues_OneKeyWithListsOfFourElementsHasBeenCreated() {
        testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        testObject2 = new BiValHolder<>(KEY_1, VAL_2);
        testObject3 = new BiValHolder<>(KEY_1, VAL_3);
        testObject4 = new BiValHolder<>(KEY_1, VAL_4);
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2, testObject3, testObject4);

        Map<String, List<String>> actualMap =
                groupingBy(testObjects, BiValHolder::getVal1, mapping(BiValHolder::getVal2, toList()));

        assertThat(actualMap).hasSize(1);
        assertThat(actualMap.get(KEY_1)).containsOnly(VAL_1, VAL_2, VAL_3, VAL_4);
    }

    @Test(expected = NullPointerException.class)
    public void groupingBy_ClassifierCaseWithNullCollection_NPEHasBeenThrown() {
        final Collection<BiValHolder<String, String>> collection = null;

        groupingBy(collection, BiValHolder::getVal1);
    }

    @Test(expected = NullPointerException.class)
    public void groupingBy_ClassifierCaseWithNullClassifier_NPEHasBeenThrown() {
        groupingBy(validBiValList, null);
    }

    @Test(expected = NullPointerException.class)
    public void groupingBy_DownstreamCaseWithNullCollection_NPEHasBeenThrown() {
        groupingBy(nullBiValList, BiValHolder::getVal1, mapping(identity(), toList()));
    }

    @Test(expected = NullPointerException.class)
    public void groupingBy_DownstreamCaseWithNullClassifier_NPEHasBeenThrown() {
        groupingBy(validBiValList, null, mapping(identity(), toList()));
    }

    @Test(expected = NullPointerException.class)
    public void groupingBy_DownstreamCaseWithNullDownstream_NPEHasBeenThrown() {
        groupingBy(validBiValList, BiValHolder::getVal1, null);
    }

    private String testMerge(String o1, String o2) {
        return o1 + ";" + o2;
    }

    public static class TestMap<K, V> extends HashMap<K, V> {
    }

}