package com.github.nhirakawa.adventofcode2018.daythree;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
interface CoordinatesModel {

  int getX();
  int getY();

}
