/*
 * Copyright (c) 2017 Clement Trosa <me@trosa.io>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
	lazy val akkaLog = "com.typesafe.akka" % "akka-slf4j_2.12" % akkaVersion
	lazy val akkaHttpC = "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion
	lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
	lazy val akkaHttpJson = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
	lazy val akkaTest = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
	lazy val tsConfig = "com.typesafe" % "config" % configVersion
	lazy val jsoup = "org.jsoup" % "jsoup" % "1.10.3"
	lazy val esC = "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion
	lazy val esCT = "com.sksamuel.elastic4s" %% "elastic4s-tcp" % elastic4sVersion
	lazy val rmq = "io.scalac" %% "reactive-rabbit" % "1.1.4"
	lazy val logBack = "ch.qos.logback" % "logback-classic" % "1.2.3"

}
