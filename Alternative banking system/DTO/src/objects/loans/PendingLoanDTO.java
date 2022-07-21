package objects.loans;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.util.Map;

public class PendingLoanDTO extends NewLoanDTO{
    private Map<String, Double>  listOfLenders;
    private double collectedSoFar;
    private double sumLeftToBeCollected;

    public double getSumLeftToBeCollected() {
        return sumLeftToBeCollected;
    }



    public PendingLoanDTO(String loanID, String borrowerName, String loanCategory, double sizeNoInterest, int timeLimitOfLoan, double interestPerPayment, int timePerPayment, String status, Map<String, Double> listOfLenders, Double collectedSoFar, Double sumLeftToBeCollected) {
        super(loanID, borrowerName, loanCategory, sizeNoInterest, timeLimitOfLoan, interestPerPayment, timePerPayment, status);
        this.listOfLenders = listOfLenders;
        this.collectedSoFar = collectedSoFar;
        this.sumLeftToBeCollected = sumLeftToBeCollected;
    }
    @Override
    public void print(){
        super.print();
        System.out.println("Lenders: ");
        System.out.println(listOfLenders);
        System.out.println("Sum collected so far: " + String.format("%.2f", collectedSoFar));
        System.out.println("Sum left to be collected: " + String.format("%.2f", sumLeftToBeCollected));
    }

    public Map<String, Double> getListOfLenders() {
        return listOfLenders;
    }

    public double getCollectedSoFar() {
        return collectedSoFar;
    }
    
}
