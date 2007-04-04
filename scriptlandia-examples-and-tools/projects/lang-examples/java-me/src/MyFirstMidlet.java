import javax.microedition.midlet.MIDlet;

public class MyFirstMidlet extends MIDlet {
  public void pauseApp() {
  }

  public void destroyApp(boolean unconditional) {
    // уведомить систему о завершении
    notifyDestroyed();
  }
  public void startApp() {
    // получить строку атрибута имени мидлета
    String name = getAppProperty("MIDlet-Name");
    // вывести сообщение в системную область
    System.out.println("MIDlet "+ name + " says: Hello. World!");
  }

}