package userinterface.table.customerTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import objects.customers.CustomerInfoDTO;
import objects.loans.NewLoanDTO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerTableController implements Initializable {

    // JavaFX components
    @FXML private TableView<CustomerInfoDTO> table;
    @FXML private TableColumn<CustomerInfoDTO, String> name;
    @FXML private TableColumn<CustomerInfoDTO, Double> balance;
//    @FXML private TableColumn<TableColumn, ?> totalLending;
    @FXML private TableColumn<CustomerInfoDTO, Integer> pendingLender;
    @FXML private TableColumn<CustomerInfoDTO, Integer> activeLender;
    @FXML private TableColumn<CustomerInfoDTO, Integer> riskLender;
    @FXML private TableColumn<CustomerInfoDTO, Integer> finishedLender;
//    @FXML private TableColumn<TableColumn, ?> totalBorrowing;
    @FXML private TableColumn<CustomerInfoDTO, Integer> newBorrower;
    @FXML private TableColumn<CustomerInfoDTO, Integer> pendingBorrower;
    @FXML private TableColumn<CustomerInfoDTO, Integer> activeBorrower;
    @FXML private TableColumn<CustomerInfoDTO, Integer> riskBorrower;
    @FXML private TableColumn<CustomerInfoDTO, Integer> finishedBorrower;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<CustomerInfoDTO, String>("name"));
        balance.setCellValueFactory(new PropertyValueFactory<CustomerInfoDTO, Double>("balance"));

        pendingLender.setCellValueFactory(new PropertyValueFactory<CustomerInfoDTO, Integer>("pendingLender"));
        activeLender.setCellValueFactory(new PropertyValueFactory<CustomerInfoDTO, Integer>("activeLender"));
        riskLender.setCellValueFactory(new PropertyValueFactory<CustomerInfoDTO, Integer>("riskLender"));
        finishedLender.setCellValueFactory(new PropertyValueFactory<CustomerInfoDTO, Integer>("finishedLender"));

        newBorrower.setCellValueFactory(new PropertyValueFactory<CustomerInfoDTO, Integer>("newBorrower"));
        pendingBorrower.setCellValueFactory(new PropertyValueFactory<CustomerInfoDTO, Integer>("pendingBorrower"));
        activeBorrower.setCellValueFactory(new PropertyValueFactory<CustomerInfoDTO, Integer>("activeBorrower"));
        riskBorrower.setCellValueFactory(new PropertyValueFactory<CustomerInfoDTO, Integer>("riskBorrower"));
        finishedBorrower.setCellValueFactory(new PropertyValueFactory<CustomerInfoDTO, Integer>("finishedBorrower"));

    }

    public void setValues(List<CustomerInfoDTO> customersList ){
        ObservableList<CustomerInfoDTO> CustomerInfoDTOObservableList = FXCollections.observableArrayList(customersList);
        table.getItems().setAll(CustomerInfoDTOObservableList);

    }
}
