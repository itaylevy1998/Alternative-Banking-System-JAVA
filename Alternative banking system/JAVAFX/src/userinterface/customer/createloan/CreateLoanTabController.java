package userinterface.customer.createloan;

import customercomponents.customerscreen.CustomerScreenController;
import exceptions.filesexepctions.TimeOfPaymentNotDivideEqualyException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.util.Duration;
import okhttp3.*;
import org.controlsfx.control.Notifications;
import userinterface.utils.HttpUtil;

import java.io.IOException;
import java.util.List;

import static userinterface.Constants.*;

public class CreateLoanTabController {
    //JAVAFX components
    @FXML
    private TextField loanNameTF;
    @FXML
    private ChoiceBox categoryCB;
    @FXML
    private TextField amountTF;
    @FXML
    private Label nameError;
    @FXML
    private TextField durationTF;
    @FXML
    private TextField timePerPaymentTF;
    @FXML
    private TextField InterestPerPaymentTF;
    @FXML
    private Label categoryError;
    @FXML
    private Label amountError;
    @FXML
    private Label durationError;
    @FXML
    private Label timePerPaymentError;
    @FXML
    private Label InterestPerPaymentError;
    @FXML
    private Label createError;

    //Regular fields
    private int duration;
    private int timePerPayment;
    private double amount;
    private int InterestPerPayment;
    private CustomerScreenController customerScreenController;
    private String category;

    @FXML
    private void initialize() {
    }

    public void setCustomerScreenController(CustomerScreenController customerScreenController) {
        this.customerScreenController = customerScreenController;
    }

    public void setCategoryCB(List<String> categories) {
        this.categoryCB.setItems(FXCollections.observableArrayList(categories));
    }

    public void setNameError(String nameError) {
        this.nameError.setText(nameError);
    }

    //Regular Methods
    @FXML
    public void createNewLoanOnAction(ActionEvent event) {
        if(checkInputs()){
            /** Here we will make a new HTTP call which shall call the relative engine method which will add the new loan.
             * NEED TO CHECK IF THERE IS A SYNC REQUIREMENT!!!!!
             * I suggest we send the information via gson.*/
            customerScreenController.LoanNameCheckAndCreate(loanNameTF.getText(), category, amount, duration, timePerPayment, InterestPerPayment);
        } else{

        }
    }

    public boolean checkInputs(){
        amount = getLoansAmount();
        duration = getLoansDuration();
        timePerPayment = getTimePerPayment();
        InterestPerPayment = getInterestPerPayment();
        boolean categoriesOK = true;
        boolean nameOK = true;
        if(loanNameTF.getText().equals(""))
        {
            nameError.setText("Please insert a name!");
            nameOK = false;
        } else{
            nameError.setText("");
        }
        if(!checkCategory()) {
            categoriesOK = false;
        }
        else {
            category = categoryCB.getSelectionModel().getSelectedItem().toString();
            categoryError.setText("");
        }



        if(amount==INVALID)
            return false;
        else
        if(duration==INVALID)
            return false;
        if(timePerPayment==INVALID)
            return false;
        if (categoriesOK == false) {
            return false;
        }
        if(nameOK == false){
            return false;
        }


        return true;
    }

    public double getLoansAmount(){

        String loansAmount = amountTF.getText();
        try {
            Double amount = Double.parseDouble(loansAmount); //NumberFormatException
            if(amount < 1){
                throw new Exception();
            }
            amountError.setText("");
            return amount;
        } catch (NumberFormatException e){
            amountError.setText("Invalid input. Please enter a positive Integer!");
        } catch (Exception e) {
            amountError.setText("Please enter a number Above 0!");
        }

        return INVALID;
    }

    public int getLoansDuration(){
        String loansDuration = durationTF.getText();
        try {
            int duration = Integer.parseInt(loansDuration); //NumberFormatException
            if(duration < 1){
                throw new Exception();
            }
            durationError.setText("");
            return duration;
        } catch (NumberFormatException e){
            durationError.setText("Invalid input. Please enter a positive Integer!");
        } catch (Exception e) {
            durationError.setText("Please enter a number Above 0!");
        }
        return INVALID;
    }

    public int getTimePerPayment(){
        String timeBetweenPayment = timePerPaymentTF.getText();
        try{
            int periodPayment = Integer.parseInt(timeBetweenPayment);
            if(periodPayment < 1)
                throw new Exception();
            if(duration % periodPayment != 0)
                throw new TimeOfPaymentNotDivideEqualyException(duration, periodPayment);
            return periodPayment;
        }catch(NumberFormatException e){
            timePerPaymentError.setText("Invalid input. Please enter a positive Integer!");
        }catch (TimeOfPaymentNotDivideEqualyException e){
            timePerPaymentError.setText(e.toString());
        } catch (Exception e) {
            timePerPaymentError.setText("Please enter a number Above 0!");
        }
        return INVALID;
    }
    public int getInterestPerPayment(){

        String interestPerPayment = InterestPerPaymentTF.getText();
        try {
            int interest = Integer.parseInt(interestPerPayment); //NumberFormatException
            if(interest < 1){
                throw new Exception();
            }
            InterestPerPaymentError.setText("");
            return interest;
        } catch (NumberFormatException e){
            InterestPerPaymentError.setText("Invalid input. Please enter a positive Integer!");
        } catch (Exception e) {
            InterestPerPaymentError.setText("Please enter an Integer Above 0!");
        }

        return INVALID;
    }
    public boolean checkCategory(){
        if(categoryCB.getSelectionModel().getSelectedItem()==null) {
            categoryError.setText("Please Choose a category!");
            return false;
        }
        categoryError.setText("");
        return true;
    }

    public void resetFields(){
        category = "";
        amountTF.setText("");
        durationTF.setText("");
        loanNameTF.setText("");
        timePerPaymentTF.setText("");
        InterestPerPaymentTF.setText("");
        nameError.setText("");
        amountError.setText("");
        categoryError.setText("");
        durationError.setText("");
        timePerPaymentError.setText("");
        InterestPerPaymentError.setText("");
    }

}
