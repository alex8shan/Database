import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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

    public Subjects create(Subjects subjects) throws SQLException {
        String insertSubjects =
            "INSERT INTO Subjects(SubjectMatter,BookId) " +
                "VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertSubjects);
            insertStmt.setString(1, subjects.getSubjectMatter());
            insertStmt.setInt(2, subjects.getBook().getBookId());
            insertStmt.executeUpdate();
            return subjects;
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

    public List<Subjects> getSubjectByBook(Books book) throws SQLException {
        List<Subjects> subjects = new ArrayList<>();
        String selectWishList = "SELECT SubjectMatter FROM Subjects WHERE BookId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectWishList);
            selectStmt.setInt(1, book.getBookId());
            results = selectStmt.executeQuery();
            while(results.next()) {
                String subjectMatter = results.getString("SubjectMatter");
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

    public List<Subjects> getSubjectBySubjectMatter(String subjectMatter) throws SQLException {
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
            while(results.next()) {
                int bookId = results.getInt("BookId");
                Books book = new Books(bookId);
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

    public Subjects delete(Subjects subjects) throws SQLException {
        String deleteUser = "DELETE FROM Subjects WHERE SubjectMatter=? AND BookId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteUser);
            deleteStmt.setString(1, subjects.getSubjectMatter());
            deleteStmt.setInt(2, subjects.getBook().getBookId());
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
