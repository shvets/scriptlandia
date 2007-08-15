// Family.java

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface Family extends Entity {
	public String getName();
	public void setName(String name);
	
	@OneToMany
	public Person[] getPeople();
}
