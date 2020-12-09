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
        List<Book> books = new ArrayList<Book>();
        //Just render the JSP.   
        try {
    		books = bookDao.getTopBooksByTitle(10);
    		System.out.print(books);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
    	messages.put("success", "Displaying results for " + 10);
        req.setAttribute("books", books);

        req.getRequestDispatcher("/showTopBooks.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        List<Book> books = new ArrayList<Book>();
        //Just render the JSP.   
        try {
    		books = bookDao.getTopBooksByTitle(10);
    		System.out.print(books);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
    	messages.put("success", "Displaying results for " + 10);
        req.setAttribute("books", books);

        req.getRequestDispatcher("/showTopBooks.jsp").forward(req, resp);
    }
}
