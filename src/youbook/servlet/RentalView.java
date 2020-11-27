package youbook.servlet;


import youbook.dao.*;
import youbook.model.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
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


@WebServlet("/rentalview")
public class RentalView extends HttpServlet {
	
	protected RentalDao rentalDao;
	protected UserDao userDao;
	protected BookDao bookDao;
	
	@Override
	public void init() throws ServletException {
		rentalDao = RentalDao.getInstance();
		userDao = UserDao.getInstance();
		bookDao = BookDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        String userName = req.getParameter("username");
        List<Rental> rentals = new ArrayList<Rental>();
        
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else {
        	// Create the BlogUser.
        	// dob must be in the format yyyy-mm-dd.
        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  	
        	try {
        		long millis=System.currentTimeMillis();
        		Date date = new Date(millis);
        		
        		rentals = rentalDao.getRentalsByUserName(userName);
        		System.out.println(rentals.get(0).getBook().getTitle());
        		messages.put("success", "Successfully created rental for " + userName);
        	} catch (SQLException e) {
        		e.printStackTrace();
				throw new IOException(e);
        	}
	        messages.put("success", "Successfully created " + userName);
        }
        //Just render the JSP. 
        req.setAttribute("rentals", rentals);
        req.getRequestDispatcher("/RentalView.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		System.out.println("reached post");
    }
}


