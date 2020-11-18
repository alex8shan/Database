package youbook.model;

public class Review {
	
	protected int reviewId;
	protected Users users;
	protected Book book;
	protected double rating;
	protected String comment;
	

	public Review(int reviewId, Users users, Book book, double rating, String comment) {
		this.reviewId = reviewId;
		this.users = users;
		this.book = book;
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
	
	public Book getBook() {
		return this.book;
	}
	
	public void setBook(Book book) {
		this.book = book;
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
