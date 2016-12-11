package com.dvelopp.functional.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dvelopp.functional.utils.FunctionUtils.consumer;
import static com.dvelopp.functional.utils.FunctionUtils.function;
import static com.dvelopp.functional.utils.ObjectUtils.requireNonNull;
import static java.util.stream.Collectors.*;

/**
 * Set of useful methods to work with collections.
 * There are two types of methods. Some of the methods extend the functionality provided by Java while other methods
 * make invocations of the same things more concise and easier than in Java.
 */
public final class CollectionUtils {

    /**
     * For each loop that consumes BiConsumer function.
     * That is made to provide default for each statement with ability to have an additional argument to the function
     * in order to allow developers to implement such cases in functional style.
     *
     * @param collection The collection to iterated over.
     * @param action     The action to be performed for each element.
     * @param arg        The argument that is considered second argument in the function.
     * @param <T>        The elements type for collection.
     * @param <R>        The argument type.
     */
    public static <T, R> void forEach(Collection<T> collection, BiConsumer<? super T, R> action, R arg) {
        requireNonNull(collection, action);
        collection.forEach(consumer(action, arg));
    }

    /**
     * Map collection to a new list according to the mapper
     *
     * @param collection - source collection
     * @param mapper     - mapper that describes how to map each element of the collection
     * @param <T>        - source collection elements type
     * @param <R>        - target list elements type
     * @return mapped list
     */
    public static <T, R> List<R> mapToList(Collection<T> collection, Function<? super T, ? extends R> mapper) {
        requireNonNull(collection, mapper);
        return collectionToMappedStream(collection, mapper).collect(toList());
    }

    /**
     * Map collection to a new list according to the mapper using {@link BiFunction} and additional argument
     *
     * @param collection - source collection
     * @param mapper     - mapper that describes how to map each element of the collection
     * @param arg        - second argument for bi function
     * @param <T>        - source collection elements type
     * @param <R>        - target list elements type
     * @param <S>        - argument type
     * @return mapped list
     */
    public static <T, R, S> List<R> mapToList(Collection<T> collection,
                                              BiFunction<? super T, ? super S, ? extends R> mapper, S arg) {
        requireNonNull(collection, mapper);
        return collectionToMappedStream(collection, function(mapper, arg)).collect(toList());
    }

    /**
     * Map collection to a new set according to the mapper
     *
     * @param collection - source collection
     * @param mapper     - mapper that describes how to map each element of the collection
     * @param <T>        - source collection elements type
     * @param <R>        - target set elements type
     * @return mapped set
     */
    public static <T, R> Set<R> mapToSet(Collection<T> collection, Function<? super T, ? extends R> mapper) {
        requireNonNull(collection, mapper);
        return collectionToMappedStream(collection, mapper).collect(toSet());
    }

    /**
     * Map collection to a new set according to the mapper using {@link BiFunction} and additional argument
     *
     * @param collection - source collection
     * @param mapper     - mapper that describes how to map each element of the collection
     * @param arg        - second argument for bi function
     * @param <T>        - source collection elements type
     * @param <R>        - target set elements type
     * @param <S>        - argument type
     * @return mapped set
     */
    public static <T, R, S> Set<R> mapToSet(Collection<T> collection,
                                            BiFunction<? super T, ? super S, ? extends R> mapper, S arg) {
        requireNonNull(collection, mapper);
        return collectionToMappedStream(collection, function(mapper, arg)).collect(toSet());
    }

    /**
     * Map list to a new list according to the mapper
     *
     * @param list   - source list
     * @param mapper - mapper that describes how to map each element of the collection
     * @param <T>    - source list elements type
     * @param <R>    - target list elements type
     * @return mapped list
     */
    public static <T, R> List<R> map(List<T> list, Function<? super T, ? extends R> mapper) {
        requireNonNull(list, mapper);
        return collectionToMappedStream(list, mapper).collect(toList());
    }

