package database;

import database.client.Customer;
import objects.DisplayCustomerName;
import objects.customers.CustomerInfoDTO;
import objects.loans.NewLoanDTO;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public interface EngineInterface{
    Boolean loadFile(InputStream XMLFile , String customerName) throws FileNotFoundException, JAXBException , Exception;
    //void checkCustomerInfo(AbsCustomers newCustomers) throws Exception;
    //void organizeInformation(AbsDescriptor descriptor) throws Exception;
   // void checkLoansInfo(List<AbsCustomer> newCustomers, List<String> newCategories, AbsLoans newLoans) throws Exception;
   // void copyDataToEngineFields(AbsCustomers newCustomers, AbsLoans newLoans, AbsCategories newCategories);
    void resetTime();
    List<NewLoanDTO> getLoansInfo(String userName);
    List<CustomerInfoDTO> getCustomersInfo();
    Customer getCustomerByName(String name) throws Exception;
    void addMoneyToAccount(Customer userChoice, double moneyToAdd);
    DisplayCustomerName namesForDisplay();
    void drawMoneyFromAccount(Customer userChoice, double moneyToDraw) throws Exception;
    void resetEngine();
}
