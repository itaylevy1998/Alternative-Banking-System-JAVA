package admincomponents.adminlogin;


import admincomponents.adminscreen.AdminScreenController;
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

import static userinterface.Constants.RESPONSE_ERROR;

public class AdminLoginController {

    //JAVAFX components
    @FXML private TextField nameTextField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;

    //Regular fields
    private Stage primaryStage;
    private Scene adminScreenScene;
    private AdminScreenController adminScreenController;


    //Constructor
    public AdminLoginController() {

    }

    @FXML
    private void initialize() {

    }

    //Setters
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void setAdminScreenScene(Scene adminScreenScene) {
        this.adminScreenScene = adminScreenScene;
    }
    public void setAdminScreenController(AdminScreenController adminScreenController) {
        this.adminScreenController = adminScreenController;
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
        String finalUrl = HttpUrl.parse(Constants.FULL_PATH_DOMAIN + Constants.LOGIN_RESOURCE)
                .newBuilder()
                .addQueryParameter("userName", userName).addQueryParameter("isAdmin", "true")
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrl).post(RequestBody.create("", MediaType.parse("")))
                .build();

        HttpUtil.runAsync(request, true, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() ->
                        Notifications.create().title("Error").text(RESPONSE_ERROR).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() != 200) {
                    Platform.runLater(() ->
                    {
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                        } catch (IOException e) {

                        }
                        response.body().close();
                    });

                } else {
                    Platform.runLater(() -> {
                        primaryStage.setTitle("Admin");
                        primaryStage.setScene(adminScreenScene);
                        adminScreenController.setPrimaryStage(primaryStage);
                        adminScreenController.setUserName(userName);
                        adminScreenController.getNameLabel().setText(adminScreenController.getNameLabel().getText() + userName);
                        adminScreenController.startInfoRefresh();
                        response.body().close();
                    });
                }
            }

        });
    }
}