package com.avian

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

/** @desc main entry 
 * @author iomonad <iomonad@riseup.net>
 */

import com.avian.worker.MainWorker
import com.avian.types.{ Query }
import akka.actor._

package object Main extends App {
    val system:ActorSystem = ActorSystem("CrawlerSystem")
    val w1:ActorRef = system.actorOf(Props[MainWorker], "w1")
    w1 ! Query("https://clojure.com")
    system.stop _
}
