
public class Address {
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress;}

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode;    }


    public void parse(String address) {
        String[] parts = address.split("\\.");
        setStreetAddress(parts[0] + ".");
        String[] remainingPart = parts[1].split(" ");
        setCity(remainingPart[1].replaceAll(",", ""));
        setState(remainingPart[2]);
        setZipCode(remainingPart[3]);
    }
}
