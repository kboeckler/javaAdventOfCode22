package com.github.kboeckler.adventOfCode22;

import java.io.Serializable;

public interface OutputSupplier {

  String welcomeMsg();

  String startSolving(int day);

  String startPart1();

  String startPart2();

  String result1(Serializable result);

  String result2(Serializable result);

  class Verbose implements OutputSupplier {

    @Override
    public String welcomeMsg() {
      return "Welcome to Advent of Code 22\n###############################\n";
    }

    @Override
    public String startSolving(int day) {
      return String.format("Solving day %d #", day);
    }

    @Override
    public String startPart1() {
      return " Part1:... ";
    }

    @Override
    public String startPart2() {
      return " Part2:... ";
    }

    @Override
    public String result1(Serializable result) {
      return result.toString();
    }

    @Override
    public String result2(Serializable result) {
      return result.toString();
    }
  }

  class Short implements OutputSupplier {

    @Override
    public String welcomeMsg() {
      return "";
    }

    @Override
    public String startSolving(int day) {
      return day + "";
    }

    @Override
    public String startPart1() {
      return " ";
    }

    @Override
    public String startPart2() {
      return " ";
    }

    @Override
    public String result1(Serializable result) {
      return result.toString();
    }

    @Override
    public String result2(Serializable result) {
      return result.toString();
    }
  }
}
