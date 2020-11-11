package youbook.dao;
import youbook.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ReviewDao {
	protected ConnectionManager connectionManager;
	private static ReviewDao instance = null;
	
	protected ReviewDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static ReviewDao getInstance() {
		if(instance == null) {
			instance = new ReviewDao();
		}
		return instance;
	}

	public Review create(Review review) throws SQLException {
		String insertReview =
			"INSERT INTO Review(UserName,BookId,Rating,Comment) " +
			"VALUES(?,?,?,?);";
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
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
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

	/**
	 * Update the content of the Review instance.
	 * This runs a UPDATE statement.
	 */
	public Review updateContent(Review review, double newRating, String newComment) throws SQLException {
		String updateReview = "UPDATE Review SET Rating=?,Comment=? WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateReview);
			updateStmt.setDouble(1, newRating);
			updateStmt.setString(2, newComment);
			updateStmt.setInt(3, review.getReviewId());
			updateStmt.executeUpdate();

			review.setRating(newRating);
			review.setComment(newComment);
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

	/**
	 * Delete the Review instance.
	 * This runs a DELETE statement.
	 */
	public Review delete(Review review) throws SQLException {
		String deleteReview = "DELETE FROM Review WHERE reviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Review instance.
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
	 * Get the Review record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Review instance.
	 * Note that we use BlogPostsDao and BlogUsersDao to retrieve the referenced
	 * BlogPosts and BlogUsers instances.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the BlogComments, BlogPosts, BlogUsers tables and then build each object.
	 */
	public Review getReviewById(int reviewId) throws SQLException {
		String selectReview =
			"SELECT ReviewId,UserName,BookId,Rating,Comment " +
			"FROM Review " +
			"WHERE ReviewId=?;";
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
			if(results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				double rating = results.getDouble("Rating");
				String comment = results.getString("Comment");
				
				User user = userDao.getUserByReviewId(resultReviewId);
				Book book = bookDao.getBookByReviewId(resultReviewId);
				Review review = new Review(resultReviewId, user,
					book, rating, comment);
				return review;
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
		return null;
	}

	/**
	 * Get the all the Reviews for a user.
	 */
	public List<Review> getReviewsForUser(User user) throws SQLException {
		List<Review> reviews = new ArrayList<Review>();
		String selectReviews =
			"SELECT ReviewId,UserName,BookId,Rating,Comment " +
			"FROM Review " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setString(1, user.getUserName());
			results = selectStmt.executeQuery();
			UserDao userDao = UserDao.getInstance();
			while(results.next()) {
				int reviewId = results.getInt("ReviewId");
				double rating = results.getDouble("Rating");
				String comment = results.getString("Comment");
				Book book = ((BookDao) results).getBookByReviewId("ReviewId");
				Review review = new Review(reviewId, user, book,
					rating, comment);
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
	
	/**
	 * Get the all the Reviews for a Book.
	 */
	public List<Review> getReviewsForBook(Book book) throws SQLException {
		List<Review> reviews = new ArrayList<Review>();
		String selectReviews =
			"SELECT ReviewId,UserName,BookId,Rating,Comment " +
			"FROM Review " +
			"WHERE BookId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setInt(1, book.getBookId());
			results = selectStmt.executeQuery();
			BookDao bookDao = BookDao.getInstance();
			while(results.next()) {
				int reviewId = results.getInt("ReviewId");
				double rating = results.getDouble("Rating");
				String comment = results.getString("Comment");
				User user = ((UserDao) results).getUserByReviewId("ReviewId");
				Review review = new Review(reviewId, user, book,
					rating, comment);
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
}

