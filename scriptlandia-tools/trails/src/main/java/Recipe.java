import java.util.Date;
 
import javax.persistence.Entity;
import javax.persistence.GeneratorType;
import javax.persistence.Id;
 
@Entity
public class Recipe
{
    private Integer id;
    
    private String title;
    
    private String description;
    
    private Date date;
 
    @Id(generate=GeneratorType.AUTO)
    public Integer getId()
    {
        return id;
    }
 
    public void setId(Integer id)
    {
        this.id = id;
    }
 
    public String getTitle()
    {
        return title;
    }
 
    public void setTitle(String title)
    {
        this.title = title;
    }
 
    public String getDescription()
    {
        return description;
    }
 
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public Date getDate()
    {
        return date;
    }
 
    public void setDate(Date date)
    {
        this.date = date;
    }
    
}
