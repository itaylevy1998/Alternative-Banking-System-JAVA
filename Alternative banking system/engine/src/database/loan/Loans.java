package database.loan;

import database.Engine;
import database.loan.status.LoanStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Loans implements Serializable, LoansInterface {

    private String borrowerName;
    private final String LOANID;
    private String loanCategory;
    private double loanSize; // includinginterest
    private int timeLimitOfLoan;
    private double InterestPerPayment;
    private int timePerPayment;
    private Map<String, Double> listOflenders;
    private double loanSizeNoInterest;
    private LoanStatus status;
    private double collectedSoFar;
    private double leftToBeCollected;


    public Loans(String borrowerName, String LOANID, String loanCategory, int timeLimitOfLoan, int InterestPerPayment, int timePerPayment, double loanSizeNoInterest) {
        this.borrowerName = borrowerName;
        this.LOANID = LOANID;
        this.loanCategory = loanCategory;
        this.loanSize = loanSizeNoInterest + loanSizeNoInterest*(InterestPerPayment /100);
        this.timeLimitOfLoan = timeLimitOfLoan;
        this.InterestPerPayment = InterestPerPayment;
        this.timePerPayment = timePerPayment;
        this.listOflenders = new HashMap<>();
        this.loanSizeNoInterest = loanSizeNoInterest;
        this.collectedSoFar = 0;
        this.leftToBeCollected = loanSizeNoInterest;
        this.status = new LoanStatus("New", 0,0,0,0,0,loanSizeNoInterest*((double)InterestPerPayment/100),loanSizeNoInterest);

    }

    public double getLeftToBeCollected() {
        return leftToBeCollected;
    }
    public String getLOANID() {
        return LOANID;
    }

    public double getLoanSize() {
        return loanSize + loanSize*(InterestPerPayment/100);
    }

    public int getTimeLimitOfLoan() {
        return timeLimitOfLoan;
    }

    public double getInterestPerPayment() {
        return InterestPerPayment;
    }

    public int getTimePerPayment() {
        return timePerPayment;
    }

    public Map<String, Double> getListOflenders() {
        return listOflenders;
    }

    public double getLoanSizeNoInterest() {
        return loanSizeNoInterest;
    }

    public String getLoanCategory() {
        return loanCategory;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public double getCollectedSoFar() {
        return collectedSoFar;
    }

    public void setCollectedSoFar(double collectedSoFar) {
        this.collectedSoFar += collectedSoFar;
    }

    public void setLeftToBeCollected(double leftToBeCollected) {
        this.leftToBeCollected -= leftToBeCollected;
    }



    @Override
    public void payment() {

    }

    public void updateStatusBeforeActive() {
        switch (status.getStatus()){
            case "New":{
                if(leftToBeCollected != 0 && collectedSoFar != 0){
                    changeToPending();
                }
                if(leftToBeCollected == 0){
                    ActivateLoan();
                }
                break;
            }
            case "Pending":{
                if(leftToBeCollected == 0){
                    ActivateLoan();
                }
                break;
            }
        }
    }

    @Override
    public void changeToPending() {
        status.setStatus("Pending");
    }

    @Override
    public void returnToActive() {
        status.setStatus("Active");
        status.setNextPaymentTime(status.getFuturePaymentYaz() - status.getNextPaymentTime());
    }

    public void ActivateLoan(){
        status.setStatus("Active");
        status.setStartingActiveTime(Engine.getTime());
        status.setNextPaymentTime(Engine.getTime()  + timePerPayment);
        double InitialComponent = loanSizeNoInterest/(timeLimitOfLoan/timePerPayment);
        double InterestComponent = InitialComponent * (double)(InterestPerPayment/100);
        for (int i = 1 ; i <= timeLimitOfLoan / timePerPayment ; i++){
            status.getSupposedPayments().add(new Payment((Engine.getTime() + timePerPayment*i ), InterestComponent,
                    InterestComponent+InitialComponent ,InitialComponent, "Not Payed" ));
        }
    }

    public void changeToRisk(){
        status.setStatus("Risk");
        status.setNextPaymentTime(1);
        //Adding payment
        double sumOfPayment = expectedPaymentAmount();
        double InitialComponent = sumOfPayment * (100/ (100+InterestPerPayment));
        double InterestComponent = sumOfPayment - InitialComponent;
        status.addPayment(new Payment(Engine.getTime(),InterestComponent, sumOfPayment, InitialComponent, "Not Payed" ));
    }

    @Override
    public int compareTo(Loans loan) {
        if(this.getStatus().getStartingActiveTime() < loan.getStatus().getStartingActiveTime())
            return -1;
        else if (this.getStatus().getStartingActiveTime() == loan.getStatus().getStartingActiveTime()) {
                if (this.getStatus().returnLastPayment().getSumOfPayment() < loan.getStatus().returnLastPayment().getSumOfPayment())
                    return -1;
            }

        return 1;
    }
    public void changeToFinish(){
        status.setFinishTime(Engine.getTime());
        status.setStatus("Finished");

    }

    public double expectedPaymentAmount(){
        return status.getSupposedToBePayedSoFar() - (status.getInterestPayed() + status.getInitialPayed());
    }


}
