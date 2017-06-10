package com.avian.network

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

/** @desc network request class
  * @author iomonad <iomonad@riseup.net>
  */

import java.io._
import scalaj.http.{HttpResponse, BaseHttp, HttpConstants}
import com.avian.utils.{Utils}
import com.avian.types._

/** @desc: Override default http client options.
  */
override object Http extends BaseHttp (
    proxyConfig = None,
    options = HttpConstants.defaultOptions,
    charset = HttpConstants.utf8,
    sendBufferSize = 4096,
    userAgent = "Mozilla/5.0 (X11; Linux x86_64; rv:52.0) Gecko/20100101 Firefox/52.0",
    compress = true
)

class Client(url: String) extends ClientActions {

    /** @desc we store current url to parse current node.
      */
    var localnode: String = url

    /** @desc set mutable request in class instance for connection reuse.
      */
    var request: HttpResponse[String] = Http(url).asString

    /** @desc to avoid multiples instances, connections can be reused
      */
    def reuse(url: String): Unit = {
        request = Http(url).asString /* Override object instance `request` */
        localnode = url
    }
}

/** @desc same client but route request throught localhost:9050 proxy
  *  to access onion hostnames.
  */
class OnionClient(url: String) extends ClientActions {

    /** @desc: We store current url to parse current node.
      */
    var localnode: String = url  
    
    /** @desc: Set tor proxy @127.0.0.1:9050 as SOCKS5 type.
      */
    var request: HttpResponse[String] = Http(url).proxy("127.0.0.1",9050).asString

    def reuse(url: String): Unit = {
        Http(url).proxy("127.0.0.1", 9050).asString /* Override object instance `request` */
        localnode = url
    }
}

/** @desc trait to extend for others methods, GET, POST ...
  */
trait ClientActions {

    /** @desc we store current url to parse current node.
      */
    var localnode: String

    /** @desc non concrete type to override on class instance
      */
    var request: HttpResponse[String]

    /** @desc extract values from headers.
      */
    object header {
        val a = getHeaders
        def getIn(key: String): String = {
            getHeaders.get(key) match {
                case Some(e) => e.mkString
                case None => "undefined"
            }
        }

        /** @desc return Headers values in sequences.
          */
        def commonToSeq(): Seq[Hraw] = {
            List("Server","Content-length").toList.map((i: String)
                => Hraw(i, getIn(i))
            )
        }

    }

    /** @desc get a kv Map of headers values
      */
    def getHeaders(): Map[String, IndexedSeq[String]] = {
        request.headers
    }

    def getStatus(): Status = {
        request.code match {
            case e => Status("Status", e.toInt)
        }
    }

    def getBody(): String = {
        request.body match {
            case e => e.mkString
        }
    }

    /** @desc get Request types from headers responses.
      */
    def makeRequest(): Request = {
        Request(
            getStatus, /* Use Status type to store code*/
            header.commonToSeq /* Use headers seq */
        )
    }

    def getRobot(): Map[String, String] = {

        /** @desc Get root domain to parse robots.
          */
        val a: String = Utils.Url.getRoot(localnode) ++ "/robots.txt"
        Http(a).asString.body.split("\n").map(_.split(":")).map(arr => arr(0) -> arr(1)).toMap
    }
}
