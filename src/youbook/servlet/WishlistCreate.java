
package youbook.servlet;
		
		import youbook.dao.*;
		import youbook.model.*;
		
		import java.io.IOException;
		import java.sql.SQLException;
		
		import java.util.ArrayList;
		import java.util.HashMap;
		import java.util.List;
		import java.util.Map;
		
		import javax.servlet.annotation.*;
		import javax.servlet.ServletException;
		import javax.servlet.http.HttpServlet;
		import javax.servlet.http.HttpServletRequest;
		import javax.servlet.http.HttpServletResponse;


@WebServlet("/wishlistcreate")
public class WishlistCreate extends HttpServlet {
	
	protected UserDao userDao;
	
	@Override
	public void init() throws ServletException {
		userDao = UserDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		List<WishList> wishlists = new ArrayList<>();
		
		req.setAttribute("wishlists", wishlists);
		
		//Just render the JSP.
		req.getRequestDispatcher("/WishlistCreate.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		// Retrieve and validate name.
		String userName = req.getParameter("username");
		List<WishList> wishlists = new ArrayList<WishList>();
		
		if (userName == null || userName.trim().isEmpty()) {
			messages.put("success", "Invalid UserName");
		} else {
			// Create the User.
			int bookId = Integer.parseInt(req.getParameter("bookid"));
			// dob must be in the format yyyy-mm-dd.
			try {
				// Exercise: parse the input for StatusLevel.
				Book book = BookDao.getInstance().getBookById(bookId);
				User user = UserDao.getInstance().getUserByUserName(userName);
				WishList wishList= new WishList(book, user);
				wishList = WishListDao.getInstance().create(wishList);
				messages.put("success", "Successfully created wishlist with user " +userName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			
			try {
				wishlists = WishListDao.getInstance().getWishListsByUserName(userName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}
		req.setAttribute("wishlists", wishlists);
		
		req.getRequestDispatcher("/WishlistCreate.jsp").forward(req, resp);
	}
}
