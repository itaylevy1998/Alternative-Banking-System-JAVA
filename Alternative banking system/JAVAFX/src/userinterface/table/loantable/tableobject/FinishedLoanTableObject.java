package userinterface.table.loantable.tableobject;

import javafx.scene.control.Button;
import objects.loans.FinishedLoanDTO;

public class FinishedLoanTableObject extends FinishedLoanDTO {
    private Button lendersButton;
    private Button paymentsButton;

    public FinishedLoanTableObject(FinishedLoanDTO loan){
        super(loan.getLoanID(), loan.getBorrowerName(), loan.getLoanCategory(), loan.getSizeNoInterest(),
                loan.getTimeLimitOfLoan(), loan.getInterestPerPayment(), loan.getTimePerPayment(), loan.getStatus(),
                loan.getListOfLenders(), loan.getCollectedSoFar(), loan.getSumLeftToBeCollected(), loan.getStartingTime(),
                loan.getPayments(), loan.getFinishingTime());
        lendersButton = new Button();
        paymentsButton = new Button();
    }

    public Button getLendersButton() {return lendersButton;}
    public Button getPaymentsButton() {return paymentsButton;}
}
