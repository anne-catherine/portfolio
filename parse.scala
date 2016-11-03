#!/bin/sh
exec scala "$0" "$@"
!#

import scala.io.Source
import scala.xml._


object ParsedWords {


  def main(args: Array[String]) {

    val lines = Source.fromFile(args(0)).getLines.toVector
    val txt = lines.mkString
    val words = txt.split("\\W+").filterNot(_.isEmpty).distinct

    for (w <- words){
      val parsed = parse(w)
      for (p <- parsed) {
          println(w + "\t" + p)
      }
    }
}

def lemmaForEntry (nseq: NodeSeq) = {
     if (nseq.size > 0) {
     nseq(0).text
     } else ""
      }


def idForEntry (nseq: NodeSeq) = {
     if (nseq.size > 0) {
     nseq(0).text.replaceFirst("http://data.perseus.org/collections/urn:cite:perseus:latlexent.", "")
     } else ""
      }


def formatEntry(e: Elem) : String = {
     val uriGroup = e \ "@uri"
     val uri = idForEntry(uriGroup)
     val headwordList = e \\ "hdwd"
     val headword = lemmaForEntry (headwordList)
     uri + "_" + headword
    }


def parse (s: String)  = {
     val baseUrl = "https://services.perseids.org/bsp/morphologyservice/analysis/word?&lang=lat&engine=morpheuslat&word="
     val request = baseUrl + s
     val morphReply = scala.io.Source.fromURL(request).mkString

     val root = XML.loadString(morphReply)
     val entries = root \\ "entry"

     val lexent = entries.map( e => e match {
     case el: Elem => formatEntry(el)
     case _ => ""
    } )
    lexent

    }

  }

ParsedWords.main(args)
