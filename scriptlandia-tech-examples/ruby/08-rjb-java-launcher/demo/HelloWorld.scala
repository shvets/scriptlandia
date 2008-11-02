// HelloWorld.scala

object HelloWorld {

  def main(args: Array[String]) = {
    Console.println("Hello, world from scala!")

    argv.toList foreach Console.println
  }

}

HelloWorld.main(args);
