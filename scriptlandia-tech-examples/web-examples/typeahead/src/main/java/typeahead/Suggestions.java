package typeahead;

import java.util.Collection;

/**
 * This class represents the adapter for the given implementation of
 * the SuggestionsProvider interface.
 *
 * @author Alexander Shvets
 * @version 1.0
 */
public class Suggestions implements SuggestionsProvider {
  /**
   * The implementation of SuggestionsProvider interface.
   */
  private static SuggestionsProvider implementation;

  /**
   * Sets current implementation.
   *
   * @param impl the implementation
   */
  public static void setImplementation(SuggestionsProvider impl) {
    implementation = impl;
  }

  /**
   * @see SuggestionsProvider
   */
  public Collection getSuggestions(String name) {
    return implementation.getSuggestions(name);
  }

  /**
   * @see SuggestionsProvider
   */
  public Collection getPartialSuggestions(String name, String partialValue) {
    return implementation.getPartialSuggestions(name, partialValue);
  }

  /**
   * @see SuggestionsProvider
   */
  public boolean search(String name, String key, String value) {
    return implementation.search(name, key, value);
  }

  /**
   * @see SuggestionsProvider
   */
  public int indexOf(String name, String key) {
    return implementation.indexOf(name, key);
  }

}
