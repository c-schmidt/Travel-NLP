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

/*
 * This class contains the times which are supported.
 * It is only a demo implementation.  
 */
class Time {
  import java.util.Calendar
  
  private val week = 7
  private val month = 30
  private val data = Calendar.getInstance
  private val currentDay = data.get(Calendar.DAY_OF_WEEK) match {
    case 1 => "Sonntag"
    case 2 => "Montag"
    case 3 => "Dienstag"
    case 4 => "Mittwoch"
    case 5 => "Donnerstag"
    case 6 => "Freitag"
    case 7 => "Samstag"
  } 
  
  private def calculateDiff(day: String):Int = {
    val current = weekMap.get(currentDay)
    val numberOfDay = weekMap.get(day)
    var div = 0
    if(!current.isEmpty && !numberOfDay.isEmpty) {
      if(current.get <= numberOfDay.get) div = numberOfDay.get - current.get
      if(current.get > numberOfDay.get) div = 7 - current.get + numberOfDay.get
    }
    div
  }
    
  private val weekMap = Map(
    "Sonntag" -> 7,
    "Montag" -> 1,
    "Dienstag" -> 2,
    "Mittwoch" -> 3,
    "Donnerstag" -> 4,
    "Freitag" -> 5,
    "Samstag" -> 6    
  )
   
  /*
   * This value maps a time expression to a number of days. 
   */
  val times = Map(
    "Sonntag" -> calculateDiff("Sonntag"),
    "Montag" -> calculateDiff("Montag"),
    "Dienstag" -> calculateDiff("Dienstag"),
    "Mittwoch" -> calculateDiff("Mittwoch"),
    "Donnerstag" -> calculateDiff("Donnerstag"),
    "Freitag" -> calculateDiff("Freitag"),
    "Samstag" -> calculateDiff("Samstag"),
    "heute" -> 0,
    "morgen" -> 1,
    "übermorgen" -> 2,
    "montags" -> calculateDiff("Montag"),
    "dienstags" -> calculateDiff("Dienstag"),
    "mittwochs" -> calculateDiff("Mittwoch"),
    "donnerstags" -> calculateDiff("Donnerstag"),
    "freitags" -> calculateDiff("Freitag"),
    "samstags" -> calculateDiff("Samstag"),
    "sonntags" -> calculateDiff("Sonntag"),
    "zwei Tagen" -> 2,
    "drei Tagen" -> 3,
    "vier Tagen" -> 4,
    "fünf Tage" -> 5,
    "sechs Tage" -> 6,
    "sieben Tage" -> 7,
    "Wochenende" -> calculateDiff("Samstag"),
    "einer Woche" -> week,
    "zwei Wochen" -> week * 2,
    "drei Wochen" -> week * 3,
    "vier Wochen" -> week * 5,
    "einem Monat" -> month,
    "zwei Monaten" -> month * 2,
    "drei Monaten" -> month * 3,
    "vier Monaten" -> month *4
  )
}