package youbook.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import youbook.dao.BookDao;
import youbook.dao.WishListDao;
import youbook.dao.UserDao;
import youbook.model.Rental;
import youbook.model.WishList;

@WebServlet("/wishlistview")
public class WishlistView extends HttpServlet{
	protected WishListDao wishlistDao;
	protected UserDao userDao;
	protected BookDao bookDao;
	
	@Override
	public void init() throws ServletException {
		wishlistDao = WishListDao.getInstance();
		userDao = UserDao.getInstance();
		bookDao = BookDao.getInstance();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        String userName = req.getParameter("username");
        List<WishList> wishlist = new ArrayList<WishList>();
        
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else { 	
        	try {
        		wishlist = wishlistDao.getWishListsByUserName(userName);
        		System.out.println(wishlist.get(0).getBook().getTitle());
        		messages.put("success", "Successfully created rental for " + userName);
        	} catch (SQLException e) {
        		e.printStackTrace();
				throw new IOException(e);
        	}
	        messages.put("success", "Successfully created " + userName);
        }
        //Just render the JSP. 
        req.setAttribute("wishlists", wishlist);
        req.getRequestDispatcher("/WishListView.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		System.out.println("reached post");
    }
}
