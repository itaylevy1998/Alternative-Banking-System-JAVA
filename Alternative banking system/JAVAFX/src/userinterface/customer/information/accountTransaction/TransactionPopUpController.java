package userinterface.customer.information.accountTransaction;

import exceptions.accountexception.WithDrawMoneyException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TransactionPopUpController {

    //JavaFX components
    @FXML private ScrollPane transactionPopUpSP;
    @FXML private Button confirmButton;
    @FXML private Label errorMessage;
    @FXML private AnchorPane transactionPopUpAP;
    @FXML private Label messageButton;
    @FXML private TextField textField;
    private Stage popUpStage;

    private Scene popUpScene;


    private String userName;
    boolean chargeOrWithdraw;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public TransactionPopUpController() {
        popUpStage = new Stage();
    }




    //Regular Fields
    private AccountTransactionController accountTransactionController;

    //Getters

    public Stage getPopUpStage() {return popUpStage;}

    public Label getMessageButton() {return messageButton;}
    public AnchorPane getTransactionPopUpAP() {return transactionPopUpAP;}

    //Setters
    public void setMessageButton(String Message) {
        messageButton.setText(Message);
    }

    //Regular Methods
    public void setPopUp(Stage primaryStage, String message, boolean popUpExist, boolean chargeOrWithdraw){
        if(!popUpExist) {
            popUpStage.initModality(Modality.WINDOW_MODAL);
            popUpStage.initOwner(primaryStage);
        }
        messageButton.setText(message);
        popUpStage.show();
        this.chargeOrWithdraw = chargeOrWithdraw;
    }

    public void setPopUpScene(){
        popUpScene = new Scene(transactionPopUpSP,451,159);
        popUpStage.setScene(popUpScene);
    }
    public void setAccountTransactionController(AccountTransactionController accountTransactionController) {
        this.accountTransactionController = accountTransactionController;
    }

    public AccountTransactionController getAccountTransactionController() {
        return accountTransactionController;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.setText(errorMessage);
    }

    public void setTextField(String textField) {
        this.textField.setText(textField);
    }

    @FXML
    void confirmButtonSetOnAction(ActionEvent event) {
        String userInput = textField.getText();
        Double userSum;
        try {
           userSum =  Double.parseDouble(userInput);
           if(userSum<=0){
               throw new Exception();
           }

            //Charge!
            if(chargeOrWithdraw) {
                getAccountTransactionController().getInformationTabController().getCustomerScreenController().chargeMoney(userSum);
            } else{
                getAccountTransactionController().getInformationTabController().getCustomerScreenController().withdrawMoney(userSum);
            }
        }
        catch (Exception e){
            errorMessage.setText("Incorrect Input. Please Enter a valid Number");
        }
    }
}
