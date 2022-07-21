package customercomponents.customerscreen;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import customercomponents.customerlogin.CustomerLoginController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.customers.CustomerInfoDTO;
import objects.customers.CustomerInfoInlayDTO;
import objects.customers.CustomersRelatedInfoDTO;
import objects.customers.PaymentUpdateDTO;
import objects.customers.loanInfo.BuySellUpdateDTO;
import objects.customers.loanInfo.CustomerFilterLoansDTO;
import objects.customers.loanInfo.LoanInfoDTO;
import objects.loans.*;
import objects.loans.payments.PaymentNotificationDTO;
import okhttp3.*;
import org.controlsfx.control.Notifications;
import userinterface.chat.chatarea.ChatAreaController;
import userinterface.chat.chatroom.ChatRoomMainController;
import userinterface.customer.createloan.CreateLoanTabController;
import userinterface.customer.information.InformationTabController;
import userinterface.customer.inlay.InlayTabController;
import userinterface.customer.loanforsell.LoanSellTabController;
import userinterface.customer.payments.PaymentsTabController;
import userinterface.table.loantable.NewLoanTableController;
import userinterface.table.loantable.tableobject.NewLoanTableObject;
import userinterface.utils.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

import static userinterface.Constants.*;
import static userinterface.Constants.GSON_INSTANCE;

public class CustomerScreenController {


    //constants
    private final String YAZSTATEMENT = "Current Yaz: " ;
    private final String FILESTATEMENT = "File: " ;
    private final String ADMIN = "Admin";
    private final String THEMEDEFAULT = "/userinterface/customer/TopCustomerDefault.css";
    private final String THEMEDARK = "/userinterface/customer/TopCustomerDark.css";
    private final String THEMEBRIGHT = "/userinterface/customer/TopCustomerBright.css";
    private final String DEFAULT = "Default";
    private final String DARK = "Dark";
    private final String BRIGHT = "Bright";

    //SubComponents
    @FXML private AnchorPane informationTab;
    @FXML private InformationTabController informationTabController;

    @FXML private AnchorPane paymentsTab;
    @FXML private PaymentsTabController paymentsTabController;

    @FXML private ScrollPane inlayTab;
    @FXML private InlayTabController inlayTabController;

    @FXML private ScrollPane createLoanTab;
    @FXML private CreateLoanTabController createLoanTabController;

    @FXML private ScrollPane loanSellTab;
    @FXML private LoanSellTabController loanSellTabController;

    @FXML private BorderPane chatRoomTab;
    @FXML private ChatRoomMainController chatRoomTabController;


    //JavaFX
    @FXML private ComboBox<String> ThemeCB;
    @FXML private ScrollPane MainSP;
    @FXML private BorderPane MainBP;
    @FXML private Label YazLABEL;
    @FXML private TabPane customerOptionsTB;
    @FXML private Label serverStatusLabel;
    @FXML private Label nameLabel;
    @FXML private Button loadFileButton;

    @FXML private Tab information;
    @FXML private Tab inlay;
    @FXML private Tab payments;
    @FXML private Tab createLoan;
    @FXML private Tab buySellLoans;
    @FXML private Tab chatRoom;

    //Regular Fields
    private Stage primaryStage;
    private String userName;
    private CustomerLoginController customerLoginController;
    private Integer lastSeenYaz;
    private Timer timer;

    //constructor
    public CustomerScreenController(){

    }

