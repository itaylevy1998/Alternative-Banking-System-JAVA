package objects.loans;

import javafx.scene.control.Button;
import objects.loans.payments.PaymentsDTO;

import java.util.List;
import java.util.Map;

public class FinishedLoanDTO extends PendingLoanDTO{
    private int startingTime;
    private List<PaymentsDTO> payments;
    private int finishingTime;

    public FinishedLoanDTO(String loanID, String borrowerName, String loanCategory, double sizeNoInterest, int timeLimitOfLoan, double interestPerPayment, int timePerPayment, String status, Map<String, Double> listOfLenders, Double collectedSoFar, Double sumLeftToBeCollected, int startingTime, List<PaymentsDTO> payments, int finishingTime) {
        super(loanID, borrowerName, loanCategory, sizeNoInterest, timeLimitOfLoan, interestPerPayment, timePerPayment, status, listOfLenders, collectedSoFar, sumLeftToBeCollected);
        this.startingTime = startingTime;
        this.payments = payments;
        this.finishingTime = finishingTime;
    }

    public int getStartingTime() {
        return startingTime;
    }
    public List<PaymentsDTO> getPayments() {
        return payments;
    }
    public int getFinishingTime() {
        return finishingTime;
    }

    public void print(){
        super.print();
        System.out.println("Activation time: " + startingTime);
        System.out.println("Finishing time: " + finishingTime);
        System.out.println("Payments: ");
        for(PaymentsDTO payment : payments){
            payment.print();
        }

    }


}
