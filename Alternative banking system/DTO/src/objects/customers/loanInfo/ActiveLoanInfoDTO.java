package objects.customers.loanInfo;

public class ActiveLoanInfoDTO extends LoanInfoDTO{
    private int nextTimePayment;
    private double expectedPayment;

    public ActiveLoanInfoDTO(String loanName, String loanCategory, double sizeNoInterest, double interestPerPayment,
                             int timePerPayment, String status, int nextTimePayment, double expectedPayment) {
        super(loanName, loanCategory, sizeNoInterest, interestPerPayment, timePerPayment, status);
            this.nextTimePayment = nextTimePayment;
            this.expectedPayment = expectedPayment;

    }

    @Override
    public void print() {
        super.print();
        System.out.println("The next time of payment is: " + nextTimePayment + " of time value");
        System.out.println("The expected payment is: " + String.format("%.2f", expectedPayment));
    }
}
