package com.avian.test.http

import org.scalatest._
import com.avian.http.client.Client

class HttpTest extends FlatSpec with Matchers {
    "The http request" should "be correctly handled" in {
        val a = new Client("http://ifconfig.io/")
        val b = new Client("http://google.nl")
        a.getStatus should be (200)
        b.getStatus should be (301) // Google blocks some crawlers.
    }
    "The http user-agent" should "be set correctly" in {
        val a = new Client("http://ifconfig.co").getStatus
        val b = new Client("http://ifconfig.io").getStatus
        assert(a == b)
    }
    "The Server value in the header" should "be parsed retrived" in {
        val a = new Client("http://ifconfig.io/")
        a.header.getIn("Server") should be ("cloudflare-nginx")
        a.header.getIn("Content-Type") should be ("text/html; charset=utf-8")
        a.header.getIn("Content-Length") should be ("undefined") // Test default values.
    }
}
