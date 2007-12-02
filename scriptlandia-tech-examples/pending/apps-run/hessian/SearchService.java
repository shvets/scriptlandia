//package com.sourcelabs.repo.browser.ui.web.service;


public interface SearchService {

  public abstract SearchHit[] findBestVersions(String s) throws Exception;

  public abstract JarArtifactInfo getArifactInfoByMD5(String s) throws Exception;

  public abstract String getHtmlReportByArtifactMD5(String[] as);
}
