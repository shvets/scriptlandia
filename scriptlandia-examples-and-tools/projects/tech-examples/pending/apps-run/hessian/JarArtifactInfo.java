//package com.sourcelabs.repo.browser.ui.web.service;

import java.util.*;

public class JarArtifactInfo {

  String group;
  String artifactId;
  String version;
  String[] licenses;
  String[] byteCodeVersions;
  String[] flags;
  String[] messages;
  String availableUpdate;

  public JarArtifactInfo() {
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public void setArtifactId(String artifactId) {
    this.artifactId = artifactId;
  }

  public String[] getLicenses() {
    return licenses;
  }

  public void setLicenses(String[] licenses) {
    this.licenses = licenses;
  }

  public String[] getByteCodeVersions() {
    return byteCodeVersions;
  }

  public void setByteCodeVersions(String[] byteCodeVersions) {
    this.byteCodeVersions = byteCodeVersions;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String[] getFlags() {
    return flags;
  }

  public void setFlags(String[] flags) {
    this.flags = flags;
  }

  public String[] getMessages() {
    return messages;
  }

  public void setMessages(String[] messages) {
    this.messages = messages;
  }

  public String getAvailableUpdate() {
    return availableUpdate;
  }

  public void setAvailableUpdate(String availableUpdate) {
    this.availableUpdate = availableUpdate;
  }

  public String toString() {
    return "JarArtifactInfo{ artifactId='" + artifactId + '\'' + ", group='" + group + '\'' + ", version='" + version + '\'' + ", licenses=" + (licenses != null ? String.valueOf(Arrays.asList(licenses)) : "''") + ", byteCodeVersions=" + (byteCodeVersions != null ? String.valueOf(Arrays.asList(byteCodeVersions)) : "''") + ", flags=" + (flags != null ? String.valueOf(Arrays.asList(flags)) : "''") + ", messages=" + (messages != null ? String.valueOf(Arrays.asList(messages)) : "''") + ", availableUpdate='" + availableUpdate + '\'' + '}';
  }
}
