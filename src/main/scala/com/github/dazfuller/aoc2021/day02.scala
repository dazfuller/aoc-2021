package com.github.dazfuller.aoc2021

object day02 extends Solution {
  override def solve(isTest: Boolean): Unit = {
    val data = Utils.getInputData(2, isTest)
    val directions = data.map(applyCourse)
    val finalPosition = directions.foldLeft((0, 0)) { case ((accX, accY), (x, y)) => (accX + x, accY + y) }
    val finalAimPosition = directions.foldLeft((0, 0, 0)) { case ((accX, accY, accAim), (x, y)) =>
      if (x == 0) { (accX, accY, accAim + y) } else { (accX + x, accY + (accAim * x), accAim) }
    }

    println("==== Day 02 ====")
    println(s"Final Position (Part 1): ${finalPosition._1 * finalPosition._2}")
    println(s"Final Position (Part 2): ${finalAimPosition._1 * finalAimPosition._2}")
  }

  private def applyCourse(instruction: String): (Int, Int) = {
    val instructionParts: Array[String] = instruction.split(" ", 2)
    val direction = instructionParts(0)
    val distance = instructionParts(1).toInt

    direction match {
      case "forward" => (distance, 0)
      case "down" => (0, distance)
      case "up" => (0, distance * -1)
    }
  }
}
