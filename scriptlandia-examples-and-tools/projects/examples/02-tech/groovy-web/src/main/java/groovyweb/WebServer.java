package groovyweb;

import org.mortbay.jetty.Server;
import org.mortbay.http.HttpListener;
import org.mortbay.http.SocketListener;

public class WebServer {

  public static void main(String[] args) {
    Server server = null;
    try {
      server = new Server();
      server.addListener(listener());
      server.addWebApplication("groovy-web", "src/main/webapp");
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
      if (server != null) {
        try {
          server.stop();
        } catch (InterruptedException ie) {
          ie.printStackTrace();
        }
      }
    }
  }

  private static HttpListener listener() {
    HttpListener listener = new SocketListener();
    listener.setPort(8080);
    return listener;
  }

}