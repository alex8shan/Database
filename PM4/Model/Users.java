package youbook.model;

public class Users extends Persons {
	public enum StatusLevels{ NOVICE, INTERMEDIATE, AVID_READER}
	protected String emailAddress;
	protected String phoneNumber;
	protected String paypalID;
	
	//For reading records which have all fields
	public Users(String userName, String firstName, String lastName, String emailAddress,
			String phoneNumber, String paypalID) {
		super(userName, firstName, lastName);
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.paypalID = paypalID;
	}

	public Users(Persons person, String firstName, String lastName, String emailAddress,
			String phoneNumber, String paypalID) {
		super(person.getUserName(), person.getFirstName(), person.getLastName());
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.paypalID = paypalID;
	}
	
	public Users(String userName) {
		super(userName);
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
