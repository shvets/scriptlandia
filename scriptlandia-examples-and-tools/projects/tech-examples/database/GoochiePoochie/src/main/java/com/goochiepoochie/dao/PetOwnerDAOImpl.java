/*
 *  This file was created by Rick Hightower of ArcMinds Inc. 
 *
 */
package springexample.hibernate;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.goochiepoochie.model.PetOwner;

public class PetOwnerDAOImpl extends HibernateDaoSupport implements PetOwnerDAO {

  public abstract void addPetOwner(PetOwner petOwner) {
    getHibernateTemplate().save(petOwner);
  }

}
