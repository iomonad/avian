package com.avian.database

/* @Desc: Database helper class for
 *  storing crawling results in the mongodb
 *  database.
 * @ Author: iomonad <iomonad@riseup.net>
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
    /* @Desc: Convert Index class to mongo object.
     */
    def indexToMongo(index: Index): MongoDBObject = {
        val builder = MongoDBObject.newBuilder
        builder += "domain" -> index.url
        builder += "headers" -> index.request
        builder += "page" -> index.page
        builder += "localnode" -> index.localnode
        builder.result
    }
    /* @Desc: Insert mongo object to the database
     */
    def insertIndex(index: Index): Unit = {
        MongoFactory.coll.save(indexToMongo(index))
    }
}
