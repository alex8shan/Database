package youbook.model;

import java.util.Date;

public class CreditCard {
	
	protected long cardNumber;
	protected Date expiration;
	protected User user;
	
	// This constructor can be used for reading records from MySQL, where we have all fields
	// including the cardNumber
	public CreditCard(long cardNumber, Date expiration, User user) {
		this.cardNumber = cardNumber;
		this.expiration = expiration;
		this.user = user;
	}
	
	// This constructor can be used for reading records from MySQL, where we only have 
	// the cardNumber, such as a foreign key reference to cardNumber.
	// Given cardNumber, we can fetch the full CreditCard record.

	public long getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpiration() {
		return this.expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
