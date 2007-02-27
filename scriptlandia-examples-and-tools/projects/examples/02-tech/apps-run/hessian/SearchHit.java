//package com.sourcelabs.repo.browser.ui.web.service;


public class SearchHit {

  String repositoryRoot;
  String group;
  String artifactId;
  String version;
  String fileName;
  String javadoc;
  String sources;
  int scoreInPercents;
  boolean hasMoreVersions;

  public SearchHit() {
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public boolean isHasMoreVersions() {
    return hasMoreVersions;
  }

  public void setHasMoreVersions(boolean hasMoreVersions) {
    this.hasMoreVersions = hasMoreVersions;
  }

  public int getScoreInPercents() {
    return scoreInPercents;
  }

  public void setScoreInPercents(int scoreInPercents) {
    this.scoreInPercents = scoreInPercents;
  }

  public String getRepositoryRoot() {
    return repositoryRoot;
  }

  public void setRepositoryRoot(String repositoryRoot) {
    this.repositoryRoot = repositoryRoot;
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

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getJavadoc() {
    return javadoc;
  }

  public void setJavadoc(String javadoc) {
    this.javadoc = javadoc;
  }

  public String getSources() {
    return sources;
  }

  public void setSources(String sources) {
    this.sources = sources;
  }

  public String toString() {
    return "SearchHit{repositoryRoot='" + repositoryRoot + '\'' + ", group='" + group + '\'' + ", artifactId='" + artifactId + '\'' + ", version='" + version + '\'' + ", fileName='" + fileName + '\'' + ", javadoc='" + javadoc + '\'' + ", sources='" + sources + '\'' + ", scoreInPercents=" + scoreInPercents + ", hasMoreVersions=" + hasMoreVersions + '}';
  }
}
