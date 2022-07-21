package userinterface.customer.information.accountTransaction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import objects.customers.AccountTransactionDTO;
import objects.customers.CustomerInfoDTO;
import userinterface.customer.information.InformationTabController;

import java.net.URL;

public class AccountTransactionController {


    //SubComponents
    private TransactionPopUpController popUpController;
    private String MESSAGE;
    private boolean popUpExist = false;
    //JavaFX components
    @FXML private AnchorPane accountTransactionAP;
    @FXML private HBox accountTransactionHB;
    @FXML private Button charge;
    @FXML private Button withdraw;
    @FXML private TableView<AccountTransactionDTO> tableView;
    @FXML private TableColumn<AccountTransactionDTO, Double> balanceAfter;
    @FXML private TableColumn<AccountTransactionDTO, Double> balanceBefore;
    @FXML private TableColumn<AccountTransactionDTO, Integer> timeOfTransaction;
    @FXML private TableColumn<AccountTransactionDTO, String> transactionAmount;



    public InformationTabController getInformationTabController() {
        return informationTabController;
    }

    //Regular Fields
    private InformationTabController informationTabController;
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
        popUpController.setUserName(this.userName);
    }

    @FXML
    private void initialize() throws Exception{
        FXMLLoader loaderPopUp = new FXMLLoader();
        URL popUpFXML = getClass().getResource("/userinterface/customer/information/accountTransaction/transactionPopUp.fxml");
        loaderPopUp.setLocation(popUpFXML);
        Parent root1 = loaderPopUp.load();
        popUpController = loaderPopUp.getController();
        MESSAGE = popUpController.getMessageButton().getText();
        popUpController.setAccountTransactionController(this);
        popUpController.setPopUpScene();

        timeOfTransaction.setCellValueFactory(new PropertyValueFactory<AccountTransactionDTO, Integer>("timeOfTransaction"));
        balanceBefore.setCellValueFactory(new PropertyValueFactory<AccountTransactionDTO, Double>("balanceBefore"));
        balanceAfter.setCellValueFactory(new PropertyValueFactory<AccountTransactionDTO, Double>("balanceAfter"));
        transactionAmount.setCellValueFactory(new PropertyValueFactory<AccountTransactionDTO, String>("realAmount"));
    }

    //Getters
    public Button getCharge() {return charge;}
    public Button getWithdraw() {return withdraw;}

    public TransactionPopUpController getPopUpController() {
        return popUpController;
    }

    //Setters
    public void setInformationTabController(InformationTabController informationTabController) {
        this.informationTabController = informationTabController;
    }

    //Regular methods
    @FXML
    void chargeButtonOnAction(ActionEvent event) {
        popUpController.setErrorMessage("");
        popUpController.setTextField("");
        popUpController.setPopUp(informationTabController.getCustomerScreenController().getPrimaryStage(), MESSAGE + "charge:", popUpExist, true);
        if(!popUpExist) {
            popUpExist = true;
           // popUpController.setEngine(this.engine);
        }
    }

    @FXML
    void withdrawButtonOnAction(ActionEvent event) {
        popUpController.setErrorMessage("");
        popUpController.setTextField("");
        popUpController.setPopUp(informationTabController.getCustomerScreenController().getPrimaryStage(), MESSAGE + "withdraw:", popUpExist, false);
        if(!popUpExist) {
            popUpExist = true;
        }
    }

    public void setTableValues(CustomerInfoDTO customer){
        ObservableList<AccountTransactionDTO> customerTransactions= FXCollections.observableArrayList(customer.getTransactionDTOS());
        tableView.getItems().setAll(customerTransactions);
    }

}
