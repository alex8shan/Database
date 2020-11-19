package youbook.model;

/**
 * Person is a simple, plain old java objects (POJO).
 * 
 * Person/PersonDao is the superclass for Admin/AdminDao and
 * User/UserDao. Our implementation of Person is a concrete class. This allows 
 * us to create records in the Person MySQL table without having the associated records
 * in the Admin or User MySQL tables. Alternatively, Person could be an
 * interface or an abstract class, which would force a Person record to be created only
 * if an Admin or User record is created, too.
 */
public class Person {
	
	protected String userName;
	protected String firstName;
	protected String lastName;
	
	// This constructor can be used for reading records from MySQL, where we have all fields
	// including the userName
	public Person(String userName, String firstName, String lastName) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	// This constructor can be used for reading records from MySQL, where we only have 
	// the userName, such as a foreign key reference to userName.
	// Given userName, we can fetch the full Person record.
	public Person(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}