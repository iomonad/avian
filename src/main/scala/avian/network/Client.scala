package com.avian.network

import java.io._
import scalaj.http.{HttpResponse, BaseHttp, HttpConstants}

/* @Desc: Network request class
 * @Author: iomonad <iomonad@riseup.net>
 */

/* @Desc: Override default http client options.
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
    /* @Desc: Set mutable request in class instance for connection reuse.
     */
    var request: HttpResponse[String] = Http(url).asString

    /* @Desc: To avoid multiples instances, connections can be reused
     */
    def reuse(url: String): Unit = {
        request = Http(url).asString /* Override object instance `request` */
    }
}

class OnionClient(url: String) extends ClientActions {
    /* @Desc: Set tor proxy @127.0.0.1:9050 as SOCKS5 type.
     */
    var request: HttpResponse[String] = Http(url).proxy("127.0.0.1",9050).asString

    def reuse(url: String): Unit = {
        Http(url).proxy("127.0.0.1",9050).asString /* Override object instance `request` */
    }
}

/* @Desc: Trait to extend for others methods, GET, POST ...
 */
trait ClientActions {
    /* @Desc: Non concrete type to override on class instance
     */
    var request: HttpResponse[String]

    /* @Desc: Extract values from headers.
     */
    object header {
        val a = getHeaders
        def getIn(key: String): String = {
            getHeaders.get(key) match {
                case Some(e) => e.mkString
                case None => "undefined"
            }
        }
    }

    /* @Desc: get a kv Map of headers values
     */
    def getHeaders(): Map[String, IndexedSeq[String]] = {
        request.headers
    }

    def getStatus(): Int = {
        request.code match {
            case e => e.toInt
        }
    }

    def getBody(): String = {
        request.body match {
            case e => e.mkString
        }
    }
}
