name := "functional-crossword"

organization := "fr.nikorada"

scalaVersion := "2.11.6"

scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)
