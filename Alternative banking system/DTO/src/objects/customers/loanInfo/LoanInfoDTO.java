package objects.customers.loanInfo;

public class LoanInfoDTO implements LoanInfoDTOInterface{

    private double loanSize;
    private String loanName;
    private String loanCategory;
    private double sizeNoInterest;
    private double interestPerPayment;
    private int timePerPayment;
    private String status;

    public double getLoanSize() {
        return loanSize;
    }

    public String getLoanName() {
        return loanName;
    }

    public String getLoanCategory() {
        return loanCategory;
    }

    public double getInterestPerPayment() {
        return interestPerPayment;
    }

    public int getTimePerPayment() {
        return timePerPayment;
    }

    public LoanInfoDTO(String loanName, String loanCategory, double sizeNoInterest, double interestPerPayment, int timePerPayment, String status) {
        this.loanSize = sizeNoInterest + sizeNoInterest*interestPerPayment/100;
        this.loanName = loanName;
        this.loanCategory = loanCategory;
        this.sizeNoInterest = sizeNoInterest;
        this.interestPerPayment = interestPerPayment;
        this.timePerPayment = timePerPayment;
        this.status = status;
    }

    public double getSizeNoInterest() {
        return sizeNoInterest;
    }

    public String getStatus() {
        return status;
    }

    public void print(){
        System.out.println("\r\nLoan name: " + loanName);
        System.out.println("Loan amount without interest: " + String.format("%.2f",sizeNoInterest));
        System.out.println("Loan amount with interest: " + String.format("%.2f",loanSize));
        System.out.println("Category: " + loanCategory);
        System.out.println("Interest per payment: " + String.format("%.2f",interestPerPayment));
        System.out.println("Time per payment: " + timePerPayment );
        System.out.println("Status: " + status);
    }

}
