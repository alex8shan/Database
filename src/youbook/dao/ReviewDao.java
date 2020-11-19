package youbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import youbook.model.Book;
import youbook.model.Review;
import youbook.model.User;

/*
 * Data access object (DAO) class to interact with the underlying Review table in your MySQL
 * instance. This is used to store Review into your MySQL instance and retrieve
 * Review from MySQL instance 
 */
public class ReviewDao {
	
	protected ConnectionManager connectionManager;
	// Single pattern: instantiation is limited to one object.
	private static ReviewDao instance = null;
	protected ReviewDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewDao getInstance() {
		if (instance == null) {
			instance = new ReviewDao();
		}
		return instance;
	}
	
	/*
	 * Save the Review instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Review create(Review review) throws SQLException {
		String insertReview = 
			"INSERT INTO Review(UserName,BookId,Rating,Comment) " +
			"VALUES(?，?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReview,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, review.getUser().getUserName());
			insertStmt.setInt(2, review.getBook().getBookId());
			insertStmt.setDouble(3, review.getRating());
			insertStmt.setString(4, review.getComment());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if(resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewId(reviewId);
			return review;
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
	 * Get the Review record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Review instance.
	 */
	public Review getReviewById(int reviewId) throws SQLException {
		String selectReview = 
			"SELECT ReviewId, UserName, BookId, Rating, Comment " +
			"FROM Review WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, reviewId);
			results = selectStmt.executeQuery();
			UserDao userDao = UserDao.getInstance();
			BookDao bookDao = BookDao.getInstance();
			if (results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				User user = userDao.getUserByUserName(results.getString("UserName"));
				Book book = bookDao.getBookById(results.getInt("BookId"));
				double rating = results.getDouble("Rating");
				String comment = results.getString("Comment");
				Review review = new Review(resultReviewId, user, book, rating, comment);
				return review;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return null;
	}
	
	/*
	 * Get the Review records by fetching it from your MySQL instance by the corresponding userName.
	 * This runs a SELECT statement and returns 0 or more Review instance(s).
	 */
	public List<Review> getReviewsByUserName(String userName) throws SQLException {
		List<Review> reviews = new ArrayList<>();
		String selectReviews =
			"SELECT ReviewId, UserName, BookId, Rating, Comment " +
			"FROM Review " +
			"WHERE UserName=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UserDao userDao = UserDao.getInstance();
			BookDao bookDao = BookDao.getInstance();
			while(results.next()) {
				int reviewId = results.getInt("ReviewId");
				User user = userDao.getUserByUserName(results.getString("UserName"));
				Book book = bookDao.getBookById(results.getInt("BookId"));
				double rating = results.getDouble("Rating");
				String comment = results.getString("Comment");
				Review review = new Review(reviewId, user, book, rating, comment);
				reviews.add(review);
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
		return reviews;
	}
	
	/*
	 * Get the Review records by fetching it from your MySQL instance by the corresponding bookId.
	 * This runs a SELECT statement and returns 0 or more Review instance(s).
	 */
	public List<Review> getReviewsByBookId(int bookId) throws SQLException {
		List<Review> reviews = new ArrayList<>();
		String selectReviews =
			"SELECT ReviewId, UserName, BookId, Rating, Comment " +
			"FROM Review " +
			"WHERE BookId=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setInt(1, bookId);
			results = selectStmt.executeQuery();
			UserDao userDao = UserDao.getInstance();
			BookDao bookDao = BookDao.getInstance();
			while(results.next()) {
				int reviewId = results.getInt("ReviewId");
				User user = userDao.getUserByUserName(results.getString("UserName"));
				Book book = bookDao.getBookById(results.getInt("BookId"));
				double rating = results.getDouble("Rating");
				String comment = results.getString("Comment");
				Review review = new Review(reviewId, user, book, rating, comment);
				reviews.add(review);
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
		return reviews;
	}
	
	/*
	 * Updates the rating of a Review.
	 * This runs an UPDATE statement.
	 */
	public Review updateRating(Review review, double newRating) throws SQLException {
		String updateRating = "UPDATE Review SET Rating=? WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateRating);
			updateStmt.setDouble(1, newRating);
			updateStmt.setInt(2, review.getReviewId());
			updateStmt.executeUpdate();
			review.setRating(newRating);
			return review;
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
	 * Updates the comment attached to a Review.
	 * This runs an UPDATE statement.
	 */
	public Review updateComment(Review review, String newComment) throws SQLException {
		String updateComment = "UPDATE Review SET Comment=? WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateComment);
			updateStmt.setString(1, newComment);
			updateStmt.setInt(2, review.getReviewId());
			updateStmt.executeUpdate();
			review.setComment(newComment);
			return review;
		}catch (SQLException e) {
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
	 * Delete the Review instance.
	 * This runs a DELETE statement.
	 */
	public Review delete(Review review) throws SQLException {
		String deleteReview = "DELETE FROM Review WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewId());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for ReviewId=" + review.getReviewId());
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