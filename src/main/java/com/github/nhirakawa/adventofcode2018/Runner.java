package com.github.nhirakawa.adventofcode2018;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nhirakawa.adventofcode2018.dayfive.DayFive;
import com.github.nhirakawa.adventofcode2018.dayfour.DayFour;
import com.github.nhirakawa.adventofcode2018.dayone.DayOne;
import com.github.nhirakawa.adventofcode2018.daythree.DayThree;
import com.github.nhirakawa.adventofcode2018.daytwo.DayTwo;

public class Runner {

  private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

  public static void main(String... args) {
    DayOne dayOne = new DayOne();
    LOG.info("[Day 1] {}", dayOne.doPartOne());
    LOG.info("[Day 1] {}", dayOne.doPartTwo());

    DayTwo dayTwo = new DayTwo();
    LOG.info("[Day 2] {}", dayTwo.doPartOne());
    LOG.info("[Day 2] {}", dayTwo.doPartTwo());

    DayThree dayThree = new DayThree();
    LOG.info("[Day 3] {}", dayThree.doPartOne());
    LOG.info("[Day 3] {}", dayThree.doPartTwo());

    DayFour dayFour = new DayFour();
    LOG.info("[Day 4] {}", dayFour.doPartOne());
    LOG.info("[Day 4] {}", dayFour.doPartTwo());

    DayFive dayFive = new DayFive();
    LOG.info("[Day 5] {}", dayFive.doPartOne());
    LOG.info("[Day 5] {}", dayFive.doPartTwo());

    Util.executor().shutdown();
  }

}
