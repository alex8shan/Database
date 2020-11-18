package youbook.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import youbook.model.Payment;
import youbook.model.Payment.PaymentType;
import youbook.model.Users;



public class PaymentDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static PaymentDao instance = null;

	protected PaymentDao() {
			connectionManager = new ConnectionManager();
		}

	public static PaymentDao getInstance() {
		if (instance == null) {
			instance = new PaymentDao();
		}
		return instance;
	}

	
	public Payment create(Payment payment) throws SQLException {
		String insertPayment = "INSERT INTO Payment(UserName,PaymentType, TotalAmount) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPayment,Statement.RETURN_GENERATED_KEYS);
			
					
			insertStmt.setString(1, payment.getUser().getUserName());
			insertStmt.setString(2, payment.getPaymentType().name());
			insertStmt.setFloat(1, payment.getTotalAmount());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int paymentId=-1;
			if(resultKey.next()) {
				paymentId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			payment.setPaymentId(paymentId);
			return payment;
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
	 * Delete the Payments instance. This runs a DELETE statement.
	 */
	public Payment delete(Payment payment) throws SQLException {
		String deletePayment = "DELETE FROM Payment WHERE PaymentId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePayment);
			deleteStmt.setInt(1, payment.getPaymentId());
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

	
	
	public Payment getPaymentByPaymentId(Integer paymentId) throws SQLException {
		String selectPayment = "SELECT PaymentId, UserName, PaymentType,TotalAmount FROM Payment WHERE PaymentId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPayment);
			selectStmt.setInt(1, paymentId);
			results = selectStmt.executeQuery();
			UsersDao userDao=UsersDao.getInstance();
			if (results.next()) {
				Users user=userDao.getUserByUserName(results.getString("UserName"));
				Integer resultPaymentId = results.getInt("PaymentId");
				Float totalAmount = results.getFloat("TotalAmount");
				PaymentType paymentType = PaymentType.valueOf(results.getString("PaymentType"));
				Payment payment = new Payment(resultPaymentId, user,paymentType, totalAmount);
				return payment;
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
	
	

}
