import java.io.Serializable;

public class Location implements Serializable{
    private Long id;
    private String name;
    private Address address = new Address();

    public Location(String name) { this.name = name;}
    public Location() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address;}
}
