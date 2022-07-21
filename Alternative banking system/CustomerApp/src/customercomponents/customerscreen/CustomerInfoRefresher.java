package customercomponents.customerscreen;

import javafx.application.Platform;
import objects.admin.LoanAndCustomerInfoDTO;
import objects.customers.CustomerInfoDTO;
import objects.customers.CustomersRelatedInfoDTO;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.FinishedLoanDTO;
import objects.loans.NewLoanDTO;
import objects.loans.PendingLoanDTO;
import okhttp3.*;
import userinterface.utils.HttpUtil;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import static userinterface.Constants.*;

public class CustomerInfoRefresher extends TimerTask {
    private CustomerScreenController customerScreenController;
    private String userName;
    private String lastSeenYaz;


    public CustomerInfoRefresher(CustomerScreenController customerScreenController, String userName) {
        this.customerScreenController = customerScreenController;
        this.userName = userName;
        lastSeenYaz = "1";
    }

    @Override
    public void run() {
        String finalUrlInformation = HttpUrl.parse(FULL_PATH_DOMAIN + CUSTOMER_PULL_INFORMATION_RESOURCE)
                .newBuilder().addQueryParameter(USERNAME, userName)
                .build()
                .toString();
        Request requestCustomerTable = new Request.Builder()
                .url(finalUrlInformation)
                .build();

        HttpUtil.runAsync(requestCustomerTable,false, new Callback() {
            public void onFailure(Call call, IOException e) {
                System.out.println("problem");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful())
                    System.out.println("major error in customer refresher!");
                else{
                    String jsonArrayOfInformation = response.body().string();
                    if(jsonArrayOfInformation==null || jsonArrayOfInformation=="")
                        return;
                    CustomersRelatedInfoDTO allTabsCustomerInformation = GSON_INSTANCE.fromJson(jsonArrayOfInformation, CustomersRelatedInfoDTO.class);
                    List<NewLoanDTO> newLoans = allTabsCustomerInformation.getNewLoans();
                    List<PendingLoanDTO> pendingLoans = allTabsCustomerInformation.getPendingLoans();
                    List<ActiveRiskLoanDTO> activeLoans = allTabsCustomerInformation.getActiveLoans();
                    List<ActiveRiskLoanDTO> riskLoans = allTabsCustomerInformation.getRiskLoans();
                    List<FinishedLoanDTO> finishedLoans = allTabsCustomerInformation.getFinishedLoans();
                    CustomerInfoDTO customerInfo = allTabsCustomerInformation.getCustomerInfo();
                    String currentYaz = allTabsCustomerInformation.getCurrentYaz();
                    String serverStatus = allTabsCustomerInformation.getServerStatus();
                    if(serverStatus.equals(REWIND)){
                        Platform.runLater(() ->  {
                            customerScreenController.setReadOnlyMode();
                            response.body().close();
                        });
                    } else{
                        Platform.runLater(() -> {
                            customerScreenController.setActiveMode();
                            if(!currentYaz.equals(lastSeenYaz)){
                                lastSeenYaz = currentYaz;
                                customerScreenController.updateCurrentTab();
                                response.body().close();
                                customerScreenController.setActiveMode();
                            }});
                    }
                        Platform.runLater(() -> {
                            customerScreenController.updateInformationTab(userName, newLoans,pendingLoans,activeLoans,riskLoans,finishedLoans, customerInfo, currentYaz, serverStatus);
                            response.body().close();
                        });

                }
            }
        });
    }
}
