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

import org.specs._

/*
 * These tests are only to get an impression which sentences your system should 
 * normally understand. They are no developer tests because your system is not
 * deterministic! All tests depend on your knowledge base.
 */
object TransformerSpec extends Specification {
  "Transformer" should {
    "find all start places when searching" >> {
      val start1 = Transformer
        .getStartPlace("Welche Möglichkeiten habe ich von Asbach nach Oberschönau zu kommen.")
      val start2 = Transformer
        .getStartPlace("Bring mich nach Schmalkalden.")
      val start3 = Transformer
        .getStartPlace("Zeige mir eine Route von der Kissinger Straße in Meiningen zum Blechhammer 9 in Schweinfurt.")
      val start4 = Transformer
        .getStartPlace("Wie komme ich von Bad Brückenau nach Bad Neustadt.")
      
      start1 must_== List("Asbach") 
      start2 must_== List()
      start3 must_== List("Kissinger Straße", "Meiningen")
      start4 must_== List("Bad Brückenau")  
    }
  }
  
  "Transformer" should {
    "find all end targest when searching" >> {
      val end1 = Transformer
        .getEndPlace("Welche Möglichkeiten habe ich von Asbach nach Oberschönau zu kommen.")
      val end2 = Transformer
        .getEndPlace("Bring mich nach Schmalkalden.")
      val end3 = Transformer
        .getEndPlace("Zeige mir eine Route von der Kissinger Straße in Meiningen zum Blechhammer 9 in Schweinfurt.")
      val end4 = Transformer
        .getEndPlace("Wie komme ich von Bad Brückenau nach Bad Neustadt.")
      
      end1 must_== List("Oberschönau") 
      end2 must_== List("Schmalkalden")
      end3 must_== List("Blechhammer 9", "Schweinfurt")
      end4 must_== List("Bad Neustadt")  
    }
  }
  
  "Transformer" should {
    "find all times when searching" >> {
      val time1 = Transformer
        .getTime("Welche Möglichkeiten habe ich um am Montag von Asbach nach Oberschönau zu kommen.")
      val time2 = Transformer
        .getTime("Welche Möglichkeiten habe ich um am Dienstag von Asbach nach Oberschönau zu kommen.")
      val time3 = Transformer
        .getTime("Welche Möglichkeiten habe ich um am Mittwoch von Asbach nach Oberschönau zu kommen.")
      val time4 = Transformer
        .getTime("Welche Möglichkeiten habe ich um am Donnerstag von Asbach nach Oberschönau zu kommen.")
      val time5 = Transformer
        .getTime("Welche Möglichkeiten habe ich um am Freitag von Asbach nach Oberschönau zu kommen.")
      val time6 = Transformer
        .getTime("Welche Möglichkeiten habe ich um am Samstag von Asbach nach Oberschönau zu kommen.")
      val time7 = Transformer
        .getTime("Welche Möglichkeiten habe ich um am Sonntag von Asbach nach Oberschönau zu kommen.")
      val time8 = Transformer
        .getTime("Bring mich am Freitag zum Schwimmbad in Bad Brückenau.")
      
      val time9 = Transformer
        .getTime("Ich möchte heute von Schmalkalden nach Berlin.")
      val time10 = Transformer
        .getTime("Ich will heute nach Bad Kissingen.")
      val time11 = Transformer
        .getTime("Bring mich heute nach Bad Kissingen.")
      val time12 = Transformer
        .getTime("Bring mich dienstags nach Suhl.")
      val time13 = Transformer
        .getTime("Ich möchte morgen nach München.")  
      
      val time14 = Transformer
        .getTime("Ich möchte in drei Monaten nach Berlin.")
      val time15 = Transformer
        .getTime("Ich will donnerstags von Bad Brückenau nach Frankfurt.")
      val time16 = Transformer
        .getTime("Ich möchte mittwochs von Kissingen nach Bad Kissingen.")
        
      time1 must_== List("Montag")
      time2 must_== List("Dienstag")
      time3 must_== List("Mittwoch")
      time4 must_== List("Donnerstag")
      time5 must_== List("Freitag")
      time6 must_== List("Samstag")
      time7 must_== List("Sonntag")
      time8 must_== List("Freitag")
      
      time9 must_== List("heute")
      time10 must_== List("heute")
      time11 must_== List("heute")
      time12 must_== List("dienstags")
      time13 must_== List("morgen")
      time14 must_== List("drei Monaten")
      time15 must_== List("donnerstags")
      time16 must_== List("mittwochs")
      
    }
  }
}