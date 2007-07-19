import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import wicket.markup.html.WebPage;
import wicket.markup.html.list.ListView;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.basic.Label;

public class AlohaPage extends WebPage {

  public AlohaPage() {
    // A Title for our page
    add(new Label("pageTitle", "Aloha - The Time is: " + new Date()));

    // A Wicket-style Loop/List of Items
    add(new ListView("people", getPeople()) {
      // This method is called for each 'entry' in the list.
      protected void populateItem(ListItem item) {
        Person person = (Person)item.getModelObject();
        item.add(new Label("firstName", person.firstName));
        item.add(new Label("lastName", person.lastName));
        item.add(new Label("email", person.email));
      }
    });
  }

  // Some arbitrary factory method.
  private List getPeople() {
    List people = new ArrayList();
    people.add(new Person("R.J.", "Lorimer", "rj@javalobby.org"));
    people.add(new Person("Rick", "Ross", "rick@javalobby.org"));
    people.add(new Person("Matt", "Schmidt", "matt@javalobby.org"));
    return people;
  }

  // some arbitrary class for data.
  class Person {
    String firstName;
    String lastName;
    String email;

    Person(String fName, String lName, String emailAddr) {
      this.firstName = fName;
      this.lastName = lName;
      this.email = emailAddr;
    }
  }
}
