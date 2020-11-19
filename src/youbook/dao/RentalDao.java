package youbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import youbook.model.Book;
import youbook.model.Rental;
import youbook.model.User;


public class RentalDao {
	
	protected ConnectionManager connectionManager;
	private static RentalDao instance = null;
	protected RentalDao() {
		connectionManager = new ConnectionManager();
	}
	public static RentalDao getInstance() {
		if(instance == null) {
			instance = new RentalDao();
		}
		return instance;
	}
	
	/*
	 * Save the Rental instance by storing it in your MySQL instance.
	 * This runs a INSERT statement
	 */
	public Rental create(Rental rental) throws SQLException {
		String insertRental =
			"INSERT INTO Rental(BookId,UserName,CheckOutDate) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRental);
			insertStmt.setInt(1, rental.getBook().getBookId());
			insertStmt.setString(2, rental.getUser().getUserName());
			insertStmt.setTimestamp(3, new Timestamp(rental.getCheckOutDate().getTime()));
			insertStmt.executeUpdate();
			return rental;
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
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}


	/*
	 * Get the all the Rentals based on the userName.
	 * This runs a SELECT statement.
	 */
	public List<Rental> getRentalsByUserName(String userName) throws SQLException {
		List<Rental> rentals = new ArrayList<Rental>();
		String selectRentals =
			"SELECT BookId, UserName, CheckOutDate " +
			"FROM Rental " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRentals);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			BookDao bookDao = BookDao.getInstance();
			UserDao userDao = UserDao.getInstance();
			while(results.next()) {
				Date created = results.getDate("Created");
				Book book = bookDao.getBookById(results.getInt("BookId"));
				User user = userDao.getUserByUserName(results.getString("UserName"));
				Rental rental = new Rental(book, user, created);
				rentals.add(rental);
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
		return rentals;
	}

	/*
	 * Gets the all the Rentals based on the bookId.
	 * This runs a SELECT statement.
	 */
	public List<Rental> getRentalsByBookId(int bookId) throws SQLException {
		List<Rental> rentals = new ArrayList<Rental>();
		String selectRentals =
			"SELECT BookId, UserName, CheckOutDate " +
			"FROM Rental " +
			"WHERE BookId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRentals);
			selectStmt.setInt(1, bookId);
			results = selectStmt.executeQuery();
			UserDao userDao = UserDao.getInstance();
			BookDao bookDao = BookDao.getInstance();
			while(results.next()) {
				while(results.next()) {
					Date created =  new Date(results.getTimestamp("Created").getTime());
					User user = userDao.getUserByUserName(results.getString("UserName"));
					Book book = bookDao.getBookById(results.getInt("BookId"));
					Rental rental = new Rental(book, user, created);
					rentals.add(rental);
				}
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
		return rentals;
	}
	

	/**
	 * Delete the Rental instance.
	 * This runs a DELETE statement.
	 */
	public Rental delete(Rental rental) throws SQLException {
		String deleteRental = "DELETE FROM Rental WHERE BookId=? AND UserName=? AND CheckOutDate=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRental);
			deleteStmt.setInt(1, rental.getBook().getBookId());
			deleteStmt.setString(2, rental.getUser().getUserName());
			deleteStmt.setDate(3, rental.getCheckOutDate());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete");
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

}
