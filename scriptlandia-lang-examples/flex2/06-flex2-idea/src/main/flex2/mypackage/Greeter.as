package mypackage {

  public class Greeter {

    public function createGreeting(name:String):String  {
      var greetingText:String = 'Hello, ';

      greetingText += (name.length < 1) ? 'Anonymous' : name;

      return greetingText;
    }

  }

}
