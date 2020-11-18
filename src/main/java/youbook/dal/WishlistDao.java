package youbook.dal;

import youbook.Model.Books;
import youbook.Model.Users;
import youbook.Model.WishList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class WishlistDao {
    private ConnectionManager connectionManager;
    private static WishlistDao instance = null;

    private WishlistDao() {
        connectionManager = new ConnectionManager();
    }
    public static WishlistDao getInstance() {
        if (instance == null) {
            instance = new WishlistDao();
        }
        return instance;
    }

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
            wishList.setWishlistId(id);
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

    public List<WishList> getWishListsByUserName(Users user) throws SQLException {
        List<WishList> wishLists = new ArrayList<>();
        String selectWishList = "SELECT WishListID,BookId FROM WishList WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectWishList);
            selectStmt.setString(1, user.getUserName());
            results = selectStmt.executeQuery();
            while(results.next()) {
                int rstId = results.getInt("WishListID");
                int rstBookId = results.getInt("BookId");
                Books book = new Books(rstBookId);
                WishList wishList = new WishList(rstId);
                wishList.setBook(book);
                wishList.setUser(user);
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

    public List<WishList> getWishListsByBookId(Books book) throws SQLException {
        List<WishList> wishLists = new ArrayList<>();
        String selectWishList = "SELECT WishListID,UserName FROM WishList WHERE BookId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectWishList);
            selectStmt.setInt(1, book.getBookId());
            results = selectStmt.executeQuery();
            while(results.next()) {
                int rstId = results.getInt("WishListID");
                String rstUserName = results.getString("UserName");
                Users usr = new Users(rstUserName);
                WishList wishList = new WishList(rstId);
                wishList.setUser(usr);
                wishList.setBook(book);
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

    public WishList delete(WishList wishList) throws SQLException {
        String deleteUser = "DELETE FROM WishList WHERE WishListID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteUser);
            deleteStmt.setInt(1, wishList.getWishlistId());
            deleteStmt.executeUpdate();

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
