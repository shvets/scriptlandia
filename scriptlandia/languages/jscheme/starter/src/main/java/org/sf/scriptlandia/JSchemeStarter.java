package org.sf.scriptlandia;

import jsint.InputPort;
import jsint.Scheme;
import jsint.U;
import jsint.E;

import java.io.StringReader;

public class JSchemeStarter extends Scheme {

  public static void main(String[] args) {
    /*if (!loadInit()) */defaultMain(args);
  }

  /**
   * The default main() behavior. *
   */
  public static void defaultMain(String[] files) {
    load(files[0]);
  }


}
