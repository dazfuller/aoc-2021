ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.7"

lazy val root = (project in file("."))
  .settings(
    name := "aoc-2021",
    organization := "com.github.dazfuller",
    description := "Solutions for the 2021 Advent of Code",
    homepage := Some(url("https://github.com/dazfuller")),
    assembly / mainClass := Some("com.github.dazfuller.aoc2021.main")
  )
