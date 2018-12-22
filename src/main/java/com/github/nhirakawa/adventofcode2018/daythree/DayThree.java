package com.github.nhirakawa.adventofcode2018.daythree;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import com.google.common.base.Suppliers;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;

public class DayThree {

  private static final Pattern CLAIM_PATTERN = Pattern.compile("#(?<id>\\d+) @ (?<x>\\d+),(?<y>\\d+): (?<width>\\d+)x(?<height>\\d+)");

  private final Supplier<List<Claim>> claimsSupplier;

  public DayThree() {
    this.claimsSupplier = Suppliers.memoize(this::getClaims);
  }

  public String doPartOne() {
    List<Claim> claims = claimsSupplier.get();

    Multiset<Coordinates> coordinateCovers = claims.stream()
        .map(Claim::getCoveringCoordinates)
        .flatMap(Set::stream)
        .collect(ImmutableMultiset.toImmutableMultiset());

    long numberOfCollisions = coordinateCovers.entrySet().stream()
        .map(Entry::getCount)
        .filter(count -> count > 1)
        .count();

    return String.valueOf(numberOfCollisions);
  }

  public String doPartTwo() {
    SetMultimap<Coordinates, String> coordinatesByClaimId = HashMultimap.create();

    for (Claim claim : claimsSupplier.get()) {
      for (Coordinates coordinates : claim.getCoveringCoordinates()) {
        coordinatesByClaimId.put(coordinates, claim.getId());
      }
    }

    Set<String> allClaimIds = claimsSupplier.get().stream()
        .map(Claim::getId)
        .collect(ImmutableSet.toImmutableSet());

    Set<String> claimsWithOverlap = new HashSet<>();

    for (Claim claim : claimsSupplier.get()) {
      for (Coordinates coordinates : claim.getCoveringCoordinates()) {
        Set<String> claimIds = coordinatesByClaimId.get(coordinates);

        if (claimIds.size() != 1) {
          claimsWithOverlap.add(claim.getId());
          continue;
        }
      }
    }

    Set<String> claimsWithoutOverlap = Sets.difference(allClaimIds, claimsWithOverlap);

    return Iterables.getOnlyElement(claimsWithoutOverlap);
  }

  private List<Claim> getClaims() {
    return readLines().stream()
        .map(this::parseClaim)
        .collect(ImmutableList.toImmutableList());
  }

  private List<String> readLines() {
    try {
      return Resources.readLines(
          Resources.getResource(
              "inputs/day-three-input.txt"
          ),
          StandardCharsets.UTF_8
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Claim parseClaim(String line) {
    Matcher matcher = CLAIM_PATTERN.matcher(line);

    Preconditions.checkState(matcher.matches());

    String id = matcher.group("id");
    int x = Integer.parseInt(matcher.group("x"));
    int y = Integer.parseInt(matcher.group("y"));
    int width = Integer.parseInt(matcher.group("width"));
    int height = Integer.parseInt(matcher.group("height"));

    return Claim.builder()
        .setId(id)
        .setX(x)
        .setY(y)
        .setWidth(width)
        .setHeight(height)
        .build();
  }

}
