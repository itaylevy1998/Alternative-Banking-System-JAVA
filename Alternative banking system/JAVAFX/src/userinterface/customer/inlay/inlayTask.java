package userinterface.customer.inlay;

import javafx.concurrent.Task;
import objects.customers.loanInfo.LoanInfoDTO;
import objects.loans.NewLoanDTO;

import java.util.List;

public class inlayTask extends Task<List<NewLoanDTO>> {
    List<String> categories;
    double minInterest;
    int minTime;
    String userName;
    int maxOpenLoans;



    public inlayTask(List<String> categories, double minInterest, int minTime, String userName, int maxOpenLoans ) {
        this.categories = categories;
        this.minInterest = minInterest;
        this.minTime = minTime;
        this.userName = userName;
        this.maxOpenLoans = maxOpenLoans;
    }

    @Override
    protected List<NewLoanDTO> call() throws Exception {
        updateProgress(0.3,1);
        Thread.sleep(700);
        updateProgress(0.6,1);
        Thread.sleep(700);
        // List<NewLoanDTO> filteredLoans = engine.validLoansToInlay(loans);
        updateProgress(1,1);
        return null;
    }
}
