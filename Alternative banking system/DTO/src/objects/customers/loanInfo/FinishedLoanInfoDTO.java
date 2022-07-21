package objects.customers.loanInfo;

public class FinishedLoanInfoDTO extends LoanInfoDTO {
    private int startingTime;
    private int finishingTime;
    public FinishedLoanInfoDTO(String loanName, String loanCategory, double sizeNoInterest, double interestPerPayment,
                               int timePerPayment, String status, int startingTime, int finishingTime) {
        super(loanName, loanCategory, sizeNoInterest, interestPerPayment, timePerPayment, status);
        this.startingTime = startingTime;
        this.finishingTime = finishingTime;
    }

    @Override
    public void print() {
        super.print();
        System.out.println("The beginning time of the loan was at: " + startingTime + " time value.");
        System.out.println("The completion time of the loan was at: " + finishingTime + " time value.");
    }
}
