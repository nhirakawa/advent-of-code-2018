package com.github.nhirakawa.adventofcode2018.daythree;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

@Value.Immutable
@ImmutableStyle
interface ClaimModel {

  String getId();
  int getX();
  int getY();
  int getWidth();
  int getHeight();

  @Value.Derived
  default Set<Coordinates> getCoveringCoordinates() {
    Set<Integer> xRange = IntStream.range(getX(), getX() + getWidth()).boxed().collect(ImmutableSet.toImmutableSet());
    Set<Integer> yRange = IntStream.range(getY(), getY() + getHeight()).boxed().collect(ImmutableSet.toImmutableSet());

    Set<List<Integer>> products = Sets.cartesianProduct(xRange, yRange);

    Set<Coordinates> coordinates = new HashSet<>();
    for (List<Integer> product : products) {
      Preconditions.checkState(product.size() == 2);

      coordinates.add(
          Coordinates.builder()
              .setX(product.get(0))
              .setY(product.get(1))
              .build()
      );
    }

    return Collections.unmodifiableSet(coordinates);
  }

}
