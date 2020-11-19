package youbook.servlet;

import youbook.dao.*;
import youbook.model.*;
import youbook.model.User.StatusLevels;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/usercreate")
public class UserCreate extends HttpServlet {
	
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
        //Just render the JSP.   
        req.getRequestDispatcher("/UserCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String userName = req.getParameter("username");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else {
        	// Create the User.
        	String firstName = req.getParameter("firstname");
        	String lastName = req.getParameter("lastname");
        	String emailID = req.getParameter("emailid");
        	String phoneNumber = req.getParameter("phonenumber");
        	String paypalID = req.getParameter("paypalid");
        	// dob must be in the format yyyy-mm-dd.
	        try {
	        	// Exercise: parse the input for StatusLevel.
	        	User user = new User(userName, firstName, lastName, 
	        			emailID, phoneNumber, paypalID, StatusLevels.NOVICE);
	        	user = userDao.create(user);
	        	messages.put("success", "Successfully created " + userName);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UserCreate.jsp").forward(req, resp);
    }
}
