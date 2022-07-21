package objects.loans;

import javafx.scene.control.CheckBox;

public class NewLoanDTO {
    private String loanID;

    private String borrowerName;

    private String loanCategory;
    private double sizeNoInterest;
    private int timeLimitOfLoan;
    private double interestPerPayment;
    private int timePerPayment;
    private String status;

    public String getLoanID() {
        return loanID;
    }
    public double getSizeNoInterest() {
        return sizeNoInterest;
    }
    public String getStatus() {
        return status;
    }
    public String getBorrowerName() {
        return borrowerName;
    }
    public String getLoanCategory() {
        return loanCategory;
    }
    public int getTimeLimitOfLoan() {
        return timeLimitOfLoan;
    }
    public double getInterestPerPayment() {
        return interestPerPayment;
    }
    public int getTimePerPayment() {
        return timePerPayment;
    }
    public void setLoanCategory(String loanCategory) {
        this.loanCategory = loanCategory;
    }

    public NewLoanDTO(String loanID, String borrowerName, String loanCategory, double sizeNoInterest, int timeLimitOfLoan, double interestPerPayment, int timePerPayment, String status) {
        this.loanID = loanID;
        this.borrowerName = borrowerName;
        this.loanCategory = loanCategory;
        this.sizeNoInterest = sizeNoInterest;
        this.timeLimitOfLoan = timeLimitOfLoan;
        this.interestPerPayment = interestPerPayment;
        this.timePerPayment = timePerPayment;
        this.status = status;
    }


    public void print(){
        System.out.println("Loan ID: " + loanID);
        System.out.println("Owner: " + borrowerName);
        System.out.println("Category: " + loanCategory);
        System.out.println("Loan amount: " + String.format("%.2f", sizeNoInterest));
        System.out.println("Time limit: " + timeLimitOfLoan);
        System.out.println("Interest per payment: " + String.format("%.2f", interestPerPayment));
        System.out.println("Time per payment: " + timePerPayment );
        System.out.println("Status: " + status);
    }
}
