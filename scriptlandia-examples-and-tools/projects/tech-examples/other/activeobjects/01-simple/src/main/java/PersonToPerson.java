// PersonToPerson.java

import net.java.ao.Entity;

public interface PersonToPerson extends Entity {
	public Person getPersonA();
	public void setPersonA(Person person);
	
	public Person getPersonB();
	public void setPersonB(Person person);
}
