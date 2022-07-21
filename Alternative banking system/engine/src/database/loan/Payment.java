package database.loan;

import database.Engine;

import java.io.Serializable;

public class Payment implements Serializable {
    private int timeOfPayment;
    private double interestComponent;
    private double sumOfPayment;
    private double initialComponent;
    private String payedSuccesfully;


    public Payment(int timeOfPayment, double interestComponent, double sumOfPayment, double initialComponent, String payedSuccesfully) {
        this.timeOfPayment = timeOfPayment;
        this.interestComponent = interestComponent;
        this.sumOfPayment = sumOfPayment;
        this.initialComponent = initialComponent;
        this.payedSuccesfully = payedSuccesfully;
    }

    public int getTimeOfPayment() {
        return timeOfPayment;
    }

    public double getInterestComponent() {
        return interestComponent;
    }

    public double getSumOfPayment() {
        return sumOfPayment;
    }

    public double getInitialComponent() {
        return initialComponent;
    }

    public boolean isPayedSuccesfully() {
        return payedSuccesfully.equals("Payed");
    }

    public String getPayedStatus(){return payedSuccesfully;}

    public void setPayedSuccesfully(String payedSuccesfully) {
        this.payedSuccesfully = payedSuccesfully;
    }

    public void print(){}
}
