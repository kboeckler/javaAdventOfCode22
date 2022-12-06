package com.github.kboeckler.adventOfCode22.solution;

import com.github.kboeckler.adventOfCode22.Day;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Day(6)
public class Day6 implements Solution {

  @Override
  public Serializable solvePart1(String[] input) {
    return solveWith(input, 4);
  }

  @Override
  public Serializable solvePart2(String[] input) {
    return solveWith(input, 14);
  }

  private static int solveWith(String[] input, int windowSize) {
    for (int i = windowSize; i < input[0].length(); i++) {
      Set<Character> elements = new HashSet<>();
      for (int j = 1; j <= windowSize; j++) {
        elements.add(input[0].charAt(i - j));
      }
      if (elements.size() == windowSize) {
        return i;
      }
    }
    throw new IllegalStateException("No solution found");
  }
}
