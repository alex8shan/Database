package youbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import youbook.model.Book;
import youbook.model.Subjects;


public class SubjectsDao {
    private ConnectionManager connectionManager;
    private static SubjectsDao instance = null;
    private SubjectsDao() {
        connectionManager = new ConnectionManager();
    }
    public static SubjectsDao getInstance() {
        if (instance == null) {
            instance = new SubjectsDao();
        }
        return instance;
    }
    
	/*
	 * Save the Subjects instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
    public Subjects create(Subjects subject) throws SQLException {
        String insertSubjects =
            "INSERT INTO Subjects(SubjectMatter,BookId) " +
                "VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertSubjects);
            insertStmt.setString(1, subject.getSubjectMatter());
            insertStmt.setInt(2, subject.getBook().getBookId());
            insertStmt.executeUpdate();
            return subject;
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
     * Gets the subject matters of a book, given the book id.
     */
    public List<Subjects> getSubjectsByBookId(int bookId) throws SQLException {
        List<Subjects> subjects = new ArrayList<>();
        String selectSubjects = "SELECT SubjectMatter FROM Subjects WHERE BookId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectSubjects);
            selectStmt.setInt(1, bookId);
            results = selectStmt.executeQuery();
            BookDao bookDao = BookDao.getInstance();
            while(results.next()) {
                String subjectMatter = results.getString("SubjectMatter");
                Book book = bookDao.getBookById(results.getInt("BookId"));
                Subjects subject = new Subjects(subjectMatter, book);
                subjects.add(subject);
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
        return subjects;
    }
    
    
    public List<Subjects> getSubjectsBySubjectMatter(String subjectMatter) throws SQLException {
        List<Subjects> subjects = new ArrayList<>();
        String selectSubjects = "SELECT BookId FROM Subjects WHERE SubjectMatter=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectSubjects);
            selectStmt.setString(1, subjectMatter);
            results = selectStmt.executeQuery();
            BookDao bookDao = BookDao.getInstance();
            while(results.next()) {
            	Book book = bookDao.getBookById(results.getInt("BookId"));
                Subjects subject = new Subjects(subjectMatter, book);
                subjects.add(subject);
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
        return subjects;
    }
    
	/*
	 * Delete the Subjects instance.
	 * This runs a DELETE statement
	 */
    public Subjects delete(Subjects subject) throws SQLException {
        String deleteSubjects = "DELETE FROM Subjects WHERE SubjectMatter=? AND BookId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteSubjects);
            deleteStmt.setString(1, subject.getSubjectMatter());
            deleteStmt.setInt(2, subject.getBook().getBookId());
            int affectedRows = deleteStmt.executeUpdate();
            if (affectedRows == 0) {
            	throw new SQLException("No records available to delete");
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
