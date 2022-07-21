package objects.customers;


import objects.customers.loanInfo.LoanInfoDTO;
import objects.loans.payments.PaymentNotificationDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerInfoDTO {

   private List<AccountTransactionDTO> transactionDTOS;
   private String name;
   private double balance;
   private Integer newBorrower;
   private Integer pendingBorrower;
   private Integer activeBorrower;
   private Integer riskBorrower;
   private Integer finishedBorrower;
   private Integer pendingLender;
   private Integer activeLender;
   private Integer riskLender;
   private Integer finishedLender;

    //getters
    public double getBalance() {return balance;}
    public List<AccountTransactionDTO> getTransactionDTOS() {return transactionDTOS;}
    public String getName() {return name;}
    public Integer getNewBorrower() {return newBorrower;}
    public Integer getPendingBorrower() {return pendingBorrower;}
    public Integer getActiveBorrower() {return activeBorrower;}
    public Integer getRiskBorrower() {return riskBorrower;}
    public Integer getFinishedBorrower() {return finishedBorrower;}
    public Integer getPendingLender() {return pendingLender;}
    public Integer getActiveLender() {return activeLender;}
    public Integer getRiskLender() {return riskLender;}
    public Integer getFinishedLender() {return finishedLender;}

    //setters
    public void setNewBorrower(Integer newBorrower) {this.newBorrower = newBorrower;}
    public void setPendingBorrower(Integer pendingBorrower) {this.pendingBorrower = pendingBorrower;}
    public void setActiveBorrower(Integer activeBorrower) {this.activeBorrower = activeBorrower;}
    public void setRiskBorrower(Integer riskBorrower) {this.riskBorrower = riskBorrower;}
    public void setFinishedBorrower(Integer finishedBorrower) {this.finishedBorrower = finishedBorrower;}
    public void setPendingLender(Integer pendingLender) {this.pendingLender = pendingLender;}
    public void setActiveLender(Integer activeLender) {this.activeLender = activeLender;}
    public void setRiskLender(Integer riskLender) {this.riskLender = riskLender;}
    public void setFinishedLender(Integer finishedLender) {this.finishedLender = finishedLender;}


    public CustomerInfoDTO(String name, double balance) {
        this.transactionDTOS = new ArrayList<>();
        this.name = name;
        this.balance = balance;
    }


}
