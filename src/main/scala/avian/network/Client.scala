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
    /* @Desc: Set immutable request in class instance.
     */
    val request: HttpResponse[String] = Http(url).asString
}

/* @Desc: Trait to extend for others methods, GET, POST ...
 */
trait ClientActions {
    /* @Desc: Non concrete type to overrid on class instance
     */
    val request: HttpResponse[String]

    /* @Desc: Extract values from headers.
     */
    object header {
        val a = getHeaders
        def getIn(key: String): String = {
            getHeaders.get(key) match {
                case Some(e) => e.mkString
                case _ => "undefined"
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
