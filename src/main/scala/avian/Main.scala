package com.avian

import com.avian.http.client._

package object Main extends App {
    val quux = List("http://clojure.org","http://scala-lang.com","http://keybase.io")
    quux.map(x => println(new Client(x).getStatus))
}
