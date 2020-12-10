package youbook.dao;

import youbook.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends PersonDao {
	
	protected ConnectionManager connectionManager;
	// Single pattern: instantiation is limited to one object.
	private static UserDao instance = null;
	protected UserDao() {
		connectionManager = new ConnectionManager();
	}
	public static UserDao getInstance() {
		if(instance == null) {
			instance = new UserDao();
		}
		return instance;
	}
	
	/**
	 * Save the User instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public User create(User user) throws SQLException{
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
	 * Update the LastName of the User instance.
	 * This runs a UPDATE statement.
	 */
	public User updateLastName(User user, String newLastName) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateLastName(user, newLastName);
		return user;
	}
	
	/**
	 * Update the FirstName of the User instance.
	 * This runs a UPDATE statement.
	 */
	public User updateFirstName(User user, String newFirstName) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateFirstName(user, newFirstName);
		return user;
	}
	
	/**
	 * Update the Email of the User instance.
	 * This runs a UPDATE statement.
	 */
	public User updateEmail(User user, String newEmail) throws SQLException {
		String updatePerson = "UPDATE User SET EmailAddress=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePerson);
			updateStmt.setString(1, newEmail);
			updateStmt.setString(2, user.getUserName());
			updateStmt.executeUpdate();
			
			// Update the person param before returning to the caller.
			user.setEmailAddress(newEmail);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	/**
	 * Update the PhoneNumber of the User instance.
	 * This runs a UPDATE statement.
	 */
	public User updatePhoneNumber(User user, String newPhoneNumber) throws SQLException {
		String updatePerson = "UPDATE User SET PhoneNumber=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePerson);
			updateStmt.setString(1, newPhoneNumber);
			updateStmt.setString(2, user.getUserName());
			updateStmt.executeUpdate();
			
			// Update the person param before returning to the caller.
			user.setPhoneNumber(newPhoneNumber);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	/**
	 * Update the Paypal of the User instance.
	 * This runs a UPDATE statement.
	 */
	public User updatePaypal(User user, String newPaypal) throws SQLException {
		String updatePerson = "UPDATE User SET PaypalId=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePerson);
			updateStmt.setString(1, newPaypal);
			updateStmt.setString(2, user.getUserName());
			updateStmt.executeUpdate();
			
			// Update the person param before returning to the caller.
			user.setPaypalID(newPaypal);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	/*
	 * Get the User record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single User instance.
	 */
	public User getUserByUserName(String userName) throws SQLException {
		// To build an User object, we need the Person record, too.
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
				User.StatusLevels status = User.StatusLevels.valueOf(results.getString("StatusLevels"));
				User user = new User(resultUserName, firstName, lastName, emailAddress, phoneNumber, paypalID, status);
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
	
	
	/*
	 * Get the User records by fetching it from your MySQL instance by the corresponding firstName.
	 * This runs a SELECT statement and returns 0 or more User instance(s).
	 */
	public List<User> getUserFromFirstName(String firstName)
			throws SQLException {
		List<User> users = new ArrayList<>();
		String selectUsers =
			"SELECT User.UserName AS UserName, FirstName, LastName, EmailAddress, PhoneNumber, PaypalID, StatusLevels" +
			"FROM User INNER JOIN Person " +
			"  ON User.UserName = Person.UserName " +
			"WHERE User.FirstName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUsers);
			selectStmt.setString(1, firstName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String userName = results.getString("UserName");
				String resultFirstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String emailAddress = results.getString("EmailAddress");
				String phoneNumber = results.getString("PhoneNumber");
				String paypalID = results.getString("PaypalID");
				User.StatusLevels statusLevels = User.StatusLevels.valueOf(results.getString("StatusLevels"));
				User user = new User(userName, resultFirstName, lastName, emailAddress, phoneNumber, paypalID, statusLevels);
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
	
	/*
	 * Delete the User instance.
	 * This runs a DELETE statement.
	 */
	public User delete(User user) throws SQLException{
		String deleteUser = "DELETE FROM User WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, user.getUserName());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for UserName=" + user.getUserName());
			}
			
			// Then also delete from the superclass.
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
}
