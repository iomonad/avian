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

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import io.trosa.avian.Exceptions.AvianUnprocessableUrl
import io.trosa.avian.Types.Pivot
import io.trosa.avian.models.Target
import io.trosa.avian.scraper.ScraperActor

class RequestActor extends Actor
    with Request
    with ActorLogging {

	import akka.pattern.pipe
	import context.dispatcher

	val scraper: ActorRef = context.actorOf(Props[ScraperActor])

	final implicit val materializer: ActorMaterializer =
		ActorMaterializer(ActorMaterializerSettings(context.system))

	val http = Http(context.system)

	override def receive: PartialFunction[Any, Unit] = {
		case Target(pivot) => process(pivot)
		case _ => new AvianUnprocessableUrl(new Throwable)
	}

	override def process(pivot: Pivot): Unit = {
		log.info("Processing pivot: %s".format(pivot))
		http.singleRequest(HttpRequest(uri = pivot))
		    .pipeTo(scraper)
	}
}