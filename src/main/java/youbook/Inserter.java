package youbook;

import youbook.Model.Book;
import youbook.dal.BookDao;
import youbook.dal.RentalDao;
import youbook.dal.UsersDao;
import youbook.dal.WishlistDao;

import java.sql.SQLException;
import java.util.List;

public class Inserter {
	public static void main(String[] args) throws SQLException {
		//Dao instance
		UsersDao usersDao = UsersDao.getInstance();
		BookDao bookDao = BookDao.getInstance();
		WishlistDao wishlistDao = WishlistDao.getInstance();
		RentalDao rentalDao = RentalDao.getInstance();
		
		// INSERT objects from our model.
		//Testing getTopBooksByTitle(Main page) and getBooksByTitle(Specific title)
		List<Book> topBooks = bookDao.getTopBooksByTitle(10);
		System.out.println("Top 10 books: \n");
		for(Book b : topBooks)
			System.out.println("????" + b);
		
		List<Book> books = bookDao.getBooksByTitle("Forever or a Day");
		System.out.println("\n Book name: Forever or a Day \n");
		for(Book b : books)
			System.out.println("????" + b);
		
		
	}
}
