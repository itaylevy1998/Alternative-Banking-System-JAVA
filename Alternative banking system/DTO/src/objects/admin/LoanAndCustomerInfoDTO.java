package objects.admin;

import objects.customers.CustomerInfoDTO;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.FinishedLoanDTO;
import objects.loans.NewLoanDTO;
import objects.loans.PendingLoanDTO;

import java.util.List;

public class LoanAndCustomerInfoDTO {
    List<CustomerInfoDTO> customerList;
    List<NewLoanDTO> newLoans;
    List<PendingLoanDTO> pendingLoans;
    List<ActiveRiskLoanDTO> activeLoans;
    List<ActiveRiskLoanDTO> riskLoans;
    List<FinishedLoanDTO> finishedLoans;

    public LoanAndCustomerInfoDTO(List<CustomerInfoDTO> customerList, List<NewLoanDTO> newLoans, List<PendingLoanDTO> pendingLoans, List<ActiveRiskLoanDTO> activeLoans, List<ActiveRiskLoanDTO> riskLoans, List<FinishedLoanDTO> finishedLoans){
        this.customerList = customerList;
        this.newLoans = newLoans;
        this.pendingLoans = pendingLoans;
        this.activeLoans = activeLoans;
        this.riskLoans = riskLoans;
        this.finishedLoans = finishedLoans;
    }

    public List<NewLoanDTO> getNewLoans() {return newLoans;}
    public List<PendingLoanDTO> getPendingLoans() {return pendingLoans;}
    public List<ActiveRiskLoanDTO> getActiveLoans() {return activeLoans;}
    public List<ActiveRiskLoanDTO> getRiskLoans() {return riskLoans;}
    public List<FinishedLoanDTO> getFinishedLoans() {return finishedLoans;}
    public List<CustomerInfoDTO> getCustomerList() {
        return customerList;
    }
}
