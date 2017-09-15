/*
 * Copyright (c) 2017 Clement Trosa <me@trosa.io>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.trosa.avian.models

import io.trosa.avian.Types.{AbsNode, CurNode, rawBody}

case class Index(/** ******************/
                 curNode: CurNode, /* Page cursor */
                 absNode: AbsNode, /* Absolute domain */
                 /** ******************/
                 headers: Map[String, String], /* Response headers */
                 method: String = "GET", /* Default request method */
                 status: Int,
                 content_type: Option[String],
                 body: rawBody,

                 /** ******************/
                 pivots: Option[List[String]], /* Maybe pivot parents ~ 20 limits, classified by pertinence*/
                 ports: Option[List[Int]], /* Scanned port result */
                 index_date: Long = System.currentTimeMillis(),
                 updated_date: Long = System.currentTimeMillis()
                )
