package com.github.dazfuller.aoc2021

object day01 extends Solution {
  override def solve(isTest: Boolean = false): Unit = {
    val data = Utils.getInputData(1, isTest).map(s => s.toInt)

    println("==== Day 01 ====")
    println(s"Number of depth increases: ${getCountFromWindows(data)}")
    println(s"Number of depth increases for 3 value window: ${getCountFromWindows(data, 3)}")
  }

  private def getCountFromWindows(data: List[Int], windowSize: Int = 1): Int =
    data.sliding(windowSize).map(g => g.sum).sliding(2).count(s => s(1) > s.head)
}
