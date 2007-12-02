package springexample.hibernate;

public interface CustomerDAO {
  public abstract void addCustomer(Customer customer);

  public abstract Customer getCustomerAccountInfo(Customer customer);

}