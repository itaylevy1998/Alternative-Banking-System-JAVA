package database.client;

import java.io.Serializable;

public class PaymentNotification implements Serializable {

    private String loanID;
    private int paymentYaz;
    private double sumOfPayment;

    public PaymentNotification(String loanID, int paymentYaz, double sumOfPayment) {
        this.loanID = loanID;
        this.paymentYaz = paymentYaz;
        this.sumOfPayment = sumOfPayment;
    }

    public String getLoanID() {return loanID;}
    public int getPaymentYaz() {return paymentYaz;}
    public double getSumOfPayment() {return sumOfPayment;}
}