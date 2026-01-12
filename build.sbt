name := "store"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.8.0-RC6"
libraryDependencies ++= {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.5.22",
    "org.scalatest" %% "scalatest" % "3.2.20" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
