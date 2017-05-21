package com.avian.scrape

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.Iterator
import org.jsoup.nodes._

object Scraper {

    def parseHtml(body: String) = {
        Jsoup.parse(body);
    }
    
    def extractUrls(html: String) = {

    }    
}
