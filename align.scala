import scala.io.Source


def aligned(s: String, parsedList: Vector[Array[String]] ) = {
    val parsedWord = parsedList.filter(_(0) == s)
    if (parsedWord.size == 0) {
      s
    } else {
      parsedWord.map(_(1)).mkString(",")
    }
  }


  val text = Source.fromFile("dbc.txt").getLines.toVector
  val textSplit = text.map(_.split("\t"))
  val sections = textSplit.map(_(1))
  val reff = textSplit.map(_(0))


  val parsedList = Source.fromFile("ParsedWords.txt").getLines.toVector
  val wordList = parsedList.map(_.split("\t")).filter(_.size == 2 )


  val alignedText = sections.map { s =>
    val words = s.split("\\W").filterNot(_.isEmpty)
    words.map ( w => aligned(w,wordList) ).mkString(" ")
  }

 println(alignedText)
