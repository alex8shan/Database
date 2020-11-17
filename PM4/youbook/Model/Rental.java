

import java.util.Date;

public class Rental {
	
	protected Books books;
	protected Users users;
	protected Date checkOutDate;
	
	public Rental(Books books, Users user, Date checkOutDate) {
		this.books = books;
		this.users = user;
		this.checkOutDate = checkOutDate;
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