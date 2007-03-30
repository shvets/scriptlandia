//

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

def factory = Persistence.createEntityManagerFactory("AddressBookStore")
def manager = factory.createEntityManager()

manager.getTransaction().begin()

manager.persist new Person(firstName: "Alexis", lastName: "Moussine")
manager.persist new Person(firstName: "Ludovic", lastName: "Champenois")
manager.persist new Person(firstName: "Francois", lastName: "Orsini")
manager.persist new Person(firstName: "Eric", lastName: "Mahe")
manager.persist new Person(firstName: "Roman", lastName: "Strobl")
manager.persist new Person(firstName: "Tor", lastName: "Norbye")
manager.persist new Person(firstName: "James", lastName: "Gosling")
manager.persist new Person(firstName: "Chet", lastName: "Haase")
manager.persist new Person(firstName: "Richard", lastName: "Bair")

manager.getTransaction().commit()
