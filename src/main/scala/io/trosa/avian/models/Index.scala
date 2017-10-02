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

package io.trosa.avian.models

import com.outworkers.phantom.CassandraTable
import com.outworkers.phantom.column.TimeUUIDColumn
import com.outworkers.phantom.connectors.{Connector, RootConnector}
import com.outworkers.phantom.dsl._
import io.trosa.avian.Types.{AbsNode, CurNode, Pivot, rawBody}

import scala.concurrent.Future

case class Index(/** ******************/
	curNode: CurNode, /* Page cursor */
	absNode: AbsNode, /* Absolute domain */
	/** ******************/
	headers: Map[String, String], /* Response headers */
	method: String = "GET", /* Default request method */
	status: Int,
	content_type: Option[String],
	body: rawBody,

	/** ******************/
	pivots: Option[List[String]], /* Maybe pivot parents ~ 20 limits, classified by pertinence*/
	index_date: Long = System.currentTimeMillis(),
	updated_date: Long = System.currentTimeMillis()
)

case class RawIndex(index: Index, pivot: Pivot)

/* Cassandra index model
*  Class extension DatabaseConnector: Custom
*  database access
* */

abstract class CassandraIndex extends CassandraTable[CassandraIndex, Index]
	with RootConnector {

	object id extends TimeUUIDColumn(this) with PartitionKey {
		override lazy val name = "index_id"
	}

	/* Datatype */

	val curNode = ???

	def save(index: Index): Future[ResultSet] = {
		store(index)
		 	.consistencyLevel_=(ConsistencyLevel.ALL)
			.future()
	}

	def getById(id: UUID): Future[Option[Index]] = {
		select.where(_.id eqs id).one()
	}
}