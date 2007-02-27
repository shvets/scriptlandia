package org.sf.scriptlandia.nailgun;

import com.martiansoftware.nailgun.NGServer;

/**
 * This is separate thread for shutting down NG server.
 *
 * @author Alexander Shvets
 * @version 1.0 12/16/2006
 */
public class NGServerShutdowner extends Thread {
  /** The Nailgun server. */
  private NGServer server;

  /**
   *  Creates new NG server shutdowner.
   *
   * @param server the Nailgun server
   */
  public NGServerShutdowner(NGServer server) {
    this.server = null;
    this.server = server;
  }

  /**
   * Main thread lifecycle method.
   */
  public void run() {
    int i = 0;
    server.shutdown(false);
    for(; server.isRunning() && i < 50; i++) {
      try {
        Thread.sleep(100L);
      }
      catch(InterruptedException interruptedexception) {
        // ignore it
      }
    }

    if(server.isRunning()) {
      System.err.println("Unable to cleanly shutdown server.  Exiting JVM Anyway.");
    } else {
      System.out.println("NGServer shut down.");
    }
  }

}
