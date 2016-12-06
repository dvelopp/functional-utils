package com.dvelopp.functional.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.dvelopp.functional.utils.CollectionUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CollectionUtilsTest {

    private SubTestObject firstSubTestObject = new SubTestObject();
    private SubTestObject secondSubTestObject = new SubTestObject();
    private TestObject firstTestObject = new TestObject(firstSubTestObject);
    private TestObject secondTestObject = new TestObject(secondSubTestObject);

    @Test
    public void forEach_ChangeElementStateToFalse_StateWasChangedForAllElements() {
        List<TestObject> list = asList(firstTestObject, secondTestObject);

        forEach(list, TestObject::setActive, false);

        assertThat(firstTestObject.isActive()).isEqualTo(false);
        assertThat(secondTestObject.isActive()).isEqualTo(false);
    }

    @Test
    public void forEach_ChangeElementStateToTrue_StateWasChangedForAllElements() {
        List<TestObject> list = asList(firstTestObject, secondTestObject);

        forEach(list, TestObject::setActive, true);

        assertThat(firstTestObject.isActive()).isEqualTo(true);
        assertThat(secondTestObject.isActive()).isEqualTo(true);
    }

    @Test
    public void mapToList_ListWithObjects_ObjectsMappedToTheList() {
        List<TestObject> testObjects = asList(firstTestObject, secondTestObject);

        List<SubTestObject> mappedObjects = mapToList(testObjects, TestObject::getSubTestObject);

        assertThat(mappedObjects).contains(firstSubTestObject, secondSubTestObject);
    }

    @Test
    public void mapToList_SetWithObjects_ObjectsMappedToTheList() {
        Set<TestObject> testObjects = new HashSet<>(asList(firstTestObject, secondTestObject));

        List<SubTestObject> mappedObjects = mapToList(testObjects, TestObject::getSubTestObject);

        assertThat(mappedObjects).contains(firstSubTestObject, secondSubTestObject);
    }

    @Test
    public void map_ListWithObjects_ObjectsMappedToTheList() {
        List<TestObject> testObjects = asList(firstTestObject, secondTestObject);

        List<SubTestObject> mappedObjects = map(testObjects, TestObject::getSubTestObject);

        assertThat(mappedObjects).contains(firstSubTestObject, secondSubTestObject);
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
        Set<TestObject> testObjects = new HashSet<>(asList(firstTestObject, secondTestObject));

        Set<SubTestObject> mappedObjects = mapToSet(testObjects, TestObject::getSubTestObject);

        assertThat(mappedObjects).contains(firstSubTestObject, secondSubTestObject);
    }

    @Test
    public void mapToSet_ListWithObjects_ObjectsMappedToTheSet() {
        List<TestObject> testObjects = asList(firstTestObject, secondTestObject);

        Set<SubTestObject> mappedObjects = mapToSet(testObjects, TestObject::getSubTestObject);

        assertThat(mappedObjects).contains(firstSubTestObject, secondSubTestObject);
    }

    @Test
    public void map_SetWithObjects_ObjectsMappedToTheSet() {
        Set<TestObject> testObjects = new HashSet<>(asList(firstTestObject, secondTestObject));

        Set<SubTestObject> mappedObjects = map(testObjects, TestObject::getSubTestObject);

        assertThat(mappedObjects).contains(firstSubTestObject, secondSubTestObject);
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
        Set<TestObject> testObjects = new HashSet<>(asList(firstTestObject, secondTestObject));

        LinkedList<SubTestObject> mappedObjects = mapToCollection(testObjects, TestObject::getSubTestObject, LinkedList::new);

        assertThat(mappedObjects).contains(firstSubTestObject, secondSubTestObject);
    }

    @Test
    public void mapToCollection_LinkedListToTreeSet_ObjectsFromTheLinkedListHaveBeenMappedToTheHashSet() {
        Set<TestObject> testObjects = new HashSet<>(asList(firstTestObject, secondTestObject));

        Set<SubTestObject> mappedObjects = mapToCollection(testObjects, TestObject::getSubTestObject, HashSet::new);

        assertThat(mappedObjects).contains(firstSubTestObject, secondSubTestObject);
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
        List<TestObject> testObjects = asList(firstTestObject, secondTestObject);

        SubTestObject[] mappedObjects = mapToArray(testObjects, TestObject::getSubTestObject, SubTestObject[]::new);

        assertThat(mappedObjects).contains(firstSubTestObject, secondSubTestObject);
    }

    @Test
    public void mapToArray_SetWithObjects_ObjectsMappedToTheArray() {
        Set<TestObject> testObjects = new HashSet<>(asList(firstTestObject, secondTestObject));

        SubTestObject[] mappedObjects = mapToArray(testObjects, TestObject::getSubTestObject, SubTestObject[]::new);

        assertThat(mappedObjects).contains(firstSubTestObject, secondSubTestObject);
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
        List<TestObject> testObjects = asList(firstTestObject);

        Map<String, SubTestObject> actualMap = mapToMap(testObjects, TestObject::getId, TestObject::getSubTestObject);

        assertThat(actualMap)
                .containsExactly(new SimpleEntry<>(firstTestObject.getId(), firstTestObject.getSubTestObject()));
    }

    @Test
    public void mapToMap_TwoObjectsAndValidMappers_ObjectsHasBeenMappedToMapAccordingToMappers() {
        List<TestObject> testObjects = asList(firstTestObject, secondTestObject);

        Map<String, SubTestObject> actualMap = mapToMap(testObjects, TestObject::getId, TestObject::getSubTestObject);

        assertThat(actualMap)
                .containsExactly(new SimpleEntry<>(firstTestObject.getId(), firstTestObject.getSubTestObject()),
                        new SimpleEntry<>(secondTestObject.getId(), secondTestObject.getSubTestObject()));
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

}