package com.github.kboeckler.adventOfCode22.solution;

import com.github.kboeckler.adventOfCode22.Runner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day2 implements Solution {

  static {
    Runner.registerSolution(2, new Day2());
  }

  @Override
  public Serializable solvePart1(String[] input) {
    Shapes shapes = new Shapes();

    List<Battle> battles = new ArrayList<>();
    for (String row : input) {
      String[] symbols = row.split(" ");
      Shape oppShape = shapes.getBySymbol(symbols[0]);
      Shape myShape = shapes.getBySymbol(symbols[1]);
      battles.add(new Battle(oppShape, myShape));
    }

    return battles.stream()
        .map(battle -> battle.getSecondShapeScore() + battle.getSecondShapeResult().getScore())
        .reduce(0, Integer::sum);
  }

  @Override
  public Serializable solvePart2(String[] input) {
    Shapes shapes = new Shapes();

    List<Battle> battles = new ArrayList<>();
    for (String row : input) {
      String[] symbols = row.split(" ");
      Shape oppShape = shapes.getBySymbol(symbols[0]);
      Result result = Result.getBySymbol(symbols[1]);
      Shape myShape = shapes.getSecondShapeSoTheSecondShapeResultsIn(oppShape, result);
      battles.add(new Battle(oppShape, myShape));
    }

    return battles.stream()
        .map(battle -> battle.getSecondShapeScore() + battle.getSecondShapeResult().getScore())
        .reduce(0, Integer::sum);
  }

  private static class Battle {
    private final Shape firstShape, secondShape;

    private Battle(Shape firstShape, Shape secondShape) {
      this.firstShape = firstShape;
      this.secondShape = secondShape;
    }

    public int getSecondShapeScore() {
      return secondShape.getScore();
    }

    public Result getSecondShapeResult() {
      if (secondShape.winsAgainst(firstShape)) {
        return Result.WIN;
      } else if (secondShape.losesAgainst(firstShape)) {
        return Result.LOSE;
      }
      return Result.DRAW;
    }
  }

  private static class Shapes {
    private final Map<ShapeType, Shape> shapes = new HashMap<>();

    Shapes() {
      Shape rock =
          Shape.builder(ShapeType.ROCK)
              .winsAgainst(ShapeType.SCISSORS)
              .losesAgainst(ShapeType.PAPER)
              .build();
      Shape paper =
          Shape.builder(ShapeType.PAPER)
              .winsAgainst(ShapeType.ROCK)
              .losesAgainst(ShapeType.SCISSORS)
              .build();
      Shape scissors =
          Shape.builder(ShapeType.SCISSORS)
              .winsAgainst(ShapeType.PAPER)
              .losesAgainst(ShapeType.ROCK)
              .build();
      shapes.put(rock.id, rock);
      shapes.put(paper.id, paper);
      shapes.put(scissors.id, scissors);
    }

    public Shape getBySymbol(String symbol) {
      switch (symbol) {
        case "A":
        case "X":
          return shapes.get(ShapeType.ROCK);
        case "B":
        case "Y":
          return shapes.get(ShapeType.PAPER);
        case "C":
        case "Z":
          return shapes.get(ShapeType.SCISSORS);
        default:
          throw new IllegalArgumentException("Illegal symbol: " + symbol);
      }
    }

    public Shape getSecondShapeSoTheSecondShapeResultsIn(Shape firstShape, Result result) {
      for (Shape shape : shapes.values()) {
        switch (result) {
          case WIN:
            if (shape.winsAgainst(firstShape)) {
              return shape;
            }
            break;
          case DRAW:
            if (shape.drawsAgainstAgainst(firstShape)) {
              return shape;
            }
            break;
          case LOSE:
            if (shape.losesAgainst(firstShape)) {
              return shape;
            }
            break;
        }
      }
      throw new IllegalStateException("Could not find a shape for the given result");
    }
  }

  private enum ShapeType {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    private final int score;

    ShapeType(int score) {
      this.score = score;
    }

    public int getScore() {
      return score;
    }
  }

  private enum Result {
    WIN(6),
    DRAW(3),
    LOSE(0);
    private final int score;

    Result(int score) {
      this.score = score;
    }

    public static Result getBySymbol(String symbol) {
      switch (symbol) {
        case "A":
        case "X":
          return LOSE;
        case "B":
        case "Y":
          return DRAW;
        case "C":
        case "Z":
          return WIN;
        default:
          throw new IllegalArgumentException("Illegal symbol: " + symbol);
      }
    }

    public int getScore() {
      return score;
    }
  }

  private static class Shape {
    private final ShapeType id;

    private final ShapeType winsAgainst;

    private final ShapeType losesAgainst;

    private final ShapeType drawsAgainst;

    private static ShapeBuilder builder(ShapeType ofType) {
      return new ShapeBuilder(ofType);
    }

    private Shape(
        ShapeType id, ShapeType winsAgainst, ShapeType losesAgainst, ShapeType drawsAgainst) {
      this.id = id;
      this.winsAgainst = winsAgainst;
      this.losesAgainst = losesAgainst;
      this.drawsAgainst = drawsAgainst;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Shape shape = (Shape) o;
      return id == shape.id;
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }

    public int getScore() {
      return id.getScore();
    }

    public boolean winsAgainst(Shape secondShape) {
      return winsAgainst.equals(secondShape.id);
    }

    public boolean losesAgainst(Shape secondShape) {
      return losesAgainst.equals(secondShape.id);
    }

    public boolean drawsAgainstAgainst(Shape secondShape) {
      return drawsAgainst.equals(secondShape.id);
    }
  }

  private static class ShapeBuilder {
    private final ShapeType id;

    private ShapeType winsAgainst;

    private ShapeType losesAgainst;

    private ShapeType drawsAgainst;

    public ShapeBuilder(ShapeType id) {
      this.id = id;
    }

    public ShapeBuilder winsAgainst(ShapeType winsAgainst) {
      this.winsAgainst = winsAgainst;
      return this;
    }

    public ShapeBuilder losesAgainst(ShapeType losesAgainst) {
      this.losesAgainst = losesAgainst;
      return this;
    }

    public ShapeBuilder drawsAgainst(ShapeType drawsAgainst) {
      this.drawsAgainst = drawsAgainst;
      return this;
    }

    public Shape build() {
      if (drawsAgainst == null) {
        drawsAgainst = id;
      }
      return new Shape(id, winsAgainst, losesAgainst, drawsAgainst);
    }
  }
}
