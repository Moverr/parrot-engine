name := """Parront-Engine""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  javaJpa,
  "org.hibernate" % "hibernate-core" % "5.4.9.Final",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "com.typesafe.play" %% "play-json" % "2.3.0",
  javaJpa,
  ws
)


libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _ )
