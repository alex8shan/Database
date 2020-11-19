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

import youbook.dao.UserDao;
import youbook.model.User;

@WebServlet("/userupdate")
public class UserUpdate extends HttpServlet {

	protected UserDao userDao;

	@Override
	public void init() throws ServletException {
		userDao = UserDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		// Just render the JSP.
		req.getRequestDispatcher("/userUpdate.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Retrieve and validate name.
		String userName = req.getParameter("username");
		if (userName == null || userName.trim().isEmpty()) {
			messages.put("success", "Invalid UserName");
			req.getRequestDispatcher("/userUpdate.jsp").forward(req, resp);
		} else {
			
			try {

				User user = userDao.getUserByUserName(userName);
				if (user == null) {
					messages.put("success", userName + " doesn't exist. Please try again.");
					req.getRequestDispatcher("/userUpdate.jsp").forward(req, resp);
					
				} else {
					String lastName = req.getParameter("lastname");
					user = userDao.updateLastName(user, lastName);
					messages.put("success", "Successfully updated " + userName);
					req.getRequestDispatcher("/FindBooks.jsp").forward(req, resp);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}

		
	}
}
