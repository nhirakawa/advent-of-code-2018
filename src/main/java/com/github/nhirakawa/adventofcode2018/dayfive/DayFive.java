package com.github.nhirakawa.adventofcode2018.dayfive;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nhirakawa.adventofcode2018.Util;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Suppliers;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.io.Resources;

public class DayFive {

  private static final Logger LOG = LoggerFactory.getLogger(DayFive.class);

  private final Supplier<String> polymerChainSupplier;

  public DayFive() {
    this.polymerChainSupplier = Suppliers.memoize(this::readPolymerString);
  }

  public String doPartOne() {
    String polymer = polymerChainSupplier.get();
    return String.valueOf(fullyReact(polymer).length());
  }

  public String doPartTwo() {
    Set<String> allCharacters = ImmutableSet.copyOf(polymerChainSupplier.get().split(""));

    List<CompletableFuture<String>> futures = allCharacters.stream()
        .map(this::fullyReactWithoutAsync)
        .collect(ImmutableList.toImmutableList());

    return futures.stream()
        .map(CompletableFuture::join)
        .min(Comparator.comparing(String::length))
        .map(String::length)
        .map(String::valueOf)
        .get();
  }

  private CompletableFuture<String> fullyReactWithoutAsync(String single) {
    return CompletableFuture.supplyAsync(() -> fullyReactWithout(single), Util.executor());
  }

  private String fullyReactWithout(String single) {
    Preconditions.checkState(single.length() == 1);

    String replace = single.toLowerCase() + single.toUpperCase();
    CharMatcher charMatcher = CharMatcher.anyOf(replace);

    String oldPolymer = polymerChainSupplier.get();
    String newPolymer = charMatcher.removeFrom(oldPolymer);

    return fullyReact(newPolymer);
  }

  private String fullyReact(String polymer) {
    String current = polymer;
    String afterReaction = react(current);

    while (!current.equals(afterReaction)) {
      current = afterReaction;
      afterReaction = react(current);
    }

    return afterReaction;
  }

  private String react(String polymer) {
    List<String> individual = Splitter.fixedLength(1).splitToList(polymer);

    boolean reacted = false;
    int firstIndex = -1;
    int secondIndex = -1;
    for (int i = 0; i < individual.size() - 1; i++) {
      String first = individual.get(i);
      String second = individual.get(i + 1);

      if (isReactive(first, second) && !reacted) {
        reacted = true;
        firstIndex = i;
        secondIndex = i + 1;
      }
    }

    List<String> result = new ArrayList<>();
    for (int i = 0; i < individual.size(); i++) {
      if (reacted && (i == firstIndex || i == secondIndex)) {
        continue;
      }

      result.add(individual.get(i));
    }

    return String.join("", result);
  }

  private boolean isReactive(String first, String second) {
    if (first.equals(second)) {
      return false;
    }

    return first.toLowerCase().equals(second.toLowerCase());

  }

  private String readPolymerString() {
    try {
      List<String> lines = Resources.readLines(
          Resources.getResource(
              "inputs/day-five-input.txt"
          ),
          StandardCharsets.UTF_8
      );

      return Iterables.getOnlyElement(lines);
    } catch (Exception e) {
      Throwables.throwIfUnchecked(e);
      throw new RuntimeException(e);
    }
  }
}
