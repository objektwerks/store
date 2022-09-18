name := "store"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.8"
libraryDependencies ++= {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.4.1",
    "org.scalatest" %% "scalatest" % "3.2.13" % Test
  )
}
