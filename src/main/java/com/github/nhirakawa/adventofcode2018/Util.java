package com.github.nhirakawa.adventofcode2018;

import java.util.Comparator;
import java.util.Optional;

import com.google.common.collect.Multiset;

public final class Util {

  private Util() {}

  public static <T> Optional<T> multisetMax(Multiset<T> multiset) {
    return multiset.entrySet().stream()
        .max(Comparator.comparing(Multiset.Entry::getCount))
        .map(Multiset.Entry::getElement);
  }

}
