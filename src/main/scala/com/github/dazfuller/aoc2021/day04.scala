package com.github.dazfuller.aoc2021

object day04 extends Solution {
  override def solve(isTest: Boolean): Unit = {
    val data = Utils.getInputData(4, isTest)
    val draw = data.head.split(",").map(_.toInt)
    val boards = data.tail.tail.filter(_.nonEmpty).sliding(5, 5).toList.map(b => new Board(b))

    var round = 0

    draw.takeWhile(v => {
      round += 1
      boards.foreach(b => {
        if (!b.isSolved) b.markValue(v)
        if (b.isSolved && b.rank == 0) {
          b.winningDrawNumber = v
          b.rank = round
        }
      })
      !boards.forall(_.isSolved)
    })

    val winningBoard = boards.minBy(b => b.rank)
    val losingBoard = boards.maxBy(b => b.rank)

    println("==== Day 04 ====")
    println(s"Final score for winning board (Part 1): ${winningBoard.unmarkedTotal * winningBoard.winningDrawNumber}")
    println(s"Final score for losing board (Part 2): ${losingBoard.unmarkedTotal * losingBoard.winningDrawNumber}")
  }
}

class Board(boardSetup: Seq[String], var winningDrawNumber: Int = 0, var rank: Int = 0) {
  private val board = Array.ofDim[(Int, Boolean)](boardSetup.length, boardSetup.length)
  var isSolved = false

  for ((line, x) <- boardSetup.view.zipWithIndex) {
    for ((v, y) <- line.trim.split("\\s+").view.zipWithIndex) {
      board(x)(y) = (v.toInt, false)
    }
  }

  def markValue(value: Int): Unit = {
    board.foreach(line => {
      for ((v, y) <- line.view.zipWithIndex) {
        if (line(y)._1 == value) line(y) = (line(y)._1, true)
      }
    })
    isSolved = checkIfSolved
  }

  def checkIfSolved: Boolean = {
    if (board.exists(l => l.forall(_._2 == true))) {
      true
    } else {
      val boardColumns = 0.until(5).map(y => 0.until(5).map(x => board(x)(y)))
      boardColumns.exists(l => l.forall(_._2 == true))
    }
  }

  def unmarkedTotal: Int = {
    board.map(l => l.filter(_._2 == false).map(_._1).sum).sum
  }
}
