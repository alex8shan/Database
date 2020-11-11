package youbook.dal;

import youbook.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
	protected ConnectionManager connectionManager;
	// Single pattern: instantiation is limited to one object.
	private static AuthorDao instance = null;
	
	protected AuthorDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static AuthorDao getInstance() {
		if(instance == null) {
			instance = new AuthorDao();
		}
		return instance;
	}
	
	/**
	 * create an author object
	 * @param author
	 * @return
	 * @throws SQLException
	 */
	
	public Authors create(Authors author) throws SQLException {

		String insertRecommendation = "INSERT INTO Authors(AuthorName, BookId) "
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRecommendation, 
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, author.getAuthorName());
			insertStmt.setInt(2, author.getBookId());
			
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int authorId = -1;
			if(resultKey.next()) {
				authorId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			author.setAuthorId(authorId);
			return author;
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
   * delete an author based on id	
   * @param author
   * @return
   * @throws SQLException
   */
	
  public Authors delete(Authors author) throws SQLException {
	String deleteAuthor = "DELETE FROM Authors WHERE AuthorId=?;";
	Connection connection = null;
	PreparedStatement deleteStmt = null;
	try {
		connection = connectionManager.getConnection();
		deleteStmt = connection.prepareStatement(deleteAuthor);
		deleteStmt.setInt(1, author.getAuthorId());
		int affectedRows = deleteStmt.executeUpdate();
		if (affectedRows == 0) {
			throw new SQLException("No records "
					+ "available to delete for Author=" 
		+ author.getAuthorId());
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
	
  /**
   * search for author based on authorid
   * @param authorId
   * @return
   * @throws SQLException
   */
  
 public Authors getAuthorById(int authorId)throws SQLException {

		String selectAuthor =
			"SELECT Authors.AuthorId AS AuthorId, "
			+ " AuthorName, BookId "
			+ "FROM Authors WHERE AuthorId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;

		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAuthor);
			selectStmt.setInt(1, authorId);
			results = selectStmt.executeQuery();

			if(results.next()) {
				int resultAuthorId = results.getInt("AuthorId");
				String authorName = results.getString("AuthorName");
				int bookId = results.getInt("BookId");
				
				Authors author = new Authors(resultAuthorId, authorName, bookId);
				return author;
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
	  * Get list of authors of a book
	  * @param authorId
	  * @return
	  * @throws SQLException
	  */
 
 	public List<Authors> getAuthorByBookId(int bookId)
				throws SQLException {
			
	String selectBooks =
		"SELECT BookId AS BookId, AuthorId, "
		+ " AuthorName "
		+ " FROM Authors WHERE BookId=?;";
	Connection connection = null;
	PreparedStatement selectStmt = null;
	ResultSet results = null;
	List<Authors> authorList = 
			new ArrayList<Authors>();
	try {
		connection = connectionManager.getConnection();
		selectStmt = connection.prepareStatement(selectBooks);
		selectStmt.setInt(1, bookId);
		results = selectStmt.executeQuery();

		while(results.next()) {
			int resultBookId = results.getInt("BookId");
			String authorName = results.getString("AuthorName");
			int authorId = results.getInt("AuthorId");
			
			Authors author = new Authors(authorId, authorName, resultBookId);
			
			
			authorList.add(author);
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
	return authorList;
}
 	/**
 	 * get books of an author
 	 * @param authorName
 	 * @return
 	 * @throws SQLException
 	 */
		
	public List<Books> getBooksByAuthorName
	(String authorName)
			throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		String selectBooks =
			"SELECT AuthorName AS AuthorName, Authors.BookId, "
			+ " Books.Title, PublisherName, PublicationYear "
			+ " FROM Authors JOIN Books ON Authors.BookId=Books.BookId"
			+ "  WHERE AuthorName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Books> bookList = 
				new ArrayList<Books>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBooks);
			selectStmt.setString(1, authorName);
			results = selectStmt.executeQuery();
	
			while(results.next()) {
				String resultAuthorName = results.getString("AuthorName");
				int bookId = results.getInt("BookId");
				String title = results.getString("Title");
				String publisherName = results.getString("PublisherName");
				String publicationYear = results.getString("PublicationYear");
				
				Books book = 
						new Books(bookId, title, 
								publisherName, publicationYear );
				bookList.add(book);
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
		return bookList;
	}
	
	/**
	 * update the author name
	 * @param author
	 * @param newName
	 * @return
	 * @throws SQLException
	 */
	
	public Authors updateAuthorName
	(Authors author, String newName) throws SQLException {
		String updateAuthorName = "UPDATE Authors SET AuthorName=? WHERE "
				+ "AuthorId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateAuthorName);
			updateStmt.setString(1, newName);
			updateStmt.setInt(2, author.getAuthorId());
			updateStmt.executeUpdate();
			
			// Update the person parameter before returning to the caller.
			author.setAuthorName(newName);
			return author;
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


	
	


