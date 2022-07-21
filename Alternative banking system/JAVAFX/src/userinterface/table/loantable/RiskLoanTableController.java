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
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.LenderMap;
import userinterface.customer.information.InformationTabController;
import userinterface.customer.payments.PaymentsTabController;
import userinterface.table.LendersTableController;
import userinterface.table.PaymentTableController;
import userinterface.table.loantable.tableobject.ActiveLoanTableObject;
import userinterface.table.loantable.tableobject.RiskLoanTableObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RiskLoanTableController {

    private boolean lenderStageExist = false;
    private boolean paymentStageExist = false;
    private PaymentTableController paymentTableController;
    private LendersTableController lendersTableController;
    //JavaFX components
    @FXML private TableView<RiskLoanTableObject> tableView;
    @FXML private TableColumn<RiskLoanTableObject, String> loanID;
    @FXML private TableColumn<RiskLoanTableObject, String> category;
    @FXML private TableColumn<RiskLoanTableObject, String> owner;
    @FXML private TableColumn<RiskLoanTableObject, Double> amount;
    @FXML private TableColumn<RiskLoanTableObject, Integer> duration;
    @FXML private TableColumn<RiskLoanTableObject, Integer> interest;
    @FXML private TableColumn<RiskLoanTableObject, Integer> timePerPayment;
    @FXML private TableColumn<RiskLoanTableObject, Button> listOfLenders;
    @FXML private TableColumn<RiskLoanTableObject, Double> startingActiveTime;
    @FXML private TableColumn<RiskLoanTableObject, Integer> nextPaymentTime;
    @FXML private TableColumn<RiskLoanTableObject, Button> payments;
    @FXML private TableColumn<RiskLoanTableObject, Double> allInitialPayedSoFar;
    @FXML private TableColumn<RiskLoanTableObject, Double> allInterestPayedSoFar;
    @FXML private TableColumn<RiskLoanTableObject, Double> allInitialLeftToPay;
    @FXML private TableColumn<RiskLoanTableObject, Double> allInterestLeftToPay;

    //Regular Fields
    private PaymentsTabController paymentsTabController;
    private InformationTabController informationTabController;
    private AdminScreenController adminScreenController;
    private Stage primaryStage;


    public void initialize() {
        loanID.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, String>("loanCategory"));
        owner.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, String>("borrowerName"));
        duration.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, Integer>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, Integer>("timePerPayment"));
        listOfLenders.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, Button>("lendersButton"));
        startingActiveTime.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, Double>("startingActiveTime"));
        nextPaymentTime.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, Integer>("nextPaymentTime"));
        payments.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, Button>("paymentsButton"));
        allInitialLeftToPay.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, Double>("allInitialLeftToPay"));
        allInitialPayedSoFar.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, Double>("allInitialPayedSoFar"));
        allInterestLeftToPay.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, Double>("allInterestLeftToPay"));
        allInterestPayedSoFar.setCellValueFactory(new PropertyValueFactory<RiskLoanTableObject, Double>("allInterestPayedSoFar"));
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

    //Getters
    public TableView<RiskLoanTableObject> getTableView() {
        return tableView;
    }

    //Setters
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setValues(List<ActiveRiskLoanDTO> riskLoansList){
        List<RiskLoanTableObject> riskLoanTableObjects = riskLoansList.stream().map(t -> new RiskLoanTableObject(t)).collect(Collectors.toList());
        ObservableList<RiskLoanTableObject> riskLoanTableObjectObservableList = FXCollections.observableArrayList(riskLoanTableObjects);
        tableView.getItems().setAll(riskLoanTableObjectObservableList);
        for(int i=0; i<tableView.getItems().size(); i++){
            int finalI = i;
            Button lendersButton = tableView.getItems().get(i).getLendersButton();
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
                    Map<String, Double> lendersMap = riskLoansList.get(finalI).getListOfLenders();
                    for (Map.Entry<String,Double> entry : lendersMap.entrySet()){
                        lenders.add(new LenderMap(entry.getKey(), entry.getValue()));
                    }
                    lendersTableController.setValues(lenders);
                    lendersTableController.getPopUpLenderStage().show();
                }
            });

            Button paymentButton = tableView.getItems().get(i).getPaymentsButton();
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

                    paymentTableController.setValues(tableView.getItems().get(finalI).getPayments());

                    paymentTableController.getPopUpPaymentStage().show();
                }
            });

        }
    }
}