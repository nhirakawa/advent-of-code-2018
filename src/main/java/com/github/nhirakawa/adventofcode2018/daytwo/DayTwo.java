package com.github.nhirakawa.adventofcode2018.daytwo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;
import com.google.common.base.Suppliers;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.io.Resources;

public class DayTwo {

  private final Supplier<List<String>> input;

  public DayTwo() {
    this.input = Suppliers.memoize(this::readLines);
  }

  public String doPartOne() {
    Set<String> twos = new HashSet<>();
    Set<String> threes = new HashSet<>();

    for (String line : input.get()) {
      Multiset<Character> multiset = HashMultiset.create();
      for (char c : line.toCharArray()) {
        multiset.add(c);
      }

      for (Entry<?> entry : multiset.entrySet()) {
        if (entry.getCount() == 2) {
          twos.add(line);
        }

        if (entry.getCount() == 3) {
          threes.add(line);
        }
      }
    }

    return String.valueOf(twos.size() * threes.size());
  }

  public String doPartTwo() {
    List<List<String>> cartesianProduct = Lists.cartesianProduct(input.get(), input.get());

    for (List<String> product : cartesianProduct) {
      Preconditions.checkState(product.size() == 2);

      String first = product.get(0);
      String second = product.get(1);

      Preconditions.checkState(first.length() == second.length());

      StringBuffer stringBuffer = new StringBuffer();
      for (int i = 0; i < first.length(); i++) {
        if (first.charAt(i) == second.charAt(i)) {
          stringBuffer.append(first.charAt(i));
        }
      }

      if (stringBuffer.length() == first.length() - 1) {
        return stringBuffer.toString();
      }
    }
    throw new UnsupportedOperationException();
  }

  private List<String> readLines() {
    try {
      return Resources.readLines(
          Resources.getResource("inputs/day-two-input.txt"),
          StandardCharsets.UTF_8
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
