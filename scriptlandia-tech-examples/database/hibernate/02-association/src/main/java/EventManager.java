import org.hibernate.Session;

import java.util.*;

public class EventManager {

    public static void main(String[] args) {
        EventManager mgr = new EventManager();

        //else if (args[0].equals("addpersontoevent")) {
          Long eventId = mgr.createAndStoreEvent("My Event", new Date());
          Long personId = mgr.createAndStorePerson("Foo", "Bar");
          mgr.addPersonToEvent(personId, eventId);
          System.out.println("Added person " + personId + " to event " + eventId);

          mgr.addEmailToPerson(personId, "emailAddress");
          System.out.println("Added email " + " to person " + personId);

        //}

        HibernateUtil.getSessionFactory().close();
    }

    public Long createAndStoreEvent(String title, Date theDate) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.beginTransaction();

        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);

        session.save(theEvent);

        session.getTransaction().commit();

        return theEvent.getId();
    }

    public Long createAndStorePerson(String firstName, String lastName) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.beginTransaction();

        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);

        session.save(person);

        session.getTransaction().commit();

        return person.getId();    
    }

  public void addPersonToEvent(Long personId, Long eventId) {

    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    Person aPerson = (Person) session.load(Person.class, personId);
    Event anEvent = (Event) session.load(Event.class, eventId);

    aPerson.getEvents().add(anEvent);

    session.getTransaction().commit();
  }

  public void addEmailToPerson(Long personId, String emailAddress) {

    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    Person aPerson = (Person) session.load(Person.class, personId);

    // The getEmailAddresses() might trigger a lazy load of the collection
    aPerson.getEmailAddresses().add(emailAddress);

    session.getTransaction().commit();
  }

}
