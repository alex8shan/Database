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

import youbook.dao.*;
import youbook.model.*;

@WebServlet("/reviewupdate")
public class ReviewUpdate extends HttpServlet {

	protected ReviewDao reviewDao;

	@Override
	public void init() throws ServletException {
		reviewDao = ReviewDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		// Just render the JSP.
		req.getRequestDispatcher("/ReviewUpdate.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Retrieve and validate name.
		String reviewId = req.getParameter("reviewId");
		if (reviewId == null || reviewId.trim().isEmpty()) {
			messages.put("success", "Invalid Review Id");
			req.getRequestDispatcher("/ReviewUpdate.jsp").forward(req, resp);
		} else {
			
			try {
				int intReviewId = Integer.parseInt(reviewId);
				Review review = reviewDao.getReviewById(intReviewId);
				if (review == null) {
					messages.put("success", reviewId + " doesn't exist. Please try again.");
					req.getRequestDispatcher("/userUpdate.jsp").forward(req, resp);
					
				} else {
					String rating = req.getParameter("rating");
					String comment = req.getParameter("comment");
					if (rating != null && !rating.trim().isEmpty()) {
						double doubleRating = Double.parseDouble(rating);
						review = reviewDao.updateRating(review, doubleRating);
					}
					if (comment != null && !comment.trim().isEmpty()) {
						review = reviewDao.updateComment(review, comment);
					}
						
					messages.put("success", "Successfully updated " + reviewId);
					req.getRequestDispatcher("/FindBooks.jsp").forward(req, resp);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}

		
	}
}
