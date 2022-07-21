package admincomponents.adminscreen;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.admin.LoanAndCustomerInfoDTO;
import okhttp3.*;
import org.controlsfx.control.*;
import userinterface.chat.chatroom.ChatRoomMainController;
import userinterface.table.customerTable.CustomerTableController;
import userinterface.table.loantable.*;
import userinterface.utils.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;

import static userinterface.Constants.*;

public class AdminScreenController {

    //Constants
    private final String THEMEDEFAULT = "/userinterface/admin/TopAdminDefault.css";
    private final String THEMEDARK = "/userinterface/admin/TopAdminDark.css";
    private final String THEMEBRIGHT = "/userinterface/admin/TopAdminBright.css";
    private final String DEFAULT = "Default";
    private final String DARK = "Dark";
    private final String BRIGHT = "Bright";
    private final String YAZSTATEMENT = "Current Yaz: " ;

    //Sub components
    @FXML private ScrollPane newLoan;
    @FXML private NewLoanTableController newLoanController;

    @FXML private ScrollPane pendingLoan;
    @FXML private PendingLoanTableController pendingLoanController;

    @FXML private ScrollPane activeLoan;
    @FXML private ActiveLoanTableController activeLoanController;

    @FXML private ScrollPane riskLoan;
    @FXML private RiskLoanTableController riskLoanController;

    @FXML private ScrollPane finishedLoan;
    @FXML private FinishedLoanTableController finishedLoanController;

    @FXML private ScrollPane customerTable;
    @FXML private CustomerTableController customerTableController;
    @FXML private ChatRoomMainController chatRoomMainController;

    //JavaFX components
    @FXML private Button IncreaseYazBUTTON;
    @FXML private Label currentYazLabel;
    @FXML private Label nameLabel;
    @FXML private ComboBox<String> skinCB;
    @FXML private ToggleSwitch rewindToggleSwitch;
    @FXML private ComboBox<String> rewindCB;
    @FXML private Button chatRoomButton;
    @FXML private AnchorPane AdminAP;
    @FXML private ScrollPane MainSP;

    //Regular Fields
    private Timer timer;
    private String userName;
    private Stage primaryStage;
    private boolean popUpExist = false;

