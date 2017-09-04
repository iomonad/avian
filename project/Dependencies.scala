import sbt._

object Dependencies {

    /* Versions
     */
    lazy val akkaVersion = "2.5.4"
    lazy val testVersion = "3.0.1"
    lazy val configVersion = "1.3.1"
    lazy val elastic4sVersion = "5.4.0"

    /* Libraries
     */
    lazy val scalaTest = "org.scalatest" %% "scalatest" % testVersion
    lazy val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
    lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
    lazy val akkaTest  = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
    lazy val tsConfig  = "com.typesafe" % "config" % configVersion
    lazy val jsoup     = "org.jsoup" % "jsoup" % "1.10.3"
    lazy val esC       = "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion
    lazy val esCT      = "com.sksamuel.elastic4s" %% "elastic4s-tcp" % elastic4sVersion
}
