
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



/**
 * Data access object (DAO) class to interact with the underlying BlogUsers table in your
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
	
	/**
	 * add a book entry in database
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public Books create(Books book) throws SQLException {
		String insertRecommendation = "INSERT INTO Book(Title, "
				+ "  PublisherName, PublicationYear) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRecommendation, 
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, book.getTitle());
			insertStmt.setString(2, book.getPublisherName());
			insertStmt.setString(2, book.getPublicationYear());
			
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
	
	/**
	 * remove a book entry
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	
	public Books delete(Books book) throws SQLException {
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
						+ "available to delete for Book=" 
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
	
	/**
	 * search a book by id
	 * @param bookId
	 * @return
	 * @throws SQLException
	 */
	
	public Books getBookById(int bookId) throws SQLException {
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
				
				Books book = 
						new Books(resultBookId, 
								title, publisherName, publicationYear );
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
		
	/**
	 * get books published in particular year
	 * @param publicationYear
	 * @return
	 * @throws SQLException
	 */
	public List<Books> getBookByPublicationYear
	(String publicationYear)throws SQLException {
		String selectBooks =
			"SELECT Book.PublicationYear AS PublicationYear, BookId, "
			+ " Title, PublisherName "
			+ " FROM Book WHERE PublicationYear=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Books> bookList = 
				new ArrayList<Books>();
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
				
				Books book = new Books(bookId, title, 
						resultPublisherName, resPublicationYear );				
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
	 * get books of a publisher
	 * @param publisherName
	 * @return
	 * @throws SQLException
	 */
	
	public List<Books> getBookByPublisherName
		(String publisherName) throws SQLException {
		String selectBooks =
			"SELECT Book.PublisherName AS PublisherName, BookId, "
			+ " Title, PublicationYear "
			+ " FROM Book WHERE PublisherName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Books> bookList = 
				new ArrayList<Books>();
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
				
				Books book = new Books(resultBookId, title, 
						resultPublisherName, publicationYear );
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
	 * update title of a book
	 * @param book
	 * @param newTitle
	 * @return
	 * @throws SQLException
	 */
	
	public Books updateTitle(Books book, String newTitle) 
			throws SQLException {
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
	
	/**
	 * update name of publisher
	 * @param book
	 * @param newPublisherName
	 * @return
	 * @throws SQLException
	 */
	public Books updatePublisherName(Books book, String newPublisherName) 
			throws SQLException {
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
			
			// Update the person parameter before returning to the caller.
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
	
	/**
	 * update publication year of book
	 * @param book
	 * @param newPublicationYear
	 * @return
	 * @throws SQLException
	 */
	
	public Books updatePublicationYear(Books book, String newPublicationYear) 
			throws SQLException {
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
			
			// Update the person parameter before returning to the caller.
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
}


	
	

