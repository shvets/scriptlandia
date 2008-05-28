// ext-xml-helper.bsh

package org.sf.scriptlandia.install;

import org.jdom.Element;
import org.jdom.Attribute;
import org.jdom.JDOMException;
import org.sf.jlaunchpad.xml.XmlHelper;

import net.sf.image4j.codec.ico.ICODecoder;

import java.util.*;
import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.*;

import org.apache.tools.ant.Project;

import javax.swing.*;

public class ExtXmlHelper extends XmlHelper {
  private List languages = new ArrayList();

  public List getLanguages() {
    return languages;
  }

  public void readLanguages(String languageDir) {
    languages.clear();

    File[] list = new File(languageDir).listFiles();

    for (File aList : list) {
      if (!aList.isHidden() && aList.isDirectory()) {
        String name = aList.getName();

        try {
          String fileName = languageDir + "/" + name + "/language.xml";

          if (new File(fileName).exists()) {
            Map map = readLanguage(languageDir, name);

            if (map != null) {
              languages.add(map);
            }
          }
        }
        catch (Throwable t) {
          t.printStackTrace();
          System.out.println("Error reading \"language.xml\" file for language: " + name + ".");
        }
      }
    }
  }

  public Map readLanguage() throws IOException, JDOMException {
    String userDir = System.getProperty("user.dir");

    String path = new File(userDir).getCanonicalPath();

    return readLanguage(new File(path).getParentFile().getPath(), new File(userDir).getName());
  }

  public Map readLanguage(String languageDir, String name) throws IOException, JDOMException {
    Map map = new HashMap();

    String fileName = languageDir + "/" + name + "/language.xml";

    read(new File(fileName));

    Element root = document.getRootElement();

    Element registration = getElementByName(root, "registration");

    List extensions = getElementByName(registration, "extensions").getChildren();

    Attribute disabled = root.getAttribute("disabled");

    if(disabled != null && disabled.getValue().equalsIgnoreCase("true")) {
      return null;
    }

    String name2 = root.getAttribute("name").getValue();

    map.put("name", name2);

    Attribute groupId = root.getAttribute("groupId");

    if(groupId != null) {
      map.put("groupId", groupId.getValue());
    }
  
    Attribute artifactId = root.getAttribute("artifactId");

    if(artifactId != null) {
      map.put("artifactId", artifactId.getValue());
    }

    Attribute version = root.getAttribute("version");

    if(version != null) {
      map.put("version", version.getValue());

      System.setProperty(name2 + ".version", version.getValue());
    }

    map.put("mimeType", registration.getAttribute("mimeType").getValue());
    map.put("icon", registration.getAttribute("icon").getValue());

    String location = languageDir + "/" + name2;

    File file = new File(location + "/" + registration.getAttribute("icon").getValue());

    if(!file.exists()) {
      file = new File(languageDir + "/scriptlandia/scriptlandia.ico");
    }

  /*  String scriptlandiaHome = System.getProperty("scriptlandia.home");

    File file = new File(scriptlandiaHome + "/images/" + registration.getAttribute("icon").getValue());

    if(!file.exists()) {
      file = new File(scriptlandiaHome + "/images/" + "scriptlandia.ico");
    }
    */

    java.util.List images = ICODecoder.read(file);
    ImageIcon icon = new ImageIcon((Image)images.get(0));

    map.put("imageIcon", icon);


    Element starter = getElementByName(registration, "starter");

    Element mainClass = getElementByName(starter, "mainClass");

    if(mainClass != null) {
      map.put("mainClass", mainClass.getValue());

      XmlHelper starterXml = new XmlHelper();
      starterXml.read(new File(languageDir + "/" + name + "/starter/pom.xml"));

      map.put("starter.groupId", starterXml.getElementByName("groupId").getValue());
      map.put("starter.artifactId", starterXml.getElementByName("artifactId").getValue());
      map.put("starter.version", starterXml.getElementByName("version").getValue());
    }
    else {
      Element starterScript = getElementByName(starter, "script");

      if(starterScript != null) {
        map.put("starterScript", starterScript.getValue());
      }
    }
    
    Element commandLine = getElementByName(starter, "commandLine");

    if(commandLine != null) {
      map.put("commandLine", replaceNames(commandLine.getValue()));
    }

    List list = new ArrayList();

    for(int j=0; j < extensions.size(); j++) {
      Element extension = (Element)extensions.get(j);

      list.add(extension.getValue());
    }

    map.put("extensions", list);

    Element script = getElementByName(registration, "script");
    if(script == null) {
       map.put("scriptName", name);
    }
    else {
      map.put("scriptName", script.getAttribute("name").getValue());
    }

    return map;
  }

  private String replaceNames(String text) {
    StringBuffer newText = new StringBuffer();

    String s = text;

    boolean ok = false;
    while(!ok) {
      int index1 = s.indexOf("${");

      if(index1 == -1) {
        newText.append(s);

        ok = true;
      }
      else {
        int index2 = s.indexOf("}");

        if(index2 == -1) {
          newText.append(s);

          ok = true;
        }
        else {
          String propertyName = s.substring(index1+2, index2);

          String propertyValue = System.getProperty(propertyName);

          if(propertyValue != null) {
            newText.append(s.substring(0, index1));            
            newText.append(propertyValue.replace('\\', '/'));
          }

          s = s.substring(index2+1);
        }
      }
    }

    return newText.toString();
  }


  public void copyProperties(String propsFileName) throws IOException {
    Properties props = new Properties();

    File propsFile = new File(propsFileName);

    if(propsFile.exists()) {
      props.load(new FileInputStream(propsFile));
    }

    String repositoryHome = System.getProperty("repository.home");

    for (Object language : languages) {
      Map map = (Map) language;

      if (map.get("name") == null || map.get("groupId") == null ||
          map.get("artifactId") == null || map.get("version") == null) {
        continue;
      }

      String name = (String) map.get("name");
      String groupId = (String) map.get("groupId");
      String artifactId = (String) map.get("artifactId");
      String version = (String) map.get("version");

      props.put(name + ".version", version);
      props.put(name + ".base", repositoryHome + "/" + groupId.replace('.', '/') + "/" +
          artifactId + "/" + version);
    }

    props.store(new FileOutputStream(propsFileName), "Scriptlandia properties");
  }

  public void setupProperties(Project project) {
    String repositoryHome = System.getProperty("repository.home");

    for (Object language : languages) {
      Map map = (Map) language;

      if (map.get("name") == null || map.get("groupId") == null ||
          map.get("artifactId") == null || map.get("version") == null) {
        continue;
      }

      String name = (String) map.get("name");
      String groupId = (String) map.get("groupId");
      String artifactId = (String) map.get("artifactId");
      String version = (String) map.get("version");

      project.setNewProperty(name + ".version", version);
      project.setNewProperty(name + ".base", repositoryHome + "/" + groupId.replace('.', '/') + "/" +
          artifactId + "/" + version);
    }
  }

  public static void main(String[] args) throws IOException, JDOMException {
    ExtXmlHelper xmlHelper = new ExtXmlHelper();
    xmlHelper.readLanguages("../../../../../languages");

    System.out.println(xmlHelper.getLanguages());
  }

}
