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

    public Set getEvents() {
        return events;
    }

    public void setEvents(Set events) {
        this.events = events;
    }

  public Set getEmailAddresses() {
    return emailAddresses;
  }

  public void setEmailAddresses(Set emailAddresses) {
    this.emailAddresses = emailAddresses;
  }
}
