package com.github.kboeckler.adventOfCode22;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Day {
  int value();
}
