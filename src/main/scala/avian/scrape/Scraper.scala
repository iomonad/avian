package com.avian.scrape

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


/** @desc scraping utils object
  * @author iomonad <iomonad@riseup.net>
  */

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.{Elements}
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import com.avian.types._

/** @desc scrape singleton for html utils.
  */
object Scraper extends ScrapeActions {
    /** @desc the body should be correctly
      * parsed before using interfaces actions.
      */
    def parse(body: String): JDoc = {
        Jsoup.parse(body)
    }
}

/** @desc scraping actions that extend the
  * main `Scraper` singleton.
  */
trait ScrapeActions {
    /** @desc the string should be parsed to Document type,
      *  Using an alias to avoid confusions.
      */
    type JDoc = org.jsoup.nodes.Document

    /** @desc the Node is simply a list of string
      * that represent a crawl endpoint for the bot.
      */
    type Nodes = List[String]
    type Node = String

    /** @desc some query helpers to grep values in body pages.
      */
    object get {

        def title(doc: JDoc): String = {
            doc.select("title").text
        }

        /** @desc extract values from `<meta name="">`
          */
        def metan(doc: JDoc, value: String): String = {
            Option(doc.select(s"meta[name=$value]").first) match {
                case Some(e) => e.attr("content")
                case None  => ""
            }
        }

        /** @desc extract values from `<meta property="">`
          */
        def metap(doc: JDoc, value: String): String = {
            Option(doc.select(s"meta[property=$value]").first) match {
                case Some(e) => e.attr("content")
                case None  => ""
            }
        }

        /** @desc get meta keywords from the documents,
          *  and split each word in list.
          */
        def keywords(doc: JDoc): List[String] = {
            Option(metan(doc,"keywords")) match {
                case Some(e) => e.split(",").map(_.toLowerCase.trim).toList
                case None => List("")
            }
        }
    }

    /** @desc get all urls in the body and wrap result
      *  in a Link sequence `(title + href)`.
      */
    def findLink(doc: JDoc): Seq[Link] = {
        doc.select("a[href]").iterator.toList.map {
            l => Link(l.attr("title"), l.attr("href"))
        }
    }

    /** @desc return Nodes on the pages.
      */
    def getNodes(doc: JDoc): Nodes = {
        doc.select("a[href]").iterator.toList.map {
            l => l.attr("href").toString
        }
    }
}

/** @desc get partialy a body and return a Page object for
  *  the database actor.
  */
object Scrape extends ScrapeActions {
    def apply(body: String): Page = {
        val a = Scraper.parse(body)
        Page(
            Scraper.get.title(a),
            Scraper.get.metan(a,"description"),
            Scraper.findLink(a),
            Scraper.get.keywords(a)
        )
    }
}
