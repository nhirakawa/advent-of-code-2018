package com.github.nhirakawa.adventofcode2018.dayone;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.io.Resources;

public class DayOne {

  private final Supplier<List<Integer>> integersSupplier;

  public DayOne() {
    this.integersSupplier = Suppliers.memoize(this::readNumbers);
  }

  public String doPartOne() {
    int sum = 0;
    for (int i : integersSupplier.get()) {
      sum += i;
    }

    return String.valueOf(sum);
  }

  public String doPartTwo() {
    Iterable<Integer> cycle = Iterables.cycle(integersSupplier.get());
    int sum = 0;
    Set<Integer> alreadySeen = new HashSet<>();
    for (int i : cycle) {
      sum += i;
      if (!alreadySeen.add(sum)) {
        return String.valueOf(sum);
      }
    }

    throw new IllegalStateException();
  }

  private List<Integer> readNumbers() {
    try {
      List<String> lines = Resources.readLines(Resources.getResource("inputs/day-one-input.txt"), StandardCharsets.UTF_8);
      return lines.stream()
          .map(Integer::valueOf)
          .collect(ImmutableList.toImmutableList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
