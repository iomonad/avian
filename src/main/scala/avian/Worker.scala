package com.avian.worker

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

/** @desc small sample to show worker process.
  * @author iomonad <iomonad@riseup.net>
  * @todo implement actor class.
  */

import com.avian.network._
import com.avian.scrape._
import com.avian.types._
import com.avian.database._
import akka.actor._
import akka.event.Logging

class MainWorker extends Actor {

    val log = Logging(context.system, this)
    val balancer = context.actorOf(Props[BalancerActor], "balancer")

    override def receive = {
        case Query(s) => {
            log.info("Running on %s query.".format(s))
            val a = new Client(s)
            /* forge result */
            val result = Types.makeIndex(s, a.makeRequest ,Scrape(a.getBody), a.localnode)           
            /* Index data to database */
            Database.insertIndex(result)            
            /* Send nodes to balancer */
            val nd = Scraper.findLink(Scraper.parse(a.getBody))
            for(n <- nd) {
                n match {
                    case Link(desc, node) => balancer ! Node(node)
                    case _ => log.error("Error while parsing node.")
                }
            }
        }
        case _ => log.error("Unknow type passed to actor.")
    }
}

class BalancerActor extends Actor {

    val log = Logging(context.system, this)
    val crawler = context.actorOf(Props[MainWorker], "crawler")

    override def receive = {
        case Node(node) => {
            log.info("Got a node: %s".format(node))
            // TODO: Link checking
            crawler ! Query(node)
        }
        case _ => log.error("Unknow type passed to actor.")
    }

}
