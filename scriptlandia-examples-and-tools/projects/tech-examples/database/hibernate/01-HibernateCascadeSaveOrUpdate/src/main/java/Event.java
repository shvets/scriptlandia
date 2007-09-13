
import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private Long id;
    private int duration;
    private String name;
    private Date startDate;
    private Location location;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name;   }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() { return duration; }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Location getLocation() { return location; }
    public void setLocation(Location location) {
        this.location = location;
    }

    public Event() { }
    public Event(String name) { this.name = name; }

}
