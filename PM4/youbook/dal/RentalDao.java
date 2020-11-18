
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

	public Rental create(Rental rental) throws SQLException {
		String insertRental =
			"INSERT INTO Rental(BookId,UserName,CheckOutDate) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRental,
				Statement.RETURN_GENERATED_KEYS);
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
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogComments instance.
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


	/**
	 * Get the all the Rentals for a user.
	 */
	public List<Rental> getRentalsForUser(Users user) throws SQLException {
		List<Rental> rentals = new ArrayList<Rental>();
		String selectRentals =
			"SELECT BookId, UserName, CheckOutDate " +
			"FROM Rental " +
			"WHERE BookId=? AND UserName=? AND CheckOutDate=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRentals);
			selectStmt.setString(1, user.getUserName());
			results = selectStmt.executeQuery();
			BookDao bookDao = BookDao.getInstance();
			while(results.next()) {
				Date created =  new Date(results.getTimestamp("Created").getTime());
				int bookId = results.getInt("BookId");
				Books book = bookDao.getBookById(bookId);
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

	/**
	 * Get the all the Rentals of a Book.
	 */
	public List<Rental> getRentalsForBook(Books book) throws SQLException {
		List<Rental> rentals = new ArrayList<Rental>();
		String selectRentals =
			"SELECT BookId, UserName, CheckOutDate " +
			"FROM Rental " +
			"WHERE BookId=? AND UserName=? AND CheckOutDate=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRentals);
			selectStmt.setInt(1, book.getBookId());
			results = selectStmt.executeQuery();
			UsersDao userDao = UsersDao.getInstance();
			while(results.next()) {
				while(results.next()) {
					Date created =  new Date(results.getTimestamp("Created").getTime());
					String userName = results.getString("UserName");
					Users user = userDao.getUserByUserName(userName);
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

}
