
name := """parrot_engine"""


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
  "org.scalactic" %% "scalactic" % "3.2.0",
  "org.scalatest" %% "scalatest-flatspec" % "3.2.0" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % "test",
  "com.typesafe.slick" %% "slick" % "3.3.2",

  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.0",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42", //org.postgresql.ds.PGSimpleDataSource dependency
  "com.typesafe.slick" %% "slick-codegen" % "3.3.2",
  javaJpa,
  ws
)


libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _ )
