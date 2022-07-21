package userinterface.table.loantable.tableobject;

import javafx.scene.control.Button;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.PendingLoanDTO;

public class ActiveLoanTableObject extends ActiveRiskLoanDTO {
    private Button lendersButton;
    private Button paymentsButton;

    public ActiveLoanTableObject(ActiveRiskLoanDTO loan){
        super(loan.getLoanID(), loan.getBorrowerName(), loan.getLoanCategory(), loan.getSizeNoInterest(), loan.getTimeLimitOfLoan(),
                loan.getInterestPerPayment(), loan.getTimePerPayment(), loan.getStatus(), loan.getListOfLenders(),
                loan.getCollectedSoFar(), loan.getSumLeftToBeCollected(), loan.getStartingActiveTime(), loan.getNextPaymentTime(),
                loan.getPayments(), loan.getAllInterestPayedSoFar(), loan.getAllInitialPayedSoFar(),
                loan.getAllInterestLeftToPay(), loan.getAllInitialLeftToPay());
        lendersButton = new Button();
        paymentsButton = new Button();
    }

    public Button getLendersButton() {return lendersButton;}
    public Button getPaymentsButton() {return paymentsButton;}
}
