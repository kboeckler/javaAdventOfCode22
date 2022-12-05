package com.github.kboeckler.adventOfCode22.solution;

import com.github.kboeckler.adventOfCode22.Runner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 implements Solution {

  static {
    Runner.registerSolution(5, new Day5());
  }

  @Override
  public Serializable solvePart1(String[] input) {
    Crane crane = new CraneBuilder("CrateMover 9000").build();
    return solveWithCrane(input, crane);
  }

  @Override
  public Serializable solvePart2(String[] input) {
    Crane crane =
        new CraneBuilder("CrateMover 9001")
            .airConditioning()
            .leatherSeats()
            .extraCupHolder()
            .canPullMultipleCrates()
            .build();
    return solveWithCrane(input, crane);
  }

  private static String solveWithCrane(String[] input, Crane crane) {
    List<List<String>> stacks = new ArrayList<>();
    List<String> instructionsReversed = new ArrayList<>();
    boolean parsingInstructions = true;
    for (int i = input.length - 1; i >= 0; i--) {
      String row = input[i];
      if (row.startsWith(" 1")) {
        parsingInstructions = false;
        int amountStacks = Integer.parseInt(row.substring(row.length() - 1));
        for (int j = 0; j < amountStacks; j++) {
          stacks.add(new ArrayList<>());
        }
        continue;
      }
      if (parsingInstructions) {
        if (row.isBlank()) {
          continue;
        }
        instructionsReversed.add(row);
      } else {
        for (int c = 0; c < row.length() / 4 + 1; c++) {
          String crate = row.substring(1 + c * 4, 2 + c * 4);
          if (!crate.isBlank()) {
            stacks.get(c).add(crate);
          }
        }
      }
    }
    for (int i = instructionsReversed.size() - 1; i >= 0; i--) {
      crane.work(stacks, instructionsReversed.get(i));
    }
    return stacks.stream().map(stack -> stack.get(stack.size() - 1)).collect(Collectors.joining());
  }

  private static class Crane {

    private final Function<Integer, IntStream> pullIterator;

    private Crane(Function<Integer, IntStream> pullIterator) {
      this.pullIterator = pullIterator;
    }

    void work(List<List<String>> stacks, String instruction) {
      String[] parts = instruction.split(" ");
      int amount = Integer.parseInt(parts[1]);
      int from = Integer.parseInt(parts[3]) - 1;
      int to = Integer.parseInt(parts[5]) - 1;
      move(stacks, amount, from, to);
    }

    protected void move(List<List<String>> stacks, int amount, int from, int to) {
      pullIterator
          .apply(amount)
          .forEach(
              i -> {
                stacks.get(to).add(stacks.get(from).get(stacks.get(from).size() - i));
              });
      for (int i = 0; i < amount; i++) {
        stacks.get(from).remove(stacks.get(from).size() - 1);
      }
    }
  }

  private static class CraneBuilder {

    private Function<Integer, IntStream> pullIterator = amount -> IntStream.range(1, amount + 1);

    CraneBuilder(String name) {}

    CraneBuilder airConditioning() {
      return this;
    }

    CraneBuilder leatherSeats() {
      return this;
    }

    CraneBuilder extraCupHolder() {
      return this;
    }

    CraneBuilder canPullMultipleCrates() {
      pullIterator = amount -> IntStream.iterate(amount, i -> i - 1).limit(amount);
      return this;
    }

    Crane build() {
      return new Crane(pullIterator);
    }
  }
}
