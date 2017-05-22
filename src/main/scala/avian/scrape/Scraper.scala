package com.avian.scrape

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.{Elements}
import scala.collection.JavaConversions._

/* @Desc: Scraping utils object
 * @Author: iomonad <iomonad@riseup.net>
 */

/* @Desc: Modelized page type
 */
sealed case class Link(title: String, href: String)
sealed case class Page(
    title: String,
    body: String,
    links: Seq[Link],
    origin: Link,
    meta: Seq[String]
)

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
    }

    /* @Desc: Get all urls in the body and wrap result
     *  in a Link sequence `(title + href)`.
     */
    def findUrl(doc: JDoc): Seq[Link] = {
        doc.select("a[href]").iterator.toList.map {
            l => Link(l.attr("title"), l.attr("href"))
        }
    }
}
