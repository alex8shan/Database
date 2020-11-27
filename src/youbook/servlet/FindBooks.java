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

@WebServlet("/findbooks")
public class FindBooks extends HttpServlet {
	
	protected BookDao bookDao;
	
	@Override
	public void init() throws ServletException {
		bookDao = BookDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Book> books = new ArrayList<Book>();
        
        // Retrieve and validate name.
        // title is retrieved from the URL query string.
        String title = req.getParameter("title");
        if (title == null || title.trim().isEmpty()) {
            messages.put("success", "Please enter a valid title name.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
        		books = bookDao.getBooksByTitle(title);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + title);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previousTitle", title);
        }
        req.setAttribute("books", books);
        
        req.getRequestDispatcher("/FindBooks.jsp").forward(req, resp);
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
        String title = req.getParameter("firstname");
        if (title == null || title.trim().isEmpty()) {
            messages.put("success", "Please enter a valid title name.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
        		books = bookDao.getBooksByTitle(title);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + title);
        }
        req.setAttribute("books", books);
        
        req.getRequestDispatcher("/FindBooks.jsp").forward(req, resp);
    }
}
