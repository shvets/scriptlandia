package com.goochiepoochie.test;

import com.goopoo.model.PetOwner;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class PetGroomingSalonTest2 extends TestCase {
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

  public void testPetOwnerCreate() {
  }

}

