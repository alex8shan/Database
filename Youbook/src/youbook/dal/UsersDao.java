package youbook.dal;


import youbook.model.*;
import youbook.model.Users.StatusLevels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDao extends PersonsDao {
	protected ConnectionManager connectionManager;
	// Single pattern: instantiation is limited to one object.
	private static UsersDao instance = null;
	protected UsersDao() {
		connectionManager = new ConnectionManager();
	}
	public static UsersDao getInstance() {
		if(instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}
	
	/**
	 * Save the Users instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Users create(Users user) throws SQLException{
		// Insert into the superclass table first.
		create(new Person(user.getUserName(), user.getFirstName(), user.getLastName()));
		
		String insertUser = "INSERT INTO User(UserName,EmailAddress,PhoneNumber,PaypalID, StatusLevels) VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			
			insertStmt.setString(1, user.getUserName());
			insertStmt.setString(2, user.getEmailAddress());
			insertStmt.setString(3, user.getPhoneNumber());
			insertStmt.setString(4, user.getPaypalID());
			insertStmt.setString(5, user.getUserLevel().toString());
			insertStmt.executeUpdate();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}
	
	/**
	 * Update the LastName of the Users instance.
	 * This runs a UPDATE statement.
	 */
	public Users updateLastName(Users user, String newLastName) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateLastName(user, newLastName);
		return user;
	}
	
	public Users getUserByUserName(String userName) throws SQLException {
		// To build an User object, we need the Persons record, too.
		String selectUser =
			"SELECT User.UserName AS UserName, FirstName, LastName, EmailAddress, PhoneNumber, PaypalID, StatusLevels " +
			"FROM User INNER JOIN Person " +
			"  ON User.UserName = Person.UserName " +
			"WHERE User.UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			
			if (results.next()) {
				String resultUserName = results.getString("UserName");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String emailAddress = results.getString("EmailAddress");
				String phoneNumber = results.getString("PhoneNumber");
				String paypalID = results.getString("PaypalID");
				StatusLevels status = Users.StatusLevels.valueOf(results.getString("StatusLevels"));
				Users user = new Users(resultUserName, firstName, lastName, emailAddress, phoneNumber, paypalID, status);
				return user;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null){
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	public Users delete(Users user) throws SQLException{
		String deleteUser = "DELETE FROM User WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, user.getUserName());
			deleteStmt.executeUpdate();
			
			// Then also delete from the superclass.
			// Note: due to the fk constraint (ON DELETE CASCADE), we could simply call
			// super.delete() without even needing to delete from Users first.
			super.delete(user);
			return null;
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
	public List<Users> getAdminFromFirstName(String firstName)
			throws SQLException {
		List<Users> users = new ArrayList<Users>();
		String selectAdministrators =
			"SELECT User.UserName AS UserName, FirstName, LastName, EmailAddress, PhoneNumber, PaypalID, StatusLevels" +
			"FROM User INNER JOIN Persons " +
			"  ON User.UserName = Persons.UserName " +
			"WHERE Persons.FirstName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAdministrators);
			selectStmt.setString(1, firstName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String userName = results.getString("UserName");
				String resultFirstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String emailAddress = results.getString("EmailAddress");
				String phoneNumber = results.getString("PhoneNumber");
				String paypalID = results.getString("PaypalID");
				StatusLevels statusLevels = Users.StatusLevels.valueOf(results.getString("StatusLevels"));
				Users user = new Users(userName, resultFirstName, lastName, emailAddress, phoneNumber, paypalID, statusLevels);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return users;
	}
}
