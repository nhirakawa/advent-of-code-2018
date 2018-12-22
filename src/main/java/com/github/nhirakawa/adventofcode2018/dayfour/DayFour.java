package com.github.nhirakawa.adventofcode2018.dayfour;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.nhirakawa.adventofcode2018.Util;
import com.google.common.base.Preconditions;
import com.google.common.base.Suppliers;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.Range;
import com.google.common.io.Resources;

public class DayFour {

  private static final Pattern GUARD_ID_PATTERN = Pattern.compile(".*Guard #(?<id>\\d+).*");
  private static final Pattern TIME_PATTERN = Pattern.compile("\\[(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2}) (?<hour>\\d{2}):(?<minute>\\d{2})].*");

  private final Supplier<ListMultimap<Integer, Range<SleepTimestamp>>> sleepTimestampsSupplier;

  public DayFour() {
    this.sleepTimestampsSupplier = Suppliers.memoize(this::parseSleepTimestamps);
  }

  public String doPartOne() {
    ListMultimap<Integer, Range<SleepTimestamp>> sleepTimestampsByGuardId = sleepTimestampsSupplier.get();

    Multiset<Integer> guardSleepCounter = HashMultiset.create();
    for (Entry<Integer, Range<SleepTimestamp>> entry : sleepTimestampsByGuardId.entries()) {
      ContiguousSet<SleepTimestamp> sleepTimestamps = getContiguousSet(entry.getValue());

      guardSleepCounter.add(entry.getKey(), sleepTimestamps.size());
    }

    Optional<Integer> sleepiestGuardId = Util.multisetMax(guardSleepCounter);
    Preconditions.checkState(sleepiestGuardId.isPresent());

    Multiset<Integer> sleepiestMinutes = HashMultiset.create();
    for (Range<SleepTimestamp> range : sleepTimestampsByGuardId.get(sleepiestGuardId.get())) {
      ContiguousSet<SleepTimestamp> allTimestampsInRange = getContiguousSet(range);

      for (SleepTimestamp sleepTimestamp : allTimestampsInRange) {
        sleepiestMinutes.add(sleepTimestamp.getMinute());
      }
    }

    Optional<Integer> sleepiestMinute = Util.multisetMax(sleepiestMinutes);
    Preconditions.checkState(sleepiestMinute.isPresent());

    int value = sleepiestGuardId.get() * sleepiestMinute.get();

    return String.valueOf(value);
  }

  public String doPartTwo() {
    ListMultimap<Integer, Range<SleepTimestamp>> sleepTimestampsByGuardId = sleepTimestampsSupplier.get();

    Multiset<GuardMinutePair> guardMinutePairs = HashMultiset.create();
    for (Entry<Integer, Range<SleepTimestamp>> entry : sleepTimestampsByGuardId.entries()) {
      ContiguousSet<SleepTimestamp> contiguousSet = getContiguousSet(entry.getValue());
      for (SleepTimestamp sleepTimestamp : contiguousSet) {
        GuardMinutePair guardMinutePair = GuardMinutePair.builder()
            .setGuardId(entry.getKey())
            .setMinute(sleepTimestamp.getMinute())
            .build();

        guardMinutePairs.add(guardMinutePair);
      }
    }

    Optional<GuardMinutePair> guardMinutePair = Util.multisetMax(guardMinutePairs);
    Preconditions.checkState(guardMinutePair.isPresent());

    int value = guardMinutePair.get().getGuardId() * guardMinutePair.get().getMinute();

    return String.valueOf(value);
  }

  private ListMultimap<Integer, Range<SleepTimestamp>> parseSleepTimestamps() {
    ListMultimap<Integer, Range<SleepTimestamp>> eventsByGuardId = ArrayListMultimap.create();

    List<String> lines = readLines();
    PeekingIterator<String> lineIterator = Iterators.peekingIterator(lines.iterator());

    while (lineIterator.hasNext()) {
      String guardLine = lineIterator.next();

      Preconditions.checkState(guardLine.contains("begins shift"), "%s is not a begin shift", guardLine);

      Matcher guardIdMatcher = GUARD_ID_PATTERN.matcher(guardLine);
      Preconditions.checkState(guardIdMatcher.matches(), "Could not parse guard ID from %s", guardLine);
      int guardId = Integer.parseInt(guardIdMatcher.group("id"));

      String nextLine = lineIterator.peek();
      while (!nextLine.contains("begins shift")) {
        Preconditions.checkState(lineIterator.hasNext());
        String asleep = lineIterator.next();
        Preconditions.checkState(asleep.contains("falls asleep"));

        Preconditions.checkState(lineIterator.hasNext());
        String awake = lineIterator.next();
        Preconditions.checkState(awake.contains("wakes up"));

        SleepTimestamp asleepTimestamp = parseSleepTimestamp(asleep);
        SleepTimestamp awakeTimestamp = parseSleepTimestamp(awake);

        Range<SleepTimestamp> sleepRange = Range.closedOpen(asleepTimestamp, awakeTimestamp);

        eventsByGuardId.put(guardId, sleepRange);

        if (!lineIterator.hasNext()) {
          break;
        }

        nextLine = lineIterator.peek();
      }
    }

    return Multimaps.unmodifiableListMultimap(eventsByGuardId);
  }

  private SleepTimestamp parseSleepTimestamp(String line) {
    Matcher timeMatcher = TIME_PATTERN.matcher(line);
    Preconditions.checkState(timeMatcher.matches(), "Could not parse time from %s", line);

    int year = Integer.parseInt(timeMatcher.group("year"));
    int month = Integer.parseInt(timeMatcher.group("month"));
    int day = Integer.parseInt(timeMatcher.group("day"));
    int hour = Integer.parseInt(timeMatcher.group("hour"));
    int minute = Integer.parseInt(timeMatcher.group("minute"));

    LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);
    ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneOffset.UTC);

    return SleepTimestamp.builder()
        .setDateTime(zonedDateTime)
        .build();
  }

  private List<String> readLines() {
    try {
      return Resources.readLines(
          Resources.getResource("inputs/day-four-input.txt"),
          StandardCharsets.UTF_8
      )
          .stream()
          .sorted()
          .collect(ImmutableList.toImmutableList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private ContiguousSet<SleepTimestamp> getContiguousSet(Range<SleepTimestamp> value) {
    return ContiguousSet.create(
        value,
        SleepTimestampDiscreteDomain.instance()
    );
  }

}
