package com.avian.database

/*
 *   Copyright (c) 2017 iomonad <iomonad@riseup.net>.
 *  
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *  
 *   http://www.apache.org/licenses/LICENSE-2.0
 *  
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

/** @desc database helper class for storing crawling 
  *  results in the mongodb database.
  * @author iomonad <iomonad@riseup.net>
  */

import com.mongodb.casbah.Imports._
import com.avian.utils.{Utils}
import com.avian.scrape.{Scrape}
import com.avian.types._

object MongoFactory {
    val client = MongoClient()
    val db = client("avian")
    val coll = db("avian")
}

object Database {
    /** @desc convert Index class to mongo object.
      * @param index page index
      */
    def indexToMongo(index: Index): MongoDBObject = {
        val builder = MongoDBObject.newBuilder
        builder += "domain" -> index.url
        builder += "headers" -> index.request
        builder += "page" -> index.page
        builder += "localnode" -> index.localnode
        builder.result
    }
    /** @desc insert mongo object to the database
      * @param index page index
      */
    def insertIndex(index: Index): Unit = {
        MongoFactory.coll.save(indexToMongo(index))
    }

   /** @desc update mongo object to the database
     * @param index page index
     */
    def updateIndex(index: Index): Unit = {
        MongoFactory.coll.update(MongoDBObject("localnode" -> index.localnode) ,indexToMongo(index))
    }

    /** @desc predicate that chech if current node have already crawled
      */
    def iscrawled(node: String): Boolean = {
        MongoFactory.coll.findOne("localnode" $ne node) match {
            case Some(e) => true
            case None => false
            case _ => false
        }
    }
}
