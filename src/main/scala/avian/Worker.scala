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

class MainWorker(url: String) extends Actor {
    override def receive = {
        case s: String => {
            val a = new Client(s)

            /* forge result */
            val result = Types.makeIndex(s, a.makeRequest ,Scrape(a.getBody), a.localnode)
            /* Index data to database */
            Database.insertIndex(result)
        }        
    }
}
