name := "store"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.6"
libraryDependencies ++= {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.6",
    "org.scalatest" %% "scalatest" % "3.2.10" % Test
  )
}
