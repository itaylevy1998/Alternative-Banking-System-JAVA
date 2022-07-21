package objects.customers.loanInfo;

public class RiskLoanInfoDTO extends LoanInfoDTO{
    private int numberOfPaymentNotPayed ;
    private double sumOfNotPayed;
    public RiskLoanInfoDTO(String loanName, String loanCategory, double sizeNoInterest, double interestPerPayment,
                           int timePerPayment, String status, int numberOfPaymentNotPayed, double sumOfNotPayed) {
        super(loanName, loanCategory, sizeNoInterest, interestPerPayment, timePerPayment, status);
        this.sumOfNotPayed = sumOfNotPayed;
        this.numberOfPaymentNotPayed = numberOfPaymentNotPayed;
    }

    @Override
    public void print() {
        super.print();
        System.out.println("The number of payments yet To be payed: " + numberOfPaymentNotPayed);
        System.out.println("Their total amount: " + String.format("%.2f", sumOfNotPayed));
    }
}
