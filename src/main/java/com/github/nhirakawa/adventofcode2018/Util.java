package com.github.nhirakawa.adventofcode2018;

import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Multiset;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public final class Util {

  private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool(
      new ThreadFactoryBuilder()
          .setNameFormat("advent-of-code")
          .build()
  );

  private Util() {}

  public static <T> Optional<T> multisetMax(Multiset<T> multiset) {
    return multiset.entrySet().stream()
        .max(Comparator.comparing(Multiset.Entry::getCount))
        .map(Multiset.Entry::getElement);
  }

  public static ExecutorService executor() {
    return EXECUTOR;
  }

}