    //Constructor
    public AdminScreenController() {

    }
    //initialize after constructor
    @FXML
    private void initialize()  {
        skinCB.getItems().addAll(DEFAULT,BRIGHT,DARK);
        rewindCB.getItems().add("1");
        MainSP.getStylesheets().add(THEMEDEFAULT);
        FXMLLoader loaderPopUp = new FXMLLoader();
        URL popUpFXML = getClass().getResource("/userinterface/chat/chatroom/chat-room-main.fxml");
        loaderPopUp.setLocation(popUpFXML);
        try {
            Parent root1 = loaderPopUp.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        chatRoomMainController = loaderPopUp.getController();
        chatRoomMainController.setAdminScreenController(this);
        chatRoomMainController.setPopUpScene();
        rewindToggleSwitch.selectedProperty().addListener((ov, oldValue, newValue) -> {
            rewindCB.setDisable(!rewindCB.isDisable());
            if (newValue) {
                activateRewind();
            } else{
                deactivateRewind();
            }
        });
    }

    //Getters
    public Label getNameLabel() {return nameLabel;}
    public NewLoanTableController getNewLoanController() {return newLoanController;}
    public FinishedLoanTableController getFinishLoanController() {return finishedLoanController;}
    public PendingLoanTableController getPendingLoanController() {return pendingLoanController;}
    public ActiveLoanTableController getActiveLoanController() {return activeLoanController;}
    public RiskLoanTableController getRiskLoanController() {return riskLoanController;}
    public CustomerTableController getCustomerTableController() {return customerTableController;}


    //Setters
    public void setUserName(String userName) {this.userName = userName;}

    public void setPrimaryStage(Stage primaryStage) {this.primaryStage = primaryStage;}

    @FXML
    public void SetSkinCBOnAction(ActionEvent actionEvent) {
        MainSP.getStylesheets().clear();
        switch(skinCB.getValue()){
            case DEFAULT:{
                MainSP.getStylesheets().add(THEMEDEFAULT);
                break;
            }
            case DARK:{
                MainSP.getStylesheets().add(THEMEDARK);
                break;
            }
            case BRIGHT:{
                MainSP.getStylesheets().add(THEMEBRIGHT);
                break;
            }
        }
    }
    //Regular Methods
    @FXML
   public void increaseYazOnAction(ActionEvent event){
        RequestBody body = RequestBody.create("", MediaType.parse("txt"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + ADMIN_INCREASE_YAZ_RESOURCE )
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();
        HttpUtil.runAsync(request, true, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() -> Notifications.create().title("Error").text(RESPONSE_ERROR).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    response.body().close();
                } else{
                    Platform.runLater(() ->{
                        String jsonYaz = null;
                        try {
                            jsonYaz = response.body().string();
                        } catch (IOException e) {

                        }
                        String YAZ = GSON_INSTANCE.fromJson(jsonYaz, String.class);
                        currentYazLabel.setText(YAZSTATEMENT + YAZ);
                        rewindCB.getItems().add(YAZ);
                        rewindCB.getSelectionModel().selectLast();
                        response.body().close();
                    });
                }
            }
        });
    }

    public void startInfoRefresh(){
        AdminInfoRefresher adminInfoRefresher = new AdminInfoRefresher(this);
        timer = new Timer();
        timer.schedule(adminInfoRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    public void activateRewind(){
        RequestBody body = RequestBody.create("", MediaType.parse("txt"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + ADMIN_ACTIVATE_REWIND_RESOURCE )
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();
        HttpUtil.runAsync(request, true, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() -> Notifications.create().title("Error").text(RESPONSE_ERROR).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    response.body().close();
                } else{
                    Platform.runLater(() -> {
                        rewindCB.setDisable(false);
                        IncreaseYazBUTTON.setDisable(true);
                        try {
                            chatRoomMainController.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        chatRoomButton.setDisable(true);
                        response.body().close();
                    });
                }
            }
        });
    }

    public void deactivateRewind(){
        RequestBody body = RequestBody.create("", MediaType.parse("txt"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + ADMIN_DEACTIVATE_REWIND_RESOURCE )
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();
        HttpUtil.runAsync(request, true, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    response.body().close();
                } else{
                    Platform.runLater(() -> {
                        rewindCB.getSelectionModel().selectLast();
                        rewindCB.setDisable(true);
                        IncreaseYazBUTTON.setDisable(false);
                        chatRoomButton.setDisable(false);
                        chatRoomMainController.setActive();
                        response.body().close();
                    });
                }
            }
        });
    }

    @FXML
   public void changeTimeForEngine(ActionEvent event){
        if(!rewindToggleSwitch.isSelected()){
            return;
        }
        RequestBody body = RequestBody.create("", MediaType.parse("txt"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + ADMIN_REWIND_TIME_RESOURCE )
                .newBuilder()
                .addQueryParameter("TimeToMove", rewindCB.getValue())
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();
        HttpUtil.runAsync(request, true, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Notifications.create().title("Error").text(RESPONSE_ERROR).hideAfter(Duration.seconds(3)).position(Pos.CENTER).showError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    response.body().close();
                } else{
                    response.body().close();
                }
            }
        });

    }

    @FXML
    public void chatRoomOnAction(ActionEvent event){
        chatRoomMainController.setPopUp(primaryStage, popUpExist);
        chatRoomMainController.getChatAreaComponentController().setAdmin(true);
        if(!popUpExist) {
            popUpExist = true;
        }
        chatRoomMainController.setActive();

    }

    public void sendMessage(String chatLine){
        String finalUrlInformation = HttpUrl
                .parse(FULL_PATH_DOMAIN + SEND_CHAT_LINE)
                .newBuilder()
                .addQueryParameter("userstring", chatLine)
                .build()
                .toString();


        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .build();
        HttpUtil.runAsync(request, true ,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                }
                else
                    chatRoomMainController.getChatAreaComponentController().getChatLineTextArea().clear();
                response.body().close();
            }
        });
    }

}
