package com.avian.test.parse

import org.scalatest._
import scala.io.{Source}
import com.avian.network.{Client}
import com.avian.scrape.{Scraper, Scrape, Link, Page}

class ParsingTest extends FlatSpec with Matchers {
    "The scrapings actions" should "be executed correctly" in {
        val a = Source.fromURL(getClass.getResource("/trivial.html")).mkString
        val parsed = Scraper.parse(a)
        assert(Scraper.get.title(parsed) == ("This is test title"))
        assert(Scraper.get.metan(parsed, "description") == ("This is a description"))
        assert(Scraper.get.metan(parsed, "buggymetaname") == (""))
        assert(Scraper.get.metap(parsed, "og:title") == ("Welcome to the site"))
        assert(Scraper.get.metap(parsed, "buggy:og:title") == (""))        
    }
    "The url finder functions" should "scrape and return link in sequence correctly" in {
        val a = Source.fromURL(getClass.getResource("/trivial.html")).mkString
        val parsed = Scraper.parse(a)
        Scraper.findLink(parsed) shouldEqual List(Link("Main Gentoo website","https://www.gentoo.org/"),Link("Contribute","https://wiki.gentoo.org/"))
    }
    "Nodes" should "correctly discovered" in {
        val a = Source.fromURL(getClass.getResource("/trivial.html")).mkString
        val parsed = Scraper.parse(a)
        assert(Scraper.getNodes(parsed) == (List("https://www.gentoo.org/","https://wiki.gentoo.org/")))
    }
    "Scrape object" should "return a correct Page type" in {
        val a = Source.fromURL(getClass.getResource("/trivial.html")).mkString
        assert(Scrape(a) == (Page
            (
                "This is test title",
                "This is a description",
                List(
                    Link("Main Gentoo website", "https://www.gentoo.org/"),
                    Link("Contribute","https://wiki.gentoo.org/")),
                List("gentoo","is","indexed","by","google"))))
    }
}
