/**
 * File: Booter.scala
 * Author: Clement Trosa <me@trosa.io>
 * Date: 19/09/2017 11:46:56 AM
 * Last Modified Date: 19/09/2017 11:46:56 AM
 * Last Modified By: Clement Trosa <me@trosa.io>
 */
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

package io.trosa.avian

import java.net
import java.net.URL

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import io.trosa.avian.Types.Pivot
import io.trosa.avian.models.Target
import io.trosa.avian.network.RequestProxyActor
import io.trosa.avian.{Supervisor => s}

object Booter extends App {

	val config = ConfigFactory.load()

	val system = ActorSystem("avian")

	/*
	* Todo: API server need implentation.
	val api = Http().bindAndHandle(Router.routes, config.getString("api.interface"), config.getString("api.port"))
	*/

	/*
	* Supervisor instance to the system
	* */
	val supervisor = system.actorOf(Props[s], "supervisor")

        val request = system.actorOf(Props[RequestProxyActor])

	val seed: URL = new URL("http://google.fr")

	import scala.concurrent.duration._
	import scala.language.postfixOps
	import scala.concurrent.ExecutionContext.Implicits.global
	system.scheduler.scheduleOnce(500 millis) {
		request ! Target(seed)
	}
}
