package com.dvelopp.functional.utils;

import com.dvelopp.functional.utils.interfaces.TriConsumer;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dvelopp.functional.utils.FunctionUtils.consumer;
import static com.dvelopp.functional.utils.FunctionUtils.function;
import static com.dvelopp.functional.utils.ObjectUtils.requireNonNull;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

/**
 * Set of useful methods to work with collections.
 * There are two types of methods. Some of the methods extend the functionality provided by Java while other methods
 * make invocations of the same things more concise and easier than in Java.
 *
 * @since 1.0
 */
public final class CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException();
    }

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
     * For each loop that consumes BiConsumer function.
     * That is made to provide default for each statement with ability to have an additional argument to the function
     * in order to allow developers to implement such cases in functional style.
     *
     * @param array  The array to iterated over.
     * @param action The action to be performed for each element.
     * @param arg    The argument that is considered second argument in the function.
     * @param <T>    The elements type for collection.
     * @param <R>    The argument type.
     */
    public static <T, R> void forEach(T[] array, BiConsumer<? super T, R> action, R arg) {
        forEachWithArray(action, arg, array);
    }

    /**
     * For each loop that consumes BiConsumer function.
     * That is made to provide default for each statement with ability to have an additional argument to the function
     * in order to allow developers to implement such cases in functional style.
     *
     * @param action The action to be performed for each element.
     * @param arg    The argument that is considered second argument in the function.
     * @param array  The array to iterated over(varargs).
     * @param <T>    The elements type for collection.
     * @param <R>    The argument type.
     */
    @SafeVarargs
    public static <T, R> void forEach(BiConsumer<? super T, R> action, R arg, T... array) {
        forEachWithArray(action, arg, array);
    }

    private static <T, R> void forEachWithArray(BiConsumer<? super T, R> action, R arg, T[] array) {
        requireNonNull(array, action);
        Stream.of(array).forEach(consumer(action, arg));
    }

    /**
     * For each loop that consumes TriConsumer function.
     * That is made to provide default for each statement with ability to have two additional arguments to the function
     * in order to allow developers to implement such cases in functional style.
     *
     * @param collection The collection to iterated over(elements of the collections are the first argument)
     * @param action     The action to be performed for each element.
     * @param firstArg   The argument that is considered second argument in the function.
     * @param secondArg  The argument that is considered third argument in the function.
     * @param <T>        The elements type for collection.
     * @param <R1>       The first argument type. (second logical argument)
     * @param <R2>       The second argument type. (third logical argument)
     */
    public static <T, R1, R2> void forEach(Collection<T> collection, TriConsumer<? super T, R1, R2> action,
                                           R1 firstArg, R2 secondArg) {
        requireNonNull(collection, action);
        collection.forEach(consumer(action, firstArg, secondArg));
    }

    /**
     * For each loop that consumes TriConsumer function.
     * That is made to provide default for each statement with ability to have two additional arguments to the function
     * in order to allow developers to implement such cases in functional style.
     *
     * @param array     The array to iterated over. (elements of the array are the first argument)
     * @param action    The action to be performed for each element.
     * @param firstArg  The argument that is considered second argument in the function.
     * @param secondArg The argument that is considered third argument in the function.
     * @param <T>       The elements type for collection.
     * @param <R1>      The first argument type. (second logical argument)
     * @param <R2>      The second argument type. (third logical argument)
     */
    public static <T, R1, R2> void forEach(T[] array, TriConsumer<? super T, R1, R2> action,
                                           R1 firstArg, R2 secondArg) {
        forEachWithArray(action, firstArg, secondArg, array);
    }

    /**
     * For each loop that consumes TriConsumer function and varargs way to specify an array.
     * That is made to provide default for each statement with ability to have two additional arguments to the function
     * in order to allow developers to implement such cases in functional style.
     *
     * @param array     The array to iterated over. (elements of the array are the first argument)
     * @param action    The action to be performed for each element.
     * @param firstArg  The argument that is considered second argument in the function.
     * @param secondArg The argument that is considered third argument in the function.
     * @param <T>       The elements type for collection.
     * @param <R1>      The first argument type. (second logical argument)
     * @param <R2>      The second argument type. (third logical argument)
     */
    @SafeVarargs
    public static <T, R1, R2> void forEach(TriConsumer<? super T, R1, R2> action,
                                           R1 firstArg, R2 secondArg, T... array) {
        forEachWithArray(action, firstArg, secondArg, array);
    }

    private static <T, R1, R2> void forEachWithArray(TriConsumer<? super T, R1, R2> action,
                                                     R1 firstArg, R2 secondArg, T[] array) {
        requireNonNull(array, action);
        Stream.of(array).forEach(consumer(action, firstArg, secondArg));
    }

    /**
     * Returns a list consisting of the results of applying the given function to the elements of the given collection.
     *
     * @param collection The source collection.
     * @param mapper     The function to apply to each element.
     * @param <T>        The source collection elements type.
     * @param <R>        The target list elements type.
     * @return the new list that contains result of applying the function for elements of given collection.
     */
    public static <T, R> List<R> mapToList(Collection<T> collection, Function<? super T, ? extends R> mapper) {
        requireNonNull(collection, mapper);
        return collectionToMappedStream(collection, mapper).collect(toList());
    }

    /**
     * Returns a list consisting of the results of applying the given function to the elements of the given collection.
     * Consumes additional argument and requires BiFunction. It can be useful when there is invocation of the method
     * with two arguments or just method with one argument, but on instance variable. It helps to write a lambda or
     * method reference cleaner in that case.
     *
     * @param collection The source collection.
     * @param mapper     The function to apply to each element.
     * @param arg        The second argument that is used for {@link BiFunction}.
     * @param <T>        The source collection elements type.
     * @param <R>        The target list elements type.
     * @param <S>        The second argument type.
     * @return the new list that contains result of applying the function for elements of given collection.
     */
    public static <T, R, S> List<R> mapToList(Collection<T> collection,
                                              BiFunction<? super T, ? super S, ? extends R> mapper, S arg) {
        requireNonNull(collection, mapper);
        return collectionToMappedStream(collection, function(mapper, arg)).collect(toList());
    }

    /**
     * Returns a set consisting of the results of applying the given function to the elements of the given collection.
     *
     * @param collection The source collection.
     * @param mapper     The function to apply to each element.
     * @param <T>        The source collection elements type.
     * @param <R>        The target set elements type.
     * @return the new set that contains result of applying the function for elements of given collection.
     */
    public static <T, R> Set<R> mapToSet(Collection<T> collection, Function<? super T, ? extends R> mapper) {
        requireNonNull(collection, mapper);
        return collectionToMappedStream(collection, mapper).collect(toSet());
    }

    /**
     * Returns a set consisting of the results of applying the given function to the elements of the given collection.
     * Consumes additional argument and requires BiFunction. It can be useful when there is invocation of the method
     * with two arguments or just method with one argument, but on instance variable. It helps to write a lambda or
     * method reference cleaner in that case.
     *
     * @param collection The source collection.
     * @param mapper     The function to apply to each element.
     * @param arg        The second argument that is used for {@link BiFunction}.
     * @param <T>        The source collection elements type.
     * @param <R>        The target set elements type.
     * @param <S>        The second argument type.
     * @return the new set that contains result of applying the function for elements of given collection.
     */
    public static <T, R, S> Set<R> mapToSet(Collection<T> collection,
                                            BiFunction<? super T, ? super S, ? extends R> mapper, S arg) {
        requireNonNull(collection, mapper);
        return collectionToMappedStream(collection, function(mapper, arg)).collect(toSet());
    }

    /**
     * Returns a list consisting of the results of applying the given function to the elements of the given list.
     * Short form of invocation.
     *
     * @param list   The source list.
     * @param mapper The function to apply to each element.
     * @param <T>    The source collection elements type.
     * @param <R>    The target list elements type.
     * @return the new list that contains result of applying the function for elements of given collection.
     */
    public static <T, R> List<R> map(List<T> list, Function<? super T, ? extends R> mapper) {
        requireNonNull(list, mapper);
        return collectionToMappedStream(list, mapper).collect(toList());
    }

    /**
     * Returns a set consisting of the results of applying the given function to the elements of the given set.
     * Short form of invocation.
     *
     * @param set    The source set.
     * @param mapper The function to apply to each element.
     * @param <T>    The source collection elements type.
     * @param <R>    The target set elements type.
     * @return the new set that contains result of applying the function for elements of given collection.
     */
    public static <T, R> Set<R> map(Set<T> set, Function<? super T, ? extends R> mapper) {
        requireNonNull(set, mapper);
        return collectionToMappedStream(set, mapper).collect(toSet());
    }

    /**
     * Returns a list consisting of the results of applying the given function to the elements of the given list.
     * Consumes additional argument and requires BiFunction. It can be useful when there is invocation of the method
     * with two arguments or just method with one argument, but on instance variable. It helps to write a lambda or
     * method reference cleaner in that case.
     *
     * @param list   The source list.
     * @param mapper The function to apply to each element.
     * @param arg    The second argument that is used for {@link BiFunction}.
     * @param <T>    The source collection elements type.
     * @param <R>    The target list elements type.
     * @param <S>    The second argument type.
     * @return the new list that contains result of applying the function for elements of given list.
     */
    public static <T, R, S> List<R> map(List<T> list, BiFunction<? super T, ? super S, ? extends R> mapper, S arg) {
        return map(list, function(mapper, arg));
    }

    /**
     * Returns a set consisting of the results of applying the given function to the elements of the given set.
     * Consumes additional argument and requires BiFunction. It can be useful when there is invocation of the method
     * with two arguments or just method with one argument, but on instance variable. It helps to write a lambda or
     * method reference cleaner in that case.
     *
     * @param set    The source set.
     * @param mapper The function to apply to each element.
     * @param arg    The second argument that is used for {@link BiFunction}.
     * @param <T>    The source collection elements type.
     * @param <R>    The target set elements type.
     * @param <S>    The second argument type.
     * @return the new set that contains result of applying the function for elements of given set.
     */
    public static <T, R, S> Set<R> map(Set<T> set, BiFunction<? super T, ? super S, ? extends R> mapper, S arg) {
        return map(set, function(mapper, arg));
    }

    /**
     * Returns a collection consisting of the results of applying the given function to the elements of the
     * given collection. Consumer the supplier for the new collection.
     *
     * @param collection        The source collection.
     * @param mapper            The function to apply to each element.
     * @param collectionFactory The new collection supplier.
     * @param <T>               The source collection elements type.
     * @param <R>               The target list elements type.
     * @param <U>               The target collection elements type.
     * @return the new list that contains result of applying the function for elements of given collection.
     */
    public static <T, R, U extends Collection<R>> U mapToCollection(Collection<T> collection,
                                                                    Function<? super T, ? extends R> mapper,
                                                                    Supplier<U> collectionFactory) {
        requireNonNull(collection, mapper, collectionFactory);
        return collectionToMappedStream(collection, mapper).collect(toCollection(collectionFactory));
    }

    /**
     * Returns an array consisting of the results of applying the given function to the elements of the given
     * collection.
     *
     * @param collection The source collection.
     * @param mapper     The function to apply to each element.
     * @param <T>        The source collection elements type.
     * @param <R>        The target array elements type.
     * @return the new array that contains result of applying the function for elements of given collection.
     */
    public static <T, R> R[] mapToArray(Collection<T> collection,
                                        Function<? super T, ? extends R> mapper,
                                        IntFunction<R[]> generator) {
        requireNonNull(collection, mapper, generator);
        return collectionToMappedStream(collection, mapper).toArray(generator);
    }

    /**
     * Returns a map consisting of the results of applying the given key/value extraction functions to the elements
     * of the given collection.
     *
     * @param collection  The source collection.
     * @param keyMapper   The function to apply to each element to get a key.
     * @param valueMapper The function to apply to each element to get a value.
     * @param <T>         The source collection elements type.
     * @param <K>         The target map keys type.
     * @param <U>         The target map values type.
     * @return the new map containing mapped key/value pairs.
     */
    public static <T, K, U> Map<K, U> mapToMap(Collection<T> collection, Function<? super T, ? extends K> keyMapper,
                                               Function<? super T, ? extends U> valueMapper) {
        requireNonNull(collection, keyMapper, valueMapper);
        return collection.stream().collect(toMap(keyMapper, valueMapper));
    }

    /**
     * Returns a map consisting of the results of applying the given key/value extraction functions to the elements
     * of the given collection and merges values for the same keys according to merge function.
     *
     * @param collection    The source collection.
     * @param keyMapper     The function to apply to each element to get a key.
     * @param valueMapper   The function to apply to each element to get a value.
     * @param mergeFunction The merger function in case there are duplicate keys.
     * @param <T>           The source collection elements type.
     * @param <K>           The target map keys type.
     * @param <U>           The target map values type.
     * @return the new map containing mapped key/value pairs.
     */
    public static <T, K, U> Map<K, U> mapToMap(Collection<T> collection, Function<? super T, ? extends K> keyMapper,
                                               Function<? super T, ? extends U> valueMapper,
                                               BinaryOperator<U> mergeFunction) {
        requireNonNull(collection, keyMapper, valueMapper, mergeFunction);
        return collection.stream().collect(toMap(keyMapper, valueMapper, mergeFunction));
    }

    /**
     * Returns the provided in supplier map consisting of the results of applying the given key/value
     * extraction functions to the elements of the given collection and merges values for the same keys according
     * to merge function.
     *
     * @param collection    The source collection.
     * @param keyMapper     The function to apply to each element to get a key.
     * @param valueMapper   The function to apply to each element to get a value.
     * @param mergeFunction The merger function in case there are duplicate keys.
     * @param mapSupplier   The factory that describes how to get an instance of the target map.
     * @param <T>           The source collection elements type.
     * @param <K>           The target map keys type.
     * @param <U>           The target map values type.
     * @param <M>           The target map type.
     * @return the map provided in supplier containing new mapped key/value pairs.
     */
    public static <T, K, U, M extends Map<K, U>> Map<K, U> mapToMap(
            Collection<T> collection, Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier) {
        requireNonNull(collection, keyMapper, valueMapper, mergeFunction, mapSupplier);
        return collection.stream().collect(toMap(keyMapper, valueMapper, mergeFunction, mapSupplier));
    }

    /**
     * Returns the provided in supplier map consisting of the results of applying the given key/value
     * extraction functions to the elements of the given collection.
     *
     * @param collection  The source collection.
     * @param keyMapper   The function to apply to each element to get a key.
     * @param valueMapper The function to apply to each element to get a value.
     * @param mapSupplier The factory that describes how to get an instance of the target map.
     * @param <T>         The source collection elements type.
     * @param <K>         The target map keys type.
     * @param <U>         The target map values type.
     * @param <M>         The target map type.
     * @return the map provided in supplier containing new mapped key/value pairs.
     */
    public static <T, K, U, M extends Map<K, U>> Map<K, U> mapToMap(
            Collection<T> collection, Function<? super T, ? extends K> keyMapper,
            Function<? super T, ? extends U> valueMapper, Supplier<M> mapSupplier) {
        requireNonNull(collection, keyMapper, valueMapper, mapSupplier);
        return collection.stream().collect(toMap(keyMapper, valueMapper, throwingMerger(), mapSupplier));
    }

    /**
     * Returns an {@code IntSummaryStatistics} describing various
     * summary data about the elements of this stream.  This is a special
     * case of a <a href="package-summary.html#Reduction">reduction</a>.
     *
     * @param collection  The source collection.
     * @param toIntMapper The <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                    <a href="package-summary.html#Statelessness">stateless</a>
     *                    function to apply to each element
     * @param <T>         The source collection elements type.
     * @return The {@code IntSummaryStatistics} describing various summary data
     * about the elements of this stream
     */
    public static <T> IntSummaryStatistics getSummaryStatistics(Collection<T> collection,
                                                                ToIntFunction<? super T> toIntMapper) {
        requireNonNull(collection, toIntMapper);
        return collection.stream().mapToInt(toIntMapper).summaryStatistics();
    }

    /**
     * Returns an {@code DoubleSummaryStatistics} describing various
     * summary data about the elements of this stream.  This is a special
     * case of a <a href="package-summary.html#Reduction">reduction</a>.
     *
     * @param collection     The source collection.
     * @param toDoubleMapper The <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                       <a href="package-summary.html#Statelessness">stateless</a>
     *                       function to apply to each element
     * @param <T>            The source collection elements type.
     * @return The {@code DoubleSummaryStatistics} describing various summary data
     * about the elements of this stream
     */
    public static <T> DoubleSummaryStatistics getSummaryStatistics(Collection<T> collection,
                                                                   ToDoubleFunction<? super T> toDoubleMapper) {
        requireNonNull(collection, toDoubleMapper);
        return collection.stream().mapToDouble(toDoubleMapper).summaryStatistics();
    }

    /**
     * Returns an {@code LongSummaryStatistics} describing various
     * summary data about the elements of this stream.  This is a special
     * case of a <a href="package-summary.html#Reduction">reduction</a>.
     *
     * @param collection   The source collection.
     * @param toLongMapper The <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                     <a href="package-summary.html#Statelessness">stateless</a>
     *                     function to apply to each element
     * @param <T>          The source collection elements type.
     * @return The {@code LongSummaryStatistics} describing various summary data
     * about the elements of this stream
     */
    public static <T> LongSummaryStatistics getSummaryStatistics(Collection<T> collection,
                                                                 ToLongFunction<? super T> toLongMapper) {
        requireNonNull(collection, toLongMapper);
        return collection.stream().mapToLong(toLongMapper).summaryStatistics();
    }

    /**
     * Returns a new map that contains grouped result of applying classifier function on the elements. The classifier
     * determines how to create a group - key. According the created key a list is collected containing as a value all
     * the elements that fulfil that key.
     *
     * @param collection The source collection.
     * @param classifier The classifier function to apply to each element to get a key.
     * @param <T>        The source and target inner collection elements type.
     * @param <K>        The target map keys type.
     * @return the new map containing mapped key/value pairs of grouped result.
     */
    public static <T, K> Map<K, List<T>> groupingBy(Collection<T> collection,
                                                    Function<? super T, ? extends K> classifier) {
        requireNonNull(collection, classifier);
        return collection.stream().collect(Collectors.groupingBy(classifier));
    }

    /**
     * Returns a new map that contains grouped result of applying classifier function on the elements. The classifier
     * determines how to create a group - key. According the created key a list is collected containing as a value all
     * the elements mapped according to downstream function.
     *
     * @param collection The source collection.
     * @param classifier The classifier function to apply to each element to get a key.
     * @param downstream The collector to map collection in the value.
     * @param <T>        The source and target inner collection elements type.
     * @param <K>        The target map keys type.
     * @param <A>        The intermediate accumulation type of the downstream collector.
     * @param <D>        The result type of the downstream reduction.
     * @return the new map containing mapped key/value pairs of grouped result after reduction.
     */
    public static <T, K, A, D> Map<K, D> groupingBy(Collection<T> collection,
                                                    Function<? super T, ? extends K> classifier,
                                                    Collector<? super T, A, D> downstream) {
        requireNonNull(collection, classifier, downstream);
        return collection.stream().collect(Collectors.groupingBy(classifier, downstream));
    }

    /**
     * Returns a new map that is provided by map factory that contains grouped result of applying classifier function
     * on the elements. The classifier determines how to create a group - key. According the created key a list
     * is collected containing as a value all the elements mapped according to downstream function.
     *
     * @param collection The source collection.
     * @param classifier The classifier function to apply to each element to get a key.
     * @param mapFactory The function which, when called, produces a new empty {@code Map} of the desired type.
     * @param downstream The collector to map collection in the value.
     * @param <T>        The source and target inner collection elements type.
     * @param <K>        The target map keys type.
     * @param <A>        The intermediate accumulation type of the downstream collector.
     * @param <D>        The result type of the downstream reduction.
     * @param <M>        The type of the resulting {@code Map}.
     * @return the new map containing mapped key/value pairs of grouped result after reduction.
     */
    public static <T, K, A, D, M extends Map<K, D>> Map<K, D> groupingBy(Collection<T> collection,
                                                                         Function<? super T, ? extends K> classifier,
                                                                         Supplier<M> mapFactory,
                                                                         Collector<? super T, A, D> downstream) {
        requireNonNull(collection, classifier, downstream, mapFactory);
        return collection.stream().collect(Collectors.groupingBy(classifier, mapFactory, downstream));
    }

    /**
     * Concurrent implementation of {@link this#groupingBy(Collection, Function)} method.
     *
     * @param collection The source collection.
     * @param classifier The classifier function to apply to each element to get a key.
     * @param <T>        The source and target inner collection elements type.
     * @param <K>        The target map keys type.
     * @return the new map containing mapped key/value pairs of grouped result.
     */
    public static <T, K> Map<K, List<T>> groupingByConcurrent(Collection<T> collection,
                                                              Function<? super T, ? extends K> classifier) {
        requireNonNull(collection, classifier);
        return collection.stream().collect(Collectors.groupingByConcurrent(classifier));
    }

    /**
     * Concurrent implementation of {@link this#groupingBy(Collection, Function, Collector)} method.
     *
     * @param collection The source collection.
     * @param classifier The classifier function to apply to each element to get a key.
     * @param downstream The collector to map collection in the value.
     * @param <T>        The source and target inner collection elements type.
     * @param <K>        The target map keys type.
     * @param <A>        The intermediate accumulation type of the downstream collector.
     * @param <D>        The result type of the downstream reduction.
     * @return the new map containing mapped key/value pairs of grouped result after reduction.
     */
    public static <T, K, A, D> Map<K, D> groupingByConcurrent(Collection<T> collection,
                                                              Function<? super T, ? extends K> classifier,
                                                              Collector<? super T, A, D> downstream) {
        requireNonNull(collection, classifier, downstream);
        return collection.stream().collect(Collectors.groupingByConcurrent(classifier, downstream));
    }

    /**
     * Concurrent implementation of {@link this#groupingBy(Collection, Function, Supplier, Collector)} method.
     *
     * @param collection The source collection.
     * @param classifier The classifier function to apply to each element to get a key.
     * @param mapFactory The function which, when called, produces a new empty {@code ConcurrentMap}
     *                   of the desired type.
     * @param downstream The collector to map collection in the value.
     * @param <T>        The source and target inner collection elements type.
     * @param <K>        The target map keys type.
     * @param <A>        The intermediate accumulation type of the downstream collector.
     * @param <D>        The result type of the downstream reduction.
     * @param <M>        The type of the resulting {@code ConcurrentMap}.
     * @return the new map containing mapped key/value pairs of grouped result after reduction.
     */
    public static <T, K, A, D, M extends ConcurrentMap<K, D>> Map<K, D> groupingByConcurrent(
            Collection<T> collection, Function<? super T, ? extends K> classifier,
            Supplier<M> mapFactory, Collector<? super T, A, D> downstream) {
        requireNonNull(collection, classifier, downstream, mapFactory);
        return collection.stream().collect(Collectors.groupingByConcurrent(classifier, mapFactory, downstream));
    }

    /**
     * Copy of {@link Collectors#throwingMerger()}. Since original method has private access level and can't be accessed
     * outside the class.
     *
     * {@link BinaryOperator} that throws {@link IllegalStateException}.
     */
    private static <T> BinaryOperator<T> throwingMerger() {
        return (key, value) -> {
            throw new IllegalStateException(format("Duplicate key %s", key));
        };
    }

    /**
     * Create a stream for the collection. If collection is null, then empty stream is returned.
     *
     * @param collection The collection to create a stream from.
     * @param <T>        The type of the elements.
     * @return a valid stream for the collection.
     */
    public static <T> Stream<T> safeStream(Collection<T> collection) {
        return collection == null ? Stream.empty() : collection.stream();
    }

    /**
     * Create a parallel stream for the collection. If collection is null, then empty stream is returned.
     *
     * @param collection The collection to create a stream from.
     * @param <T>        The type of the elements.
     * @return a valid parallel stream for the collection.
     */
    public static <T> Stream<T> safeParallelStream(Collection<T> collection) {
        return collection == null ? Stream.<T>empty().parallel() : collection.parallelStream();
    }

    /**
     * Creates a lazily concatenated stream whose elements are all the
     * elements of the first collection followed by all the elements of the
     * second collection.  The resulting stream is ordered if both
     * of the input collections are ordered, and parallel if either of the input
     * collection is parallel.
     *
     * @param <T>         The type of stream elements.
     * @param collection1 The first stream.
     * @param collection2 The second stream.
     * @return the concatenation of the two input collections.
     */
    public static <T> Stream<T> concatSafeStream(Collection<T> collection1, Collection<T> collection2) {
        return Stream.concat(safeStream(collection1), safeStream(collection2));
    }

    /**
     * Shortcut for getting stream from collection and mapping it using the mapper function.
     *
     * @param collection The source collection.
     * @param mapper     The function to apply to each element of the collection.
     * @param <T>        The source collection type.
     * @param <R>        The target collection type.
     * @return the new collection that containing results of applying the merger function for each element.
     */
    private static <T, R> Stream<? extends R> collectionToMappedStream(Collection<T> collection,
                                                                       Function<? super T, ? extends R> mapper) {
        return collection.stream().map(mapper);
    }

}
