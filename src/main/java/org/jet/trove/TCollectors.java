package org.jet.trove;

import gnu.trove.list.TByteList;
import gnu.trove.list.TCharList;
import gnu.trove.list.TDoubleList;
import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import gnu.trove.list.TLongList;
import gnu.trove.list.TShortList;
import gnu.trove.list.array.TByteArrayList;
import gnu.trove.list.array.TCharArrayList;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TLongArrayList;
import gnu.trove.list.array.TShortArrayList;
import gnu.trove.map.TDoubleIntMap;
import gnu.trove.map.TDoubleLongMap;
import gnu.trove.map.TDoubleObjectMap;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TDoubleIntHashMap;
import gnu.trove.map.hash.TDoubleLongHashMap;
import gnu.trove.map.hash.TDoubleObjectHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.set.TByteSet;
import gnu.trove.set.TCharSet;
import gnu.trove.set.TDoubleSet;
import gnu.trove.set.TIntSet;
import gnu.trove.set.TLongSet;
import gnu.trove.set.TShortSet;
import gnu.trove.set.hash.TByteHashSet;
import gnu.trove.set.hash.TCharHashSet;
import gnu.trove.set.hash.TDoubleHashSet;
import gnu.trove.set.hash.TIntHashSet;

import gnu.trove.set.TFloatSet;
import gnu.trove.set.hash.TFloatHashSet;
import gnu.trove.set.hash.TLongHashSet;
import gnu.trove.set.hash.TShortHashSet;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Implementations of {@link Collector} that implements reduction operations for accumulating elements into Trove collections.
 * More info on trove <a href="https://bitbucket.org/trove4j/trove">trove</a>
 *
 * @author tgorthi
 * @since Dec 2019
 */