    /**
     * Map set to a new set according to the mapper
     *
     * @param set    - source set
     * @param mapper - mapper that describes how to map each element of the collection
     * @param <T>    - source set elements type
     * @param <R>    - target set elements type
     * @return mapped set
     */
    public static <T, R> Set<R> map(Set<T> set, Function<? super T, ? extends R> mapper) {
        requireNonNull(set, mapper);
        return collectionToMappedStream(set, mapper).collect(toSet());
    }

    /**
     * Map list to a new list according to the mapper using {@link BiFunction} and additional argument
     *
     * @param list   - source list
     * @param mapper - BiFunction mapper that describes how to map each element of the collection
     * @param arg    - second argument for bi function
     * @param <T>    - source list elements type
     * @param <R>    - target list elements type
     * @param <S>    - argument type
     * @return mapped list
     */
    public static <T, R, S> List<R> map(List<T> list, BiFunction<? super T, ? super S, ? extends R> mapper, S arg) {
        return map(list, function(mapper, arg));
    }

    /**
     * Map set to a new set according to the mapper using {@link BiFunction} and additional argument
     *
     * @param set    - source set
     * @param mapper - BiFunction mapper that describes how to map each element of the collection
     * @param arg    - second argument for bi function
     * @param <T>    - source set elements type
     * @param <R>    - target set elements type
     * @param <S>    - argument type
     * @return mapped set
     */
    public static <T, R, S> Set<R> map(Set<T> set, BiFunction<? super T, ? super S, ? extends R> mapper, S arg) {
        return map(set, function(mapper, arg));
    }

    /**
     * Map collection to a new collection according to the mapper
     *
     * @param collection        - source collection
     * @param mapper            - mapper that describes how to map each element of the collection
     * @param collectionFactory - factory that describes how to create instance of the target collection
     * @param <T>               - source collection elements type
     * @param <R>               - target collection elements type
     * @param <U>               - target collection type
     * @return mapped collection of any the selected type
     */
    public static <T, R, U extends Collection<R>> U mapToCollection(Collection<T> collection,
                                                                    Function<? super T, ? extends R> mapper,
                                                                    Supplier<U> collectionFactory) {
        requireNonNull(collection, mapper, collectionFactory);
        return collectionToMappedStream(collection, mapper).collect(toCollection(collectionFactory));
    }

    /**
     * Map collection to a new array according to the mapper
     *
     * @param collection - source collection
     * @param mapper     - mapper that describes how to map each element of the collection
     * @param generator  - generator that describes how to create a new array of the target type
     * @param <T>        - source collection elements type
     * @param <R>        - target array elements type
     * @return mapped array
     */
    public static <T, R> R[] mapToArray(Collection<T> collection,
                                        Function<? super T, ? extends R> mapper,
                                        IntFunction<R[]> generator) {
        requireNonNull(collection, mapper, generator);
        return collectionToMappedStream(collection, mapper).toArray(generator);
    }


    private static <T, R> Stream<? extends R> collectionToMappedStream(Collection<T> collection,
                                                                       Function<? super T, ? extends R> mapper) {
        return collection.stream().map(mapper);
    }

    /**
     * Map collection to a new map using according to mappers provided for keys and values
     *
     * @param collection  - source collection
     * @param keyMapper   - mapper that describes how to map keys
     * @param valueMapper - mapper that describes how to map values
     * @param <T>         - source collection elements type
     * @param <K>         - target map keys type
     * @param <U>         - target map values type
     * @return map containing mapped key/value pairs
     */
    public static <T, K, U> Map<K, U> mapToMap(Collection<T> collection, Function<? super T, ? extends K> keyMapper,
                                               Function<? super T, ? extends U> valueMapper) {
        requireNonNull(collection, keyMapper, valueMapper);
        return collection.stream().collect(toMap(keyMapper, valueMapper));
    }

