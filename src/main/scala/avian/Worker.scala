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
import com.avian.utils.Utils
import com.avian.database._
import scala.concurrent.duration._
import akka.util.Timeout
import akka.actor._
import akka.event.Logging
import akka.actor.SupervisorStrategy._

/** @desc Regular worker that process new url
  * not already crawler a returns nodes to balancer
  */
class RegularWorker extends Actor {

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
                    case Link(desc, node) => Utils.Url.regulize(node) match {
                        case Some(node) => balancer ! Node(node)
                        case None => /* Avoid malformed urls */
                    }
                    case _ => log.error("Error while parsing node.")
                }
            }
        }
        case _ => log.error("Unknow type passed to actor.")
    }
}

/** @desc update worker that re-crawl link
  *  and update is content in the database
  */
class UpdateWorker extends Actor {

    val log = Logging(context.system, this)
    val balancer = context.actorOf(Props[BalancerActor], "balancer")

    override def receive = {
        case Query(s) => {
            log.info("Running on %s query.".format(s))

            val a = new Client(s)

            /* forge result */
            val result = Types.makeIndex(s, a.makeRequest ,Scrape(a.getBody), a.localnode)

            /* update data to database */
            Database.updateIndex(result)

            /* Send nodes to balancer */
            val nd = Scraper.findLink(Scraper.parse(a.getBody))
            for(n <- nd) {
                n match {
                    case Link(desc, node) => Utils.Url.regulize(node) match {
                        case Some(node) => balancer ! Node(node)
                        case None => 
                    }
                    case _ => log.error("Error while parsing node.")
                }
            }
        }
        case _ => log.error("Unknow type passed to actor.")
    }
}

class OnionWorker extends Actor {

    val log = Logging(context.system, this)
    val balancer = context.actorOf(Props[BalancerActor], "balancer")

    override def receive = {
        case Query(s) => {
            log.info("Running on %s query.".format(s))

            val a = new OnionClient(s) /* Use request through tor proxy */


            /* forge result */
            val result = Types.makeIndex(s, a.makeRequest ,Scrape(a.getBody), a.localnode)           

            /* update data to database */
            Database.updateIndex(result)            

            /* Send nodes to balancer */
            val nd = Scraper.findLink(Scraper.parse(a.getBody))
            for(n <- nd) {
                n match {
                    case Link(desc, node) => Utils.Url.regulize(node) match {
                        case Some(node) => balancer ! Node(node)
                        case None => 
                    }
                    case _ => log.error("Error while parsing node.")
                }
            }
        }
        case _ => log.error("Unknow type passed to actor.")
    }    
}

/** @desc main supervisor that balance work to actors.
  */
class BalancerActor extends Actor {

    /** @desc redefining supervison strategy
      */
    override val supervisorStrategy = OneForOneStrategy(
        maxNrOfRetries = 2,
        withinTimeRange = 2 seconds) {
        case _: Exception => Restart /* retry request on exception, 2 times ; else die */
    }

    /* Worker references */
    val cfetch = context.actorOf(Props[RegularWorker], "cfetch")
    val ufetch = context.actorOf(Props[UpdateWorker], "ufetch")
    val ofetch = context.actorOf(Props[OnionWorker], "ofetch")
    val log = Logging(context.system, this)
   
    override def receive = {
        case Node(node) => {
            log.info("Got a node: %s".format(node))
            // TODO: writing a dispatching system
            cfetch ! Query(node)
        }
        case _ => log.error("Unknow type passed to balancer.")
    }
}
