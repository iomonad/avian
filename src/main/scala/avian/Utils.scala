package com.avian.utils

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

/** @desc some helpers to execute some trivials tasks
  *  that don't need class instance.
  * @author iomonad <iomonad@riseup.net>
  */

import scala.util.{Try}
import scala.util.control.{ControlThrowable, NonFatal}
import scala.util.matching.{Regex}
import java.net.{ URL, MalformedURLException }
import scala.util.control.Exception._

object Utils {
    object Url {
        /** @desc get root domain from url
          * @param localnode current url
          */
        def getRoot(localnode: String): String = {
            val url = new URL(localnode)
            url.getHost match {
                case u => s"https://$u/"
            }
        }

        /** @desc regularize url for safe and nonnull request
          * @param url url to normalize
          */
        def regulize(url: String): Option[String] = {
            catching(classOf[MalformedURLException]) opt new URL(url) match {
                case Some(v) => Some(v.toString)
                case None => None
            }
        }
    }
}
