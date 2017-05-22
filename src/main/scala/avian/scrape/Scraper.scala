package com.avian.scrape

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.{Elements}

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
    
    object get {
        def title(doc: JDoc): String = {
            doc.select("title").text
        }
    }
}
