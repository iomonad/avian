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

import akka.actor.{Actor, Props}
import io.trosa.avian.Exceptions.{AvianTimeoutRequest, AvianUnprocessableUrl}

class Supervisor extends Actor {

		import akka.actor.OneForOneStrategy
		import akka.actor.SupervisorStrategy._

		import scala.concurrent.duration._
		import scala.language.postfixOps

		override val supervisorStrategy =
				OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
						case _: AvianTimeoutRequest => Restart
						case _: AvianUnprocessableUrl => Stop
						case _: Exception => Escalate
				}

		def receive = {
				case p: Props => sender() ! context.actorOf(p)
		}
}
