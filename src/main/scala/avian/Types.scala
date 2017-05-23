package com.avian.types

import com.avian.utils.{Utils}
/* @Desc: Projects types for database use
 * @Author: iomonad <iomonad@riseup.net>
 */

/* @Desc: Modelized page type
 */
sealed case class Link(title: String, href: String)
sealed case class Page(
    title: String,
    desc: String,
    links: Seq[Link],
    keywords: List[String]
)

/* @Desc: Main type that regroup:
 *    - Current crawled node
 *    - Responses headers
 *    - Reponses body
 */
sealed case class Index(
    url: String, //
    request: Request, // Headers
    page: Page, // Body page
    localnode: String // Current page
)

/* @Desc: The request type modelize responses
 *  Headers
 */
sealed case class Status(s: String, code: Int)
sealed case class Request(
    status: Status,
    headers: Seq[Hraw]
)

/* @Desc: Simple Header class to store headers values
 *  using keys values.
 */
sealed case class Hraw(k: String, v: String)

object Types {
    /* @Desc: Create storable index object.
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
