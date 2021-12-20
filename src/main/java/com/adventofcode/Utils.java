package com.adventofcode;

import com.adventofcode.input.day19.Pair;
import com.adventofcode.input.day19.SamePair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.stream.Stream;

import static com.adventofcode.input.day19.Pair.pair;
import static com.adventofcode.input.day19.SamePair.samePair;

public class Utils {

    public static <T> Stream<Pair<Integer, T>> indexedStream(List<T> from) {
        List<Pair<Integer, T>> toBeStreamed = new ArrayList<>();
        for (int i = 0; i < from.size(); i++) {
            toBeStreamed.add(pair(i, from.get(i)));
        }
        return toBeStreamed.stream();
    }

    public static <T> Consumer<Pair<SamePair<Integer>, T>> indexedConsumer(BiConsumer<SamePair<Integer>, T> consumer) {
        return (Pair<SamePair<Integer>, T> input) -> consumer.accept(input.first(), input.second());
    }

    public static <T, U, V> Function<Pair<T, U>, Pair<T, V>> pairMapper(BiFunction<T, U, V> mapper) {
        return (Pair<T, U> input) -> pair(input.first(), mapper.apply(input.first(), input.second()));
    }

    public static <T, U, V> Function<Pair<T, U>, Pair<T, V>> valueMapper(Function<U, V> mapper) {
        return (Pair<T, U> input) -> pair(input.first(), mapper.apply(input.second()));
    }

    public static <T, U, V> ToIntFunction<Pair<T, U>> pairMapperToInt(ToIntBiFunction<T, U> mapper) {
        return (Pair<T, U> input) -> mapper.applyAsInt(input.first(), input.second());
    }

    public static <T, U> Consumer<Pair<T, U>> pairConsumer(BiConsumer<T, U> consumer) {
        return (Pair<T, U> input) -> consumer.accept(input.first(), input.second());
    }

    public static <T, U> Predicate<Pair<T, U>> pairPredicate(BiPredicate<T, U> predicate) {
        return (Pair<T, U> input) -> predicate.test(input.first(), input.second());
    }

    public static <T> Predicate<Pair<SamePair<Integer>, T>> indexedPredicate(Function<T, Boolean> predicate) {
        return (Pair<SamePair<Integer>, T> input) -> predicate.apply(input.second());
    }

    public static <T> Function<SamePair<Integer>, Pair<SamePair<Integer>, T>> indexedSupplier(Function<SamePair<Integer>, T> supplier) {
        return (SamePair<Integer> input) -> pair(input, supplier.apply(input));
    }

    public static <T, U> Function<Pair<SamePair<Integer>, T>, Pair<SamePair<Integer>, U>> indexed(Function<T, U> transformer) {
        return (Pair<SamePair<Integer>, T> input) -> pair(input.first(), transformer.apply(input.second()));
    }

    public static <T> Pair<SamePair<Integer>, T> indexedPair(Integer i, Integer j, T value) {
        return pair(samePair(i, j), value);
    }
}
