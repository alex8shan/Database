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

import youbook.dal.UsersDao;
import youbook.model.Users;


@WebServlet("/login")
public class Login extends HttpServlet {
	
	protected UsersDao usersDao;
	
	@Override
	public void init() throws ServletException {
		usersDao = UsersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/Login.jsp").forward(req, resp);
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
            messages.put("success", "UserName cannot be empty");
            req.getRequestDispatcher("/Login.jsp").forward(req, resp);
        } else {
        	
	        try {
	        	
	        	Users user = usersDao.getUserByUserName(userName);
	        	if(user==null) {
	        		messages.put("success", userName+" doesn't exist. Please try again or create a new user." );
	        		req.getRequestDispatcher("/Login.jsp").forward(req, resp);
	        	}
	        	else {
	        	messages.put("success", "Login Successfull " + userName);
	        	req.getRequestDispatcher("/FindBooks.jsp").forward(req, resp);}
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        
    }
}
