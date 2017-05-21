import sbt._

object Dependencies {

    /* Versions
     */
    lazy val akkaVersion = "2.5.0"
    lazy val testVersion = "3.0.1"
    lazy val configVersion = "1.3.1"
    lazy val mongoVersion = "3.1.1"
    lazy val sl4jVersion = "1.7.21"
    lazy val scalajversion = "2.3.0"
    lazy val jsoupversion = "1.10.2"

    /* Libraries
     */
    lazy val scalaTest = "org.scalatest" %% "scalatest" % testVersion
    lazy val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
    lazy val akkaTest  = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
    lazy val tsConfig  = "com.typesafe" % "config" % configVersion
    lazy val mongoDb   = "org.mongodb" %% "casbah" % mongoVersion
    lazy val sl4j      = "org.slf4j" % "slf4j-simple" % sl4jVersion
    lazy val scalajHttp = "org.scalaj" %% "scalaj-http" % scalajversion
    lazy val jsoup =  "org.jsoup" % "jsoup" % jsoupversion
}