package com.adventofcode;

import com.adventofcode.input.day19.Pair;
import com.adventofcode.input.day19.SamePair;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.adventofcode.input.day19.Pair.pair;
import static com.adventofcode.input.day19.SamePair.samePair;

public class Utils {

    public static <T> Consumer<Pair<SamePair<Integer>, T>> indexedConsumer(BiConsumer<SamePair<Integer>, T> consumer) {
        return (Pair<SamePair<Integer>, T> input) -> consumer.accept(input.first(), input.second());
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
