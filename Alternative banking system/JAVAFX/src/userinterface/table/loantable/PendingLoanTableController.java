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
import objects.loans.LenderMap;
import objects.loans.PendingLoanDTO;
import userinterface.customer.information.InformationTabController;
import userinterface.customer.inlay.InlayTabController;
import userinterface.table.LendersTableController;
import userinterface.table.loantable.tableobject.NewLoanTableObject;
import userinterface.table.loantable.tableobject.PendingLoanTableObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PendingLoanTableController {

    //Sub Components
    private LendersTableController lendersTableController;
    private boolean lenderStageExist = false;

    //JavaFX components
    @FXML private TableView<PendingLoanTableObject> tableView;
    @FXML private TableColumn<PendingLoanTableObject, String> loanID;
    @FXML private TableColumn<PendingLoanTableObject, String> category;
    @FXML private TableColumn<PendingLoanTableObject, String> owner;
    @FXML private TableColumn<PendingLoanTableObject, Double> amount;
    @FXML private TableColumn<PendingLoanTableObject, Integer> duration;
    @FXML private TableColumn<PendingLoanTableObject, Integer> interest;
    @FXML private TableColumn<PendingLoanTableObject, Integer> timePerPayment;
    @FXML private TableColumn<PendingLoanTableObject, Button> listOfLenders;
    @FXML private TableColumn<PendingLoanTableObject, Double> collectedSoFar;
    @FXML private TableColumn<PendingLoanTableObject, Double> sumLeftToBeCollected;

    //Regular Fields
    private InlayTabController inlayTabController;
    private InformationTabController informationTabController;
    private AdminScreenController adminScreenController;
    private Button listOfLendersButton = new Button("Show");
    private Stage primaryStage;


    //Constructor
    public PendingLoanTableController() {

    }

    //Initialize after constructor
    public void initialize() {
        loanID.setCellValueFactory(new PropertyValueFactory<PendingLoanTableObject, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<PendingLoanTableObject, String>("loanCategory"));
        owner.setCellValueFactory(new PropertyValueFactory<PendingLoanTableObject, String>("borrowerName"));
        duration.setCellValueFactory(new PropertyValueFactory<PendingLoanTableObject, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<PendingLoanTableObject, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<PendingLoanTableObject, Integer>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<PendingLoanTableObject, Integer>("timePerPayment"));
        listOfLenders.setCellValueFactory(new PropertyValueFactory<PendingLoanTableObject, Button>("lendersButton"));
        collectedSoFar.setCellValueFactory(new PropertyValueFactory<PendingLoanTableObject, Double>("collectedSoFar"));
        sumLeftToBeCollected.setCellValueFactory(new PropertyValueFactory<PendingLoanTableObject, Double>("sumLeftToBeCollected"));
        FXMLLoader loaderlenders = new FXMLLoader();
        URL lendersFXML = getClass().getResource("/userinterface/table/lendersTable.fxml");
        loaderlenders.setLocation(lendersFXML);
        try {
            Parent root1 = loaderlenders.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lendersTableController = loaderlenders.getController();
    }

    //Getters
    public TableView<PendingLoanTableObject> getTableView() {return tableView;}

    //Setters
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setValues(List<PendingLoanDTO> pendingLoansList){
        List<PendingLoanTableObject> pendingLoanTableObjects = pendingLoansList.stream().map(t -> new PendingLoanTableObject(t)).collect(Collectors.toList());
        ObservableList<PendingLoanTableObject> pendingLoanDTOObservableList = FXCollections.observableArrayList(pendingLoanTableObjects);
        tableView.getItems().setAll(pendingLoanDTOObservableList);
        for(int i=0; i<tableView.getItems().size(); i++){
            tableView.getItems().get(i).getLendersButton().setText("Show");
           Button lendersButton = tableView.getItems().get(i).getLendersButton();
            int finalI = i;
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
                    Map<String, Double> lendersMap = pendingLoansList.get(finalI).getListOfLenders();
                    for (Map.Entry<String,Double> entry : lendersMap.entrySet()){
                        lenders.add(new LenderMap(entry.getKey(), entry.getValue()));
                }
                    lendersTableController.setValues(lenders);
                    lendersTableController.getPopUpLenderStage().show();
                }
            });
        }
    }
}