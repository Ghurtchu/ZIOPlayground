ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

libraryDependencies += "dev.zio" %% "zio" % "1.0.12"

lazy val root = (project in file("."))
  .settings(
    name := "ZIOPlayground"
  )
