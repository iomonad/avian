package com.avian.scrape

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.{Elements}
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import com.avian.types._

/* @Desc: Scraping utils object
 * @Author: iomonad <iomonad@riseup.net>
 */

/* @Desc: Scrape singleton for html utils.
 */
object Scraper extends ScrapeActions {
    /* @Desc: The body should be correctly
     * parsed before using interfaces actions.
     */
    def parse(body: String): JDoc = {
        Jsoup.parse(body)
    }
}

/* @Desc: Scraping actions that extend the
 * main `Scraper` singleton.
 */
trait ScrapeActions {
    /* @Desc: The string should be parsed to Document type,
     *  Using an alias to avoid confusions.
     */
    type JDoc = org.jsoup.nodes.Document

    /* @Desc: The Node is simply a list of string
     * that represent a crawl endpoint for the bot.
     */
    type Nodes = List[String]
    type Node = String

    /* @Desc: Some query helpers to grep values in body pages.
     */
    object get {

        def title(doc: JDoc): String = {
            doc.select("title").text
        }

        /* @Desc: Extract values from `<meta name="">`
         */
        def metan(doc: JDoc, value: String): String = {
            Option(doc.select(s"meta[name=$value]").first) match {
                case Some(e) => e.attr("content")
                case None  => ""
            }
        }

        /* @Desc: Extract values from `<meta property="">`
         */
        def metap(doc: JDoc, value: String): String = {
            Option(doc.select(s"meta[property=$value]").first) match {
                case Some(e) => e.attr("content")
                case None  => ""
            }
        }

        /* Get meta keywords from the documents,
         *  and split each word in list.
         */
        def keywords(doc: JDoc): List[String] = {
            Option(metan(doc,"keywords")) match {
                case Some(e) => e.split(",").map(_.toLowerCase.trim).toList
                case None => List("")
            }
        }
    }

    /* @Desc: Get all urls in the body and wrap result
     *  in a Link sequence `(title + href)`.
     */
    def findLink(doc: JDoc): Seq[Link] = {
        doc.select("a[href]").iterator.toList.map {
            l => Link(l.attr("title"), l.attr("href"))
        }
    }

    /* @Desc: Return Nodes on the pages.
     */
    def getNodes(doc: JDoc): Nodes = {
        doc.select("a[href]").iterator.toList.map {
            l => l.attr("href").toString
        }
    }
}

/* @Desc: Get partialy a body and return a Page object for
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
