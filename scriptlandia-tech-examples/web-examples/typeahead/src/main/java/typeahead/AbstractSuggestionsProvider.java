package typeahead;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * The default implementation of the SuggestionsProvider interface.
 * Works with Pair elements.
 *
 * @author Alexander Shvets
 * @version 1.0
 */
public abstract class AbstractSuggestionsProvider implements SuggestionsProvider {

  /**
   * @see SuggestionsProvider
   */
  public Collection getPartialSuggestions(String name, String partialValue) {
    Collection suggestions = getSuggestions(name);

    Collection newSuggestions = new ArrayList();

    if (suggestions != null) {
      if (partialValue == null || partialValue.trim().length() == 0) {
        newSuggestions.addAll(suggestions);
      }
      else {
        String textboxValue = partialValue.toLowerCase();

        if (textboxValue != null && partialValue.trim().length() > 0) {
          // Search for matching suggestions.

          Iterator iterator = suggestions.iterator();

          while (iterator.hasNext()) {
            Object pair = iterator.next();

            String currentContent = getContent(pair);

            if (currentContent.toLowerCase().startsWith(textboxValue)) {
              newSuggestions.add(pair);
            }
          }
        }
      }
    }

    return newSuggestions;
  }

  /**
   * @see SuggestionsProvider
   */
  public boolean search(String name, String key, String value) {
    Collection suggestions = getSuggestions(name);

    boolean found = false;

    Iterator iterator = suggestions.iterator();

    while (iterator.hasNext() && !found) {
      Object pair = iterator.next();

      String currentKey = getKey(pair);
      String currentContent = getContent(pair);

      if (currentContent.equalsIgnoreCase(value)) {
        if (key.equalsIgnoreCase(currentKey)) {
          found = true;
        }
      }
    }

    return found;
  }

  /**
   * @see SuggestionsProvider
   */
  public int indexOf(String name, String key) {
    Collection suggestions = getSuggestions(name);

    int index = -1;

    Iterator iterator = suggestions.iterator();

    for (int i = 0; iterator.hasNext() && index == -1; i++) {
      Object pair = iterator.next();

      String currentKey = getKey(pair);

      if (currentKey.equalsIgnoreCase(key)) {
        index = i;
      }
    }

    return index;
  }

  /**
   * Gets the key.
   *
   * @param pair the pair
   * @return the key
   */
  protected abstract String getKey(Object pair);

  /**
   * Gets the content.
   *
   * @param pair the pair
   * @return the content
   */
  protected abstract String getContent(Object pair);

}
