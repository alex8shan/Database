import java.sql.SQLException;

public class Inserter {
	public static void main(String[] args) throws SQLException {
		//Dao instance
		UsersDao usersDao = UsersDao.getInstance();
		BookDao bookDao = BookDao.getInstance();
		WishlistDao wishlistDao = WishlistDao.getInstance();
		RentalDao rentalDao = RentalDao.getInstance();
		
	}
}
