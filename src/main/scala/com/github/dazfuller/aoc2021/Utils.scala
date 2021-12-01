package com.github.dazfuller.aoc2021

import scala.io.Source

object Utils {
  def getInputData(day: Int, isTest: Boolean = false): List[String] = {
    val dayDir = f"day${day.toString.padTo(2, '0').reverse}"
    val filePart = s"${if (isTest) "test_" else ""}input.txt"
    val inputPath = getClass.getResource(s"/$dayDir/$filePart").getPath
    val source = Source.fromFile(inputPath)

    source.getLines().toList
  }
}
