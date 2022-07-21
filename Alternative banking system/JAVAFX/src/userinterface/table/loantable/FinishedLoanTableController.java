package userinterface.table.loantable;

import admincomponents.adminscreen.AdminScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.loans.FinishedLoanDTO;
import objects.loans.LenderMap;
import userinterface.customer.information.InformationTabController;
import userinterface.customer.payments.PaymentsTabController;
import userinterface.table.LendersTableController;
import userinterface.table.PaymentTableController;
import userinterface.table.loantable.tableobject.ActiveLoanTableObject;
import userinterface.table.loantable.tableobject.FinishedLoanTableObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FinishedLoanTableController {

    //Sub Components
    private LendersTableController lendersTableController;
    private PaymentTableController paymentTableController;


    //JavaFX components
    @FXML private TableView<FinishedLoanTableObject> finishedTable;
    @FXML private TableColumn<FinishedLoanTableObject, String> loanID;
    @FXML private TableColumn<FinishedLoanTableObject, String> category;
    @FXML private TableColumn<FinishedLoanTableObject, String> owner;
    @FXML private TableColumn<FinishedLoanTableObject, Double> amount;
    @FXML private TableColumn<FinishedLoanTableObject, Integer> duration;
    @FXML private TableColumn<FinishedLoanTableObject, Integer> interest;
    @FXML private TableColumn<FinishedLoanTableObject, Integer> timePerPayment;
    @FXML private TableColumn<FinishedLoanTableObject, Button> listOfLenders;
    @FXML private TableColumn<FinishedLoanTableObject, Button> payments;
    @FXML private TableColumn<FinishedLoanTableObject, Integer> startingTime;
    @FXML private TableColumn<FinishedLoanTableObject, Integer> finishingTime;

    //Regular Fields
    private boolean lenderStageExist = false;
    private boolean paymentStageExist = false;
    private PaymentsTabController paymentsTabController;
    private InformationTabController informationTabController;
    private AdminScreenController adminScreenController;
    private Stage primaryStage;

    public void initialize(){
        loanID.setCellValueFactory(new PropertyValueFactory<FinishedLoanTableObject, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<FinishedLoanTableObject, String>("loanCategory"));
        owner.setCellValueFactory(new PropertyValueFactory<FinishedLoanTableObject, String>("borrowerName"));
        duration.setCellValueFactory(new PropertyValueFactory<FinishedLoanTableObject, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<FinishedLoanTableObject, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<FinishedLoanTableObject, Integer>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<FinishedLoanTableObject, Integer>("timePerPayment"));
        listOfLenders.setCellValueFactory(new PropertyValueFactory<FinishedLoanTableObject, Button>("lendersButton"));
        startingTime.setCellValueFactory(new PropertyValueFactory<FinishedLoanTableObject, Integer>("startingTime"));
        payments.setCellValueFactory(new PropertyValueFactory<FinishedLoanTableObject, Button>("paymentsButton"));
        finishingTime.setCellValueFactory(new PropertyValueFactory<FinishedLoanTableObject, Integer>("finishingTime"));
        FXMLLoader loaderlenders = new FXMLLoader();
        URL lendersFXML = getClass().getResource("/userinterface/table/lendersTable.fxml");
        loaderlenders.setLocation(lendersFXML);
        try {
            Parent root1 = loaderlenders.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lendersTableController = loaderlenders.getController();

        FXMLLoader loaderPayment = new FXMLLoader();
        URL paymentFXML = getClass().getResource("/userinterface/table/paymentTable.fxml");
        loaderPayment.setLocation(paymentFXML);
        try {
            Parent root1 = loaderPayment.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        paymentTableController = loaderPayment.getController();
    }

    //Setters

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setValues(List<FinishedLoanDTO> finishedLoansList){
        List<FinishedLoanTableObject> finishedLoanTableObjects = finishedLoansList.stream().map(t -> new FinishedLoanTableObject(t)).collect(Collectors.toList());
        ObservableList<FinishedLoanTableObject> finishedLoanTableObjectObservableList = FXCollections.observableArrayList(finishedLoanTableObjects);
        finishedTable.getItems().setAll(finishedLoanTableObjectObservableList);
        for(int i=0; i<finishedTable.getItems().size(); i++){
            int finalI = i;
            Button lendersButton = finishedTable.getItems().get(i).getLendersButton();
            lendersButton.setText("Show");
            lendersButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent actionEvent){
                    if(!lenderStageExist){
                        lenderStageExist = true;
                        lendersTableController.setPopUpScene();
                        lendersTableController.getPopUpLenderStage().initModality(Modality.WINDOW_MODAL);
                        lendersTableController.getPopUpLenderStage().initOwner(primaryStage);
                    }
                    List<LenderMap> lenders = new ArrayList<>();
                    Map<String, Double> lendersMap = finishedLoansList.get(finalI).getListOfLenders();
                    for (Map.Entry<String,Double> entry : lendersMap.entrySet()){
                        lenders.add(new LenderMap(entry.getKey(), entry.getValue()));
                    }
                    lendersTableController.setValues(lenders);
                    lendersTableController.getPopUpLenderStage().show();
                }
            });

            Button paymentButton = finishedTable.getItems().get(i).getPaymentsButton();
            paymentButton.setText("Show");
            paymentButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent){
                    if(!paymentStageExist){
                        paymentStageExist = true;
                        paymentTableController.setPopUpScene();
                        paymentTableController.getPopUpPaymentStage().initModality(Modality.WINDOW_MODAL);
                        paymentTableController.getPopUpPaymentStage().initOwner(primaryStage);
                    }
                    paymentTableController.setValues(finishedTable.getItems().get(finalI).getPayments());
                    paymentTableController.getPopUpPaymentStage().show();
                }
            });

        }
    }
    public TableView<FinishedLoanTableObject> getFinishedTable() {
        return finishedTable;
    }

}