    /**
     * Map collection to a new map using according to mappers provided for keys and values and merges values for the
     * same keys according to merge function
     *
     * @param collection    - source collection
     * @param keyMapper     - mapper that describes how to map keys
     * @param valueMapper   - mapper that describes how to map values
     * @param mergeFunction - merger in case there are duplicate keys
     * @param <T>           - source collection elements type
     * @param <K>           - target map keys type
     * @param <U>           - target map values type
     * @return map containing mapped key/value pairs
     */
    public static <T, K, U> Map<K, U> mapToMap(Collection<T> collection, Function<? super T, ? extends K> keyMapper,
                                               Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction) {
        requireNonNull(collection, keyMapper, valueMapper, mergeFunction);
        return collection.stream().collect(toMap(keyMapper, valueMapper, mergeFunction));
    }

    /**
     * Map collection to a provided map using according to mappers provided for keys and values and merges values for
     * the same keys according to merge function
     *
     * @param collection    - source collection
     * @param keyMapper     - mapper that describes how to map keys
     * @param valueMapper   - mapper that describes how to map values
     * @param mergeFunction - merger in case there are duplicate keys
     * @param mapSupplier   - factory that describes how to create instance of the target map
     * @param <T>           - source collection elements type
     * @param <K>           - target map keys type
     * @param <U>           - target map values type
     * @param <M>           - target map type
     * @return map containing mapped key/value pairs
     */
    public static <T, K, U, M extends Map<K, U>> Map<K, U> mapToMap(
            Collection<T> collection, Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier) {
        requireNonNull(collection, keyMapper, valueMapper, mergeFunction, mapSupplier);
        return collection.stream().collect(toMap(keyMapper, valueMapper, mergeFunction, mapSupplier));
    }

    /**
     * Map collection to a provided map using according to mappers provided for keys and values
     *
     * @param collection  - source collection
     * @param keyMapper   - mapper that describes how to map keys
     * @param valueMapper - mapper that describes how to map values
     * @param mapSupplier - factory that describes how to create instance of the target map
     * @param <T>         - source collection elements type
     * @param <K>         - target map keys type
     * @param <U>         - target map values type
     * @param <M>         - target map type
     * @return map containing mapped key/value pairs
     */
    public static <T, K, U, M extends Map<K, U>> Map<K, U> mapToMap(
            Collection<T> collection, Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper, Supplier<M> mapSupplier) {
        requireNonNull(collection, keyMapper, valueMapper, mapSupplier);
        return collection.stream().collect(toMap(keyMapper, valueMapper, throwingMerger(), mapSupplier));
    }

    /**
     * Map collection to a new map grouped by key
     *
     * @param collection - source collection
     * @param classifier - mapper that describes how to map keys to group by
     * @param <T>        - source and target inner collection elements type
     * @param <K>        - target map keys type
     * @return map containing mapped key/value pairs
     */
    public static <T, K> Map<K, List<T>> groupingBy(Collection<T> collection,
                                                    Function<? super T, ? extends K> classifier) {
        requireNonNull(collection, classifier);
        return collection.stream().collect(Collectors.groupingBy(classifier));
    }

    /**
     * Map collection to a new map grouped by key and collected with downstream collector
     *
     * @param collection - source collection
     * @param classifier - mapper that describes how to map keys to group by
     * @param downstream - collector to map collection in the value
     * @param <T>        - source and target inner collection elements type
     * @param <K>        - target map keys type
     * @param <A>        - the intermediate accumulation type of the downstream collector
     * @param <D>        - the result type of the downstream reduction
     * @return map containing mapped key/value pairs
     */
    public static <T, K, A, D> Map<K, D> groupingBy(Collection<T> collection,
                                                    Function<? super T, ? extends K> classifier,
                                                    Collector<? super T, A, D> downstream) {
        requireNonNull(collection, classifier, downstream);
        return collection.stream().collect(Collectors.groupingBy(classifier, downstream));
    }

    public static <U> BinaryOperator<U> usingNewMerger() {
        return (o, o2) -> o2;
    }

    public static <U> BinaryOperator<U> usingOldMerger() {
        return (o, o2) -> o2;
    }

    public static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }

}
