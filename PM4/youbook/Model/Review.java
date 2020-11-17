

public class Review {
	
	protected int reviewId;
	protected Users users;
	protected Books books;
	protected double rating;
	protected String comment;
	

	public Review(int reviewId, Users users, Books books, double rating, String comment) {
		this.reviewId = reviewId;
		this.users = users;
		this.books = books;
		this.rating = rating;
		this.comment = comment;
	}
	
	public Review(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getReviewId() {
		return this.reviewId;
	}
	
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	
	public Books getBook() {
		return this.books;
	}
	
	public void setBook(Books books) {
		this.books = books;
	}
	
	public Users getUser() {
		return this.users;
	}
	public void setUser(Users users) {
		this.users = users;
	}
	public double getRating() {
		return this.rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
