package userinterface.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.loans.payments.PaymentsDTO;

import java.util.List;

public class PaymentTableController {

    //JavaFX Components
    @FXML private ScrollPane paymentSP;
    @FXML private TableView<PaymentsDTO> tableView;
    @FXML private TableColumn<PaymentsDTO, Integer> timeOfPayment;
    @FXML private TableColumn<PaymentsDTO, Double> sumOfPayment;
    @FXML private TableColumn<PaymentsDTO, Double> interestComponent;
    @FXML private TableColumn<PaymentsDTO, Double> initialComponent;
    @FXML private TableColumn<PaymentsDTO, String> payedSuccessfully;

    //Regular Fields
    private Stage popUpPaymentStage;
    private Scene popUpPaymentScene;

    public void initialize(){
        timeOfPayment.setCellValueFactory(new PropertyValueFactory<PaymentsDTO,Integer>("timeOfPayment"));
        sumOfPayment.setCellValueFactory(new PropertyValueFactory<PaymentsDTO,Double>("sumOfPayment"));
        interestComponent.setCellValueFactory(new PropertyValueFactory<PaymentsDTO,Double>("interestComponent"));
        initialComponent.setCellValueFactory(new PropertyValueFactory<PaymentsDTO,Double>("initialComponent"));
        payedSuccessfully.setCellValueFactory(new PropertyValueFactory<PaymentsDTO,String>("payedSuccessfully"));
    }

    //Getters
    public Stage getPopUpPaymentStage() {
        return popUpPaymentStage;
    }

    public void setValues(List<PaymentsDTO> paymentsDTOList){
        ObservableList<PaymentsDTO> paymentsDTOObservableList = FXCollections.observableArrayList(paymentsDTOList);
        tableView.getItems().setAll(paymentsDTOObservableList);
        tableView.getColumns().get(2); //?
    }

    public PaymentTableController(){
        popUpPaymentStage = new Stage();
    }

    public void setPopUpScene(){
        popUpPaymentScene = new Scene(paymentSP, 400, 292);
        popUpPaymentStage.setScene(popUpPaymentScene);
    }

}