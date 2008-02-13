package typeahead;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The default implementation of the SuggestionsProvider interface.
 * Works with Pair elements.
 *
 * @author Alexander Shvets
 * @version 1.0
 */
public class StatesCountriesSuggestionsProvider extends AbstractSuggestionsProvider {
  private Collection countries;
  private Collection states;

  public StatesCountriesSuggestionsProvider() {
    countries = getData("/typeahead/countries.txt");
    states = getData("/typeahead/states.txt");
  }

  protected String getKey(Object pair) {
    return ((Pair) pair).getKey();
  }

  protected String getContent(Object pair) {
    return ((Pair) pair).getContent();
  }

  private Collection getData(String resourceName) {
    Collection list = new ArrayList();

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
            String key = line.substring(0, index).trim();
            String content = line.substring(index+1).trim();

            list.add(new Pair(key, content));
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
    return list;
  }

  public Collection getSuggestions(String name) {
    Collection list = null;

    if(name.equalsIgnoreCase("states")) {
      list = states;
    }
    else if(name.equalsIgnoreCase("countries")) {
      list = countries;
    }

    return list;
  }

}
