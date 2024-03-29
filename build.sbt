name := "store"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.4.1-RC2"
libraryDependencies ++= {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.5.3",
    "org.scalatest" %% "scalatest" % "3.2.18" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
