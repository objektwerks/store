name := "store"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.6.4-RC2"
libraryDependencies ++= {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.5.17",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
