package typeahead;

import java.util.Collection;

/**
 * This interface represents the behavior of data provider in the form
 * of the collection. Each element of the collection is (key;value) pair.
 *
 * @author Alexander Shvets
 * @version 1.0
 */
public interface SuggestionsProvider {
  /**
   * Gets the complete collection of suggestions.
   *
   * @param name the name of the collection
   * @return the complete collection of elements
   */
  Collection getSuggestions(String name);

  /**
   * Gets the partial collection of suggestions.
   *
   * @param name         the name of the collection
   * @param partialValue the partial value; elemenmts in the collection
   *                     should start from this string
   * @return the partial collection of elements
   */
  Collection getPartialSuggestions(String name, String partialValue);

  /**
   * Returns the index of the suggestion, specified by the key; otherwise
   * it's -1.
   *
   * @param name the name of the collection
   * @param key  the key
   * @return the index of the element within the collection
   */
  int indexOf(String name, String key);

  /**
   * Searches the suggestion within the collection. First, it will
   * search by values and, if they match, by keys.
   *
   * @param name  the name of the collection
   * @param key   the key
   * @param value the value
   * @return true if the suggestion is withing the collection; false otherwise
   */
  boolean search(String name, String key, String value);

}