    @FXML
    private void initialize() {
        informationTabController.setCustomerScreenController(this);
        paymentsTabController.setCustomerScreenController(this);
        inlayTabController.setCustomerScreenController(this);
        loanSellTabController.setCustomerScreenController(this);
        createLoanTabController.setCustomerScreenController(this);
        chatRoomTabController.setCustomerScreenController(this);
        customerOptionsTB.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            //String user = UserCB.getValue();
            switch(newTab.getText()){
                case "Information":{
                    break;
                } case "Inlay":{
                    updateInlayTab();
                    break;
                } case "Payments":{
                    updatePayments();
                    break;
                }
                case "Buy/Sell Loans":{
                    updateLoanSellTab();
                    break;
                }
                case "Create Loan":{
                    updateCreateLoanTab();
                    break;
                }
            }
        });
        ThemeCB.getItems().addAll(DEFAULT,BRIGHT,DARK);
        MainSP.getStylesheets().add(THEMEDEFAULT);
        lastSeenYaz = 1;
    }

    //getters
    public Label getNameLabel() {return nameLabel;}
    public Label getYazLABEL() {return YazLABEL;}
    public ScrollPane getMainSP() {return MainSP;}
    public ComboBox<String> getThemeCB() {return ThemeCB;}
    public String getUserName() {return userName;}
    public Stage getPrimaryStage() {return primaryStage;}
    public InformationTabController getInformationTabController() {
        return informationTabController;
    }
    public PaymentsTabController getPaymentsTabController() {
        return paymentsTabController;
    }
    public InlayTabController getInlayTabController() {
        return inlayTabController;
    }
    public ChatRoomMainController getChatRoomMainController() {return chatRoomTabController;}

    //Setters
    public void setPrimaryStage(Stage primaryStage) {this.primaryStage = primaryStage;}
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setCustomerLoginController(CustomerLoginController customerLoginController) {
        this.customerLoginController = customerLoginController;
    }

    //Regular Methods
    @FXML
    public void SetThemeCBOnAction(ActionEvent actionEvent) {
        MainSP.getStylesheets().clear();
        switch(ThemeCB.getValue()){
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

    public void startInfoRefresh(String customerName){
        CustomerInfoRefresher customerInfoRefresher = new CustomerInfoRefresher(this, customerName);
        timer = new Timer();
        timer.schedule(customerInfoRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    public void startChatRefresh(){

    };
    public void updateInformationTab (String UserPick, List<NewLoanDTO> newLoans, List<PendingLoanDTO> pendingLoans, List<ActiveRiskLoanDTO> activeLoans, List<ActiveRiskLoanDTO> riskLoans , List<FinishedLoanDTO> finishedLoans, CustomerInfoDTO customerInfo,String currentYaz, String serverStatus){
        //Transactions and balance
        informationTabController.setUserName(UserPick);
        informationTabController.getTransactionInfoController().setTableValues(customerInfo);
        informationTabController.getBalanceLabel().setText("Balance: "+ customerInfo.getBalance());

        //Loaner Tables
        informationTabController.getNewLoanerTableController().setValues(newLoans);
        informationTabController.getPendingLoanerTableController().setValues(pendingLoans.stream().filter(l -> l.getBorrowerName().equals(UserPick)).collect(Collectors.toList()));
        informationTabController.getActiveLoanerTableController().setValues(activeLoans.stream().filter(l -> l.getBorrowerName().equals(UserPick)).collect(Collectors.toList()));
        informationTabController.getRiskLoanerTableController().setValues(riskLoans.stream().filter(l -> l.getBorrowerName().equals(UserPick)).collect(Collectors.toList()));
        informationTabController.getFinishedLoanerTableController().setValues(finishedLoans.stream().filter(l -> l.getBorrowerName().equals(UserPick)).collect(Collectors.toList()));

        //Lender Tables
        informationTabController.getPendingLenderTableController().setValues(pendingLoans.stream().filter(l -> l.getListOfLenders().containsKey(UserPick)).collect(Collectors.toList()));
        informationTabController.getActiveLenderTableController().setValues(activeLoans.stream().filter(l -> l.getListOfLenders().containsKey(UserPick)).collect(Collectors.toList()));
        informationTabController.getRiskLenderTableController().setValues(riskLoans.stream().filter(l -> l.getListOfLenders().containsKey(UserPick)).collect(Collectors.toList()));
        informationTabController.getFinishedLenderTableController().setValues(finishedLoans.stream().filter(l -> l.getListOfLenders().containsKey(UserPick)).collect(Collectors.toList()));

        serverStatusLabel.setText("Server Status: " + serverStatus);
        YazLABEL.setText(YAZSTATEMENT + currentYaz);

    }

    public void chargeMoney(final Double amount){
        RequestBody body = RequestBody.create("", MediaType.parse("txt"));

        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_CHARGE_MONEY)
                .newBuilder()
                .addQueryParameter(AMOUNT, amount.toString())
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("call = " + call + ", e = " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    Platform.runLater(()-> {
                        try {
                            informationTabController.getTransactionInfoController().getPopUpController().setErrorMessage(response.body().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        response.body().close();
                    });
                }
                else{
                    CustomerInfoDTO customerInfo = GSON_INSTANCE.fromJson(response.body().string(), CustomerInfoDTO.class);
                    Platform.runLater(()->{
                        informationTabController.getBalanceLabel().setText("Balance: " + customerInfo.getBalance());
                        informationTabController.getTransactionInfoController().setTableValues(customerInfo);
                        informationTabController.getTransactionInfoController().getPopUpController().setErrorMessage(null);
                        informationTabController.getTransactionInfoController().getPopUpController().getPopUpStage().close();
                        response.body().close();
                    });

                }
            }
        });
    }
    public void withdrawMoney(final Double amount){
        RequestBody body = RequestBody.create("", MediaType.parse("txt"));

        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_WITHDRAW_MONEY)
                .newBuilder()
                .addQueryParameter(AMOUNT, amount.toString())
                .build()
                .toString();

        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("call = " + call + ", e = " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    Platform.runLater(()-> {
                        try {
                            informationTabController.getTransactionInfoController().getPopUpController().setErrorMessage(response.body().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        response.body().close();
                    });
                }
                else{
                    CustomerInfoDTO customerInfo = GSON_INSTANCE.fromJson(response.body().string(), CustomerInfoDTO.class);
                    Platform.runLater(()->{
                        informationTabController.getBalanceLabel().setText("Balance: " + customerInfo.getBalance());
                        informationTabController.getTransactionInfoController().setTableValues(customerInfo);
                        informationTabController.getTransactionInfoController().getPopUpController().setErrorMessage(null);
                        informationTabController.getTransactionInfoController().getPopUpController().getPopUpStage().close();
                        response.body().close();
                    });

                }
            }
        });
    }
    public void updatePayments(){
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_PAYMENT_INFO_RESOURCE)
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    PaymentUpdateDTO paymentUpdateDTO = GSON_INSTANCE.fromJson(response.body().string(), PaymentUpdateDTO.class);
                    Platform.runLater(() ->{
                        paymentsTabController.setValues(paymentUpdateDTO.getPaymentNotifications(), paymentUpdateDTO.getMakeActivePayment(),
                                paymentUpdateDTO.getRiskLoans(), paymentUpdateDTO.getCloseActiveLoans());
                        paymentsTabController.getCloseLoanError().setText("");
                        paymentsTabController.getCompletePaymentError().setText("");
                        paymentsTabController.getPaymentAmountTextField().setText("");
                        response.body().close();
                    });
                }
            }
        });
    }


    public void updateInlayTab(){
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_PULL_CATEGORIES_RESOURCE)
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Notifications.create().title("Error").text("Error getting call").hideAfter(Duration.seconds(3)).position(Pos.CENTER).showError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(!response.isSuccessful()) {
                    Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(3)).position(Pos.CENTER).showError();
                    Platform.runLater(()->{
                        inlayTabController.addCategoriesToCCB(null);
                        inlayTabController.resetFields();
                        response.body().close();});

                }
                else
                {
                    List<String> categories;
                    categories = Arrays.asList(GSON_INSTANCE.fromJson(response.body().string(), String[].class)).stream().collect(Collectors.toList());
                    Platform.runLater(()->{
                        inlayTabController.addCategoriesToCCB(categories);
                        inlayTabController.resetFields();
                        response.body().close();});
                }
            }
        });

    }

    public void getFilteredLoans(List<String> categories, Integer minInterest, Integer minYAZ, Integer maxOpenLoans , Integer amountToInvest){
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_INLAY_FILTER_RESOURCE)
                .newBuilder()
                .addQueryParameter("minInterest", String.valueOf(minInterest))
                .addQueryParameter("minYAZ", String.valueOf(minYAZ))
                .addQueryParameter("maxOpenLoans", String.valueOf(maxOpenLoans))
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()) {
                    Platform.runLater(() -> {
                        try {
                            String error = response.body().string();
                            Notifications.create().title("Error").text(error).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        response.body().close();
                    });
                } else
                {
                    String responseJson = response.body().string();
                    response.body().close();
                    CustomerFilterLoansDTO filteredLoans = GSON_INSTANCE.fromJson(responseJson, CustomerFilterLoansDTO.class);
                    List<NewLoanDTO> newLoans = filteredLoans.getNewLoans();
                    List<PendingLoanDTO> pendingLoans = filteredLoans.getPendingLoans();
                    Platform.runLater(()-> {
                        inlayTabController.inlayImplement(newLoans, pendingLoans);
                        response.body().close();});
                }
            }
        });
    }

    public void makeInlay(List<NewLoanDTO> loans, Integer amountToInvest, Integer maxOwnership){
        Gson gson = new Gson();
        String json = gson.toJson(loans.stream().map(NewLoanDTO::getLoanID).collect(Collectors.toList()));
        RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CHECK_INLAY_INPUT_RESOURCE)
                .newBuilder()
                .addQueryParameter(AMOUNT, String.valueOf(amountToInvest))
                .addQueryParameter("maxOwnership", String.valueOf(maxOwnership))
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("call = " + call + ", e = " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    Platform.runLater(()->{
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                        } catch (IOException e) {

                        }
                        response.body().close();
                    });
                } else{
                    Platform.runLater(() -> {
                        inlayTabController.resetFields();
                        Notifications successInlay = Notifications.create().title("Success").text("The Inlay was successfully complete!").hideAfter(Duration.seconds(5)).position(Pos.CENTER);
                        successInlay.showConfirm();
                        response.body().close();
                    });
                }
            }
        });
    }


    public void updateLoanSellTab(){
        Request request = new Request.Builder()
                .url(FULL_PATH_DOMAIN + CUSTOMER_BUYSELL_PULL_RESOURCE)
                .build();
        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    response.body().close();
                } else{
                    String responseJson = response.body().string();
                    response.body().close();
                    BuySellUpdateDTO buySellLoans = GSON_INSTANCE.fromJson(responseJson, BuySellUpdateDTO.class);
                    List<String> loansAvailableToSell = buySellLoans.getLoansAvailableToSell();
                    List<LoansForSaleDTO> loansAvailableToBuy = buySellLoans.getLoansAvailableToBuy();
                    Platform.runLater(() ->{
                        loanSellTabController.setValues(loansAvailableToSell,loansAvailableToBuy);
                        response.body().close();
                    });


                }
            }
        });

    }

    public void updateCreateLoanTab(){
        Request request = new Request.Builder()
                .url(FULL_PATH_DOMAIN + CUSTOMER_PULL_CATEGORIES_RESOURCE)
                .build();
        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("call = " + call + ", e = " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Platform.runLater(()->
                {
                    try {
                        createLoanTabController.setCategoryCB(Arrays.asList(GSON_INSTANCE.fromJson(response.body().string(), String[].class)));
                        createLoanTabController.resetFields();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    response.body().close();
                });
            }
        });
    }

    public void LoanNameCheckAndCreate(String loanID,String category, Double amount, Integer loanDuration, Integer timePerPayment, Integer loanInterest){
        RequestBody body = RequestBody.create(
                "", MediaType.parse("txt"));

            String finalUrlLoansTable = HttpUrl.parse(FULL_PATH_DOMAIN + CREATE_LOAN_RESOURCE)
                    .newBuilder()
                    .addQueryParameter("loanID", loanID)
                    .addQueryParameter("category", category)
                    .addQueryParameter(AMOUNT, amount.toString())
                    .addQueryParameter("loanDuration", loanDuration.toString())
                    .addQueryParameter("timePerPayment", timePerPayment.toString())
                    .addQueryParameter("loanInterest", loanInterest.toString())
                    .build()
                    .toString();
            Request request = new Request.Builder()
                    .url(finalUrlLoansTable)
                    .post(body)
                    .build();
            HttpUtil.runAsync(request, false, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("error");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Platform.runLater(()->{
                            Notifications.create().title("Loan Created!").text("The loan was successfully created!").hideAfter(Duration.seconds(3)).position(Pos.CENTER).showConfirm();
                            createLoanTabController.resetFields();
                            response.body().close();
                        });


                    } else{
                        Platform.runLater(()-> {
                            try {
                                Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(3)).position(Pos.CENTER).showError();
                            } catch (IOException e) {

                            }
                            response.body().close();
                        });
                    }
                }
            });

    }

    @FXML
    public void LoadFileOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        String absolutePath = selectedFile.getAbsolutePath();
        createFileRequest(absolutePath);
    }

    public void createFileRequest(String absolutePath){
        File f = new File(absolutePath);
        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("file1", f.getName(), RequestBody.create(f, MediaType.parse("text/xml")))
                        .build();

       Request request = new Request.Builder()
                .url(FULL_PATH_DOMAIN + UPLOAD_FILE)
                .post(body)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Platform.runLater(() ->
                        Notifications.create().title("Error").text("Unknown Error").hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    Platform.runLater(() ->
                    {
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                        } catch (IOException e) {
//                            throw new RuntimeException(e);
                        }
                        response.body().close();
                    });
                } else{
                    Platform.runLater(() ->
                    {
                        try {
                            Notifications.create().title("Success").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showConfirm();
                        } catch (IOException e) {
//
                        }
                        response.body().close();
                    });
                }

            }
        });

    }
    public void makePayment(String loanID, String activeOrRisk, Double amountToPay){
        RequestBody body = RequestBody.create(
                "", MediaType.parse("txt"));
        String finalUrlLoansTable;
        if(activeOrRisk.equalsIgnoreCase("Active")){
            finalUrlLoansTable = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_MAKE_ACTIVE_PAYMENT_RESOURCE)
                    .newBuilder()
                    .addQueryParameter("loanID", loanID)
                    .build()
                    .toString();
        } else{
            finalUrlLoansTable = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_MAKE_RISK_PAYMENT_RESOURCE)
                    .newBuilder()
                    .addQueryParameter("loanID", loanID)
                    .addQueryParameter(AMOUNT, String.valueOf(amountToPay))
                    .build()
                    .toString();
        }

        Request request = new Request.Builder()
                .url(finalUrlLoansTable)
                .post(body)
                .build();
        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    Platform.runLater(()->{
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        paymentsTabController.getCompletePaymentError().setText("");
                        response.body().close();
                    });

                } else{
                    Platform.runLater(()->{
                        Notifications.create().text("Payment completed Successfully!").hideAfter(Duration.seconds(5)).position(Pos.CENTER).showConfirm();
                        updatePayments();
                        response.body().close();
                    });

                }
            }
        });
    }

    public void closeLoan(String loanName){
        RequestBody body = RequestBody.create(
                "", MediaType.parse("txt"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_CLOSE_LOAN_RESOURCE)
                .newBuilder()
                .addQueryParameter("loanID", loanName)
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation).post(body)
                .build();
        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful())
                {
                    Platform.runLater(()->
                    {
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(3)).position(Pos.CENTER).showError();
                            paymentsTabController.getCloseLoanError().setText("");

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        response.body().close();
                    });

                }
                else {
                    Platform.runLater(() -> {
                        Notifications.create().title("Success").text("The loan was successfully closed!").hideAfter(Duration.seconds(4)).position(Pos.CENTER).showConfirm();
                        updatePayments();
                        response.body().close();
                    });

                }
            }
        });
    }
    public void putLoansOnSale(List<String> loansToSale){
        Gson gson = new Gson();
        String json = gson.toJson(loansToSale, new TypeToken<List<String>>(){}.getType());
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_SELL_LOANS_RESOURCE )
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();
        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Platform.runLater(() -> {
                    if (!response.isSuccessful()) {
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                        } catch (IOException e) {

                        }

                    } else {
                        Notifications.create().title("Success").text("The chosen loans has moved to the transfer list!").hideAfter(Duration.seconds(5)).position(Pos.CENTER).showConfirm();
                    }
                    updateLoanSellTab();
                    response.body().close();
                });
            }
        });
    }
    public void buyLoan(LoansForSaleDTO loansForSale){
        Gson gson = new Gson();
        String json = gson.toJson(loansForSale, new TypeToken<LoansForSaleDTO>(){}.getType());
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_BUY_LOAN_RESOURCE )
                .newBuilder()
                .build()
                .toString();
        Request request = new Request.Builder()
                .url(finalUrlInformation)
                .post(body)
                .build();

        HttpUtil.runAsync(request, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Platform.runLater(()->{
                    if(response.isSuccessful()){
                        Notifications.create().title("Success").text("Loan purchase has been completed successfully!")
                                .hideAfter(Duration.seconds(5)).position(Pos.CENTER).showConfirm();
                    }
                    else{
                        try {
                            Notifications.create().title("Error").text(response.body().string()).hideAfter(Duration.seconds(5)).position(Pos.CENTER).showError();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    updateLoanSellTab();
                    response.body().close();
                });
            }
        });
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
        HttpUtil.runAsync(request, false ,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                }
                else
                    chatRoomTabController.getChatAreaComponentController().getChatLineTextArea().clear();
                response.body().close();
            }
        });
    }
    public void setReadOnlyMode(){
        customerOptionsTB.getSelectionModel().selectFirst();
        inlay.setDisable(true);
        payments.setDisable(true);
        createLoan.setDisable(true);
        buySellLoans.setDisable(true);
        chatRoom.setDisable(true);
        loadFileButton.setDisable(true);
        chatRoomTabController.setInActive();
        informationTabController.getTransactionInfoController().getCharge().setDisable(true);
        informationTabController.getTransactionInfoController().getWithdraw().setDisable(true);

    }
    public void setActiveMode(){
        inlay.setDisable(false);
        payments.setDisable(false);
        createLoan.setDisable(false);
        buySellLoans.setDisable(false);
        chatRoom.setDisable(false);
        loadFileButton.setDisable(false);
        chatRoomTabController.setActive();
        informationTabController.getTransactionInfoController().getCharge().setDisable(false);
        informationTabController.getTransactionInfoController().getWithdraw().setDisable(false);
    }

    public void updateCurrentTab(){
        switch(customerOptionsTB.getSelectionModel().getSelectedItem().getText()){
            case "Information":{
                break;
            } case "Inlay":{
                updateInlayTab();
                break;
            } case "Payments":{
                updatePayments();
                break;
            }
            case "Buy/Sell Loans":{
                updateLoanSellTab();
                break;
            }
            case "Create Loan":{
                updateCreateLoanTab();
                break;
            }
        }
    }

}
