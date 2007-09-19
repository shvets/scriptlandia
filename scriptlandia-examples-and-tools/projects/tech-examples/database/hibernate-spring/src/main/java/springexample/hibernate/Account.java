package springexample.hibernate;

import java.math.BigDecimal;
import java.util.Date;

public class Account {
	
	private Long id = new Long(-1);
	
	private String accountName;

	private String type;
	
	private Double balance;
	
	private Date createDate;
	
	private Date updateDate;
	
	private Customer customer;

	public String getAccountName() {
		return accountName;
	}
	

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	

	
	public Date getCreateDate() {
		return createDate;
	}
	

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	

	public Long getId() {
		return id;
	}
	

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getType() {
		return type;
	}
	

	public void setType(String type) {
		this.type = type;
	}
	

	public Date getUpdateDate() {
		return updateDate;
	}
	

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public Customer getCustomer() {
		return customer;
	}
	


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Double getBalance() {
		return balance;
	}
	


	public void setBalance(Double balance) {
		this.balance = balance;
	}
	


		
	

}
