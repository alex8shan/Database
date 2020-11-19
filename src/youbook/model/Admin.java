package youbook.model;

import java.util.Date;

public class Admin extends Person {
	
	protected Date lastLogin;
	
	// This constructor can be used for reading records from MySQL, where we have all fields
	// including the userName.
	public Admin(String userName, String firstName, String lastName, Date lastLogin) {
		super(userName, firstName, lastName);
		this.lastLogin = lastLogin;
	}
	
	// This constructor can be used for reading records from MySQL, where we only have 
	// the userName, such as a foreign key reference to userName.
	// Given the userName, we can fetch the full Admin record.
	public Admin(String userName) {
		super(userName);
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
}
