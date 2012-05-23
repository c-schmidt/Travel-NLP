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

import java.io.FileInputStream
import java.io.IOException
import opennlp.tools.namefind.{TokenNameFinderModel, NameFinderME, NameSample}
import opennlp.tools.util.Span

case class TransformResult(startPlaces: List[String], endPlaces: List[String], time: String, numberOfDays: Int) 

object Transformer {
  private val userDir = System.getProperty("user.dir")
  private val path = "/src/main/resources/ner/"
  private val startPlaceFile = userDir + path + "de-start.bin"
  private val endPlaceFile = userDir + path + "de-end.bin"
  private val timeFile = userDir + path + "de-time.bin"
    
  /*
   * To get the right modle-file for the NER-process.
   * @param file is the filename
   */
  private def getModle(file: String): Option[TokenNameFinderModel] = {
    val modelIn = new FileInputStream(file)
    
    try {
      Some(new TokenNameFinderModel(modelIn))
    }
    catch {
      case e: IOException => e.printStackTrace()
        None
    }
    finally {
      if (modelIn != null) {
        try {
          modelIn.close()
        }
        catch {
          case _ =>
        }
       }
    }
  }
  
  /*
   * To get a NameFinder object for the NER-process.
   * @param file is the filename
   */
  private def getNameFinder(file: String): Option[NameFinderME] = {
    val model = getModle(file)
    if(!model.isEmpty) Some(new NameFinderME(model.get))
    else None
  }
  
  /*
   * To transform a sentence in the expected format.
   * @param sentence to transform 
   */
  private def transformSentence(sentence: String) = {
    sentence.replaceAll(",","").split(" ")
  }
  
  /*
   * To calculate the entities with the calculated probabilities. 
   */
  private def calculateEntities(spans: Array[Span], sentenceArr: Array[String]) = {      
    var entities = spans map { span =>
      val start = span.getStart()
      val end = span.getEnd() - 1 
      var entity = "" 
      for(tokenIndex <- start to end) {
        if(tokenIndex < end) entity = entity + sentenceArr(tokenIndex) + " "
        else entity = entity + sentenceArr(tokenIndex) + ""
      }
      entity.replace(".","")
    } 
    entities.toList
  }
  
  /*
   * Makes the NER-process with a sentence and a model.
   * The result is a list with all matched entities
   */
  private def nerProcess(sentence: String, file: String) = {
    val sentenceArr = transformSentence(sentence)
    val nameFinder = getNameFinder(file)
    nameFinder match {
      case Some(nf) => 
        val spans = nf.find(sentenceArr)
        nf.clearAdaptiveData()
        calculateEntities(spans, sentenceArr)
      case None => List[String]()  
    }
  }
  
  /*
   * To analyse the start places in a sentence.
   */
  def getStartPlace(sentence: String) = {
    nerProcess(sentence, startPlaceFile) 
  }
  
  /*
   * To analyse the target places in a sentence.
   */
  def getEndPlace(sentence: String) = {
    nerProcess(sentence, endPlaceFile) 
  }
  
  /*
   * To analyse the time token.
   */
  def getTime(sentence: String) = {
    nerProcess(sentence, timeFile)
  }
  
  /*
   * To get the days between the current day an the start of
   * journey.
   * @param time is a time token like 'Montag'
   */
  def getNumberOfDays(time: String) = {
    val timeCalculator = new Time
    timeCalculator.times.get(time).getOrElse(0) 
  }
  
  def analyse(sentence: String) = {
    val timeCalculator = new Time
    val startPlaces = getStartPlace(sentence)
    val endPlaces = getEndPlace(sentence) 
    val time = getTime(sentence) match {
      case List() => ""
      case List((t:String),_*) => t  
    }
    val numberOfDays = getNumberOfDays(time)
    
    TransformResult(startPlaces, endPlaces, time, numberOfDays)
  } 
}