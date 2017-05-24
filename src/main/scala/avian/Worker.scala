package com.avian.worker

import com.avian.network._
import com.avian.scrape._
import com.avian.types._
import com.avian.database._
import akka.actor._

/* @Desc: Small sample to show worker process.
 *  TODO: Implement actor class.
 */
class MainWorker(url: String) extends Actor {
    override def receive = {
        case s: String => {
            val a = new Client(s)

            val result = Types.makeIndex(s, a.makeRequest ,Scrape(a.getBody), a.localnode)
            Database.insertIndex(result)
        }        
    }
}
