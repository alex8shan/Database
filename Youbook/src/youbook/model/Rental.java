package youbook.model;

import java.util.Date;

public class Rental {
	
	protected Book book;
	protected Users users;
	protected Date checkOutDate;
	
	public Rental(Book book, Users user, Date checkOutDate) {
		this.book = book;
		this.users = user;
		this.checkOutDate = checkOutDate;
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

	public void setUser(Users user) {
		this.users = user;
	}

	public Date getCheckOutDate() {
		return this.checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
}