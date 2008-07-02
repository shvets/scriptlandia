package tutorial.swf;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


/**
 * @author <a href="mailto:wiradikusuma@jug.or.id">Thomas Wiradikusuma</a>
 */
@Embeddable
public class PersonName implements Serializable {
	@Column(length = 50)
	private String first;

	@Column(length = 50)
	private String last;

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String toString() {
		return first + " " + last;
	}
}
