package youbook.model;


public class User extends Person {
	
	public enum StatusLevels{ NOVICE, INTERMEDIATE, AVID_READER}
	protected String emailAddress;
	protected String phoneNumber;
	protected String paypalID;
	protected StatusLevels userLevel;
	
	// This constructor can be used for reading records from MySQL, where we have all fields
	// including the usrName.
	public User(String userName, String firstName, String lastName, String emailAddress,
			String phoneNumber, String paypalID, StatusLevels userLevel) {
		super(userName, firstName, lastName);
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.paypalID = paypalID;
		this.userLevel = userLevel;
	}
	
	// This constructor can be used for reading records from MySQL, where we only have 
	// the userName, such as a foreign key reference to userName.
	// Given userName, we can fetch the full User record.
	public User(String userName) {
		super(userName);
	}
	
	public StatusLevels getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(StatusLevels userLevel) {
		this.userLevel = userLevel;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPaypalID() {
		return this.paypalID;
	}

	public void setPaypalID(String paypalID) {
		this.paypalID = paypalID;
	}
}
