/**
 * File: Dependencies.scala
 * Author: Clement Trosa <me@trosa.io>
 * Date: 05/09/2017 09:42:14 AM
 * Last Modified Date: 05/09/2017 09:43:41 AM
 * Last Modified By: Clement Trosa <me@trosa.io>
 */
import sbt._

object Dependencies {

    /* Versions
     */
    lazy val akkaVersion = "2.4.19"
    lazy val testVersion = "3.0.1"
    lazy val configVersion = "1.3.1"
    lazy val elastic4sVersion = "5.4.0"
    lazy val akkaHttpVersion = "10.0.10"

    /* Libraries
     */
    lazy val scalaTest = "org.scalatest" %% "scalatest" % testVersion
    lazy val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
    lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
    lazy val akkaHttpC  = "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion
    lazy val akkaHttp  = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
    lazy val akkaTest  = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
    lazy val tsConfig  = "com.typesafe" % "config" % configVersion
    lazy val jsoup     = "org.jsoup" % "jsoup" % "1.10.3"
    lazy val esC       = "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion
    lazy val esCT      = "com.sksamuel.elastic4s" %% "elastic4s-tcp" % elastic4sVersion
}
