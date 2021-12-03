package com.github.dazfuller.aoc2021

object main{
  def main(args: Array[String]): Unit = {
    val solutions = List[Solution](
      day01,
      day02,
      day03
    )

    solutions.foreach(s => s.solve(isTest = false))
  }
}
