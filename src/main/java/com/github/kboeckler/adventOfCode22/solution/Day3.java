package com.github.kboeckler.adventOfCode22.solution;

import com.github.kboeckler.adventOfCode22.Day;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Day(3)
public class Day3 implements Solution {

  @Override
  public Serializable solvePart1(String[] input) {
    List<List<String>> groupsOfParts = new ArrayList<>();
    for (String row : input) {
      List<String> parts = new ArrayList<>();
      parts.add(row.substring(0, row.length() / 2));
      parts.add(row.substring(row.length() / 2));
      groupsOfParts.add(parts);
    }
    return groupsOfParts.stream()
        .map(this::toSharedString)
        .map(this::stringToPriority)
        .reduce(0, Integer::sum);
  }

  @Override
  public Serializable solvePart2(String[] input) {
    List<List<String>> groupsOfParts = new ArrayList<>();
    for (int i = 0; i < input.length / 3; i++) {
      List<String> parts = new ArrayList<>();
      parts.add(input[i * 3]);
      parts.add(input[(i * 3) + 1]);
      parts.add(input[(i * 3) + 2]);
      groupsOfParts.add(parts);
    }
    return groupsOfParts.stream()
        .map(this::toSharedString)
        .map(this::stringToPriority)
        .reduce(0, Integer::sum);
  }

  private String toSharedString(List<String> parts) {
    return parts.stream()
        .reduce(
            (a, b) -> {
              String nonCommonLetters = a.replaceAll("[" + b + "]", "");
              return a.replaceAll("[" + nonCommonLetters + "]", "");
            })
        .orElse("");
  }

  private int stringToPriority(String str) {
    return str.chars().distinct().map(this::toPriority).sum();
  }

  private int toPriority(int letter) {
    return letter >= (int) 'a' ? letter - (int) 'a' + 1 : letter - (int) 'A' + 27;
  }
}
