package youbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import youbook.model.Admin;
import youbook.model.Person;


public class AdminDao extends PersonDao {
	
	protected ConnectionManager connectionManager;
	// Single pattern: instantiation is limited to one object.
	private static AdminDao instance = null;
	protected AdminDao() {
		connectionManager = new ConnectionManager();
	}
	public static AdminDao getInstance() {
		if(instance == null) {
			instance = new AdminDao();
		}
		return instance;
	}
	
	/**
	 * Save the Admin instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Admin create(Admin admin) throws SQLException{
		// Insert into the superclass table first.
		create(new Person(admin.getUserName(), admin.getFirstName(), admin.getLastName()));
		String insertAdmin = "INSERT INTO Administrator(LastLogin) VALUES(?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAdmin);
			insertStmt.setTimestamp(1, new Timestamp(admin.getLastLogin().getTime()));
			insertStmt.executeUpdate();
			return admin;
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
	 * Update the LastName of the Admin instance.
	 * This runs a UPDATE statement.
	 */
	public Admin updateLastName(Admin admin, String newLastName) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateLastName(admin, newLastName);
		return admin;
	}
	
	/*
	 * Get the Admin record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Admin instance.
	 */
	public Admin getAdminByUserName(String userName) throws SQLException {
		// To build an Admin object, we need the Person record, too.
		String selectAdmin =
			"SELECT Administrator.UserName AS UserName, FirstName, LastName, LastLogin " +
			"FROM Administrator INNER JOIN Person " +
			"  ON Administrator.UserName = Person.UserName " +
			"WHERE Administrator.UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAdmin);
			
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			
			if (results.next()) {
				String resultUserName = results.getString("UserName");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				Date lastLogin = new Date(results.getTimestamp("Created").getTime());
				Admin admin = new Admin(resultUserName, firstName, lastName, lastLogin);
				return admin;
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
	 * Get the Admin records by fetching it from your MySQL instance by the corresponding firstName.
	 * This runs a SELECT statement and returns 0 or more Admin instance(s).
	 */
	public List<Admin> getAdminFromFirstName(String firstName)
			throws SQLException {
		List<Admin> admins = new ArrayList<Admin>();
		String selectAdministrators =
			"SELECT Administrator.UserName AS UserName, FirstName, LastName, LastLogin " +
			"FROM Administrator INNER JOIN Person " +
			"  ON Administrator.UserName = Person.UserName " +
			"WHERE Person.FirstName=?;";
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
				Date lastLogin = new Date(results.getTimestamp("LastLogin").getTime());
				Admin admin = new Admin(userName, resultFirstName, lastName, lastLogin);
				admins.add(admin);
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
		return admins;
	}
	
	/*
	 * Delete the Admin instance.
	 * This runs a DELETE statement.
	 */
	public Admin delete(Admin admin) throws SQLException{
		String deleteAdmin = "DELETE FROM Administrator WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAdmin);
			deleteStmt.setString(1, admin.getUserName());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for UserName=" + admin.getUserName());
			}
			// Then also delete from the superclass.
			super.delete(admin);
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
