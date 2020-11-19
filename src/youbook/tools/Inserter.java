package youbook.tools;

import java.sql.SQLException;

import youbook.dao.AuthorDao;
import youbook.dao.UserDao;
import youbook.model.Author;
import youbook.model.User;

public class Inserter {
	public static void main(String[] args) throws SQLException {
		
		// Testing UPDATE operation: Updates an author's name
		AuthorDao authorDao = AuthorDao.getInstance();
		Author author = authorDao.getAuthorById(1); //Sarah Jacoby
		authorDao.updateAuthorName(author, "Sarah Jacob");
		
		// Testing DELETE operation: Deletes user "toby99"
		UserDao userDao = UserDao.getInstance();
		User toby = userDao.getUserByUserName("toby99");
		userDao.delete(toby);
	}
}