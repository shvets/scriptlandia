package tutorial.swf;

import java.util.List;

/**
 * @author <a href="mailto:wiradikusuma@jug.or.id">Thomas Wiradikusuma</a>
 */
public interface PersonService {
	List<Person> getList();

	Person get(Long id);	

	void save(Person person);

	void remove(Person person);

	Person prepare(Person person);
}
