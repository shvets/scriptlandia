package springexample.hibernate;

import org.springframework.orm.jpa.support.JpaDaoSupport;

public class AccountDAOImpl extends JpaDaoSupport implements AccountDAO {

  public void addAccount(Account account) {
    getJpaTemplate().persist(account);
  }

/*
public Patient getById(Integer pId) 
      throws DataAccessException {
      return getJpaTemplate().find(Patient.class, pId);
    }

    public List getByLastName(String pLastName) 
      throws DataAccessException {
        
        List patients = getJpaTemplate().find(
            "SELECT p " + 
            "FROM " + Patient.class.getSimpleName() + " p " + 
            "WHERE p.lastName LIKE ?1", pLastName);

         return (patients.isEmpty())? 
            Collections.EMPTY_LIST : patients;
    }

 public Patient save(Patient pPatient) 
      throws DataAccessException {
      getJpaTemplate().persist(pPatient);
        
   return pPatient;
   }

   public Patient update(Patient pPatient) 
      throws DataAccessException {

      return getJpaTemplate().merge(pPatient);
   }
*/

}
