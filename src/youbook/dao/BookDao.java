package youbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import youbook.model.Book;


/**
 * Data access object (DAO) class to interact with the underlying Book table in your
 * MySQL instance. This is used to store into your MySQL instance and
 * retrieve from MySQL instance.
 */
public class BookDao {
	protected ConnectionManager connectionManager;
	// Single pattern: instantiation is limited to one object.
	private static BookDao instance = null;
	protected BookDao() {
		connectionManager = new ConnectionManager();
	}
	public static BookDao getInstance() {
		if(instance == null) {
			instance = new BookDao();
		}
		return instance;
	}
	
	/*
	 * Save the Book instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Book create(Book book) throws SQLException {
		String insertBook = "INSERT INTO Book(Title,PublisherName,PublicationYear) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertBook, 
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, book.getTitle());
			insertStmt.setString(2, book.getPublisherName());
			insertStmt.setString(3, book.getPublicationYear());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int bookId = -1;
			if(resultKey.next()) {
				bookId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			book.setBookId(bookId);
			return book;
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
	 * Get the Book record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Book instance.
	 */
	public Book getBookById(int bookId) throws SQLException {
		String selectBooks =
			"SELECT Book.BookId AS BookId, "
			+ " Title, PublisherName, PublicationYear "
			+ "FROM Book WHERE BookId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;

		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBooks);
			selectStmt.setInt(1, bookId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultBookId = results.getInt("BookId");
				String title = results.getString("Title");
				String publisherName = results.getString("PublisherName");
				String publicationYear = results.getString("PublicationYear");
				Book book = new Book(resultBookId, title, publisherName, publicationYear );
				return book;
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
	 * Get the Book records by fetching it from your MySQL instance by the corresponding publicationYear.
	 * This runs a SELECT statement and returns 0 or more Book instance(s).
	 */
	public List<Book> getBookByPublicationYear (String publicationYear)throws SQLException {
		String selectBooks =
			"SELECT Book.PublicationYear AS PublicationYear, BookId, "
			+ " Title, PublisherName "
			+ " FROM Book WHERE PublicationYear=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Book> bookList = new ArrayList<Book>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBooks);
			selectStmt.setString(1, publicationYear);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int bookId = results.getInt("BookId");
				String title = results.getString("Title");
				String resultPublisherName = results.getString("PublisherName");
				String resPublicationYear = results.getString("PublicationYear");
				Book book = new Book(bookId, title, resultPublisherName, resPublicationYear );				
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
	 * Get the Book records by fetching it from your MySQL instance by the corresponding publisherName.
	 * This runs a SELECT statement and returns 0 or more Book instance(s).
	 */
	public List<Book> getBookByPublisherName (String publisherName) throws SQLException {
		String selectBooks =
			"SELECT Book.PublisherName AS PublisherName, BookId, "
			+ " Title, PublicationYear "
			+ " FROM Book WHERE PublisherName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Book> bookList = new ArrayList<Book>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBooks);
			selectStmt.setString(1, publisherName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int resultBookId = results.getInt("BookId");
				String title = results.getString("Title");
				String resultPublisherName = results.getString("PublisherName");
				String publicationYear = results.getString("PublicationYear");
				Book book = new Book(resultBookId, title,resultPublisherName, publicationYear );
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
	 * Get the Book records by fetching it from your MySQL instance by the corresponding title.
	 * This runs a SELECT statement and returns 0 or more Book instance(s).
	 */
	public List<Book> getBooksByTitle(String bookTitle) throws SQLException {
		String selectBooks =
				"SELECT Book.BookId AS BookId, "
						+ " Title, PublisherName, PublicationYear "
						+ "FROM Book where Title = ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Book> bookList = new ArrayList<Book>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBooks);
			selectStmt.setString(1, bookTitle);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int bookId = results.getInt("BookId");
				String title = results.getString("Title");
				String resultPublisherName = results.getString("PublisherName");
				String resPublicationYear = results.getString("PublicationYear");
				Book book = new Book(bookId, title,resultPublisherName, resPublicationYear );
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
	
	//Method to get books by keywords
	public List<Book> getBooksByKeywords(String bookTitle) throws SQLException {
		String selectBooks =
				"SELECT Book.BookId AS BookId, "
						+ " Title, PublisherName, PublicationYear "
						+ "FROM Book where Title like ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Book> bookList = new ArrayList<Book>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBooks);
			selectStmt.setString(1, "%"+bookTitle+"%");
			results = selectStmt.executeQuery();
			while(results.next()) {
				int bookId = results.getInt("BookId");
				String title = results.getString("Title");
				String resultPublisherName = results.getString("PublisherName");
				String resPublicationYear = results.getString("PublicationYear");
				Book book = new Book(bookId, title,resultPublisherName, resPublicationYear );
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
	 * Get the Book records by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns the first N (bookNum) books in database order by title (in alphabetical order).
	 */
	public List<Book> getTopBooksByTitle(int bookNum) throws SQLException {
		String selectBooks =
				"SELECT Book.BookId AS BookId, "
						+ " Title, PublisherName, PublicationYear "
						+ "FROM Book order by Title limit ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		List<Book> bookList = new ArrayList<Book>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBooks);
			selectStmt.setInt(1, bookNum);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int bookId = results.getInt("BookId");
				String title = results.getString("Title");
				String resultPublisherName = results.getString("PublisherName");
				String resPublicationYear = results.getString("PublicationYear");
				Book book = new Book(bookId, title, resultPublisherName, resPublicationYear );
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
	 * Updates the title of a Book instance.
	 * This runs an UPDATE statement.
	 */
	public Book updateTitle(Book book, String newTitle)throws SQLException {
		String updateBookTitle = "UPDATE Book SET "
				+ "  Title=? WHERE BookId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateBookTitle);
			updateStmt.setString(1, newTitle);
			updateStmt.setInt(2, book.getBookId());
			updateStmt.executeUpdate();
		
			// Update the person parameter before returning to the caller.
			book.setTitle(newTitle);
			return book;
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
	 * Updates the publisher of a Book instance.
	 * This runs an UPDATE statement.
	 */
	public Book updatePublisherName(Book book, String newPublisherName) throws SQLException {
		String updatePublisherName = "UPDATE Book SET "
				+ "  PublisherName=? WHERE BookId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePublisherName);
			updateStmt.setString(1, newPublisherName);
			updateStmt.setInt(2, book.getBookId());
			updateStmt.executeUpdate();
			book.setPublisherName(newPublisherName);
			return book;
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
	 * Updates the publication year of a Book instance.
	 * This runs an UPDATE statement.
	 */
	public Book updatePublicationYear(Book book, String newPublicationYear)throws SQLException {
		String updatePublicationYear = "UPDATE Book SET "
				+ "  PublicationYear=? WHERE BookId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePublicationYear);
			updateStmt.setString(1, newPublicationYear);
			updateStmt.setInt(2, book.getBookId());
			updateStmt.executeUpdate();
			
			book.setPublicationYear(newPublicationYear);
			return book;
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
	 * Delete the Book instance.
	 * This runs a DELETE statement
	 */
	public Book delete(Book book) throws SQLException {
		String deleteBook = "DELETE FROM Book WHERE BookId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteBook);
			deleteStmt.setInt(1, book.getBookId());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records "
						+ "available to delete for BookId=" 
			+ book.getBookId());
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


	
	

