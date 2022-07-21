package userinterface.chat.chatroom;


import admincomponents.adminscreen.AdminScreenController;
import customercomponents.customerscreen.CustomerScreenController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import userinterface.chat.api.ChatCommands;
import userinterface.chat.api.HttpStatusUpdate;
import userinterface.chat.chatarea.ChatAreaController;
import userinterface.chat.commands.CommandsController;
import userinterface.chat.users.UsersListController;

import java.io.Closeable;
import java.io.IOException;

public class ChatRoomMainController implements Closeable, HttpStatusUpdate{

    @FXML private VBox usersListComponent;
    @FXML private UsersListController usersListComponentController;
    @FXML private VBox actionCommandsComponent;
    @FXML private CommandsController actionCommandsComponentController;
    @FXML private GridPane chatAreaComponent;
    @FXML private ChatAreaController chatAreaComponentController;
    @FXML private BorderPane chatRoomBP;

    private AdminScreenController adminScreenController;
    private CustomerScreenController customerScreenController;
    private Stage adminStage;
    private Scene popUpScene;
    private boolean isActive = false;

    public ChatRoomMainController() {
        adminStage = new Stage();
    }

    @FXML
    public void initialize() {
        usersListComponentController.setHttpStatusUpdate(this);
     //   actionCommandsComponentController.setChatCommands(this);
        chatAreaComponentController.setHttpStatusUpdate(this);
        chatAreaComponentController.setChatRoomMainController(this);
        chatAreaComponentController.autoUpdatesProperty().bind(actionCommandsComponentController.autoUpdatesProperty());
        usersListComponentController.autoUpdatesProperty().bind(actionCommandsComponentController.autoUpdatesProperty());
    }

//    @Override
//    public void updateHttpLine(String line) {
//        chatAppMainController.updateHttpLine(line);
//    }

    @Override
    public void close() throws IOException {
        usersListComponentController.close();
        chatAreaComponentController.close();
        isActive = false;
        adminStage.close();
    }

    public void setActive() {
        if(!isActive) {
            usersListComponentController.startListRefresher();
            chatAreaComponentController.startListRefresher();
            isActive = true;
        }
    }

    public void setInActive() {
        try {
            usersListComponentController.close();
            chatAreaComponentController.close();
            isActive = false;
        } catch (Exception ignored) {}
    }

    public void setCustomerScreenController(CustomerScreenController customerScreenController) {
        this.customerScreenController = customerScreenController;
    }

    public void setAdminScreenController(AdminScreenController adminScreenController) {
        this.adminScreenController = adminScreenController;
    }


    @Override
    public void updateHttpLine(String line) {

    }
    public void setPopUp(Stage primaryStage, boolean popUpExist){
        if(!popUpExist) {
            //adminStage.initModality(Modality.WINDOW_MODAL);
            adminStage.initOwner(primaryStage);
        }
        adminStage.show();
    }

    public void setPopUpScene(){
        popUpScene = new Scene(chatRoomBP,888,412);
        adminStage.setScene(popUpScene);
    }

    public CustomerScreenController getCustomerScreenController() {
        return customerScreenController;
    }

    public AdminScreenController getAdminScreenController() {
        return adminScreenController;
    }

    public ChatAreaController getChatAreaComponentController() {
        return chatAreaComponentController;
    }

    public UsersListController getUsersListComponentController() {
        return usersListComponentController;
    }
    //    public void setChatAppMainController(ChatAppMainController chatAppMainController) {
//        this.chatAppMainController = chatAppMainController;
//    }

//    @Override
//    public void logout() {
//        chatAppMainController.switchToLogin();
//    }
}
