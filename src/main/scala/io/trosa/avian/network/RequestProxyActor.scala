/**
 * File: RequestProxyActor.scala
 * Author: Clement Trosa <me@trosa.io>
 * Date: 19/09/2017 11:49:55 AM
 * Last Modified Date: 19/09/2017 11:49:55 AM
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

package io.trosa.avian.network

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.http.scaladsl.{ClientTransport, Http}
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.settings.ConnectionPoolSettings
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import io.trosa.avian.Exceptions.AvianUnprocessableUrl
import io.trosa.avian.Types.Pivot
import io.trosa.avian.models.Target
import io.trosa.avian.scraper.ScraperActor

class RequestProxyActor extends Actor
    with Request
	with ActorLogging {

	import akka.pattern.pipe
	import context.dispatcher

	implicit val system: ActorSystem = ActorSystem()

	val scraper = context.actorOf(Props[ScraperActor])

	final implicit val materializer: ActorMaterializer =
		ActorMaterializer(ActorMaterializerSettings(context.system))

	val http = Http(context.system)

	override def receive: PartialFunction[Any, Unit] = {
		case Target(pivot) => process(pivot)
		case _ => new AvianUnprocessableUrl(new Throwable)
	}

	override def process(pivot: Pivot): Unit = {

		val proxy = ClientTransport.httpsProxy(InetSocketAddress
                    .createUnresolved("127.0.0.1", 9050))

		log.info("Processing proxy pivot: %s".format(pivot.toString))
		http.singleRequest(HttpRequest(uri = pivot.toString),
			settings = ConnectionPoolSettings(system).withTransport(proxy))
		 	.pipeTo(scraper)
	}
}
