package objects.customers;

import objects.admin.LoanAndCustomerInfoDTO;
import objects.loans.*;
import objects.loans.payments.PaymentNotificationDTO;

import java.util.List;

public class CustomersRelatedInfoDTO {
    String serverStatus;
    String currentYaz;
    List<NewLoanDTO> newLoans;
    List<PendingLoanDTO> pendingLoans;
    List<ActiveRiskLoanDTO> activeLoans;
    List<ActiveRiskLoanDTO> riskLoans;
    List<FinishedLoanDTO> finishedLoans;
    CustomerInfoDTO customerInfo;


    public CustomersRelatedInfoDTO(List<NewLoanDTO> newLoans, List<PendingLoanDTO> pendingLoans, List<ActiveRiskLoanDTO> activeLoans, List<ActiveRiskLoanDTO> riskLoans, List<FinishedLoanDTO> finishedLoans, CustomerInfoDTO customerInfo, String serverStatus, String currentYaz) {
        this.newLoans = newLoans;
        this.pendingLoans = pendingLoans;
        this.activeLoans = activeLoans;
        this.riskLoans = riskLoans;
        this.finishedLoans = finishedLoans;
        this.customerInfo = customerInfo;
        this.serverStatus = serverStatus;
        this.currentYaz = currentYaz;
    }

    //Getters
    public String getServerStatus() {return serverStatus;}
    public String getCurrentYaz() {return currentYaz;}
    public List<NewLoanDTO> getNewLoans() {return newLoans;}
    public List<PendingLoanDTO> getPendingLoans() {return pendingLoans;}
    public List<ActiveRiskLoanDTO> getActiveLoans() {return activeLoans;}
    public List<ActiveRiskLoanDTO> getRiskLoans() {return riskLoans;}
    public List<FinishedLoanDTO> getFinishedLoans() {return finishedLoans;}
    public CustomerInfoDTO getCustomerInfo() {
        return customerInfo;
    }

}
