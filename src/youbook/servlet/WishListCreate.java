package youbook.servlet;

import youbook.dao.*;
import youbook.model.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/wishlistcreate")
public class WishListCreate extends HttpServlet {
	
	protected WishListDao wishListDao;
	protected UserDao userDao;
	protected BookDao bookDao;
	
	@Override
	public void init() throws ServletException {
		userDao = UserDao.getInstance();
		bookDao = BookDao.getInstance();
		wishListDao = WishListDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
        String userName = req.getParameter("username");
        
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else {
        	// Create the BlogUser.
        	int bookId = Integer.parseInt(req.getParameter("bookId"));
        	
        	try {
        		long millis=System.currentTimeMillis();
        		Date date = new Date(millis);
        		User user = userDao.getUserByUserName(userName);
        		Book book = bookDao.getBookById(bookId);
				WishList wishList = new WishList(book, user);
				wishList = wishListDao.create(wishList);
        		messages.put("success", "Successfully created rental for " + userName);
        	} catch (SQLException e) {
        		e.printStackTrace();
				throw new IOException(e);
        	}
	        messages.put("success", "Successfully created " + userName);
        }
		//Just render the JSP.
        req.getRequestDispatcher("/wishListView.jsp").forward(req, resp);
    }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		
		// Retrieve and validate name.
		String userName = req.getParameter("username");
		String bookId = req.getParameter("bookId");
		
		if (userName == null || userName.trim().isEmpty()) {
			messages.put("success", "Invalid UserName");
		} else if(bookId == null || bookId.trim().isEmpty()) {
			messages.put("success", "Invalid BookId");
		} else {
			
			// dob must be in the format yyyy-mm-dd.
			try {
				// Exercise: parse the input for StatusLevel.
				User user = userDao.getUserByUserName(userName);
				int intBookId = Integer.parseInt(bookId);
				Book book = bookDao.getBookById(intBookId);
				WishList wishList = new WishList(book, user);
				wishListDao.create(wishList);
				messages.put("success", "Successfully created ");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}
		
		req.getRequestDispatcher("/WishListCreate.jsp").forward(req, resp);
	}
}
