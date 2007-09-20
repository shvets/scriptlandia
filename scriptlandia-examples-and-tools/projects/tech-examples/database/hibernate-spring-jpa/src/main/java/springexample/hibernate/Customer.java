package springexample.hibernate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Customer implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id = (long) -1;

  private String userId;

  private String password;

  private String email;

  private String firstName;

  private String lastName;

  private BigDecimal balance;

  @OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "customer")
  private Set<Account> accounts = new HashSet<Account>();

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Set<Account> getAccounts() {
    return accounts;
  }

  public void setAccounts(Set<Account> accounts) {
    this.accounts = accounts;
  }

  public void addAccount(Account account) {
    account.setCustomer(this);
    accounts.add(account);
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String toString() {
    return userId + ": " + accounts;
  }

}
