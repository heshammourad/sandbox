package com.heshammourad.sandbox.riddler;

import java.util.Random;

/**
 * @author hmourad
 *
 *         This class simulates the Riddler Express problem at <a href=
 *         "https://fivethirtyeight.com/features/work-a-shift-in-the-riddler-gift-shop/">https://fivethirtyeight.com/features/work-a-shift-in-the-riddler-gift-shop/</a>
 */
public class Vitamins {

  private static final Random RANDOM = new Random();
  private static final int RUNS = 1000000;
  private static final int TABLETS = 100;

  public static void main(String[] args) {
    int totalDays = 0; // Total days until half tablet pulled
    for (int i = 0; i < RUNS; i++) {
      // boolean array represents tablets in bottle
      // false = whole tablet
      // true = half tablet
      boolean[] bottle = new boolean[TABLETS];
      boolean tablet = false;
      do {
        int index = RANDOM.nextInt(TABLETS);
        tablet = bottle[index];
        bottle[index] = true;
        totalDays++;
      } while (!tablet);
    }
    System.out.println((1.0 * totalDays) / RUNS);
  }

}
