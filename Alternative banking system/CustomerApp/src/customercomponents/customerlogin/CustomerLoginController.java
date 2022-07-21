package customercomponents.customerlogin;

import customercomponents.customerscreen.CustomerScreenController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import okhttp3.*;
import org.controlsfx.control.Notifications;
import userinterface.Constants;
import userinterface.utils.HttpUtil;

import java.io.IOException;

public class CustomerLoginController {

    //JAVAFX components
    @FXML private TextField nameTextField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;

    //Regular fields
    private Stage primaryStage;
    private Scene customerScreenScene;
    private CustomerScreenController customerScreenController;

    //Setters
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setCustomerScreenScene(Scene customerScreenScene) {
        this.customerScreenScene = customerScreenScene;
    }

    public void setCustomerScreenController(CustomerScreenController customerScreenController) {
        this.customerScreenController = customerScreenController;
    }

    //Regular Methods
    @FXML
    void loginOnAction(ActionEvent event) {
        String userName = nameTextField.getText();
        if (userName.equals("")) {
            errorLabel.setVisible(true);
            return;
        }
        errorLabel.setVisible(false);
        String finalUrl = HttpUrl.parse(Constants.FULL_PATH_DOMAIN + Constants.LOGIN_RESOURCE).newBuilder()
                .addQueryParameter("userName", userName).addQueryParameter("isAdmin", "false")
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrl).post(RequestBody.create("", MediaType.parse("")))
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() ->
                        Notifications.create().title("Error").text("Can't communicate with the server!").hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Platform.runLater(() -> {
                            Notifications.create().title("Error").text("This user name is already in use!").hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                            response.body().close();});
                } else {
                    Platform.runLater(() -> {
                        primaryStage.setTitle(userName);
                        primaryStage.setScene(customerScreenScene);
                        customerScreenController.setUserName(userName);
                        customerScreenController.getNameLabel().setText(customerScreenController.getNameLabel().getText() + userName);
                        customerScreenController.startInfoRefresh(userName);
                        customerScreenController.getChatRoomMainController().setActive();
                        response.body().close();

                    });
                }
            }
        });
    }
}