name := "functional-io"

organization := "fr.valtech"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq (
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "com.h2database" % "h2" % "1.3.175",
  "mysql" % "mysql-connector-java" % "5.1.34",
  "org.slf4j" % "slf4j-nop" % "1.6.4"
)

scalacOptions ++= Seq("-feature", "-deprecation")
