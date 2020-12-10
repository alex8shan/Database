package youbook.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import youbook.dao.WishListDao;
import youbook.model.WishList;


@WebServlet("/wishlistdelete")
public class WishListDelete extends HttpServlet {
	
	protected WishListDao wishListDao;
	
	@Override
	public void init() throws ServletException {
		wishListDao = WishListDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		String userName = req.getParameter("username");
		String bookId = req.getParameter("bookId");
		
		if (userName == null || userName.trim().isEmpty()) {
			messages.put("success", "Invalid UserName");
		} else if(bookId == null || bookId.trim().isEmpty()) {
			messages.put("success", "Invalid BookId");
		} else {
			
			try {
				
				WishList wishList = wishListDao.getWishListByUsernameAndBookId(userName,Integer.parseInt(bookId));
				int wishListId = wishList.getWishlistId();
				if(wishList == null) {
					messages.put("success", wishListId +" doesn't exist. Please try again." );
				}
				else {
					wishListDao.delete(wishList);
					messages.put("success", "Delete Successfull ");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			
		}
		//Just render the JSP.
        req.getRequestDispatcher("/wishlistview").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
}
