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

package io.trosa.avian.scraper

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.util.ByteString

class ScraperActor extends Actor
	with ActorLogging {

	import scala.concurrent.ExecutionContext.Implicits.global

	implicit val materializer =
		ActorMaterializer(ActorMaterializerSettings(context.system))

	/*
	* Receive Future Http[T] request from pipe
	* by the Request Trait class implementation
	* */

	override def receive = {
		case HttpResponse(StatusCodes.OK, headers, entity, _) =>
			entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
				log.info("Got response, body: " + body.utf8String)
			}
		case resp @ HttpResponse(code, _, _, _) =>
			log.info("Request failed, response code: " + code)
			resp.discardEntityBytes()
	}
}
