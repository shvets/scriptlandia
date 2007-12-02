package com.goochiepoochie.test;

import com.goochiepoochie.dao.PetOwnerDAO;
import com.goochiepoochie.model.Cat;
import com.goochiepoochie.model.Dog;
import com.goochiepoochie.model.PetOwner;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MyUnitTest extends TestCase {
  private static ApplicationContext ac = new FileSystemXmlApplicationContext(
                    new String[] {
                      "src/main/resources/beans.xml",
                      "src/test/resources/beans-junit.xml",
                     });

  @Override
  protected void setUp() throws Exception {
    super.setUp();

  }

  @Override
  protected void tearDown() throws Exception {

    super.tearDown();
  }

  public void testCreate1() throws Exception {
    PetOwner petOwner = new PetOwner();
    petOwner.setFirstName("Alexander");
    petOwner.setLastName("Shvets");

    Cat cat1 = new Cat();
    cat1.setName("Cheeta");

    Dog dog1 = new Dog();
    dog1.setName("Bobik");

    petOwner.addPet(cat1);
    petOwner.addPet(dog1);

    PetOwnerDAO petOwnerDAO = (PetOwnerDAO) ac.getBean("petOwnerDAO");

    petOwnerDAO.addPetOwner(petOwner);
  }

  public void testList1() throws Exception {
    PetOwner petOwner = new PetOwner();
    petOwner.setLastName("Shvets");

    PetOwnerDAO petOwnerDAO = (PetOwnerDAO ) ac.getBean("petOwnerDAO");
    PetOwner petOwnerRecord = petOwnerDAO.getPetOwnerInfo(petOwner);

    System.out.println("Pet owner found: " + petOwnerRecord);
  }

  /**
     * Runs the test case.
   * @param args args
   */
  public static void main(String[] args) {
    TestSuite testSuite = new TestSuite();

    for (String arg : args) {
      MyUnitTest test = (MyUnitTest) ac.getBean("myUnitTest");

      test.setName(arg);

      testSuite.addTest(test);
    }

    junit.textui.TestRunner.run(testSuite);
  }

}
