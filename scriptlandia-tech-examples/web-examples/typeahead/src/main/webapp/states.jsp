<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.URLConnection"%>
<%@ page import="java.util.LinkedHashMap"%>

<%!
  private static Map states = new HashMap();
  private static Map countries = new HashMap();

  private static Map getData(String resourceName) {
    Map map = new LinkedHashMap();

    BufferedReader reader = null;

    try {
      URL url = new URL("http://" + "localhost:9090" + resourceName);

      URLConnection connection = url.openConnection();
      connection.setDoInput(true);

      InputStream is = connection.getInputStream();

      reader = new BufferedReader(new InputStreamReader(is));

      for(boolean ok=false; !ok;) {
        String line = reader.readLine();

        if(line == null) {
          ok = true;
        }
        else if (line.trim().length() > 0) {
          int index = line.indexOf(' ');

          if(index != -1) {
            map.put(line.substring(0, index).trim(), line.substring(index+1).trim());
          }
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      if(reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return map;
  }

  static {
    states = getData("/typeahead/states.txt");
    countries = getData("/typeahead/countries.txt");
  }

  public Map getStates() {
    return states;
  }

  public Map getCountries() {
    return countries;
  }

%>