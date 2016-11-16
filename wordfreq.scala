#!/bin/sh
exec scala "$0" "$@"
!#

import scala.io.Source

object WordFreq {

  def main(args: Array[String]) {

    val lines = Source.fromFile(args(0)).getLines.toVector

    val txt = lines.mkString

    val words = txt.split("\\W+").filterNot(_.isEmpty)

    val grouped = words.groupBy(w => w)

    val freqs = grouped.map { case (k,v) => (k,v.size) }

    val sorted = freqs.toSeq.sortBy(_._2)

    for (wd <- sorted ) {
     println(wd)
     }

println("Read " + lines.size + " sections.")
println ("Read " + txt.size + " characters.")
println("Read " + words.size + " words.")
println ("Read " + grouped.size + " unique words.")

  }

}

WordFreq.main(args)
