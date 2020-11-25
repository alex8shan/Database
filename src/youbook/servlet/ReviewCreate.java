package youbook.servlet;

import youbook.dao.*;
import youbook.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/reviewcreate")
public class ReviewCreate extends HttpServlet {
	
	protected ReviewDao reviewDao;
	protected UserDao userDao;
	protected BookDao bookDao;
	
	@Override
	public void init() throws ServletException {
		userDao = UserDao.getInstance();
		bookDao = BookDao.getInstance();
		reviewDao = ReviewDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/ReviewCreate.jsp").forward(req, resp);
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
        	// Create the User.
        	String rating = req.getParameter("rating");
        	String comment = req.getParameter("comment");
        	
        	// dob must be in the format yyyy-mm-dd.
	        try {
	        	// Exercise: parse the input for StatusLevel.
	        	User user = userDao.getUserByUserName(userName);
	        	int intBookId = Integer.parseInt(bookId);
	        	Book book = bookDao.getBookById(intBookId);
	        	double doubleRating = Double.parseDouble(rating);
	        	Review review = new Review(user, book, doubleRating, comment);
	        	review = reviewDao.create(review);
	        	messages.put("success", "Successfully created ");
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/ReviewCreate.jsp").forward(req, resp);
    }
}
