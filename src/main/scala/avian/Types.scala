package com.avian.types

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

/** @desc projects types for database use
  * @author iomonad <iomonad@riseup.net>
  */

import com.avian.utils.{Utils}

/** @desc query type for actor request
  */
sealed case class Query(url: String)

/** @desc modelized page type
  */
sealed case class Link(title: String, href: String)
sealed case class Node(node: String)
sealed case class Page(
    title: String,
    desc: String,
    links: Seq[Link],
    keywords: List[String]
)

/** @desc Main type that regroup
  *  (1) -> Current crawled node
  *  (2) ->  Responses headers
  *  (3) ->  Reponses body
  */
sealed case class Index(
    url: String, //
    request: Request, // Headers
    page: Page, // Body page
    localnode: String // Current page
)

/** @desc the request type modelize responses headers
  */
sealed case class Status(s: String, code: Int)
sealed case class Request(
    status: Status,
    headers: Seq[Hraw]
)

/** @desc simple Header class to store headers values
  *  using keys values.
  */
sealed case class Hraw(k: String, v: String)

object Types {
    /** @desc create storable index object.
      * @param url current parent referent
      * @param request headers and related request data
      * @param page html page data
      */
    def makeIndex(url: String, request: Request, page: Page, localnode: String): Index = {
        Index(
            Utils.Url.getRoot(url), /* Domain */
            request, /* Headers Seq */
            page, /* Parsed body */
            localnode /* Current node (url) */
        )
    }
}
