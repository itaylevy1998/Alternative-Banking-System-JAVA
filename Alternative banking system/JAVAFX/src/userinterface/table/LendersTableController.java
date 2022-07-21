package userinterface.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objects.loans.LenderMap;
import userinterface.table.loantable.PendingLoanTableController;

import java.util.List;
import java.util.Map;

public class LendersTableController {

    @FXML private ScrollPane lendersTableSP;
    @FXML private TableView<LenderMap> lendersTable;
    @FXML private TableColumn<LenderMap, String> loanerName;
    @FXML private TableColumn<LenderMap, Double> amount;

    private Stage popUpLenderStage;
    private Scene popUpLenderScene;

    public Stage getPopUpLenderStage() {
        return popUpLenderStage;
    }

    public void initialize(){
        loanerName.setCellValueFactory(new PropertyValueFactory<LenderMap, String>("Name"));
        amount.setCellValueFactory(new PropertyValueFactory<LenderMap, Double>("Amount"));
    }

    public void setValues(List<LenderMap> values){
        ObservableList<LenderMap> listData = FXCollections.observableArrayList(values);
        lendersTable.getItems().setAll(listData);
    }

    public LendersTableController(){
        popUpLenderStage = new Stage();
    }

    public void setPopUpScene(){
        popUpLenderScene = new Scene(lendersTableSP, 222, 292);
        popUpLenderStage.setScene(popUpLenderScene);
    }
}