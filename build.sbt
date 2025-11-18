name := "store"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.8.0-RC1"
libraryDependencies ++= {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.5.21",
    "org.scalatest" %% "scalatest" % "3.2.20" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
