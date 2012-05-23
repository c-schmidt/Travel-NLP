/*
* Copyright 2007-2012 WorldWide Conferencing, LLC
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions
* and limitations under the License.
*/

package org.scoding.nlp

import com.typesafe.play.mini._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.libs.json._
import Json._

/*
 * This object represents a small REST-service.
 * You can call this service with your browser.
 * http://your.host/transform?sentence=...  
 */
object TransformApp extends Application {
  private def toStringMap(in: Map[String,Seq[String]]) = in.map(e => e._1 -> e._2.head)
  
  def route  =  {
    case GET(Path("/transform")) =>  Action{ request =>
      val query = toStringMap(request.queryString)
      query.get("sentence") match {
        case Some(s:String) if s != "" =>
          val analysis = Transformer.analyse(s)
          val startPlaces = toJson(analysis.startPlaces)
          val endPlaces = toJson(analysis.endPlaces)
          val result = Map[String,JsValue](
            "startPlaces" -> startPlaces,
            "endPlace" -> endPlaces,
            "time" -> toJson(analysis.time),
            "numberOfDays" -> toJson(analysis.numberOfDays)
          )      
          Ok(toJson(result))
        case _ => Status(404)(toJson(Map("exception" -> "File Not Found")))
      }
    }
  }
}