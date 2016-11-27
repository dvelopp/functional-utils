package com.dvelopp.functional.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Stream;

import static com.dvelopp.functional.utils.FunctionUtils.consumer;
import static com.dvelopp.functional.utils.FunctionUtils.function;
import static com.dvelopp.functional.utils.ObjectUtils.requireNonNull;
import static java.util.stream.Collectors.*;

/**
 * Utility class to improve the way working with collection is implemented by default.
 */
public final class CollectionUtils {

    /**
     * For each with BiConsumer function.
     * This for each can take an additional argument to the function in order to allow developers
     * to implement such cases in functional style (Java 8)
     *
     * @param collection - the collection to use in for each function
     * @param action     - the bi consumer based function to perform on each element with passed argument
     * @param arg        - argument to send as the second argument to bi consumer function
     * @param <T>        - type of the elements in the collection
     * @param <R>        - second argument type
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

}
