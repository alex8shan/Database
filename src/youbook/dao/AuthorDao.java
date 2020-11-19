package youbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import youbook.model.Author;
import youbook.model.Book;

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
	
	/*
	 * Save the Author instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Author create(Author author) throws SQLException {
		String insertAuthor = "INSERT INTO Author(Name, BookId) "
				+ " VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAuthor, 
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, author.getName());
			insertStmt.setInt(2, author.getBook().getBookId());
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
	
	/*
	 * Get the Author record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Author instance.
	 */
	 public Author getAuthorById(int authorId)throws SQLException {
		 String selectAuthor = "SELECT Author.AuthorId AS AuthorId, "
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
			BookDao bookDao = BookDao.getInstance();
			if(results.next()) {
				int resultAuthorId = results.getInt("AuthorId");
				String authorName = results.getString("Name");
				Book book = bookDao.getBookById(results.getInt("BookId"));
				Author author = new Author(resultAuthorId, authorName, book);
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
	 
	 /*
	  * Updates the author's name
	  * This runs an UPDATE statement
	  */
	public Author updateAuthorName (Author author, String newName) throws SQLException {
		String updateAuthorName = "UPDATE Author SET Name=? WHERE AuthorId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateAuthorName);
			updateStmt.setString(1, newName);
			updateStmt.setInt(2, author.getAuthorId());
			updateStmt.executeUpdate();
				
			author.setName(newName);
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
	
	 /*
	  * Get Books written by author(s) with authorName
	  */
	public List<Book> getBooksByAuthorName (String authorName) throws SQLException {
		String selectBooks =
			"SELECT Name AS AuthorName, Author.BookId, "
			+ " Book.Title, PublisherName, PublicationYear "
			+ " FROM Author JOIN Book ON Author.BookId=Book.BookId"
			+ "  WHERE Name=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Book> bookList = new ArrayList<Book>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBooks);
			selectStmt.setString(1, authorName);
			results = selectStmt.executeQuery();
		
			while(results.next()) {
				int bookId = results.getInt("BookId");
				String title = results.getString("Title");
				String publisherName = results.getString("PublisherName");
				String publicationYear = results.getString("PublicationYear");
					
				Book book = new Book(bookId, title, publisherName, publicationYear );
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
	
	
	/*
	 * Gets all author(s) of a book.
	 */
	public List<Author> getAuthorByBookId(int bookId)throws SQLException {
		String selectBooks =
			"SELECT BookId, AuthorId, "
			+ " Name "
		    + " FROM Author WHERE BookId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Author> authorList = new ArrayList<Author>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBooks);
			selectStmt.setInt(1, bookId);
			results = selectStmt.executeQuery();
			BookDao bookDao = BookDao.getInstance();
			while(results.next()) {
				String authorName = results.getString("Name");
				int authorId = results.getInt("AuthorId");
				Book book = bookDao.getBookById(results.getInt("BookId"));
				Author author = new Author(authorId, authorName, book);
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
	
	
	/*
	 * Delete the Author instance.
	 * This runs a DELETE statement
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
					+ "available to delete for AuthorId=" 
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

}

