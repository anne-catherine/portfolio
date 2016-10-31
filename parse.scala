#!/bin/sh
exec scala "$0" "$@"
!#

import scala.io.Source
import scala.xml._


object ParsedWords {



  def main(args: Array[String]) {



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



    val lines = Source.fromFile(args(0)).getLines.toVector
    val txt = lines.mkString
    val words = txt.split("\\W+").filterNot(_.isEmpty).distinct

    words.map (w => (w, parse (w)) )
  }

}


ParsedWords.main(args)
