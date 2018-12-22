package com.github.nhirakawa.adventofcode2018.dayfour;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface GuardMinutePairModel {

  int getGuardId();
  int getMinute();

}
