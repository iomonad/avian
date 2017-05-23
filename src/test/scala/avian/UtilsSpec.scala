package com.avian.test.utils

import org.scalatest._
import com.avian.utils.{Utils}

class UtilsTest extends FlatSpec with Matchers {
    "The getRoot regex" should "be used correctly" in {
        assert(Utils.Url.getRoot("https://google.nl/googlebot.html") == ("https://google.nl/"))
    }   
}
