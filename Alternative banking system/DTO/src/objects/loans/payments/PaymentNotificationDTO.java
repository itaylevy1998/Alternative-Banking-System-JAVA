package objects.loans.payments;

public class PaymentNotificationDTO {
    //Class Fields
    private String loanID;
    private int paymentYaz;
    private double sumOfPayment;

    //Constructor
    public PaymentNotificationDTO(String loanID, int paymentYaz, double sumOfPayment) {
        this.loanID = loanID;
        this.paymentYaz = paymentYaz;
        this.sumOfPayment = sumOfPayment;
    }

    //Getters
    public String getLoanID() {return loanID;}
    public int getPaymentYaz() {return paymentYaz;}
    public double getSumOfPayment() {return sumOfPayment;}
}
