package youbook.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import youbook.model.Users;
import youbook.model.CreditCard;

public class CreditCardDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static CreditCardDao instance = null;

	protected CreditCardDao() {
			connectionManager = new ConnectionManager();
		}

	public static CreditCardDao getInstance() {
		if (instance == null) {
			instance = new CreditCardDao();
		}
		return instance;
	}

	/**
	 * Save the CreditCard instance by storing it in your MySQL instance. This runs a
	 * INSERT statement.
	 */
	public CreditCard create(CreditCard creditCard) throws SQLException {
		String insertCreditCard = "INSERT INTO CreditCard(CardNumber,Expiration,UserName) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCreditCard);
			
			insertStmt.setLong(1, creditCard.getCardNumber());
			insertStmt.setDate(2, new java.sql.Date(creditCard.getExpiration().getTime()));
			insertStmt.setString(3, creditCard.getUser().getUserName());
			insertStmt.executeUpdate();
			return creditCard;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (insertStmt != null) {
				insertStmt.close();
			}
		}
	}

	/**
	 * Delete the CreditCards instance. This runs a DELETE statement.
	 */
	public CreditCard delete(CreditCard creditCard) throws SQLException {
		String deleteCreditCard = "DELETE FROM CreditCard WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCreditCard);
			deleteStmt.setString(1, creditCard.getUser().getUserName());
			deleteStmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

	
	public List<CreditCard> getCreditCardsByUserName(String userName) throws SQLException {
		List<CreditCard> creditCardList = new ArrayList<>();
		String selectCreditCard = "SELECT CardNumber, Expiration, UserName FROM CreditCard WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCreditCard);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao userDao=UsersDao.getInstance();
			while (results.next()) {
				Users user=userDao.getUserByUserName(results.getString("UserName"));
				Long cardNumber = results.getLong("CardNumber");
				Date expiration =  new Date(results.getTimestamp("Expiration").getTime());
				CreditCard creditCard = new CreditCard(cardNumber,expiration, user);
				creditCardList.add(creditCard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return creditCardList;
	}
	
	public CreditCard getCreditCardByCardNumber(Long cardNumber) throws SQLException {
		String selectCreditCard = "SELECT CardNumber, Expiration, UserName FROM CreditCard WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCreditCard);
			selectStmt.setLong(1, cardNumber);
			results = selectStmt.executeQuery();
			UsersDao userDao=UsersDao.getInstance();
			if (results.next()) {
				Users user=userDao.getUserByUserName(results.getString("UserName"));
				Long resultCardNumber = results.getLong("CardNumber");
				Date expiration =  new Date(results.getTimestamp("Expiration").getTime());
				CreditCard creditCard = new CreditCard(resultCardNumber,expiration, user);
				return creditCard;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return null;
	}
	
	public CreditCard updateExpiration(CreditCard creditCard, Date newExpiration) throws SQLException {
		String updateCreditCardExpiration = "UPDATE CreditCard SET Expiration=? WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCreditCardExpiration);
			updateStmt.setDate(1, new java.sql.Date(newExpiration.getTime()));
			updateStmt.setLong(2, creditCard.getCardNumber());
			updateStmt.executeUpdate();
			creditCard.setExpiration(newExpiration);
			return creditCard;
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

}
