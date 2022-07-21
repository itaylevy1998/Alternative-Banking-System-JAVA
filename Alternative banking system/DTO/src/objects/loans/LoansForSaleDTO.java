package objects.loans;

import javafx.scene.control.Button;
import objects.loans.payments.PaymentsDTO;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class LoansForSaleDTO {
    private String loanID;
    private String category;
    private String owner;
    private String seller;
    private double sizeNoInterest;
    private int timeLimitOfLoan;
    private double interestPerPayment;
    private int timePerPayment;
    private Map<String, Double> listOfLenders;
    private int startingActiveTime;
    private int nextPaymentTime;
    private List<PaymentsDTO> Payments;
    private double price;
    private double expectedProfit;

    public LoansForSaleDTO(String loanID, String category, String owner, String seller, double sizeNoInterest, int timeLimitOfLoan, double interestPerPayment, int timePerPayment, Map<String, Double> listOfLenders, int startingActiveTime, int nextPaymentTime, List<PaymentsDTO> payments, double price, double expectedProfit) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.loanID = loanID;
        this.category = category;
        this.owner = owner;
        this.seller = seller;
        this.sizeNoInterest = sizeNoInterest;
        this.timeLimitOfLoan = timeLimitOfLoan;
        this.interestPerPayment = interestPerPayment;
        this.timePerPayment = timePerPayment;
        this.listOfLenders = listOfLenders;
        //this.lendersButton = new Button();
        this.startingActiveTime = startingActiveTime;
        this.nextPaymentTime = nextPaymentTime;
        this.Payments = payments;
       // this.paymentsButton = new Button();
        this.price = Double.parseDouble(df.format(price));
        this.expectedProfit = Double.parseDouble(df.format(expectedProfit));
    }
    //Getters
    public String getLoanID() {return loanID;}
    public String getCategory() {return category;}
    public String getOwner() {return owner;}
    public String getSeller() {return seller;}
    public double getSizeNoInterest() {return sizeNoInterest;}
    public int getTimeLimitOfLoan() {return timeLimitOfLoan;}
    public double getInterestPerPayment() {return interestPerPayment;}
    public int getTimePerPayment() {return timePerPayment;}
    public Map<String, Double> getListOfLenders() {return listOfLenders;}
    //public Button getLendersButton() {return lendersButton;}
    public int getStartingActiveTime() {return startingActiveTime;}
    public int getNextPaymentTime() {return nextPaymentTime;}
    public List<PaymentsDTO> getPayments() {return Payments;}
    public double getPrice() {return price;}
    public double getExpectedProfit() {return expectedProfit;}
}
