package userinterface.customer.payments;

import customercomponents.customerscreen.CustomerScreenController;
import exceptions.accountexception.NotEnoughMoneyInAccount;
import exceptions.accountexception.WithDrawMoneyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.payments.PaymentNotificationDTO;
import org.controlsfx.control.Notifications;
import userinterface.table.loantable.ActiveLoanTableController;
import userinterface.table.loantable.RiskLoanTableController;
import userinterface.table.loantable.tableobject.ActiveLoanTableObject;
import userinterface.table.loantable.tableobject.RiskLoanTableObject;

import java.util.List;

public class PaymentsTabController {

    //Sub components
    @FXML private ScrollPane closeLoanActiveTable;
    @FXML private ActiveLoanTableController closeLoanActiveTableController;

    @FXML private ScrollPane closeLoanRiskTable;
    @FXML private RiskLoanTableController closeLoanRiskTableController;

    @FXML private ScrollPane makePaymentActiveTable;
    @FXML private ActiveLoanTableController makePaymentActiveTableController;

    @FXML private ScrollPane makePaymentRiskTable;
    @FXML private RiskLoanTableController makePaymentRiskTableController;
//    @FXML private AnchorPane finishImage;
//    @FXML private FinishAnimationController finishImageController;


    //JavaFX components
    @FXML private Button closeLoanButton;
    @FXML private Label closeLoanError;

    @FXML private Button completePaymentButton;
    @FXML private Label completePaymentError;

    @FXML private AnchorPane paymentsTabAP;
    @FXML private TabPane closeOrPaymentTabPane;
    @FXML private Tab closeLoanTab;
    @FXML private AnchorPane closeLoanAP;
    @FXML private TabPane closeLoanTabPane;
    @FXML private Tab closeLoanActiveTab;
    @FXML private Tab closeLoanRiskTab;
    @FXML private HBox closeButtonAndErrorHB;
    @FXML private Tab makePaymentTab;
    @FXML private AnchorPane makePaymentAP;
    @FXML private TabPane makePaymentTabPane;
    @FXML private Tab makePaymentActiveTab;
    @FXML private Tab makePaymentRiskTab;
    @FXML private TextField paymentAmountTextField;
    @FXML private Label paymentAmountLabel;
    @FXML private AnchorPane notificationsAP;
    @FXML private Label notificationsTitle;
    @FXML private TableView<PaymentNotificationDTO> notificationsTableView;
    @FXML private TableColumn<PaymentNotificationDTO, String> loanIDNotification;
    @FXML private TableColumn<PaymentNotificationDTO, Integer> YAZNotification;
    @FXML private TableColumn<PaymentNotificationDTO, Double> SumNotification;


    //Regular Fields
    private CustomerScreenController customerScreenController;
//    private Engine engine;
//    private boolean animation;
    @FXML
    private void initialize() {
        loanIDNotification.setCellValueFactory(new PropertyValueFactory<>("loanID"));
        YAZNotification.setCellValueFactory(new PropertyValueFactory<>("paymentYaz"));
        SumNotification.setCellValueFactory(new PropertyValueFactory<>("sumOfPayment"));
         makePaymentTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
             makePaymentActiveTableController.getTableView().getSelectionModel().clearSelection();
             makePaymentRiskTableController.getTableView().getSelectionModel().clearSelection();
             paymentAmountLabel.setVisible(!paymentAmountLabel.isVisible());
             if(paymentAmountLabel.isVisible()){
                 completePaymentButton.setText("Confirm Payment");
             }else{
                 completePaymentButton.setText("Complete payment for selected loan");
             }
             paymentAmountTextField.clear();
             paymentAmountTextField.setVisible(!paymentAmountTextField.isVisible());
             completePaymentError.setText("");

        });
         closeLoanTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
             closeLoanActiveTableController.getTableView().getSelectionModel().clearSelection();
             closeLoanRiskTableController.getTableView().getSelectionModel().clearSelection();
             closeLoanError.setText("");

         });
         closeOrPaymentTabPane.getSelectionModel().selectedItemProperty().addListener((ov,oldTab,newTab)->{

         });
    }


    //Getters
    public CustomerScreenController getTopCustomerController() {
        return customerScreenController;
    }
    public Label getCloseLoanError() {return closeLoanError;}
    public Label getCompletePaymentError() {return completePaymentError;}
    public TextField getPaymentAmountTextField() {return paymentAmountTextField;}


    //Setters
    public void setCustomerScreenController(CustomerScreenController customerScreenController) {
        this.customerScreenController = customerScreenController;
    }



    public void setValues(List<PaymentNotificationDTO> paymentNotifications, List<ActiveRiskLoanDTO> makePaymentActive, List<ActiveRiskLoanDTO> riskLoans, List<ActiveRiskLoanDTO> closeLoanActive){
        ObservableList<PaymentNotificationDTO> PaymentNotificationDTOObservableList = FXCollections.observableList(paymentNotifications);
        notificationsTableView.getItems().setAll(PaymentNotificationDTOObservableList);

        closeLoanRiskTableController.setValues(riskLoans);

        makePaymentActiveTableController.setValues(makePaymentActive);

        makePaymentRiskTableController.setValues(riskLoans);

        closeLoanActiveTableController.setValues(closeLoanActive);
        completePaymentError.setText("");

    }

    //Regular methods
    @FXML
    public void completePaymentOnAction(ActionEvent actionEvent) {
        try {
            ActiveLoanTableObject selectedActiveLoan = null;
            RiskLoanTableObject selectedRiskLoan = null;
            if (makePaymentActiveTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
                selectedActiveLoan = makePaymentActiveTableController.getTableView().getSelectionModel().getSelectedItem();
                customerScreenController.makePayment(selectedActiveLoan.getLoanID(), "Active", 0.0);
            }
            if (makePaymentRiskTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
                selectedRiskLoan = makePaymentRiskTableController.getTableView().getSelectionModel().getSelectedItem();
                try {
                    String amount = paymentAmountTextField.getText();
                    Double Amount = Double.parseDouble(amount);
                    if (Amount <= 0) {
                        throw new Exception();
                    }
                    customerScreenController.makePayment(selectedRiskLoan.getLoanID(),"Risk", Amount);
                } catch (NumberFormatException e) {
                    completePaymentError.setText("Invalid input!");
                } catch (Exception e){
                    completePaymentError.setText("Please enter a positive number!");
                }
            }
            if(selectedActiveLoan == null && selectedRiskLoan == null){
                throw new Exception();//user didn't select
            }
        } catch (Exception e){
            completePaymentError.setText("No loan has been selected!");
        }

    }

    @FXML
    public void closeLoanOnAction(ActionEvent actionEvent) {
        try{
            ActiveLoanTableObject selectedActiveLoan = null;
            RiskLoanTableObject selectedRiskLoan = null;
            if (closeLoanActiveTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
                selectedActiveLoan = closeLoanActiveTableController.getTableView().getSelectionModel().getSelectedItem();
                customerScreenController.closeLoan(selectedActiveLoan.getLoanID());
            }
            if (closeLoanRiskTableController.getTableView().getSelectionModel().getSelectedItem() != null) {
                selectedRiskLoan = closeLoanRiskTableController.getTableView().getSelectionModel().getSelectedItem();
                customerScreenController.closeLoan(selectedRiskLoan.getLoanID());
            }
            if(selectedActiveLoan == null && selectedRiskLoan == null){
                throw new Exception();//user didn't select
            }

        } catch (Exception e) {
            closeLoanError.setText("No loan has been selected!");
        }
    }
}



