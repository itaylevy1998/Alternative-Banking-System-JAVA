package userinterface.customer.loanforsell;
import customercomponents.customerscreen.CustomerScreenController;

import exceptions.accountexception.NotEnoughMoneyInAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import objects.loans.LoansForSaleDTO;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.Notifications;
import userinterface.table.loantable.LoanBuyTableController;

import java.util.List;

public class LoanSellTabController {

    //Sub components
    @FXML private ScrollPane buyLoansTable;
    @FXML private LoanBuyTableController buyLoansTableController;

    //JavaFX components
    @FXML private ScrollPane buySellLoanSP;
    @FXML private AnchorPane buySellLoanAP;
    @FXML private TabPane buySellLoanTP;
    @FXML private Tab sellLoanTab;
    @FXML private CheckListView<String> sellLoanCLV;
    @FXML private Button confirmSellButton;
    @FXML private Label sellErrorMessage;
    @FXML private Tab buyLoanTab;
    @FXML private Button confirmBuyButton;
    @FXML private Label errorBuyMessage;

    //Regular Fields
    private CustomerScreenController customerScreenController;
//    private Engine engine;


    @FXML
    private void initialize() {
        buySellLoanTP.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            sellErrorMessage.setVisible(false);
            errorBuyMessage.setText("");
        });

    }

    //Setters
    public void setCustomerScreenController(CustomerScreenController customerScreenController) {
        this.customerScreenController = customerScreenController;
    }

    public void setValues(List<String> loansAvailableToSell, List<LoansForSaleDTO> loansAvailableToBuy) {
        sellLoanCLV.getCheckModel().clearChecks();
        sellLoanCLV.getItems().clear();
        sellLoanCLV.getItems().addAll(loansAvailableToSell);
        buyLoansTableController.setValues(loansAvailableToBuy);
        sellErrorMessage.setVisible(false);
    }

    //Regular methods
    @FXML
    public void confirmSellOnAction(ActionEvent actionEvent) {
        List<String> selectedLoans = sellLoanCLV.getCheckModel().getCheckedItems();
        if (selectedLoans.size() == 0) {
            sellErrorMessage.setVisible(true);    //error
            return;
        }
        customerScreenController.putLoansOnSale(selectedLoans);
    }

    @FXML
    public void confirmBuyButtonOnAction(ActionEvent actionEvent) {
        try{
        if (buyLoansTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
            customerScreenController.buyLoan(buyLoansTableController.getTableView().getSelectionModel().getSelectedItem());
        }else{
            throw new Exception();//user didn't select
        }
    } catch(Exception e){
        errorBuyMessage.setText("No loan has been selected!");
    }

    }
}


