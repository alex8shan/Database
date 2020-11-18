package youbook.model;


public class Users extends Person {
	public enum StatusLevels{ NOVICE, INTERMEDIATE, AVID_READER}
	protected String emailAddress;
	protected String phoneNumber;
	protected String paypalID;
	protected StatusLevels userLevel;
	
	//For reading records which have all fields
	public Users(String userName, String firstName, String lastName, String emailAddress,
			String phoneNumber, String paypalID, StatusLevels userLevel) {
		super(userName, firstName, lastName);
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.paypalID = paypalID;
		this.userLevel = userLevel;
	}

	public Users(Person person, String firstName, String lastName, String emailAddress,
			String phoneNumber, String paypalID, StatusLevels userLevel) {
		super(person.getUserName(), person.getFirstName(), person.getLastName());
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.paypalID = paypalID;
		this.userLevel = userLevel;
	}
	
	public StatusLevels getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(StatusLevels userLevel) {
		this.userLevel = userLevel;
	}

	public Users(String userName) {
		super(userName);
	}

	public Users(String userName, String firstName, String lastName) {
		 super(userName, firstName, lastName);
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPaypalID() {
		return paypalID;
	}

	public void setPaypalID(String paypalID) {
		this.paypalID = paypalID;
	}
}
