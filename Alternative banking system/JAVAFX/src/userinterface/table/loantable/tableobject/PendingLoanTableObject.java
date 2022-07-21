package userinterface.table.loantable.tableobject;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import objects.loans.PendingLoanDTO;

import java.util.Map;

public class PendingLoanTableObject extends PendingLoanDTO {
    private CheckBox IsSelected;
    private Button lendersButton;

    public PendingLoanTableObject(PendingLoanDTO loan){
        super(loan.getLoanID(), loan.getBorrowerName(), loan.getLoanCategory(),
                loan.getSizeNoInterest(), loan.getTimeLimitOfLoan(), loan.getInterestPerPayment(),
                loan.getTimePerPayment(), loan.getStatus(), loan.getListOfLenders(), loan.getCollectedSoFar(),
                loan.getSumLeftToBeCollected());
        IsSelected = new CheckBox();
        lendersButton = new Button();
    }

    public CheckBox getIsSelected() {
        return IsSelected;
    }
    public Button getLendersButton() {
        return lendersButton;
    }
}
