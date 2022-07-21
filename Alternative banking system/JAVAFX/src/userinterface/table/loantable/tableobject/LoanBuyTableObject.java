package userinterface.table.loantable.tableobject;

import javafx.scene.control.Button;
import objects.loans.LoansForSaleDTO;

public class LoanBuyTableObject extends LoansForSaleDTO {
    Button lendersButton;
    Button paymentsButton;

    public LoanBuyTableObject (LoansForSaleDTO loan){
        super(loan.getLoanID(), loan.getCategory(), loan.getOwner(),loan.getSeller(), loan.getSizeNoInterest(),
                loan.getTimeLimitOfLoan(), loan.getInterestPerPayment(), loan.getTimePerPayment(),loan.getListOfLenders(),
                loan.getStartingActiveTime(), loan.getNextPaymentTime(), loan.getPayments(), loan.getPrice(),loan.getExpectedProfit());
        lendersButton = new Button();
        paymentsButton = new Button();
    }

    public Button getLendersButton() {return lendersButton;}
    public Button getPaymentsButton() {return paymentsButton;}
}
