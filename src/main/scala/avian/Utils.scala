package com.avian.utils

import scala.util.{Try}
import scala.util.control.{ControlThrowable, NonFatal}
import scala.util.matching.{Regex}
import java.net.{URL}

/* @Desc: Some helpers to execute some trivials tasks
 *  that don't need class instance.
 * @Author: iomonad <iomonad@riseup.net>
 */

object Utils {
    object Url {

        /* @Desc: Get root domain from url
         */
        def getRoot(localnode: String): String = {
            val url = new URL(localnode)
            url.getHost match {
                case u => s"https://$u/"
            }
        }
    }
}
