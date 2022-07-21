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
import objects.loans.payments.PaymentsDTO;
import userinterface.customer.information.InformationTabController;
import userinterface.customer.payments.PaymentsTabController;
import userinterface.table.LendersTableController;
import userinterface.table.PaymentTableController;
import userinterface.table.loantable.tableobject.ActiveLoanTableObject;
import userinterface.table.loantable.tableobject.PendingLoanTableObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActiveLoanTableController{

    private boolean lenderStageExist = false;
    private boolean paymentStageExist = false;
    private LendersTableController lendersTableController;
    private PaymentTableController paymentTableController;
    //JavaFX components
    @FXML private TableView<ActiveLoanTableObject> tableView;
    @FXML private TableColumn<ActiveLoanTableObject, String> loanID;
    @FXML private TableColumn<ActiveLoanTableObject, String> category;
    @FXML private TableColumn<ActiveLoanTableObject, String> owner;
    @FXML private TableColumn<ActiveLoanTableObject, Double> amount;
    @FXML private TableColumn<ActiveLoanTableObject, Integer> duration;
    @FXML private TableColumn<ActiveLoanTableObject, Integer> interest;
    @FXML private TableColumn<ActiveLoanTableObject, Integer> timePerPayment;
    @FXML private TableColumn<ActiveLoanTableObject, Button> listOfLenders;
    @FXML private TableColumn<ActiveLoanTableObject, Integer> startingActiveTime;
    @FXML private TableColumn<ActiveLoanTableObject, Integer> nextPaymentTime;
    @FXML private TableColumn<ActiveLoanTableObject, Button> payments;
    @FXML private TableColumn<ActiveLoanTableObject, Double> allInitialPayedSoFar;
    @FXML private TableColumn<ActiveLoanTableObject, Double> allInterestPayedSoFar;
    @FXML private TableColumn<ActiveLoanTableObject, Double> allInitialLeftToPay;
    @FXML private TableColumn<ActiveLoanTableObject, Double> allInterestLeftToPay;

    //Regular Fields
    private PaymentsTabController paymentsTabController;
    private InformationTabController informationTabController;
    private AdminScreenController adminScreenController;

    private Stage primaryStage;


    @FXML
    private void initialize(){
        loanID.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, String>("loanCategory"));
        owner.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, String>("borrowerName"));
        duration.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, Integer>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, Integer>("timePerPayment"));
        listOfLenders.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, Button>("lendersButton"));
        startingActiveTime.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, Integer>("startingActiveTime"));
        nextPaymentTime.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, Integer>("nextPaymentTime"));
        payments.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, Button>("paymentsButton"));
        allInitialPayedSoFar.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, Double>("allInitialPayedSoFar"));
        allInterestPayedSoFar.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, Double>("allInterestPayedSoFar"));
        allInitialLeftToPay.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, Double>("allInitialLeftToPay"));
        allInterestLeftToPay.setCellValueFactory(new PropertyValueFactory<ActiveLoanTableObject, Double>("allInterestLeftToPay"));
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
    public TableView<ActiveLoanTableObject> getTableView() {
        return tableView;
    }

    //Setters
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }



    public void setValues(List<ActiveRiskLoanDTO> activeLoansList) {
        List<ActiveLoanTableObject> activeLoanTableObjects = activeLoansList.stream().map(t -> new ActiveLoanTableObject(t)).collect(Collectors.toList());
        ObservableList<ActiveLoanTableObject> activeLoanTableObjectObservableList = FXCollections.observableArrayList(activeLoanTableObjects);
        tableView.getItems().setAll(activeLoanTableObjectObservableList);
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
                    Map<String, Double> lendersMap = activeLoansList.get(finalI).getListOfLenders();
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
                    List<PaymentsDTO> pay = tableView.getItems().get(finalI).getPayments();
                    paymentTableController.setValues(pay);
                    paymentTableController.getPopUpPaymentStage().show();
                }
            });

        }
    }
}
