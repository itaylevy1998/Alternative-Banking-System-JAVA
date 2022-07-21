package userinterface.customer.payments;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class FinishAnimationController {

    @FXML private AnchorPane finishImageAP;
    @FXML
    private ImageView finishImage;
    private ScaleTransition scaleTransition;
    private TranslateTransition translateTransition;
    @FXML
    public void initialize(){
        scaleTransition = new ScaleTransition();
        translateTransition = new TranslateTransition();
    }
    public void setAnimation(){
        scaleTransition.setNode(finishImage);
        scaleTransition.setDuration(Duration.millis(2000));
        scaleTransition.setCycleCount(2);
        scaleTransition.setInterpolator(Interpolator.LINEAR);
        scaleTransition.setByX(0.5);
        scaleTransition.setByY(0.5);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
//        translateTransition.setNode(finishImage);
//        translateTransition.setDuration(Duration.millis(2000));
//        translateTransition.setCycleCount(1);
//        translateTransition.setByX(1);
//        //translateTransition.setByY(0);
//        translateTransition.setAutoReverse(true);
//        translateTransition.play();
    }

}
