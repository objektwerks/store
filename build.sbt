name := "objektwerks.store"
version := "1.0"
scalaVersion := "2.11.7"
ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
libraryDependencies ++= {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "org.scalatest" % "scalatest_2.11" % "2.2.5" % "test"
  )
}
scalacOptions ++= Seq(
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:reflectiveCalls",
  "-language:higherKinds",
  "-feature",
  "-unchecked",
  "-deprecation",
  "-Xlint:-nullary-unit,_",
  "-Xfatal-warnings"
)
fork in test := true
javaOptions += "-server -Xss1m -Xmx2g"
logLevel := Level.Info