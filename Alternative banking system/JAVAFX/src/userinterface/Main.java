package userinterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.net.URL;

public class Main extends Application {
    private final double WIDTH = 1200;
    private final double HEIGHT = 550;
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Admin
        FXMLLoader loaderAdmin = new FXMLLoader();
        URL adminFXML = getClass().getResource("/userinterface/admin/topAdmin/TopAdmin.fxml");
        loaderAdmin.setLocation(adminFXML);
        Parent root1 = loaderAdmin.load();
//        TopAdminController topAdminController = loaderAdmin.getController();
        Scene AdminScene = new Scene(root1, WIDTH, HEIGHT);

        //Customer
        FXMLLoader loaderCustomer = new FXMLLoader();
        URL customerFXML = getClass().getResource("/userinterface/customer/customerScreen.fxml");
        loaderCustomer.setLocation(customerFXML);
        Parent root2 = loaderCustomer.load();
        //TopCustomerController topCustomerController = loaderCustomer.getController();
        Scene CustomerScene = new Scene(root2, WIDTH, HEIGHT);

        //Main Controller
//        MainController mainController = new MainController(primaryStage, topAdminController, AdminScene, topCustomerController,CustomerScene);
//        mainController.setSubControllers();

        //Start program
        primaryStage.setTitle("Alternative Banking System");
        primaryStage.setScene(AdminScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
