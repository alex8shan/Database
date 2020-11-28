package youbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import youbook.model.Book;
import youbook.model.User;
import youbook.model.WishList;


public class WishListDao {
    private ConnectionManager connectionManager;
    private static WishListDao instance = null;
    private WishListDao() {
        connectionManager = new ConnectionManager();
    }
    public static WishListDao getInstance() {
        if (instance == null) {
            instance = new WishListDao();
        }
        return instance;
    }

	/*
	 * Save the WishList instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
    public WishList create(WishList wishList) throws SQLException {
        String insertWishlist =
            "INSERT INTO WishList(BookId,UserName) " +
                "VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertWishlist,
                Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, wishList.getBook().getBookId());
            insertStmt.setString(2, wishList.getUser().getUserName());
            insertStmt.executeUpdate();
            resultKey = insertStmt.getGeneratedKeys();
            int id = -1;
            if(resultKey.next()) {
                id = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            wishList.setWishListId(id);
            return wishList;
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
	 * Get the WishList record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single WishList instance.
	 */
    public WishList getWishListById(int wishListId) throws SQLException {
    	String selectWishList = 
    		"SELECT WishList.WishListId AS WishListId, BookId, UserName " +
    		"FROM WishList " +
    		"WHERE WishList.WishListId=?;";
    	Connection connection = null;
    	PreparedStatement selectStmt = null;
    	ResultSet results = null;
    	try {
    		connection = connectionManager.getConnection();
    		selectStmt = connection.prepareStatement(selectWishList);
    		selectStmt.setInt(1, wishListId);
    		results = selectStmt.executeQuery();
    		UserDao userDao = UserDao.getInstance();
    		BookDao bookDao = BookDao.getInstance();
    		if(results.next()) {
    			int resultWishListId = results.getInt("WishListId");
    			User user = userDao.getUserByUserName(results.getString("UserName"));
    			Book book = bookDao.getBookById(results.getInt("BookId"));
    			WishList wishList = new WishList(resultWishListId, book, user);
    			return wishList;
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
	 * Get the WishList records by fetching it from your MySQL instance by the corresponding userName.
	 * This runs a SELECT statement and returns 0 or more WishList instance(s).
	 */
    public List<WishList> getWishListsByUserName(String userName) throws SQLException {
        List<WishList> wishLists = new ArrayList<>();
        String selectWishList = "SELECT WishListID,BookId,UserName FROM WishList WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectWishList);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();
    		UserDao userDao = UserDao.getInstance();
    		BookDao bookDao = BookDao.getInstance();
            while(results.next()) {
                int rstId = results.getInt("WishListID");
                Book book = bookDao.getBookById(results.getInt("BookId"));
                User user = userDao.getUserByUserName(results.getString("UserName"));
                WishList wishList = new WishList(rstId, book, user);
                wishLists.add(wishList);
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
        return wishLists;
    }

	/*
	 * Get the WishList records by fetching it from your MySQL instance by the corresponding bookId.
	 * This runs a SELECT statement and returns 0 or more WishList instance(s).
	 */
    public List<WishList> getWishListsByBookId(int bookId) throws SQLException {
        List<WishList> wishLists = new ArrayList<>();
        String selectWishList = "SELECT WishListID,UserName,BookId FROM WishList WHERE UserName=? and BookId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectWishList);
            selectStmt.setInt(2, bookId);
            results = selectStmt.executeQuery();
            UserDao userDao = UserDao.getInstance();
    		BookDao bookDao = BookDao.getInstance();
            while(results.next()) {
                int rstId = results.getInt("WishListID");
                Book book = bookDao.getBookById(results.getInt("BookId"));
                User user = userDao.getUserByUserName(results.getString("UserName"));
                WishList wishList = new WishList(rstId, book, user);
                wishLists.add(wishList);
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
        return wishLists;
    }
	
	/**
	 * Get the WishList based on
	 * userName and bookId
	 */
	public WishList getWishListByUsernameAndBookId(String userName, int bookId) throws SQLException {
		WishList wishList;
		String selectWishList = "SELECT WishListID,UserName,BookId FROM WishList WHERE BookId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectWishList);
			selectStmt.setString(1, userName);
			selectStmt.setInt(2, bookId);
			results = selectStmt.executeQuery();
			UserDao userDao = UserDao.getInstance();
			BookDao bookDao = BookDao.getInstance();
			int rstId = results.getInt("WishListID");
			Book book = bookDao.getBookById(results.getInt("BookId"));
			User user = userDao.getUserByUserName(results.getString("UserName"));
			wishList = new WishList(rstId, book, user);

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
		return wishList;
	}
	
	/*
	 * Delete the WishList instance.
	 * This runs a DELETE statement
	 */
    public WishList delete(WishList wishList) throws SQLException {
        String deleteWishList = "DELETE FROM WishList WHERE WishListID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteWishList);
            deleteStmt.setInt(1, wishList.getWishlistId());
            int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for WishListId=" + wishList.getWishlistId());
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
