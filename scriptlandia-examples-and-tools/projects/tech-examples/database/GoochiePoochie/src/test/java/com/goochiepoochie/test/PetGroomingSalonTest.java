package com.goochiepoochie.test;

import com.goopoo.model.PetOwner;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class PetGroomingSalonTest extends TestCase {
  private EntityManager em;
  private EntityManagerFactory emf;

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    emf = Persistence.createEntityManagerFactory("goopoo");
    em = emf.createEntityManager();
  }

  @Override
  protected void tearDown() throws Exception {
    em.close();
    emf.close();

    super.tearDown();
  }

  public void testPersist1() {
    PetOwner petOwner = new PetOwner();
    petOwner.setFirstName("Alexander");
    petOwner.setLastName("Shvets");

    // no primary key
    assertNull(petOwner.getId());

    EntityTransaction tx = em.getTransaction();

    tx.begin();
    em.persist(petOwner);
    tx.commit();

    // this may not work when using TopLink as the persistence provider,
    // but this archtetype is configured for Hibernate
    // should now have a primary key
    assertNotNull(petOwner.getId());
  }

  public void testPersist2() {
    EntityTransaction tx = em.getTransaction();

    tx.begin();

    List petOwners =
        em.createQuery("select po from PetOwner po order by po.lastName asc").getResultList();

    System.out.println(petOwners.size() + " pet owners(s) found:");

    for (Object m : petOwners) {
      PetOwner loadedMsg = (PetOwner) m;
      System.out.println(loadedMsg.getLastName());
    }

    tx.commit();
  }

}

