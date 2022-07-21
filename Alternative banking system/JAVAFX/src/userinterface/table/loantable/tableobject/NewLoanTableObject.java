package userinterface.table.loantable.tableobject;

import javafx.scene.control.CheckBox;
import objects.loans.NewLoanDTO;

public class NewLoanTableObject extends NewLoanDTO {
    private CheckBox IsSelected;

    public NewLoanTableObject(NewLoanDTO newLoanDTO) {
        super(newLoanDTO.getLoanID(), newLoanDTO.getBorrowerName(), newLoanDTO.getLoanCategory(),
                newLoanDTO.getSizeNoInterest(), newLoanDTO.getTimeLimitOfLoan(), newLoanDTO.getInterestPerPayment(),
                newLoanDTO.getTimePerPayment(), newLoanDTO.getStatus());
        IsSelected = new CheckBox();
    }
    public CheckBox getIsSelected() {
        return IsSelected;
    }

}
