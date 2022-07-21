package objects.loans;

import javafx.scene.control.Button;
import objects.loans.payments.PaymentsDTO;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class ActiveRiskLoanDTO extends PendingLoanDTO{
    private int startingActiveTime;
    private int nextPaymentTime;
    private List<PaymentsDTO> Payments;
    private double loanSize;
    private double allInterestPayedSoFar;
    private double allInitialPayedSoFar;
    private double allInterestLeftToPay;
    private double allInitialLeftToPay;

    public int getStartingActiveTime() {
        return startingActiveTime;
    }
    public int getNextPaymentTime() {
        return nextPaymentTime;
    }
    public double getLoanSize() {
        return loanSize;
    }
    public double getAllInterestPayedSoFar() {
        return allInterestPayedSoFar;
    }
    public double getAllInitialPayedSoFar() {
        return allInitialPayedSoFar;
    }
    public double getAllInterestLeftToPay() {
        return allInterestLeftToPay;
    }
    public double getAllInitialLeftToPay() {
        return allInitialLeftToPay;
    }
    public List<PaymentsDTO> getPayments() {
        return Payments;
    }



    public ActiveRiskLoanDTO(String loanID, String borrowerName, String loanCategory, double sizeNoInterest, int timeLimitOfLoan, double interestPerPayment, int timePerPayment, String status, Map<String, Double> listOfLenders, Double collectedSoFar, Double sumLeftToBeCollected, int startingActiveTime, int nextPaymentTime, List<PaymentsDTO> payments, double allInterestPayedSoFar, double allInitialPayedSoFar, double allInterestLeftToPay, double allInitialLeftToPay) {
        super(loanID, borrowerName, loanCategory, sizeNoInterest, timeLimitOfLoan, interestPerPayment, timePerPayment, status, listOfLenders, collectedSoFar, sumLeftToBeCollected);
        DecimalFormat df = new DecimalFormat("#.##");
        this.startingActiveTime = startingActiveTime;
        this.nextPaymentTime = nextPaymentTime;
        this.Payments = payments;
        this.allInterestPayedSoFar = Double.parseDouble(df.format(allInterestPayedSoFar));
        this.allInitialPayedSoFar = Double.parseDouble(df.format(allInitialPayedSoFar));;
        this.allInterestLeftToPay = Double.parseDouble(df.format(allInterestLeftToPay));
        this.allInitialLeftToPay = Double.parseDouble(df.format(allInitialLeftToPay));
        this.loanSize = sizeNoInterest + sizeNoInterest*(interestPerPayment)/100;
    }



    @Override
    public void print(){
        super.print();
        System.out.println("Total payment including interest: " + String.format("%.2f", loanSize));
        System.out.println("Activation time: " + startingActiveTime);
        System.out.println("Next payment date: " + nextPaymentTime);
        System.out.println("Payments: ");
        for (PaymentsDTO payment : Payments) {
            payment.print();
        }
        System.out.println("Initial sum payed so far: " + String.format("%.2f", allInitialPayedSoFar));
        System.out.println("Interest sum payed so far: " + String.format("%.2f", allInterestPayedSoFar));
        System.out.println("Initial sum yet to be payed: " + String.format("%.2f", allInitialLeftToPay));
        System.out.println("Interest sum yet to be payed: " + String.format("%.2f", allInterestLeftToPay));
    }
}
