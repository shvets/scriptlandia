package tutorial.swf;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author <a href="mailto:wiradikusuma@jug.or.id">Thomas Wiradikusuma</a>
 */
@Repository
@Service("service")
public class PersonServiceImpl implements PersonService {
	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Transactional(readOnly = true)
	public List<Person> getList() {
		return em.createQuery("select o from Person o").getResultList();
	}

	@Transactional(readOnly = true)
	public Person get(Long id) {
		return em.find(Person.class, id);
	}

	public void save(Person person) {
		em.merge(person);
	}

	@Transactional
	public void remove(Person person) {
		em.remove(person);
	}

	public Person prepare(Person person) {
		if (person != null) {
			return person;
		}
		return new Person();
	}
}
