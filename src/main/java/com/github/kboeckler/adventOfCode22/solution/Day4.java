package com.github.kboeckler.adventOfCode22.solution;

import com.github.kboeckler.adventOfCode22.Day;
import java.io.Serializable;
import java.util.Arrays;

@Day(4)
public class Day4 implements Solution {

  @Override
  public Serializable solvePart1(String[] input) {
    return Arrays.stream(input).map(this::parsePair).filter(Pair::matchesFully).count();
  }

  @Override
  public Serializable solvePart2(String[] input) {
    return Arrays.stream(input).map(this::parsePair).filter(Pair::matchesPartially).count();
  }

  private Pair parsePair(String row) {
    String[] split = row.split(",");
    return Pair.of(Range.fromRangeString(split[0]), Range.fromRangeString(split[1]));
  }

  private static class Pair {

    private final Range first, second;

    private static Pair of(Range first, Range second) {
      return new Pair(first, second);
    }

    private Pair(Range first, Range second) {
      this.first = first;
      this.second = second;
    }

    boolean matchesFully() {
      return (first.from <= second.from && first.to >= second.to)
          || (second.from <= first.from && second.to >= first.to);
    }

    boolean matchesPartially() {
      return !(first.to < second.from || second.to < first.from);
    }
  }

  private static class Range {
    private final int from, to;

    private static Range fromRangeString(String range) {
      String[] interval = range.split("-");
      return new Range(Integer.valueOf(interval[0]), Integer.valueOf(interval[1]));
    }

    private Range(int from, int to) {
      this.from = from;
      this.to = to;
    }
  }
}
