package com.github.kboeckler.adventOfCode22.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

  @org.junit.jupiter.api.Test
  void example() {
    String[] input = {"A Y", "B X", "C Z"};
    assertEquals(15, new Day2().solvePart1(input));
  }

  @org.junit.jupiter.api.Test
  void ay() {
    String[] input = {"A Y"};
    assertEquals(8, new Day2().solvePart1(input));
  }

  @org.junit.jupiter.api.Test
  void bx() {
    String[] input = {"B X"};
    assertEquals(1, new Day2().solvePart1(input));
  }

  @org.junit.jupiter.api.Test
  void cz() {
    String[] input = {"C Z"};
    assertEquals(6, new Day2().solvePart1(input));
  }

  @org.junit.jupiter.api.Test
  void example2() {
    String[] input = {"A Y", "B X", "C Z"};
    assertEquals(12, new Day2().solvePart2(input));
  }

  @org.junit.jupiter.api.Test
  void ay2() {
    String[] input = {"A Y"};
    assertEquals(4, new Day2().solvePart2(input));
  }

  @org.junit.jupiter.api.Test
  void bx2() {
    String[] input = {"B X"};
    assertEquals(1, new Day2().solvePart2(input));
  }

  @org.junit.jupiter.api.Test
  void cz2() {
    String[] input = {"C Z"};
    assertEquals(7, new Day2().solvePart2(input));
  }

}
