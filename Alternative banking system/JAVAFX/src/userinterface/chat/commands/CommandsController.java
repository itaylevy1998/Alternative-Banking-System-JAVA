package userinterface.chat.commands;


import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import userinterface.chat.api.ChatCommands;
import userinterface.utils.HttpUtil;

import java.io.IOException;

public class CommandsController {

    private ChatCommands chatCommands;
    private final BooleanProperty autoUpdates;
    @FXML private ToggleButton autoUpdatesButton;

    public CommandsController() {
        autoUpdates = new SimpleBooleanProperty();
    }

    @FXML
    public void initialize() {
        autoUpdates.bind(autoUpdatesButton.selectedProperty());
    }

    public ReadOnlyBooleanProperty autoUpdatesProperty() {
        return autoUpdates;
    }

//    @FXML
//    void logoutClicked(ActionEvent event) {
//        chatCommands.updateHttpLine(Constants.LOGOUT);
//        HttpUtil.runAsync(Constants.LOGOUT, new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                chatCommands.updateHttpLine("Logout request ended with failure...:(");
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                if (response.isSuccessful() || response.isRedirect()) {
//                    HttpClientUtil.removeCookiesOf(Constants.BASE_DOMAIN);
//                    chatCommands.logout();
//                }
//            }
//        });
//
//    }

    @FXML
    void quitClicked(ActionEvent event) {
        Platform.exit();
    }

//   public void setChatCommands(ChatCommands chatRoomMainController) {
//        this.chatCommands = chatRoomMainController;
//    }
}
