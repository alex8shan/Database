package youbook.dal;

import youbook.Model.Author;
import youbook.Model.Book;

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
	
	public Author create(Author author) throws SQLException {

		String insertRecommendation = "INSERT INTO Author(AuthorID, BookId) "
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
	
  public Author delete(Author author) throws SQLException {
	String deleteAuthor = "DELETE FROM Author WHERE AuthorId=?;";
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
  
 public Author getAuthorById(int authorId)throws SQLException {

		String selectAuthor =
			"SELECT Author.AuthorId AS AuthorId, "
			+ " Name, BookId "
			+ "FROM Author WHERE AuthorId=?;";
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
				
				Author author = new Author(resultAuthorId, authorName, bookId);
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
	  * @param
	  * @return
	  * @throws SQLException
	  */
 
 	public List<Author> getAuthorByBookId(int bookId)
				throws SQLException {
			
	String selectBooks =
		"SELECT BookId AS BookId, AuthorId, "
		+ " Name "
		+ " FROM Author WHERE BookId=?;";
	Connection connection = null;
	PreparedStatement selectStmt = null;
	ResultSet results = null;
	List<Author> authorList =
			new ArrayList<Author>();
	try {
		connection = connectionManager.getConnection();
		selectStmt = connection.prepareStatement(selectBooks);
		selectStmt.setInt(1, bookId);
		results = selectStmt.executeQuery();

		while(results.next()) {
			int resultBookId = results.getInt("BookId");
			String authorName = results.getString("AuthorName");
			int authorId = results.getInt("AuthorId");
			
			Author author = new Author(authorId, authorName, resultBookId);
			
			
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
		
	public List<Book> getBooksByAuthorName
	(String authorName)
			throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		String selectBooks =
			"SELECT Name AS AuthorName, Author.BookId, "
			+ " Book.Title, PublisherName, PublicationYear "
			+ " FROM Author JOIN Book ON Author.BookId=Book.BookId"
			+ "  WHERE Name=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Book> bookList =
				new ArrayList<Book>();
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
				
				Book book =
						new Book(bookId, title,
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
	
	public Author updateAuthorName
	(Author author, String newName) throws SQLException {
		String updateAuthorName = "UPDATE Author SET Name=? WHERE "
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


	
	


