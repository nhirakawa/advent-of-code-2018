package com.github.nhirakawa.adventofcode2018.dayfour;

import java.time.ZonedDateTime;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public abstract class SleepTimestampModel implements Comparable<SleepTimestamp> {

  public abstract ZonedDateTime getDateTime();

  @Value.Auxiliary
  public int getYear() {
    return getDateTime().getYear();
  }

  @Value.Auxiliary
  public int getMonth() {
    return getDateTime().getMonthValue();
  }

  @Value.Auxiliary
  public int getDay() {
    return getDateTime().getDayOfMonth();
  }

  @Value.Auxiliary
  public int getHour() {
    return getDateTime().getHour();
  }

  @Value.Auxiliary
  public int getMinute() {
    return getDateTime().getMinute();
  }

  @Value.Auxiliary
  public SleepTimestamp next() {
    return SleepTimestamp.builder()
        .setDateTime(getDateTime().plusMinutes(1))
        .build();
  }

  @Value.Auxiliary
  public SleepTimestamp previous() {
    return SleepTimestamp.builder()
        .setDateTime(getDateTime().minusMinutes(1))
        .build();
  }

  @Override
  public int compareTo(SleepTimestamp o) {
    return getDateTime().compareTo(o.getDateTime());
  }

}
