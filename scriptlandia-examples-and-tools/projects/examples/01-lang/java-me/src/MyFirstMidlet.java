import javax.microedition.midlet.MIDlet;

public class MyFirstMidlet extends MIDlet {
  public void pauseApp() {
  }

  public void destroyApp(boolean unconditional) {
    // 㢥������ ��⥬� � �����襭��
    notifyDestroyed();
  }
  public void startApp() {
    // ������� ��ப� ��ਡ�� ����� ������
    String name = getAppProperty("MIDlet-Name");
    // �뢥�� ᮮ�饭�� � ��⥬��� �������
    System.out.println("MIDlet "+ name + " says: Hello. World!");
  }

}