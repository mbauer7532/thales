package thales.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import thales.utils.RefModule.Ref;
import thales.utils.TupleModule.Pair;

/**
 * Created by Neo on 2/26/2016.
 */

public final class Functionals {

    private static final class ZipperSpliterator<T, U, W> extends Spliterators.AbstractSpliterator<W> implements Consumer<Object> {

        private final Spliterator<T> mLeftSpliter;
        private final Spliterator<U> mRightSpliter;
        private final BiFunction<? super T, ? super U, ? extends W> mZipper;
        private Object mCache;

        private ZipperSpliterator(
            final Spliterator<T> leftSpliter,
            final Spliterator<U> rightSpliter,
            final BiFunction<? super T, ? super U, ? extends W> zipper,
            final long sizeEst,
            final int additionalCharacteristics) {

            super(sizeEst, additionalCharacteristics);

            mLeftSpliter  = leftSpliter;
            mRightSpliter = rightSpliter;
            mZipper       = zipper;
        }

        public static <T, U, W> ZipperSpliterator<T, U, W> create(
            final Spliterator<T> leftSpliter,
            final Spliterator<U> rightSpliter,
            final BiFunction<? super T, ? super U, ? extends W> zipper,
            final long sizeEst,
            final int additionalCharacteristics) {

            return new ZipperSpliterator<>(leftSpliter, rightSpliter, zipper, sizeEst, additionalCharacteristics);
        }

