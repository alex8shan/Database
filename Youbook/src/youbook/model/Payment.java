package youbook.model;

public class Payment {

	protected Integer PaymentId;
	protected Users user;
	protected PaymentType paymentType;
	public enum PaymentType {
		CREDIT_CARD,PAYPAL
	}
	protected Float totalAmount;
	public Payment(Integer paymentId, Users user, PaymentType paymentType, Float totalAmount) {
		super();
		PaymentId = paymentId;
		this.user = user;
		this.paymentType = paymentType;
		this.totalAmount = totalAmount;
	}
	public Payment(Users user, PaymentType paymentType, Float totalAmount) {
		super();
		this.user = user;
		this.paymentType = paymentType;
		this.totalAmount = totalAmount;
	}
	public Integer getPaymentId() {
		return PaymentId;
	}
	public void setPaymentId(Integer paymentId) {
		PaymentId = paymentId;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public Float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
