package admincomponents;

import admincomponents.adminlogin.AdminLoginController;
import admincomponents.adminscreen.AdminScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import userinterface.MainController.MainController;
//import userinterface.admin.topAdmin.TopAdminController;
//import customercomponents.customerscreen.TopCustomerController;

import java.net.URL;

public class AdminMain extends Application {
    private final double WIDTH = 1200;
    private final double HEIGHT = 550;
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Admin Login scene
        FXMLLoader adminLogin = new FXMLLoader();
        URL adminLoginFXML = getClass().getResource("/admincomponents/adminlogin/adminLogin.fxml");
        adminLogin.setLocation(adminLoginFXML);
        Parent root1 = adminLogin.load();
        AdminLoginController adminLoginController = adminLogin.getController();
        adminLoginController.setPrimaryStage(primaryStage);
        Scene adminLoginScene = new Scene(root1, WIDTH, HEIGHT);

        //Admin Screen scene
        FXMLLoader adminScreen = new FXMLLoader();
        URL adminScreenFXML = getClass().getResource("/userinterface/admin/adminScreen.fxml");
        adminScreen.setLocation(adminScreenFXML);
        Parent root2 = adminScreen.load();
        AdminScreenController adminScreenController = adminScreen.getController();
        Scene adminScreenScene = new Scene(root2,WIDTH,HEIGHT);

        adminLoginController.setAdminScreenScene(adminScreenScene);
        adminLoginController.setAdminScreenController(adminScreenController);

        //Start program
        primaryStage.setTitle("Login");
        primaryStage.setScene(adminLoginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
