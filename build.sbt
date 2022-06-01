ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

libraryDependencies += "dev.zio" %% "zio" % "1.0.12"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio-test"          % "1.0.12" % "test",
  "dev.zio" %% "zio-test-sbt"      % "1.0.12" % "test",
  "dev.zio" %% "zio-test-magnolia" % "1.0.12" % "test" // optional
)
testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")

lazy val root = (project in file("."))
  .settings(
    name := "ZIOPlayground"
  )
