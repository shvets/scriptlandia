package org.sf.scriptlandia;

import java.io.*;
import java.util.*;

import org.symeria.xgql.XGQLContent;

/**
 * This class is used for starting XGQL file as executable.
 *
 * @author Alexander Shvets
 * @version 1.0 05/26/2007
 */
public class XgqlStarter {

  /**
   * Constructor used when creating Main for later arg processing
   * and startup
   */
  public XgqlStarter() {
  }

  /**
   * Starts the ant launcher.
   *
   * @param args command line parameters
   */
  public void start(String[] args) throws Throwable {
    Properties props = new Properties();

    String className1 = System.getProperty("xgql.className");
    String url1 = System.getProperty("xgql.url");
    String login1 = System.getProperty("xgql.login");
    String password1 = System.getProperty("xgql.password");

    props.load(new FileInputStream(System.getProperty("user.dir") + "/xgql.properties"));

    String className2 = props.getProperty("xgql.className");
    String url2 = props.getProperty("xgql.url");
    String login2 = props.getProperty("xgql.login");
    String password2 = props.getProperty("xgql.password");

    String className = (className1 == null) ? className2 : className1;
    String url = (url1 == null) ? url2 : url1;
    String login = (login1 == null) ? login2 : login1; 
    String password = (password1 == null) ? password2 : password1;

    if(className == null) {
      className = "org.hsqldb.jdbcDriver";
    }

    if(url == null) {
      url = "jdbc:hsqldb:file:testdb";
    }

    if(login == null) {
      login = "SA";
    }

    if(password == null) {
      password = "";
    }

    System.out.println("className: " + className);
    System.out.println("url: " + url);
    System.out.println("login: " + login);

    XGQLContent xgql = new XGQLContent(className, url, login, password);

    String fullFileName = args[0];

    String result = xgql.executeQuery(new File(fullFileName));

    System.out.println("Result: " + result);
  }

  /**
   * Command line entry point. 
   *
   * @param args Command line arguments. Must not be <code>null</code>.
   */
  public static void main(String[] args) throws Throwable {
    XgqlStarter starter = new XgqlStarter();

    starter.start(args);
  }

}
