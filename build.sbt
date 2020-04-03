name := "eascrupt"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "net.ruippeixotog" %% "scala-scraper" % "2.2.0",
  "org.apache.kafka" %% "kafka" % "2.4.1",
  "dev.zio" %% "zio" % "1.0.0-RC18"
)