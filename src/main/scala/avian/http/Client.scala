package com.avian.http

import java.io._
import scalaj.http.{Http, HttpResponse, BaseHttp, HttpConstants}

/* @Desc: Network request singleton
 * @Author: Clement Trösa
 */
object Client {

    /* @Desc: Override default http client options.
     */
    object Http extends BaseHttp (
        proxyConfig = None,
        options = HttpConstants.defaultOptions,
        charset = HttpConstants.utf8,
        sendBufferSize = 4096,
        userAgent = "avian/1.1",
        compress = true
    )

    /* @Desc: Get request implementation
     */    
    object get {
        def apply(url: String): HttpResponse[String] = {
            Http(url).asString
        }
    }

    /* @Desc: Post request implementation
     */
    object post {
        def apply(url: String): HttpResponse[String] = {
            Http(url).method("POST").asString
        }
    }
}
