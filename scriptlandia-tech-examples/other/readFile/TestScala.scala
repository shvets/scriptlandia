import scala.io.Source

object TestScala {
  val MAXLENGTH = 70

  def main(args: Array[String]): unit = {
    args.foreach { filename =>
      var file = Source.fromFile(filename)
      var counted = file.getLines.counted
      counted.foreach { line => 
        if (line.length - 1 > MAXLENGTH) {
          Console.println(filename + " line=" + (counted.count+1) + " chars=" + line.length)
        }
      }
    }
  }
}



