import java.sql.SQLException;
import java.text.DateFormat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import net.java.ao.DatabaseProvider;
import net.java.ao.EntityManager;
import net.java.ao.Transaction;
import net.java.ao.schema.Generator;

/*
 * Created on May 2, 2007
 */

/**
 * @author Daniel Spiewak
 */
public class Driver {
  public static void main(String... args) throws Exception {

    Properties dbProperties = new Properties();

    InputStream is = Driver.class.getResourceAsStream("/db.properties");

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

/*Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

String url = "jdbc:derby:test";
String userName = "";
String password = "";
*/

/*		if (args.length < 3) {
			System.err.println("You must specify a URI, username and password (in that order)");
			System.exit(-1);
		}
*/		

		long millis = System.currentTimeMillis();
//		EntityManager manager = new EntityManager(DatabaseProvider.getInstance(url, userName, password));
    EntityManager manager = new EntityManager(dbProperties.getProperty("db.uri"), dbProperties.getProperty("db.username"), 
    dbProperties.getProperty("db.password"));

		
//		Logger.getLogger("net.java.ao").setLevel(Level.INFO);
		
		runMigrationTest(manager);
		runTestTest(manager);
		runRoomsTest(manager);
		runManyTest(manager);
//		runTransactionTest(manager);
		
		System.out.println("Total time: " + (System.currentTimeMillis() - millis));
		
		manager.getProvider().dispose();
	}
	
	private static void runMigrationTest(EntityManager manager) throws SQLException {
		boolean needsSchema = false;
		try {
			manager.find(Test.class);
		} catch (SQLException e) {
			needsSchema = true;
		}
		
		if (needsSchema) {

			Generator.migrate(manager.getProvider(), RoomToTest.class, Account.class);
			Room kitchen = manager.create(Room.class);
			kitchen.setName("Kitchen");
			
			Room pantry = manager.create(Room.class);
			pantry.setName("Pantry");
			pantry.setParent(kitchen);
		}
	}
	
	private static void runTestTest(EntityManager manager) throws SQLException {
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
		for (Test test : manager.find(Test.class)) {
			System.out.print("id = " + test.getID() + ", ");
			System.out.print("name = " + test.getName() + ", ");
			System.out.print("age = " + test.getAge() + ", ");
			System.out.print("date = " + format.format(test.getDate().getTime()) + ", ");
			System.out.print("room = " + test.getRoom().getName() + ", ");
			
			String parentName = "null";
			Room parent = test.getRoom().getParent();
			if (parent != null) {
				parentName = parent.getName();
			}
			
			System.out.println("roomParent = " + parentName);
		}
		System.out.println();
	}
	
	private static void runRoomsTest(EntityManager manager) throws SQLException {
		for (Room room : manager.find(Room.class)) {
			System.out.print(room.getName() + " = {");
			
			for (Test test : room.getTests()) {
				System.out.print(test.getName() + ",");
			}
			System.out.println("}");
		}
		System.out.println();
	}
	
	private static void runManyTest(EntityManager manager) throws SQLException {
		for (Test test : manager.find(Test.class)) {
			System.out.print(test.getName() + " = {");
			
			for (Room room : test.getRooms()) {
				System.out.print(room.getName() + ",");
			}
			System.out.println("}");
		}
		System.out.println();
	}
	
	private static void runTransactionTest(final EntityManager manager) throws SQLException {
		final Account[] accounts = manager.find(Account.class);
		
		accounts[0].setBalance(12345);
		accounts[1].setBalance(54321);
		accounts[2].setBalance(11111);
		
		Thread threadA = new Transaction(manager) {
			public void run() {
				final int DRAW = 22222;
				
				int balance = accounts[1].getBalance();		// should grab read lock
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				
				accounts[1].setBalance(balance - DRAW);		// should grab write lock (and fail)
				
				balance = accounts[0].getBalance();
				accounts[0].setBalance(balance + DRAW);
			}
		}.executeConcurrently();
		
		Thread threadB = new Transaction(manager) {
			public void run() {
				final int DRAW = 11111;
				
				int balance = accounts[2].getBalance();
				accounts[2].setBalance(balance - DRAW);
				
				balance = accounts[1].getBalance();		// should grab read lock
				accounts[1].setBalance(balance + DRAW);		// should grab write lock
			}
		}.executeConcurrently();
		
		try {
			threadA.join();
			threadB.join();
		} catch (InterruptedException e) {
		}
	}
}
