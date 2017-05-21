package com.avian.test.http

import org.scalatest._
import com.avian.http.client.Client

class HttpTest extends FlatSpec with Matchers {
    "The http request" should "be correctly handled" in {
        val a = new Client("http://ifconfig.io/")
        a.getStatus should be (200)
    }
    "The http user-agent" should "be set correctly" in {
        val a = new Client("http://ifconfig.io/ua")
        // TODO: a.getBody should be ("avian/1.1")
    }
    "The Server value in the header" should "be parsed retrived" in {
        val a = new Client("http://ifconfig.io/")
        a.header.getServer should be ("cloudflare-nginx")
    }
}
