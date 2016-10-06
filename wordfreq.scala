#!/bin/sh
exec scala "$0" "$@"
!#

import scala.io.Source

object WordFreq {

  def main(args: Array[String]) {

    val lines = Source.fromFile(args(0)).getLines.toVector

    println("Read "  + lines.size + " sections")

    val txt = lines.mkString

    println ("Read " + txt.size + " characters.")

    val words = txt.split("\\W+").filterNot(_.isEmpty)

    println("Read " + words.size + " words.")

    val grouped = words.groupBy(w => w)

    println ("Read " + grouped.size + " unique words.")

    val freqs = grouped.map { case (k,v) => (k,v.size) }

    val sorted = freqs.toSeq.sortBy(_._2)

    for (wd <- sorted ) {
     println(wd)
     }

  }

}

WordFreq.main(args)
