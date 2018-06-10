package com.avenuecode.orders.util;

/**
 * Helper utility functions.
 */
public class Util {
  
  private Util() {
    throw new AssertionError("Not to be instantiated");
  }
  
  /**
   * Returns {@code true} if the two objects are equal, {@code false} otherwise.
   */
  public static boolean isEqual(Object first, Object second) {
    return (first == second) || (first != null && first.equals(second));
  }

}
