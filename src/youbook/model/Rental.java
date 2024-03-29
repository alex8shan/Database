package youbook.model;

import java.sql.Date;

public class Rental {
	
	protected Book book;
	protected User user;
	protected Date checkOutDate;
	
	public Rental(Book book, User user, Date created) {
		this.book = book;
		this.user = user;
		this.checkOutDate = created;
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