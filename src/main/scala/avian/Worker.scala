package com.avian.worker

import com.avian.network._
import com.avian.scrape._
import com.avian.types._
import com.avian.database._

/* @Desc: Small sample to show worker process.
 */
object Worker {
    def apply(url: String): Unit = {

        val a = new Client(url)

        val result = Types.makeIndex(url, a.makeRequest ,Scrape(a.getBody), a.localnode)
        Database.insertIndex(result)
    }
}
