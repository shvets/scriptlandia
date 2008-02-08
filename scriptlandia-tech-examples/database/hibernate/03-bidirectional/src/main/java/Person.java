import java.util.*;


public class Person {

    private Long id;
    private int age;
    private String firstname;
    private String lastname;

    private Set events = new HashSet();

  private Set emailAddresses = new HashSet();

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }
    
    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public Person() {}

    /*public*/protected Set getEvents() {
        return events;
    }

    /*public*/protected void setEvents(Set events) {
        this.events = events;
    }


public void addToEvent(Event event) {
    this.getEvents().add(event);
    event.getParticipants().add(this);
}

public void removeFromEvent(Event event) {
    this.getEvents().remove(event);
    event.getParticipants().remove(this);
}

  
  public Set getEmailAddresses() {
    return emailAddresses;
  }

  public void setEmailAddresses(Set emailAddresses) {
    this.emailAddresses = emailAddresses;
  }
}
