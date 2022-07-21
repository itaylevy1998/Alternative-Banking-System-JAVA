package userinterface.table.loantable;

import admincomponents.adminscreen.AdminScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.loans.NewLoanDTO;
import userinterface.customer.information.InformationTabController;
import userinterface.customer.inlay.InlayTabController;
import userinterface.table.loantable.tableobject.NewLoanTableObject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NewLoanTableController implements Initializable {

    //JavaFX components
    @FXML private TableView<NewLoanTableObject> tableView;
    @FXML private TableColumn<NewLoanTableObject, String> loanID;
    @FXML private TableColumn<NewLoanTableObject, String> category;
    @FXML private TableColumn<NewLoanTableObject, String> owner;
    @FXML private TableColumn<NewLoanTableObject, Integer> duration;
    @FXML private TableColumn<NewLoanTableObject, Double> amount;
    @FXML private TableColumn<NewLoanTableObject, Integer> interest;
    @FXML private TableColumn<NewLoanTableObject, Integer> timePerPayment;


    //Regular Fields
    private InlayTabController inlayTabController;
    private InformationTabController informationTabController;
    private AdminScreenController adminScreenController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loanID.setCellValueFactory(new PropertyValueFactory<NewLoanTableObject, String>("loanID"));
        category.setCellValueFactory(new PropertyValueFactory<NewLoanTableObject, String>("loanCategory"));
        owner.setCellValueFactory(new PropertyValueFactory<NewLoanTableObject, String>("borrowerName"));
        duration.setCellValueFactory(new PropertyValueFactory<NewLoanTableObject, Integer>("timeLimitOfLoan"));
        amount.setCellValueFactory(new PropertyValueFactory<NewLoanTableObject, Double>("sizeNoInterest"));
        interest.setCellValueFactory(new PropertyValueFactory<NewLoanTableObject, Integer>("interestPerPayment"));
        timePerPayment.setCellValueFactory(new PropertyValueFactory<NewLoanTableObject, Integer>("timePerPayment"));
    }


    //Setters

    //Getters
    public TableView<NewLoanTableObject> getTableView() {
        return tableView;
    }

    //Regular Methods
    public void setValues(List<NewLoanDTO> newLoansList){
        List<NewLoanTableObject> newLoanTableObjects = newLoansList.stream().map(t -> new NewLoanTableObject(t)).collect(Collectors.toList());
        ObservableList<NewLoanTableObject> newLoanDTOObservableList = FXCollections.observableArrayList(newLoanTableObjects);
        tableView.getItems().setAll(newLoanDTOObservableList);
    }
}
