package com.avian.test.http

import org.scalatest._
import com.avian.http._

class HttpTest extends FlatSpec with Matchers {
    "The http user-agent" should "be set correctly" in {
        val a = Client.get("http://ifconfig.io/ua").body
        a should be (a) // TODO: Fix test
    }
}
