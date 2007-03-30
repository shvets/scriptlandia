import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import javax.persistence.Query

def factory = Persistence.createEntityManagerFactory("AddressBookStore")
def manager = factory.createEntityManager()

def personQuery = manager.createQuery("SELECT p FROM Person p ORDER BY p.firstName DESC")
personQuery.getResultList().each { println "${it.firstName} ${it.lastName}" }

personQuery = manager.createNamedQuery("Person.findByFirstName").setParameter("firstName", "Alexis")
personQuery.getResultList().each { println "${it.firstName} ${it.lastName}" }