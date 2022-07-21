package userinterface.chat.chatarea;


import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import okhttp3.*;

import userinterface.chat.api.HttpStatusUpdate;
import userinterface.chat.chatarea.model.ChatLinesWithVersion;
import userinterface.chat.chatroom.ChatRoomMainController;
import userinterface.utils.HttpUtil;

import java.io.Closeable;
import java.io.IOException;
import java.util.Timer;
import java.util.stream.Collectors;
import static userinterface.Constants.*;

public class ChatAreaController implements Closeable {

    private final IntegerProperty chatVersion;
    private final BooleanProperty autoScroll;
    private final BooleanProperty autoUpdate;
    private HttpStatusUpdate httpStatusUpdate;
    private ChatAreaRefresher chatAreaRefresher;
    private ChatRoomMainController chatRoomMainController;
    private Timer timer;
    private boolean isAdmin = false;

    @FXML private ToggleButton autoScrollButton;
    @FXML private TextArea chatLineTextArea;
    @FXML private TextArea mainChatLinesTextArea;
    @FXML private Label chatVersionLabel;

    public ChatAreaController() {
        chatVersion = new SimpleIntegerProperty();
        autoScroll = new SimpleBooleanProperty();
        autoUpdate = new SimpleBooleanProperty();
    }

    @FXML
    public void initialize() {
        autoScroll.bind(autoScrollButton.selectedProperty());
        chatVersionLabel.textProperty().bind(Bindings.concat("Chat Version: ", chatVersion.asString()));
    }

    public TextArea getChatLineTextArea() {
        return chatLineTextArea;
    }

    public void setChatRoomMainController(ChatRoomMainController chatRoomMainController) {
        this.chatRoomMainController = chatRoomMainController;
    }

    public void setAdmin(boolean admin) {isAdmin = admin;}

    public BooleanProperty autoUpdatesProperty() {
        return autoUpdate;
    }

    @FXML
    public void sendButtonClicked(ActionEvent event) {
        String chatLine = chatLineTextArea.getText();
        if(!isAdmin)
         chatRoomMainController.getCustomerScreenController().sendMessage(chatLine);
        else
            chatRoomMainController.getAdminScreenController().sendMessage(chatLine);
    }

    public void setHttpStatusUpdate(HttpStatusUpdate chatRoomMainController) {
        this.httpStatusUpdate = chatRoomMainController;
    }

    private void updateChatLines(ChatLinesWithVersion chatLinesWithVersion) {
        if (chatLinesWithVersion.getVersion() != chatVersion.get()) {
            String deltaChatLines = chatLinesWithVersion
                    .getEntries()
                    .stream()
                    .map(singleChatLine -> {
                        long time = singleChatLine.getTime();
                        return String.format(CHAT_LINE_FORMATTING, time, time, time, singleChatLine.getUsername(), singleChatLine.getChatString());
                    }).collect(Collectors.joining());

            Platform.runLater(() -> {
                chatVersion.set(chatLinesWithVersion.getVersion());

                if (autoScroll.get()) {
                    mainChatLinesTextArea.appendText(deltaChatLines);
                    mainChatLinesTextArea.selectPositionCaret(mainChatLinesTextArea.getLength());
                    mainChatLinesTextArea.deselect();
                } else {
                    int originalCaretPosition = mainChatLinesTextArea.getCaretPosition();
                    mainChatLinesTextArea.appendText(deltaChatLines);
                    mainChatLinesTextArea.positionCaret(originalCaretPosition);
                }
            });
        }
    }

    public void startListRefresher() {
        chatAreaRefresher = new ChatAreaRefresher(
                chatVersion,
                autoUpdate,
                httpStatusUpdate::updateHttpLine,
                this::updateChatLines, isAdmin);
        timer = new Timer();
        timer.schedule(chatAreaRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    @Override
    public void close() throws IOException {
        chatVersion.set(0);
        chatLineTextArea.clear();
        if (chatAreaRefresher != null && timer != null) {
            chatAreaRefresher.cancel();
            timer.cancel();
        }
    }
}