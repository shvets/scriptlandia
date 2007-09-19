package aaa;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;
/**
 * @author Chris Maki
 * @Revision $Revision: 1.2 $
 */
public class ForumEntityTest extends TestCase {

    private EntityManager em;
    private EntityManagerFactory emf;
    private Forum forum;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        emf = Persistence.createEntityManagerFactory("jpa-101");
        em = emf.createEntityManager();
        forum = new Forum();
        forum.setName("Example Forum");
        forum.setDescription("Example forum instance to get you started.");
    }

    @Override
    protected void tearDown() throws Exception {
        em.close();
        emf.close();
        super.tearDown();
    }
    
    public void testPersist() {
        // no primary key
        assertNull(forum.getId());
        em.getTransaction().begin();
        em.persist(forum);
        em.getTransaction().commit();
        
        // this may not work when using TopLink as the persistence provider,
        // but this archtetype is configured for Hibernate 
        // should now have a primary key
        assertNotNull(forum.getId());
    }
    
}
