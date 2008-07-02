package tutorial.swf;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;


/**
 * @author <a href="mailto:wiradikusuma@jug.or.id">Thomas Wiradikusuma</a>
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"first"})})
public class Person implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private Integer version;

	private PersonName name = new PersonName();

	private String email;

	private int workExperience;

	private Boolean female;

	private MaritalStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public PersonName getName() {
		return name;
	}

	public void setName(PersonName name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(int workExperience) {
		this.workExperience = workExperience;
	}

	public Boolean getFemale() {
		return female;
	}

	public void setFemale(Boolean female) {
		this.female = female;
	}

	public MaritalStatus getStatus() {
		return status;
	}

	public void setStatus(MaritalStatus status) {
		this.status = status;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Person person = (Person) o;

		if (name != null ? !name.equals(person.name) : person.name != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (name != null ? name.hashCode() : 0);
	}
}
