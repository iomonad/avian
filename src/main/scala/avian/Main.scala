package com.avian

import com.avian.worker.MainWorker
import akka.actor._

package object Main extends App {
    val system:ActorSystem = ActorSystem("WorkerSystem")
    val w1:ActorRef = system.actorOf(Props[MainWorker], "w1")
    w1 ! "https://gentoo.org"
}
