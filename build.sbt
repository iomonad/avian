import Dependencies._
import org.scoverage.coveralls.Imports.CoverallsKeys._

/* Global configuration */
lazy val root = (project in file(".")).
    settings(
        inThisBuild(List(
            organization := "io.trosa",
            scalaVersion := "2.12.2",
            version      := "0.0.1"
        )),
        name := "avian",
        libraryDependencies ++= Seq(
            scalaTest % Test,
            tsConfig,
            akkaActor,
            akkaStream,
            akkaTest,
            jsoup,
            esC,
            esCT
        )
    )

/* Scala lang related */
scalacOptions += "-deprecation"

/* Code coverage configuration */
coverageEnabled := true
coverageMinimum := 70
coverageFailOnMinimum := false
coverageHighlighting := true
coverageExcludedPackages := "<empty>;io.trosa.avian.Main"
