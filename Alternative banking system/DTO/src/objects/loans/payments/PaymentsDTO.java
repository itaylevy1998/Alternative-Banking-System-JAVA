package objects.loans.payments;

import java.text.DecimalFormat;

public class PaymentsDTO {
    private int timeOfPayment;
    private double interestComponent;
    private double sumOfPayment;
    private double initialComponent;
    private String payedSuccesfully;

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

    public String getPayedSuccessfully() {
        return payedSuccesfully;
    }

    public PaymentsDTO(int timeOfPayment, Double interestComponent, double sumOfPayment, double initialComponent, String payedSuccesfully) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.timeOfPayment = timeOfPayment;
        this.interestComponent = Double.parseDouble(df.format(interestComponent));
        this.sumOfPayment = sumOfPayment;
        this.initialComponent = Double.parseDouble(df.format(initialComponent));;
        this.payedSuccesfully = payedSuccesfully;



    }

    public void print(){
        System.out.println("Time of payment: " + timeOfPayment);
        System.out.println("Interest component: " + String.format("%.2f", interestComponent));
        System.out.println("Initial component: " + String.format("%.2f", initialComponent));
        System.out.println("Total sum of payment: " + String.format("%.2f", sumOfPayment));

    }
}
