package exceptions.filesexepctions;

public class TimeOfPaymentNotDivideEqualyException extends Exception implements ExcepctionInterface{
    private int totalTimeToPay;
    private int timeBetweenPayment;

    public TimeOfPaymentNotDivideEqualyException(int totalTimeToPay, int timeBetweenPayment) {
        this.totalTimeToPay = totalTimeToPay;
        this.timeBetweenPayment = timeBetweenPayment;
    }

    @Override
    public void printMessage() {
        System.out.println("The customer has "+ totalTimeToPay + " time period to pay, which isn't possible if he pays every " + timeBetweenPayment + " time period ");
    }

    @Override
    public String toString() {
        return "The customer has "+ totalTimeToPay + " time period to pay, which isn't possible if he pays every " + timeBetweenPayment + " time period ";
    }
}
