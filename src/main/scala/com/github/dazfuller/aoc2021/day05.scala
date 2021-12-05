package com.github.dazfuller.aoc2021

object day05 extends Solution {
  override def solve(isTest: Boolean): Unit = {
    val coordPattern = """(\d+),(\d+) -> (\d+),(\d+)""".r
    val data = Utils.getInputData(5, isTest)
      .map(l => {
        val coordPattern(x1, y1, x2, y2) = l
        Seq((x1.toInt, y1.toInt), (x2.toInt, y2.toInt))
      })

    val maxPosition = data.foldLeft((0, 0)) { case ((accX, accY), Seq((x1, y1), (x2, y2))) =>
      val x = Seq(accX, x1, x2).max
      val y = Seq(accY, y1, y2).max
      (x, y)
    }

    val ventPositions = data
      .filter(line => line.head._1 == line(1)._1 || line.head._2 == line(1)._2)
      .flatMap(line => {
        val (x1, y1, x2, y2, xIncrement, yIncrement) = getPositionInfo(line)
        x1.to(x2, xIncrement).flatMap(x => y1.to(y2, yIncrement).map(y => (x, y)))
      })

    val ventDiagonals = data
      .filter(line => line.head._1 != line(1)._1 && line.head._2 != line(1)._2)
      .flatMap(line => {
        val (x1, y1, x2, y2, xIncrement, yIncrement) = getPositionInfo(line)
        x1.to(x2, xIncrement).zip(y1.to(y2, yIncrement)).map(pos => (pos._1, pos._2))
      })

    val map = Array.ofDim[Int](maxPosition._2 + 1, maxPosition._1 + 1)

    println("==== Day 05 ====")
    applyPositions(ventPositions, map)
    println(s"Total number of dangerous points (Part 1): ${map.map(l => l.count(_ >= 2)).sum}")

    applyPositions(ventDiagonals, map)
    println(s"Total number of dangerous points (Part 2): ${map.map(l => l.count(_ >= 2)).sum}")
  }

  private def applyPositions(ventPositions: List[(Int, Int)], map: Array[Array[Int]]): Unit =
    ventPositions.foreach(p => map(p._2)(p._1) += 1)

  private def getPositionInfo(line: Seq[(Int, Int)]): (Int, Int, Int, Int, Int, Int) = {
    val (x1, y1) = line.head
    val (x2, y2) = line(1)
    val xIncrement = if (x1 <= x2) 1 else -1
    val yIncrement = if (y1 <= y2) 1 else -1

    (x1, y1, x2, y2, xIncrement, yIncrement)
  }
}
