package objects.customers.loanInfo;

import objects.loans.NewLoanDTO;
import objects.loans.PendingLoanDTO;
import sun.rmi.runtime.NewThreadAction;

import java.util.List;

public class CustomerFilterLoansDTO {
    List<NewLoanDTO> newLoans;
    List<PendingLoanDTO> pendingLoans;


    public CustomerFilterLoansDTO(List<NewLoanDTO> newLoans, List<PendingLoanDTO> pendingLoans) {
        this.newLoans = newLoans;
        this.pendingLoans = pendingLoans;
    }

    public List<NewLoanDTO> getNewLoans() {return newLoans;}
    public List<PendingLoanDTO> getPendingLoans() {return pendingLoans;}
}
