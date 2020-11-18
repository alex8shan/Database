package youbook.model;

import java.util.Date;

public class CreditCard {
	
	protected Long cardNumber;
	protected Date expiration;
	protected Users user;
	
	public CreditCard(Long cardNumber, Date expiration, Users user) {
		super();
		this.cardNumber = cardNumber;
		this.expiration = expiration;
		this.user = user;
	}

	public Long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	
}
