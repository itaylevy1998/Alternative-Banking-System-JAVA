package userinterface.table.loantable;

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
import objects.loans.LoansForSaleDTO;
import objects.loans.payments.PaymentsDTO;
import userinterface.customer.loanforsell.LoanSellTabController;
import userinterface.table.LendersTableController;
import userinterface.table.PaymentTableController;
import userinterface.table.loantable.tableobject.LoanBuyTableObject;
import userinterface.table.loantable.tableobject.PendingLoanTableObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class LoanBuyTableController {

    private boolean lenderStageExist = false;
    private boolean paymentStageExist = false;
    private LendersTableController lendersTableController;
    private PaymentTableController paymentTableController;
    //JavaFX components
    @FXML private TableView<LoanBuyTableObject> tableView;
    @FXML private TableColumn<LoanBuyTableObject, String> loanID;
    @FXML private TableColumn<LoanBuyTableObject, String> category;
    @FXML private TableColumn<LoanBuyTableObject, String> owner;
    @FXML private TableColumn<LoanBuyTableObject, String> seller;
    @FXML private TableColumn<LoanBuyTableObject, Double> amount;
    @FXML private TableColumn<LoanBuyTableObject, Integer> duration;
    @FXML private TableColumn<LoanBuyTableObject, Double> interest;
    @FXML private TableColumn<LoanBuyTableObject, Integer> timePerPayment;
    @FXML private TableColumn<LoanBuyTableObject, Button> listOfLenders;
    @FXML private TableColumn<LoanBuyTableObject, Integer> startingActiveTime;
    @FXML private TableColumn<LoanBuyTableObject, Integer> nextPaymentTime;
    @FXML private TableColumn<LoanBuyTableObject, Button> payments;
    @FXML private TableColumn<LoanBuyTableObject, Double> price;
    @FXML private TableColumn<LoanBuyTableObject, Double> expectedProfit;

    //Regular Fields
    private LoanSellTabController loanSellTabController;
    private Stage primaryStage;

    @FXML
    public void initialize(){
        loanID.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, String>("category"));
        owner.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, String>("owner"));
        seller.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, String>("seller"));
        duration.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, Double>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, Integer>("timePerPayment"));
        listOfLenders.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, Button>("lendersButton"));
        startingActiveTime.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, Integer>("startingActiveTime"));
        nextPaymentTime.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, Integer>("nextPaymentTime"));
        payments.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, Button>("paymentsButton"));
        price.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, Double>("price"));
        expectedProfit.setCellValueFactory(new PropertyValueFactory<LoanBuyTableObject, Double>("expectedProfit"));
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
    public TableView<LoanBuyTableObject> getTableView() {
        return tableView;
    }

    //Setters
    public void setLoanSellTabController(LoanSellTabController loanSellTabController) {
        this.loanSellTabController = loanSellTabController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setValues(List<LoansForSaleDTO> loansAvailableToBuy) {
        List<LoanBuyTableObject> loansToBuy = loansAvailableToBuy.stream().map(t -> new LoanBuyTableObject(t)).collect(Collectors.toList());
        ObservableList<LoanBuyTableObject> loansToBuyList = FXCollections.observableList(loansToBuy);
        tableView.getItems().setAll(loansToBuyList);
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
                    Map<String, Double> lendersMap = loansToBuyList.get(finalI).getListOfLenders();
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
