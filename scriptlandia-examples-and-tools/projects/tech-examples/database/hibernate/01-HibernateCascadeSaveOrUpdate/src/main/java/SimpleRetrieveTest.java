
import java.util.*;

import org.hibernate.*;
import org.hibernate.criterion.*;

public class SimpleRetrieveTest {
	
	
	public static void main(String[] args) {
		HibernateUtil.setup("create table EVENTS ( uid int, name VARCHAR, start_Date date, duration int, location_id int);");
		HibernateUtil.setup("create table locations ( uid int, name VARCHAR, street_address VARCHAR, city VARCHAR, state VARCHAR, zip_Code VARCHAR);");
		
		// hibernate code start
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();


        Location location = new Location();
        location.setName("USA");
        location.getAddress().setStreetAddress("St.");
        location.getAddress().setCity("Regina");
        location.getAddress().setState("SK");
        location.getAddress().setZipCode("22204");

        Event event = new Event();
        event.setName("Annual Meeting");
        event.setDuration(60);
        event.setStartDate(createDate(2004, 11, 1));
        event.setLocation(location);

//        session.save(location);
        session.save(event);


        tx.commit();
		HibernateUtil.closeSession();

		HibernateUtil.sessionFactory.close();

        HibernateUtil.checkData("select * from events");
        HibernateUtil.checkData("select * from locations");
		// hibernate code end
	}
    private static Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
	
}