public class TCollectors
{
    private static final Set<Collector.Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    private static final Set<Collector.Characteristics> CH_UNORDERED_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED,
            Collector.Characteristics.IDENTITY_FINISH));

    /* Sets Start**/

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TIntSet}.
     */
    public static Collector<Integer, TIntSet, TIntSet> toTIntSet()
    {
        return new TCollector<>(TIntHashSet::new,
                TIntSet::add,
                (setA, setB) -> {
                    setA.addAll(setB);
                    return setA;
                },
                CH_UNORDERED_ID);
    }

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TFloatSet}.
     */
    public static Collector<Float, TFloatSet, TFloatSet> toTFloatSet()
    {
        return new TCollector<>(TFloatHashSet::new,
                TFloatSet::add,
                (setA, setB) -> {
                    setA.addAll(setB);
                    return setA;
                },
                CH_UNORDERED_ID);
    }

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TDoubleSet}.
     */
    public static Collector<Double, TDoubleSet, TDoubleSet> toTDoubleSet()
    {
        return new TCollector<>(TDoubleHashSet::new,
                TDoubleSet::add,
                (setA, setB) -> {
                    setA.addAll(setB);
                    return setA;
                },
                CH_UNORDERED_ID);
    }

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TCharHashSet}.
     */
    public static Collector<Character, TCharSet, TCharSet> toTCharSet()
    {
        return new TCollector<>(TCharHashSet::new,
                TCharSet::add,
                (setA, setB) -> {
                    setA.addAll(setB);
                    return setA;
                },
                CH_UNORDERED_ID);
    }

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TByteSet}.
     */
    public static Collector<Byte, TByteSet, TByteSet> toTByteSet()
    {
        return new TCollector<>(TByteHashSet::new,
                TByteSet::add,
                (setA, setB) -> {
                    setA.addAll(setB);
                    return setA;
                },
                CH_UNORDERED_ID);
    }

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TLongSet}.
     */
    public static Collector<Long, TLongSet, TLongSet> toTLongSet()
    {
        return new TCollector<>(TLongHashSet::new,
                TLongSet::add,
                (setA, setB) -> {
                    setA.addAll(setB);
                    return setA;
                },
                CH_UNORDERED_ID);
    }

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TShortSet}.
     */
    public static Collector<Short, TShortSet, TShortSet> toTShortSet()
    {
        return new TCollector<>(TShortHashSet::new,
                TShortSet::add,
                (setA, setB) -> {
                    setA.addAll(setB);
                    return setA;
                },
                CH_UNORDERED_ID);
    }

    /* Sets End**/
    /* Map Start**/

    /**
     * Copied from {@link java.util.stream.Collectors}
     */
    private static <T> BinaryOperator<T> throwingMerger()
    {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }

    /**
     * @param <B> type of input parameters
     * @return {@link Collector} that accumulates the input elements into a new {@link TIntIntMap}.
     */
    public static <B> Collector<B, TIntIntMap, TIntIntMap> toTIntIntMap(Function<B, Integer> keyMapper,
                                                                        Function<B, Integer> valueMapper)
    {
        return new TCollector<>(TIntIntHashMap::new,
                (map, e) -> map.put(keyMapper.apply(e),
                        valueMapper.apply(e)),
                throwingMerger(),
                CH_ID);
    }

    /**
     * @param <B> type of input parameters
     * @return {@link Collector} that accumulates the input elements into a new {@link TIntIntMap}.
     */
    public static <B> Collector<B, TIntIntMap, TIntIntMap> toTIntIntMap(TIntSupplier<B> keySupplier,
                                                                        TIntSupplier<B> valueSupplier)
    {
        return new TCollector<>(TIntIntHashMap::new,
                (map, e) -> map.put(keySupplier.apply(e),
                        valueSupplier.apply(e)),
                throwingMerger(),
                CH_ID);
    }

    /**
     * @param <B> type of input parameters
     * @return {@link Collector} that accumulates the input elements into a new {@link TDoubleIntMap}.
     */
    public static <B> Collector<B, TDoubleIntMap, TDoubleIntMap> toTDoubleIntMap(Function<B, Double> keyMapper,
                                                                                 Function<B, Integer> valueMapper)
    {
        return new TCollector<>(TDoubleIntHashMap::new,
                (map, e) -> map.put(keyMapper.apply(e),
                        valueMapper.apply(e)),
                throwingMerger(),
                CH_ID);
    }

    /**
     * @param <B> type of input parameters
     * @return {@link Collector} that accumulates the input elements into a new {@link TDoubleIntMap}.
     */
    public static <B> Collector<B, TDoubleIntMap, TDoubleIntMap> toTDoubleIntMap(TDoubleSupplier<B> keySupplier,
                                                                                 TIntSupplier<B> valueSupplier)
    {
        return new TCollector<>(TDoubleIntHashMap::new,
                (map, e) -> map.put(keySupplier.apply(e),
                        valueSupplier.apply(e)),
                throwingMerger(),
                CH_ID);
    }

    /**
     * @param <B> type of input parameters
     * @return {@link Collector} that accumulates the input elements into a new {@link TDoubleLongMap}.
     */
    public static <B> Collector<B, TDoubleLongMap, TDoubleLongMap> toTDoubleLongMap(Function<B, Double> keyMapper,
                                                                                    Function<B, Integer> valueMapper)
    {
        return new TCollector<>(TDoubleLongHashMap::new,
                (map, e) -> map.put(keyMapper.apply(e),
                        valueMapper.apply(e)),
                throwingMerger(),
                CH_ID);
    }

    /**
     * @param <B> type of input parameters
     * @return {@link Collector} that accumulates the input elements into a new {@link TDoubleLongMap}.
     */
    public static <B> Collector<B, TDoubleLongMap, TDoubleLongMap> toTDoubleLongMap(TDoubleSupplier<B> keySupplier,
                                                                                    TIntSupplier<B> valueSupplier)
    {
        return new TCollector<>(TDoubleLongHashMap::new,
                (map, e) -> map.put(keySupplier.apply(e),
                        valueSupplier.apply(e)),
                throwingMerger(),
                CH_ID);
    }

    /**
     * @param <B> type of input parameters
     * @return {@link Collector} that accumulates the input elements into a new {@link TDoubleLongMap}.
     */
    public static <B> Collector<B, TDoubleLongMap, TDoubleLongMap> toTDoubleLongMap(TDoubleSupplier<B> keySupplier,
                                                                                    TLongSupplier<B> valueSupplier)
    {
        return new TCollector<>(TDoubleLongHashMap::new,
                (map, e) -> map.put(keySupplier.apply(e),
                        valueSupplier.apply(e)),
                throwingMerger(),
                CH_ID);
    }

    /**
     * @param <B> type of input parameters
     * @param <V> type of value objects in the final Map
     * @return {@link Collector} that accumulates the input elements into a new {@link TDoubleLongMap}.
     */
    public static <B, V> Collector<B, TDoubleObjectMap<V>, TDoubleObjectMap<V>> toTDoubleObjectMap(Function<B, Double> keyMapper,
                                                                                                   Function<B, V> valueMapper)
    {
        return new TCollector<>(TDoubleObjectHashMap::new,
                (map, e) -> map.put(keyMapper.apply(e),
                        valueMapper.apply(e)),
                throwingMerger(),
                CH_ID);
    }

    /* Map Start**/
    /* Lists Start**/

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TIntList}.
     */
    public static Collector<Integer, TIntList, TIntList> toTIntList()
    {
        return new TCollector<>(TIntArrayList::new,
                TIntList::add,
                (ListA, ListB) -> {
                    ListA.addAll(ListB);
                    return ListA;
                },
                CH_UNORDERED_ID);
    }

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TFloatList}.
     */
    public static Collector<Float, TFloatList, TFloatList> toTFloatList()
    {
        return new TCollector<>(TFloatArrayList::new,
                TFloatList::add,
                (ListA, ListB) -> {
                    ListA.addAll(ListB);
                    return ListA;
                },
                CH_UNORDERED_ID);
    }

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TDoubleList}.
     */
    public static Collector<Double, TDoubleList, TDoubleList> toTDoubleList()
    {
        return new TCollector<>(TDoubleArrayList::new,
                TDoubleList::add,
                (ListA, ListB) -> {
                    ListA.addAll(ListB);
                    return ListA;
                },
                CH_UNORDERED_ID);
    }

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TCharArrayList}.
     */
    public static Collector<Character, TCharList, TCharList> toTCharList()
    {
        return new TCollector<>(TCharArrayList::new,
                TCharList::add,
                (ListA, ListB) -> {
                    ListA.addAll(ListB);
                    return ListA;
                },
                CH_UNORDERED_ID);
    }

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TByteList}.
     */
    public static Collector<Byte, TByteList, TByteList> toTByteList()
    {
        return new TCollector<>(TByteArrayList::new,
                TByteList::add,
                (ListA, ListB) -> {
                    ListA.addAll(ListB);
                    return ListA;
                },
                CH_UNORDERED_ID);
    }

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TLongList}.
     */
    public static Collector<Long, TLongList, TLongList> toTLongList()
    {
        return new TCollector<>(TLongArrayList::new,
                TLongList::add,
                (ListA, ListB) -> {
                    ListA.addAll(ListB);
                    return ListA;
                },
                CH_UNORDERED_ID);
    }

    /**
     * @return {@link Collector} that accumulates the input elements into a new {@link TShortList}.
     */
    public static Collector<Short, TShortList, TShortList> toTShortList()
    {
        return new TCollector<>(TShortArrayList::new,
                TShortList::add,
                (ListA, ListB) -> {
                    ListA.addAll(ListB);
                    return ListA;
                },
                CH_UNORDERED_ID);
    }
    /* Lists End**/

    /**
     * Simple implementation class for {@code Collector}.
     *
     * @param <A> the mutable accumulation type of the reduction operation (often hidden as an implementation detail)
     * @param <T> the type of elements to be collected
     * @param <R> the type of the result
     */
    private static class TCollector<T, A, R> implements Collector<T, A, R>
    {
        private final Supplier<A> m_supplier;
        private final BiConsumer<A, T> m_accumulator;
        private final BinaryOperator<A> m_combiner;
        private final Function<A, R> m_finisher;
        private final Set<Characteristics> m_characteristics;

        TCollector(Supplier<A> supplier,
                   BiConsumer<A, T> accumulator,
                   BinaryOperator<A> combiner,
                   Function<A, R> finisher,
                   Set<Characteristics> characteristics)
        {
            m_supplier = supplier;
            m_accumulator = accumulator;
            m_combiner = combiner;
            m_finisher = finisher;
            m_characteristics = characteristics;
        }

        TCollector(Supplier<A> supplier,
                   BiConsumer<A, T> accumulator,
                   BinaryOperator<A> combiner,
                   Set<Characteristics> characteristics)
        {
            this(supplier,
                    accumulator,
                    combiner,
                    i -> (R) i,
                    characteristics);
        }

        @Override
        public BiConsumer<A, T> accumulator()
        {
            return m_accumulator;
        }

        @Override
        public Supplier<A> supplier()
        {
            return m_supplier;
        }

        @Override
        public BinaryOperator<A> combiner()
        {
            return m_combiner;
        }

        @Override
        public Function<A, R> finisher()
        {
            return m_finisher;
        }

        @Override
        public Set<Characteristics> characteristics()
        {
            return m_characteristics;
        }
    }

    @FunctionalInterface
    public interface TIntSupplier<B>
    {
        int apply(B b);
    }

    @FunctionalInterface
    public interface TDoubleSupplier<B>
    {
        double apply(B b);
    }

    @FunctionalInterface
    public interface TFloatSupplier<B>
    {
        double apply(B b);
    }

    @FunctionalInterface
    public interface TLongSupplier<B>
    {
        long apply(B b);
    }

    @FunctionalInterface
    public interface TCharSupplier<B>
    {
        char apply(B b);
    }
}