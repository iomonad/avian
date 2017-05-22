package com.avian

import com.avian.network.{Client}

package object Main extends App {
    val quux = List("https://gentoo.org")
    quux.map(x => println(new Client(x).getBody))
}
