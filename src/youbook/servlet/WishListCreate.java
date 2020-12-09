package youbook.servlet;

import youbook.dao.*;
import youbook.model.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        	int bookId = Integer.parseInt(req.getParameter("bookId"));
        	
        	try {
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
        List<WishList> wishlists = new ArrayList<>();
        try {
			wishlists = wishListDao.getWishListsByUserName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        req.setAttribute("wishlists", wishlists);
        req.getRequestDispatcher("/WishListView.jsp").forward(req, resp);
    }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
        String userName = req.getParameter("username");
        
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else {
        	int bookId = Integer.parseInt(req.getParameter("bookId"));
        	
        	try {
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
        List<WishList> wishlists = new ArrayList<>();
        try {
			wishlists = wishListDao.getWishListsByUserName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        req.setAttribute("wishlists", wishlists);
        req.getRequestDispatcher("/WishListView.jsp").forward(req, resp);
	}
}
