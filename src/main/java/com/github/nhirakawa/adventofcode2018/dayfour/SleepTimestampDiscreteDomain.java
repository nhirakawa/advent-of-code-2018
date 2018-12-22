package com.github.nhirakawa.adventofcode2018.dayfour;

import java.time.Duration;

import com.google.common.collect.DiscreteDomain;

final class SleepTimestampDiscreteDomain extends DiscreteDomain<SleepTimestamp> {

  private static final SleepTimestampDiscreteDomain INSTANCE = new SleepTimestampDiscreteDomain();

  private SleepTimestampDiscreteDomain() {}

  static SleepTimestampDiscreteDomain instance() {
    return INSTANCE;
  }

  @Override
  public SleepTimestamp next(SleepTimestamp value) {
    return value.next();
  }

  @Override
  public SleepTimestamp previous(SleepTimestamp value) {
    return value.previous();
  }

  @Override
  public long distance(SleepTimestamp start, SleepTimestamp end) {
    return Duration.between(start.getDateTime(), end.getDateTime()).toMinutes();
  }

}
