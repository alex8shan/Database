package youbook.model;

import java.util.Date;

public class Rental {
	
	protected Book book;
	protected User user;
	protected Date checkOutDate;
	
	public Rental(Book book, User user, Date checkOutDate) {
		this.book = book;
		this.user = user;
		this.checkOutDate = checkOutDate;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCheckOutDate() {
		return this.checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
}