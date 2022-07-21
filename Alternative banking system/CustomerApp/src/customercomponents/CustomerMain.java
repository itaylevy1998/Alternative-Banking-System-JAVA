package customercomponents;

import customercomponents.customerscreen.CustomerScreenController;
import customercomponents.customerlogin.CustomerLoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import userinterface.MainController.MainController;
//import userinterface.admin.topAdmin.TopAdminController;
//import userinterface.customer.TopCustomerController;

import java.net.URL;

public class CustomerMain extends Application {
    private final double WIDTH = 1200;
    private final double HEIGHT = 550;
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Customer Login scene
        FXMLLoader customerLogin = new FXMLLoader();
        URL customerLoginFXML = getClass().getResource("/customercomponents/customerlogin/customerLogin.fxml");
        customerLogin.setLocation(customerLoginFXML);
        Parent root1 = customerLogin.load();
        CustomerLoginController customerLoginController = customerLogin.getController();
        customerLoginController.setPrimaryStage(primaryStage);
        Scene customerLoginScene = new Scene(root1, WIDTH, HEIGHT);

        //Customer Screen scene
        FXMLLoader customerScreen = new FXMLLoader();
        URL customerScreenFXML = getClass().getResource("/userinterface/customer/customerScreen.fxml");
        customerScreen.setLocation(customerScreenFXML);
        Parent root2 = customerScreen.load();
        CustomerScreenController customerScreenController = customerScreen.getController();
        Scene customerScreenScene = new Scene(root2,WIDTH,HEIGHT);

        customerLoginController.setCustomerScreenScene(customerScreenScene);
        customerLoginController.setCustomerScreenController(customerScreenController);
        customerScreenController.setPrimaryStage(primaryStage);
        customerScreenController.setCustomerLoginController(customerLoginController);

        //Start program
        primaryStage.setTitle("Login");
        primaryStage.setScene(customerLoginScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

