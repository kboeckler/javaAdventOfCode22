package com.github.kboeckler.adventOfCode22.solution;

import com.github.kboeckler.adventOfCode22.Day;
import com.github.kboeckler.adventOfCode22.Runner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Day(1)
public class Day1 implements Solution {

  @Override
  public Serializable solvePart1(String[] input) {
    return parseElfChunks(input).stream()
        .map(elf -> elf.stream().map(Integer::parseInt).reduce(0, Integer::sum))
        .max(Integer::compareTo)
        .orElse(0);
  }

  @Override
  public Serializable solvePart2(String[] input) {
    return parseElfChunks(input).stream()
        .map(elf -> elf.stream().map(Integer::parseInt).reduce(0, Integer::sum))
        .sorted((a, b) -> Integer.compare(b, a))
        .limit(3)
        .reduce(0, Integer::sum);
  }

  private List<List<String>> parseElfChunks(String[] input) {
    List<List<String>> elves = new ArrayList<>();
    List<String> currentElf = new ArrayList<>();
    for (String row : input) {
      if (row.isBlank()) {
        elves.add(currentElf);
        currentElf = new ArrayList<>();
        continue;
      }
      currentElf.add(row);
    }
    return elves;
  }
}