        @Override
        public void accept(final Object x) {
            mCache = x;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean tryAdvance(final Consumer<? super W> action) {

            if (mLeftSpliter.tryAdvance(this)) {
                final T leftElem = (T) mCache;

                if (mRightSpliter.tryAdvance(this)) {
                    final U rightElem = (U) mCache;

                    action.accept(mZipper.apply(leftElem, rightElem));
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Generates a stream that applies an operation to elements of two streams. For example, we can add the corresponding elements of two streams:<br>
     * s1 = Arrays.streamOf(a1, a2, a3)<br>
     * s2 = Arrays.streamOf(b1, b2, b3)<br>
     * result = zip(s1, s2, (a, b) -&#62; a + b)
     *
     * @param <T> Type of first stream.
     * @param <U> Type of second stream.
     * @param <W> Type of result stream.
     * @param ts First input stream.
     * @param us Second input stream.
     * @param zipper Function that is applied to elements of the streams.
     * @return A new stream that produces results by applying the operation of the given zipper object.
     */
    @SuppressWarnings("unchecked")
    public static <T, U, W> Stream<W> zip(
        final Stream<? extends T> ts,
        final Stream<? extends U> us,
        final BiFunction<? super T, ? super U, ? extends W> zipper) {

        Objects.requireNonNull(zipper);
        final Spliterator<T> leftSpliter  = (Spliterator<T>) Objects.requireNonNull(ts).spliterator();
        final Spliterator<U> rightSpliter = (Spliterator<U>) Objects.requireNonNull(us).spliterator();

        // Combining loses DISTINCT and SORTED characteristics and for other
        // characteristics the combined stream has a characteristic if both
        // streams being combined have the characteristic.
        final int characteristics = leftSpliter.characteristics() & rightSpliter.characteristics() & ~(Spliterator.DISTINCT | Spliterator.SORTED);
        final long size = Math.min(leftSpliter.estimateSize(), rightSpliter.estimateSize());

        final Spliterator<W> cs = ZipperSpliterator.create(leftSpliter, rightSpliter, zipper, size, characteristics);

        return StreamSupport.stream(cs, ts.isParallel() || us.isParallel());
    }

    /**
     * Generates a stream that applies an operation to elements of two lists. For example, we can add the corresponding elements of two lists:<br>
     * s1 = Arrays.streamOf(a1, a2, a3)<br>
     * s2 = Arrays.streamOf(b1, b2, b3)<br>
     * result = zip(s1, s2, (a, b) -&#62; a + b)
     *
     * @param <T> Type of first stream.
     * @param <U> Type of second stream.
     * @param <W> Type of result stream.
     * @param ts First input stream.
     * @param us Second input stream.
     * @param zipper Function that is applied to elements of the streams.
     * @return A new stream that produces results by applying the operation of the given zipper object.
     */
    public static <T, U, W> Stream<W> zip(
        final List<? extends T> ts,
        final List<? extends U> us,
        final BiFunction<? super T, ? super U, ? extends W> zipper) {
        return zip(ts.stream(), us.stream(), zipper);
    }

    /**
     * This method is the same as zip, but with the exception that the second argument is a function that is applied to the elements of the stream given
     * as fist argument.<br>Let <i>x</i> be an element of the ts, <i>g</i> the function passed and <i>f</i> the function that receives x and g(x) as arguments.<br>
     * This method will return:<br>
     * zip(x, g(x), f)
     * @param <T>
     * @param <U>
     * @param <W>
     * @param ts Input stream
     * @param g A function that is applied to elements of ts
     * @param f A function that is applied to elements of ts and to g(ts)
     * @return
     */
    public static <T, U, W> Stream<W> zipWithFObj(final Stream<? extends T> ts, final Function<? super T, ? extends U> g, final BiFunction<? super U, ? super T, ? extends W> f) {
        return ts.map(x -> f.apply(g.apply(x), x));
    }

    /**
     * Convenience method for zipWithFObj(Stream, Function, BinFunction) that receives a List instead.
     * See {@link #zipWithFObj(java.util.stream.Stream, java.util.function.Function, java.util.function.BiFunction) zipWithFObj}
     * @param <T>
     * @param <U>
     * @param <W>
     * @param ts
     * @param g
     * @param f
     * @return
     */
    public static <T, U, W> Stream<W> zipWithFObj(final List<? extends T> ts, final Function<? super T, ? extends U> g, final BiFunction<? super U, ? super T, ? extends W> f) {
        return zipWithFObj(ts.stream(), g, f);
    }

    /**
     * Generates an index i for each element e in ts. Then calls zip(i, e, ts)<br>
     * See {@link #zipWithFObj(java.util.stream.Stream, java.util.function.Function, java.util.function.BiFunction)}
     * @param <T>
     * @param <W>
     * @param ts
     * @param f
     * @return
     */
    public static <T, W> Stream<W> zipWithIndex(final Stream<? extends T> ts, final BiFunction<Integer, ? super T, ? extends W> f) {
        return zip(IntStream.iterate(0, x -> x + 1).boxed(), ts, f);
    }


    /**
     * Convenience method for zipWithIndex(Stream, BiFunction)
     * See {@link #zipWithIndex(java.util.stream.Stream, java.util.function.BiFunction) }
     * @param <T>
     * @param <W>
     * @param ts
     * @param f
     * @return
     */
    public static <T, W> Stream<W> zipWithIndex(final List<? extends T> ts, final BiFunction<Integer, ? super T, ? extends W> f) {
        return zipWithIndex(ts.stream(), f);
    }

    /**
     * Receives two streams as arguments, calls zip on them and applies a function <i>f</i> that receives two arguments,
     * one from ts and one from us. <i>f</i> consumes the result (e.g prints it -- does not return anything)
     * @param <T>
     * @param <U>
     * @param ts
     * @param us
     * @param f
     */
    public static <T, U> void app(
        final Stream<? extends T> ts,
        final Stream<? extends U> us,
        final BiConsumer<? super T, ? super U> f) {
        zip(ts, us, (t, u) -> { f.accept(t, u); return null; }).forEach(x -> {});
    }

    /**
     * Convenience method for {@link #app(java.util.stream.Stream, java.util.stream.Stream, java.util.function.BiConsumer) app}
     * @param <T>
     * @param <U>
     * @param ts
     * @param us
     * @param f
     */
    public static <T, U> void app(
        final List<? extends T> ts,
        final List<? extends U> us,
        final BiConsumer<? super T, ? super U> f) {
        zip(ts.stream(), us.stream(), (t, u) -> { f.accept(t, u); return null; }).forEach(x -> {});
    }

    /**
     * Similar to {@link #zipWithIndex(java.util.stream.Stream, java.util.function.BiFunction) zipWithIndex} but without returning a result.
     *
     * @param <T>
     * @param ts Input stream
     * @param f Function that receives two arguments (one is an Integer) and is the index. The second must be an element from ts
     */
    public static <T> void appWithIndex(final Stream<? extends T> ts, final BiConsumer<Integer, ? super T> f) {
        zipWithIndex(ts, (Integer i, T t) -> {f.accept(i, t); return null; }).forEach(x -> {});
    }

    /**
     * Convenience method for {@link #appWithIndex(java.util.stream.Stream, java.util.function.BiConsumer) appWithIndex}
     * @param <T>
     * @param ts
     * @param f
     */
    public static <T> void appWithIndex(final List<? extends T> ts, final BiConsumer<Integer, ? super T> f) {
        appWithIndex(ts.stream(), f);
    }

    /**
     * @param <T>
     * @return A collector that generates an ArrayList as its collection
     */
    public static <T> Collector<T, ?, List<T>> toArrayList() {
        return Collectors.toCollection(ArrayList::new);
    }

    /**
     * @param <T>
     * @return A collector that generates a LinkedList as its collection
     */
    public static <T> Collector<T, ?, List<T>> toLinkedList() {
        return Collectors.toCollection(LinkedList::new);
    }

    /**
     * @param <T>
     * @return A collector that generates a HashSet as its collection
     */
    public static <T> Collector<T, ?, HashSet<T>> toHashSet() {
        return Collectors.toCollection(HashSet::new);
    }

    /**
     * @param <T>
     * @return A collector that generates a TreeSet as its collection
     */
    public static <T> Collector<T, ?, TreeSet<T>> toTreeSet() {
        return Collectors.toCollection(TreeSet::new);
    }

    /**
     * @param <T>
     * @param <K>
     * @param <U>
     * @param keyMapper
     * @param valueMapper
     * @return A collector that generates a HashMap as its collection
     */
    public static <T, K, U> Collector<T, ?, HashMap<K, U>> toHashMap(final Function<? super T, ? extends K> keyMapper, final Function<? super T, ? extends U> valueMapper) {
        return Collectors.toMap(keyMapper, valueMapper, throwingMerger(), HashMap::new);
    }

    /**
     * Returns a Collector that accumulates elements into a
     * HashMap whose keys and values are the result of applying the provided
     * mapping functions to the input elements.
     *
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @param mergeFunction a merge function, used to resolve collisions between
     *                      values associated with the same key
     * @return a Collector which collects elements into a HashMap
     * whose keys are the result of applying a key mapping function to the input
     * elements, and whose values are the result of applying a value mapping
     * function to all input elements equal to the key and combining them
     * using the merge function
     */
    public static <T, K, U> Collector<T, ?, Map<K,U>> toHashMap(
        final Function<? super T, ? extends K> keyMapper,
        final Function<? super T, ? extends U> valueMapper,
        final BinaryOperator<U> mergeFunction) {

        return Collectors.toMap(keyMapper, valueMapper, mergeFunction, HashMap::new);
    }

    /**
     *
     * @param <T>
     * @param <K>
     * @param <U>
     * @param keyMapper
     * @param valueMapper
     * @return A collector that generates a TreeMap as its collection
     */
    public static <T, K, U> Collector<T, ?, TreeMap<K, U>> toTreeMap(final Function<? super T, ? extends K> keyMapper, final Function<? super T, ? extends U> valueMapper) {
        return Collectors.toMap(keyMapper, valueMapper, throwingMerger(), TreeMap::new);
    }

    /**
     * Returns a Collector that accumulates elements into a
     * TreeMap whose keys and values are the result of applying the provided
     * mapping functions to the input elements.
     *
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param keyMapper a mapping function to produce keys
     * @param valueMapper a mapping function to produce values
     * @param mergeFunction a merge function, used to resolve collisions between
     *                      values associated with the same key
     * @return a Collector which collects elements into a TreeMap
     * whose keys are the result of applying a key mapping function to the input
     * elements, and whose values are the result of applying a value mapping
     * function to all input elements equal to the key and combining them
     * using the merge function
     */
    public static <T, K, U> Collector<T, ?, TreeMap<K, U>> toTreeMap(
        final Function<? super T, ? extends K> keyMapper,
        final Function<? super T, ? extends U> valueMapper,
        final BinaryOperator<U> mergeFunction) {

        return Collectors.toMap(keyMapper, valueMapper, mergeFunction, TreeMap::new);
    }

    /**
     *
     * @param <T>
     * @param <U>
     * @param xOpt An Optional object.
     * @param mapFun Function that received a T and returns a U.
     * @param supFun Function that returns a U. Takes no arguments.
     * @return If xOpt is present, calls mapFun(xOpt), else supFun()
     */
    public static <T, U> U mapOpt(final Optional<T> xOpt, final Function<T, U> mapFun, final Supplier<U> supFun) {

        return xOpt.isPresent() ? mapFun.apply(xOpt.get()) : supFun.get();
    }

    /**
     *
     * @param <T>
     * @param xOpt An Optional object.
     * @param appFun Function that received a T and does something with it (consumers it)
     * @param f A runnable that is run if the value of xOpt is not present (takes no input and returns nothing).
     */
    public static <T> void appOpt(final Optional<T> xOpt, final Consumer<T> appFun, final Runnable f) {

        if (xOpt.isPresent())
            appFun.accept(xOpt.get());
        else
            f.run();
    }

    /**
     * Partitions a stream based on a predicate.
     * @param <T>
     * @param s A stream of T's
     * @param p A Predicate function that takes as input a t (of type T) and return true if t satisfies a condition or false if it doesn't.
     * @return A pair of Lists. The first list contains those t's that satisfied the condition of the given predicate and the second contains those who didn't.
     */
    public static <T> Pair<List<T>, List<T>> partition(final Stream<T> s, final Predicate<T> p) {

        final ArrayList<T> r0 = new ArrayList<>(), r1 = new ArrayList<>();

        s.forEach(x -> { (p.test(x) ? r0 : r1).add(x); });

        return Pair.create(r0, r1);
    }

    public static <T> Pair<List<T>, List<T>> partition(final List<T> lst, final Predicate<T> p) {
        return partition(lst.stream(), p);
    }

    public static <T, Key> List<Integer> findPartitions(final List<T> s, final Function<T, Key> getKey, final BiPredicate<Key, Key> eq) {
        final List<Integer> res = new ArrayList<>();

        if (s.isEmpty()) {
            return res;
        }
        else {
            res.add(0);
            final Ref<Key> key = new Ref<>(getKey.apply(s.get(0)));

            appWithIndex(s, (i, e) -> {
                final Key currentKey = getKey.apply(e);
                if (! eq.test(key.r, currentKey)) {
                    res.add(i);
                    key.r = currentKey;
                }
            });
            res.add(s.size());
        }

        return res;
    }

    public static <T> List<List<T>> splitByIndexes(final List<T> s, final List<Integer> indexes) {

        return zipPairwiseToStream(indexes, Pair::create)
            .map(p -> s.subList(p.getFirst(), p.getSecond()))
            .collect(Functionals.toArrayList());
    }

    public static <T, W> Stream<W> zipPairwiseToStream(final List<T> lst, final BiFunction<T, T, W> f) {
        final int size = lst.size();
        return zip(lst.subList(0, size - 1), lst.subList(1, size), f);
    }

    public static <T, W> List<W> zipPairwiseToList(final List<T> lst, final BiFunction<T, T, W> f) {
        return zipPairwiseToStream(lst, f).collect(Functionals.toArrayList());
    }

    public static <T> List<List<T>> splitIntoSublists(final List<T> lst, final int n) {
        if (n < 1) {
            throw new IllegalArgumentException("N must be greater than or equal to 1.");
        }

        final List<List<T>> res = new ArrayList<>();
        final int len = lst.size();

        for (int i = 0; i < len; i += n) {
            final int m = Math.min(i + n, len);
            res.add(lst.subList(i, m));
        }

        return res;
    }

    /**
     * Converts an Optional to a Stream
     * @param <T>
     * @param opt
     * @return
     */
    public static <T> Stream<T> optToStream(final Optional<T> opt) {
        return opt.isPresent() ? Stream.of(opt.get()) : Stream.empty();
    }

    /**
     * Converts an Object to a Stream
     * @param <T>
     * @param obj
     * @return
     */
    public static <T> Stream<T> objToStream(final T obj) {
        return obj != null ? Stream.of(obj) : Stream.empty();
    }

    /**
     * Turns an iterator into a stream.  When the stream is consumed so will the iterator.
     * @param <T> The element type of the iterator.
     * @param iterator The iterator in question.
     * @return A Stream of the elements that the iterator refers to.
     */
    public static <T> Stream<T> toStream(final Iterator<T> iterator) {
        final Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED);
        return StreamSupport.stream(spliterator, false);
    }

    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); };
    }

    @FunctionalInterface
    public interface IntBiConsumer {
        public void accept(final int x, final int y);
    }

    @FunctionalInterface
    public interface IntObjConsumer<T> {
        public void accept(final int x, final T obj);
    }

    @FunctionalInterface
    public interface IntObjObjConsumer<T, U> {
        public void accept(final int x, final T obj1, final U obj2);
    }

    /**
     * @param <T>
     * @param val
     * @param low
     * @param high
     * @return <em>true</em> if val is in the range [low, high], else <em>false</em>.
     * @throws IllegalArgumentException is low is larger than high.
     */
    public static <T extends Comparable<T>> boolean isBetweenInclusive(final T val, final T low, final T high) {

        if (low.compareTo(high) > 0)
            throw new IllegalArgumentException(String.format("'low' cannot be larger than 'high', but low was %s and high was %s.", low.toString(), high.toString()));

        return val.compareTo(low) >= 0 && val.compareTo(high) <= 0;
    }

    /**
     * Applies the given function to two Optional values. If any of the equals Optional.empty(), the result
     * will be Optional.empty().
     * @param optA
     * @param optB
     * @param operation
     * @return
     */
    public static <T, U, R> Optional<R> optFunc(final Optional<T> optA, final Optional<U> optB, final BiFunction<T, U, R> operation) {

        return optA.flatMap(a -> optB.map(b -> operation.apply(a, b)));
    }

    /**
     * Returns the value to which the specified key is mapped, or Optional.empty() if this map contains no mapping for the key.
     *
     * In other words:<br>
     * <code>
     * mapGet(map, key);
     * </code>
     *
     * is equivalent to
     * <code>
     * Optional.ofNullable(map.get(key));
     * </code>
     */
    public static <K, V> Optional<V> mapGet(final Map<K, V> m, final K key) {

        return Optional.ofNullable(m.get(key));
    }

    /**
     * @param pred The predigate to be negated.
     * @param <V> A predicate on Vs.
     * @return The negated predicate.
     */
    public static <V> Predicate<V> negate(final Predicate<V> pred) {
        return pred.negate();
    }

    public static <T, U, R> Function<T, R> compose(final Function<U, R> f1, final Function<T, U> f2) {
        return f1.compose(f2);
    }

    public static <T, U> Consumer<T> compose(final Consumer<U> f1, final Function<T, U> f2) {
        return t -> f1.accept(f2.apply(t));
    }

    public static <K, V> V getOrDefault(final Map<K, V> m, final K k, final Supplier<V> f) {
        final V v = m.get(k);
        return v == null ? f.get() : v;
    }

    public static <K, A, B> Pair<Map<K, A>, Map<K, B>> partitionEithersMap(final Map<K, Either<A, B>> m) {

        final Map<K, A> lefts = new HashMap<>();
        final Map<K, B> rights = new HashMap<>();

        m.entrySet().forEach(e -> e.getValue().consume(le -> lefts.put(e.getKey(), le), ri -> rights.put(e.getKey(), ri)));

        return Pair.create(lefts, rights);
    }

    /**
     * Returns a Collector implementing a cascaded "group by" operation
     * on input elements of type T, grouping elements according to a
     * classification function, and then performing a reduction operation on
     * the values associated with a given key using the specified downstream
     * Collector.
     *
     * @param <T> the type of the input elements
     * @param <K> the type of the keys
     * @param <A> the intermediate accumulation type of the downstream collector
     * @param <D> the result type of the downstream reduction
     * @param classifier a classifier function mapping input elements to keys
     * @param downstream a {@code Collector} implementing the downstream reduction
     * @return a {@code Collector} implementing the cascaded group-by operation collecting the
     *          results in a {@link HashMap}
     */
    public static <T, K, A, D> Collector<T, ?, HashMap<K, D>> groupingByToHashMap(
        final Function<? super T, ? extends K> classifier,
        final Collector<? super T, A, D> downstream) {

        return Collectors.groupingBy(classifier, HashMap::new, downstream);
    }

    /**
     * Returns a Collector implementing a "group by" operation on
     * input elements of type T, grouping elements according to a
     * classification function, and returning the results in a {@code Map}.
     *
     * @param <T> the type of the input elements
     * @param <K> the type of the keys
     * @param classifier the classifier function mapping input elements to keys
     * @return a {@code Collector} implementing the group-by operation collecting the
     *          results in a {@link HashMap}
     */
    public static <T, K> Collector<T, ?, HashMap<K, List<T>>> groupingByToHashMap(final Function<? super T, ? extends K> classifier) {

        return groupingByToHashMap(classifier, Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns a Collector implementing a cascaded "group by" operation
     * on input elements of type T, grouping elements according to a
     * classification function, and then performing a reduction operation on
     * the values associated with a given key using the specified downstream
     * Collector.
     *
     * @param <T> the type of the input elements
     * @param <K> the type of the keys
     * @param <A> the intermediate accumulation type of the downstream collector
     * @param <D> the result type of the downstream reduction
     * @param classifier a classifier function mapping input elements to keys
     * @param downstream a {@code Collector} implementing the downstream reduction
     * @return a {@code Collector} implementing the cascaded group-by operation collecting the
     *          results in a {@link TreeMap}
     */
    public static <T, K, A, D> Collector<T, ?, TreeMap<K, D>> groupingByToTreeMap(
        final Function<? super T, ? extends K> classifier,
        final Collector<? super T, A, D> downstream) {

        return Collectors.groupingBy(classifier, TreeMap::new, downstream);
    }

    /**
     * Returns a Collector implementing a cascaded "group by" operation
     * on input elements of type T, grouping elements according to a
     * classification function, and then performing a reduction operation on
     * the values associated with a given key using the specified downstream
     * Collector.
     *
     * @param <T> the type of the input elements
     * @param <K> the type of the keys
     * @param <A> the intermediate accumulation type of the downstream collector
     * @param <D> the result type of the downstream reduction
     * @param classifier a classifier function mapping input elements to keys
     * @return a {@code Collector} implementing the cascaded group-by operation collecting the
     *          results in a {@link TreeMap}
     */
    public static <T, K, A, D> Collector<T, ?, TreeMap<K, List<T>>> groupingByToTreeMap(final Function<? super T, ? extends K> classifier) {

        return groupingByToTreeMap(classifier, Collectors.toCollection(ArrayList::new));
    }

    @FunctionalInterface
    public interface TriFunction<T1, T2, T3, R> {

        /**
         * Applies this function to the given arguments.
         *
         * @param t1 the first function argument
         * @param t2 the second function argument
         * @param t3 the second function argument
         * @return the function result
         */
        R apply(final T1 t1, final T2 t2, final T3 t3);
    }

    @FunctionalInterface
    public interface TriConsumer<T1, T2, T3> {

        /**
         * Applies this function to the given arguments.
         *
         * @param t1 the first function argument
         * @param t2 the second function argument
         * @param t3 the second function argument
         * @return the function result
         */
        void accept(final T1 t1, final T2 t2, final T3 t3);
    }

    public static <A> boolean hasDuplicates(final List<A> as) {

        if (as.size() < 2)
            return false;

        final Set<A> sa = new HashSet<>(as.size(), 1);

        for (final A a: as) {
            final boolean added = sa.add(a);

            if (! added) {
                return true;
            }
        }

        return false;
    }

    public static <T> Stream<T> iterate(final int limit, final IntFunction<T> f) {
        return IntStream.range(0, limit).mapToObj(f);
    }
}
