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

package io.trosa.avian.persistence

import akka.actor.{Actor, ActorLogging}
import io.trosa.avian.Exceptions.AvianUnprocessableUrl
import io.trosa.avian.Types.Pivot
import io.trosa.avian.models.{CassandraIndex, Index, RawIndex}
import com.outworkers.phantom.dsl._
import com.typesafe.config.ConfigFactory

object DefaultDatabaseConnector {
	private val config = ConfigFactory.load()

	private val hosts = config.getStringList("cassandra.host").asInstanceOf[Array[String]]
	private val keyspace = config.getString("cassandra.keyspace")

	val connector: CassandraConnection = ContactPoints(hosts).keySpace(keyspace)
}

/*
* Generic connector
* */

abstract class DatabaseConnector(override val connector: CassandraConnection)
    extends Database[DatabaseConnector](connector) {

	/*
	*  More database configuration go here.
	* */

	object index extends CassandraIndex with Connector
}

/*
* Database action singleton
* */

object DatabaseAction
    extends DatabaseConnector(DefaultDatabaseConnector.connector)

class CassandraActor extends Actor
    with ActorLogging {

		override def preStart(): Unit = super.preStart

		override def receive: Receive = {
			case RawIndex(index, pivot) => process(index, pivot)
			case _ => throw new AvianUnprocessableUrl(new Exception)
		}

		def process(index: Index, pivot: Pivot): Unit = {
			log.info("Storing to cassandra for pivot: %s".format(pivot))
		}

		override def postRestart(reason: Throwable): Unit = super.postRestart(reason)
}