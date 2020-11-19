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

@WebServlet("/findtopbooks")
public class showTopBooks extends HttpServlet {
	
	protected BookDao bookDao;
	
	@Override
	public void init() throws ServletException {
		bookDao = BookDao.getInstance();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/showTopBooks.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Book> books = new ArrayList<Book>();
        
        // Retrieve and validate name.
        // title is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindUsers.jsp).
        String num = req.getParameter("num");
        if (num == null || num.trim().isEmpty()) {
            messages.put("success", "Please enter a valid number.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	Integer number = Integer.parseInt(num);
        	try {
        		books = bookDao.getTopBooksByTitle(number);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + num);
        }
        req.setAttribute("books", books);
        
        req.getRequestDispatcher("/showTopBooks.jsp").forward(req, resp);
    }
}
