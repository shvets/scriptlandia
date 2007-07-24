import java.sql.SQLException;
import java.text.DateFormat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Arrays;

import net.java.ao.DatabaseProvider;
import net.java.ao.EntityManager;
import net.java.ao.Transaction;
import net.java.ao.schema.Generator;

public class TestMain {
  public static void main(String... args) throws Exception {
    Properties dbProperties = new Properties();

    InputStream is = TestMain.class.getResourceAsStream("/db.properties");

    try {
      dbProperties.load(is);
    }
    catch (IOException e) {

    }
    finally {
      try {
        is.close();
      }
      catch (IOException e) {}
    }

    // retrieves an EntityManager relevant to the specified URI
    // if available (i.e. the classpath set appropriately), the connection will be pooled
//    EntityManager manager = new EntityManager("jdbc:derby:test;create=true", "", "");
    EntityManager manager = new EntityManager(dbProperties.getProperty("db.uri"), dbProperties.getProperty("db.username"), 
      dbProperties.getProperty("db.password"));

    try {
      Generator.migrate(manager.getProvider(), PersonToPerson.class, Person.class);
    }
    catch(Throwable t) {}

    Family family = manager.create(Family.class);
    family.setName("Spiewak");
    		    		
    Person me = manager.create(Person.class);
    me.setName("Daniel Spiewak");
    me.setAge(27);
    me.setComment("I love databasing");
    me.setFamily(family);

    Person you = manager.create(Person.class);
    you.setName("Joe Blow");
    you.setAge(23);
    you.setComment("Guess who?");
    you.setFamily(family);

    PersonToPerson relation = manager.create(PersonToPerson.class);
    relation.setPersonA(me);
    relation.setPersonB(you);

    System.out.println("family:" + Arrays.asList(family.getPeople()));		// ...returns new Person[] {you, me}
    System.out.println("you:" + Arrays.asList(you.getPeople()));		// ...returns new Person[] {me}


    // notice, this is the first use of SQL in the whole example
    Family[] families = manager.findWithSQL(Family.class, "familyID", "SELECT familyID FROM person");

    // returns any person with age >= 18
    Person[] overAge = manager.find(Person.class, "age >= ?", 18);

    /*
     * notice the varargs parameters, as well as the direct use of an
     * entity instance without worrying about the ID value
     */
    Person[] inFamilyOver21 = manager.find(Person.class, "age >= ? AND familyID = ?", 21, family);

    System.out.println("families: " + Arrays.asList(families));
    System.out.println("overAge: " + Arrays.asList(overAge));
    System.out.println("inFamilyOver21: " + Arrays.asList(inFamilyOver21));
  }

}
