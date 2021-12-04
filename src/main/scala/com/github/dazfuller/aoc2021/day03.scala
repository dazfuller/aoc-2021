package com.github.dazfuller.aoc2021

object day03 extends Solution {
  override def solve(isTest: Boolean): Unit = {
    val data = Utils.getInputData(3, isTest).map(l => l.toCharArray.map(c => c.toInt - 48))
    val width = data.head.length
    val mask = Integer.parseInt("1" * width, 2)

    val mcbPerColumn = getMostCommonBitsPerColumn(data)

    val gammaString = mcbPerColumn.mkString
    val gammaValue = Integer.parseInt(gammaString, 2)
    val epsilonValue = mask ^ gammaValue

    println("==== Day 03 ====")
    println(s"Submarine power consumption: ${gammaValue * epsilonValue}")

    val oxygenRating = findSensorValue(data, getMostCommonBitsPerColumn)
    val co2Rating = findSensorValue(data, getLeastCommonBitsPerColumn)
    println(s"Submarine life support rating: ${oxygenRating * co2Rating}")
  }

  def findSensorValue(values: Seq[Array[Int]], f: Seq[Array[Int]] => Seq[Int]): Integer = {
    val width = values.head.length
    var sensorData = values
    var commonBitLength = 1
    var commonBitFilter = f(sensorData)
    while (sensorData.length != 1 && commonBitLength <= width) {
      val substr = commonBitFilter.take(commonBitLength).mkString
      sensorData = sensorData.filter(v => v.mkString.startsWith(substr))
      commonBitFilter = f(sensorData)
      commonBitLength += 1
    }

    Integer.parseInt(sensorData.head.mkString, 2)
  }

  def getMostCommonBitsPerColumn(values: Seq[Array[Int]]): Seq[Int] = {
    val width = values.head.length

    0.until(width)
      .map(i => values.map(l => l(i)))
      .map(l => l.groupMapReduce(identity)(_ => 1)(_ + _).maxBy(i => i._2 + i._1)._1)
  }

  def getLeastCommonBitsPerColumn(values: Seq[Array[Int]]): Seq[Int] = {
    val width = values.head.length

    0.until(width)
      .map(i => values.map(l => l(i)))
      .map(l => l.groupMapReduce(identity)(_ => 1)(_ + _).toList.sortBy(_._2).minBy(_._2)._1)
  }
}
