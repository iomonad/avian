package com.avian

import com.avian.network.{Client}
import com.avian.scrape.{Scraper}

package object Main extends App {
    val b  = Scraper.parse(new Client("https://gentoo.org").getBody)
    val n = Scraper.getNodes(b)
    for(node <- n) println(node)
}
