package com.dvelopp.functional.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.dvelopp.functional.utils.CollectionUtils.*;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CollectionUtilsTest {

    private static final String KEY_1 = "key1";
    private static final String KEY_2 = "key2";
    private static final String VAL_1 = "val1";
    private static final String VAL_2 = "val2";
    private final BiValHolder<String, String> testObject1 = new BiValHolder<>(KEY_1, VAL_1);
    private final BiValHolder<String, String> testObject2 = new BiValHolder<>(KEY_2, VAL_2);

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
    public void mapToList_ListIsNull_NullPointerExceptionHasBeenThrown() {
        mapToList(null, TestObject::getSubTestObject);
    }

    @Test(expected = NullPointerException.class)
    public void mapToList_MapperIsNull_NullPointerExceptionHasBeenThrown() {
        mapToList(new ArrayList<>(), null);
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
    public void mapToSet_SetIsNull_NullPointerExceptionHasBeenThrown() {
        mapToSet(null, TestObject::getSubTestObject);
    }

    @Test(expected = NullPointerException.class)
    public void mapToSet_MapperIsNull_NullPointerExceptionHasBeenThrown() {
        mapToSet(new HashSet<>(), null);
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
    public void mapToCollection_SetIsNull_NullPointerExceptionHasBeenThrown() {
        Function<TestObject, SubTestObject> mapper = TestObject::getSubTestObject;
        Supplier<LinkedList<SubTestObject>> collectionSupplier = LinkedList::new;

        mapToCollection(null, mapper, collectionSupplier);
    }

    @Test(expected = NullPointerException.class)
    public void mapToCollection_MapperIsNull_NullPointerExceptionHasBeenThrown() {
        Set<TestObject> testObjects = new HashSet<>();
        Supplier<LinkedList<SubTestObject>> collectionSupplier = LinkedList::new;

        mapToCollection(testObjects, null, collectionSupplier);
    }

    @Test(expected = NullPointerException.class)
    public void mapToCollection_CollectionSupplierIsNull_NullPointerExceptionHasBeenThrown() {
        Set<TestObject> testObjects = new HashSet<>();
        Function<TestObject, SubTestObject> mapper = TestObject::getSubTestObject;

        mapToCollection(testObjects, mapper, null);
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
    public void mapToArray_CollectionIsNull_NullPointerExceptionHasBeenThrown() {
        mapToArray(null, TestObject::getSubTestObject, SubTestObject[]::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToArray_MapperIsNull_NullPointerExceptionHasBeenThrown() {
        mapToArray(new HashSet<>(), null, SubTestObject[]::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToArray_ArrayGeneratorIsNull_NullPointerExceptionHasBeenThrown() {
        mapToArray(new HashSet<>(), TestObject::getSubTestObject, null);
    }

    @Test
    public void mapToMap_OneObjectAndValidMappers_ObjectHasBeenMappedToMapAccordingToMappers() {
        List<BiValHolder<String, String>> testObjects = asList(testObject1);

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

    @Test
    public void mapToMap_EmptyCollection_EmptyMapHasBeenCreated() {
        List<TestObject> testObjects = emptyList();

        Map<String, SubTestObject> actualMap = mapToMap(testObjects, TestObject::getId, TestObject::getSubTestObject);

        assertThat(actualMap).isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_NullCollection_NullPointerExceptionHasBeenThrown() {
        mapToMap(null, TestObject::getId, TestObject::getSubTestObject);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_NullKeyMapper_NullPointerExceptionHasBeenThrown() {
        mapToMap(emptyList(), null, TestObject::getSubTestObject);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_NullValueMapper_NullPointerExceptionHasBeenThrown() {
        mapToMap(emptyList(), TestObject::getId, null);
    }

    @Test
    public void mapToMap_MergeFunctionAndOneDuplicate_ObjectsHaveBeenMappedToMapAccordingToMappersAndDuplicatesMerged() {
        BiValHolder<String, String> testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        BiValHolder<String, String> testObject2 = new BiValHolder<>(KEY_1, VAL_2);
        BiValHolder<String, String> testObject3 = new BiValHolder<>(KEY_2, VAL_1);
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
        BiValHolder<String, String> testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        BiValHolder<String, String> testObject2 = new BiValHolder<>(KEY_1, VAL_2);
        BiValHolder<String, String> testObject3 = new BiValHolder<>(KEY_2, VAL_1);
        BiValHolder<String, String> testObject4 = new BiValHolder<>(KEY_2, VAL_2);
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
        BiValHolder<String, String> testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        BiValHolder<String, String> testObject2 = new BiValHolder<>(KEY_2, VAL_2);
        List<BiValHolder<String, String>> testObjects = asList(testObject1, testObject2);

        Map<String, String> actualMap = mapToMap(testObjects, BiValHolder::getVal1, BiValHolder::getVal2,
                this::testMerge);

        SimpleEntry<String, String> entry1 = new SimpleEntry<>(testObject1.getVal1(), testObject1.getVal2());
        SimpleEntry<String, String> entry2 = new SimpleEntry<>(testObject2.getVal1(), testObject2.getVal2());
        assertThat(actualMap).containsOnly(entry1, entry2);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_MergeFunctionCaseNullCollection_NullPointerExceptionHasBeenThrown() {
        Collection<BiValHolder> collection = null;

        mapToMap(collection, BiValHolder::getVal1, BiValHolder::getVal2, (o1, o2) -> o1 + ";" + o2);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_MergeFunctionCaseNullKeyMapper_NullPointerExceptionHasBeenThrown() {
        mapToMap(new ArrayList<BiValHolder>(), null, BiValHolder::getVal2, (o1, o2) -> o1 + ";" + o2);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_MergeFunctionCaseNullValueMapper_NullPointerExceptionHasBeenThrown() {
        mapToMap(new ArrayList<BiValHolder>(), BiValHolder::getVal1, null, (o1, o2) -> o1 + ";" + o2);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_MergeFunctionCaseNullMergeFunction_NullPointerExceptionHasBeenThrown() {
        mapToMap(new ArrayList<BiValHolder>(), BiValHolder::getVal1, BiValHolder::getVal2, null);
    }

    @Test
    public void mapToMap_MapSupplier_MapProvidedBySupplierHasBeenFilledWithNewObjects() {
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
        BiValHolder<String, String> testObject1 = new BiValHolder<>(KEY_1, VAL_1);
        BiValHolder<String, String> testObject2 = new BiValHolder<>(KEY_1, VAL_2);
        BiValHolder<String, String> testObject3 = new BiValHolder<>(KEY_2, VAL_1);
        BiValHolder<String, String> testObject4 = new BiValHolder<>(KEY_2, VAL_2);
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
    public void mapToMap_SupplierMapAndMergeFunctionCaseNullCollection_NullPointerExceptionHasBeenThrown() {
        Collection<BiValHolder<String, String>> collection = null;

        mapToMap(collection, BiValHolder::getVal1, BiValHolder::getVal2, this::testMerge, TestMap::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_SupplierMapAndMergeFunctionCaseNullKeyMapper_NullPointerExceptionHasBeenThrown() {
        List<BiValHolder<String, String>> collection = new ArrayList<>();

        mapToMap(collection, null, BiValHolder::getVal2, this::testMerge, TestMap::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_SupplierMapAndMergeFunctionCaseNullValueMapper_NullPointerExceptionHasBeenThrown() {
        List<BiValHolder<String, String>> collection = new ArrayList<>();

        mapToMap(collection, BiValHolder::getVal1, null, this::testMerge, TestMap::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_SupplierMapAndMergeFunctionCaseNullMergeFunction_NullPointerExceptionHasBeenThrown() {
        List<BiValHolder<String, String>> collection = new ArrayList<>();

        mapToMap(collection, BiValHolder::getVal1, BiValHolder::getVal2, null, TestMap::new);
    }

    @Test(expected = NullPointerException.class)
    public void mapToMap_SupplierMapAndMergeFunctionCaseNullSupplierMapCase_NullPointerExceptionHasBeenThrown() {
        List<BiValHolder<String, String>> collection = new ArrayList<>();

        mapToMap(collection, BiValHolder::getVal1, BiValHolder::getVal2, this::testMerge, null);
    }

    private String testMerge(String o1, String o2) {
        return o1 + ";" + o2;
    }

    public static class TestMap<K, V> extends HashMap<K, V> {
    }

}