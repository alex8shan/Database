package youbook.model;

import java.util.Date;

public class Admin extends Persons {
	protected Date lastLogin;

	public Admin(String userName, String firstName, String lastName, Date lastLogin) {
		super(userName, firstName, lastName);
		this.lastLogin = lastLogin;
	}

	public Admin(Persons person, Date lastLogin) {
		super(person.getUserName(), person.getFirstName(), person.getLastName());
		this.lastLogin = lastLogin;
	}
	
	public Admin(String userName) {
		super(userName);
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
}
