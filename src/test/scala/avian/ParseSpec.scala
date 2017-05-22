package com.avian.test.parse

import org.scalatest._
import scala.io.{Source}
import com.avian.network.{Client}
import com.avian.scrape.{Scraper}

class ParsingTest extends FlatSpec with Matchers {
    "The scrapings actions" should "be executed correctly" in {
        val a = Source.fromURL(getClass.getResource("/trivial.html")).mkString
        val parsed = Scraper.parse(a)
        assert(Scraper.get.title(parsed) == ("This is test title"))
    }
}
