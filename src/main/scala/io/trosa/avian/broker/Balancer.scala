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

package io.trosa.avian.broker

import java.net.{MalformedURLException, URL}

import akka.actor.{Actor, ActorLogging, Props}
import io.trosa.avian.Exceptions.AvianUnprocessableUrl
import io.trosa.avian.Types.Pivot
import io.trosa.avian.models.Target
import io.trosa.avian.network.{RequestActor, RequestProxyActor}


class BalancerActor extends Actor
    with ActorLogging {

	override def receive: Receive = {
		case Target(pivot) => process(pivot)
		case _ => throw new AvianUnprocessableUrl(new Exception)
	}

	/*
	*  Regular pivot actor processor
	* */

	val request = context.actorOf(Props[RequestActor])

	/*
	*  Proxy-routed pivot actor processor
	* */

	val requestProxy = context.actorOf(Props[RequestProxyActor])

	/*
	* Balancer will in a first time determine if the url needs proxy.
	* One other actor, the kafka consumer, will pipe stream inside.
	* When booting, one pivot will be passed to the balancer actor.
	* */

	def process(pivot: Pivot): Unit = {
		isValid(pivot) match {
			case true => isOnion(pivot) match {
				case true => {
					log.info("Balancing tor url: %s".format(pivot))
					requestProxy ! Target(pivot)
				}
				case false => {
					log.info("Balancing regular url: %s".format(pivot))
					request ! Target(pivot)
				}
			}
			case false => throw new AvianUnprocessableUrl(new Throwable)
		}
	}

	private def isOnion(pivot: Pivot): Boolean = {
		isValid(pivot) match {
			case true => {
				val x = new URL(pivot)
				x.getHost().split("\\.")(0) match {
					case "onion" => true
					case _ => false
				}
			}
			case _ => false
		}
	}


	private def isValid(pivot: Pivot): Boolean = {
		try {
			val foo = new URL(pivot)
			true
		} catch {
			case _: Throwable => false
		}
	}
}
