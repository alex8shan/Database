package youbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import youbook.model.Payment;
import youbook.model.Payment.PaymentType;
import youbook.model.User;


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
	
	/*
	 * Save the Payment instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Payment create(Payment payment) throws SQLException {
		String insertPayment = "INSERT INTO Payment(UserName,PaymentType,TotalAmount) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPayment,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, payment.getUser().getUserName());
			insertStmt.setString(2, payment.getPaymentType().name());
			insertStmt.setDouble(1, payment.getTotalAmount());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int paymentId = -1;
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
	
	/*
	 * Get the Payment record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Payment instance.
	 */
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
			UserDao userDao = UserDao.getInstance();
			if (results.next()) {
				int resultPaymentId = results.getInt("PaymentId");
				double totalAmount = results.getDouble("TotalAmount");
				PaymentType paymentType = PaymentType.valueOf(results.getString("PaymentType"));
				User user = userDao.getUserByUserName(results.getString("UserName"));
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
	
	/*
	 * Get the Payment records by fetching it from your MySQL instance by the corresponding userName.
	 * This runs a SELECT statement and returns 0 or more Payment instance(s).
	 */
	public List<Payment> getPaymentsByUserName(String userName) throws SQLException {
		List<Payment> payments = new ArrayList<>();
		String selectPayments = 
			"SELECT PaymentId, UserName, PaymentType, TotalAmount " + 
			"FROM Payment WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPayments);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UserDao userDao = UserDao.getInstance();
			while(results.next()) {
				int paymentId = results.getInt("PaymentId");
				double totalAmount = results.getDouble("TotalAmount");
				PaymentType paymentType = PaymentType.valueOf(results.getString("PaymentType"));
				User user = userDao.getUserByUserName(results.getString("UserName"));
				Payment payment = new Payment(paymentId, user,paymentType, totalAmount);
				payments.add(payment);
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
		return payments;
	}

	/**
	 * Delete the Payment instance. This runs a DELETE statement.
	 */
	public Payment delete(Payment payment) throws SQLException {
		String deletePayment = "DELETE FROM Payment WHERE PaymentId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePayment);
			deleteStmt.setInt(1, payment.getPaymentId());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for PaymentId=" + payment.getPaymentId());
			}
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
	

}
