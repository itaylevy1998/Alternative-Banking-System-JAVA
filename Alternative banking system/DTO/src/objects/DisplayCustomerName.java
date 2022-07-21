package objects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DisplayCustomerName {
   private Map<String, Double> customerMap;

    public DisplayCustomerName() {
        this.customerMap = new LinkedHashMap();
    }

    public Map<String, Double> getCustomerList() {
        return customerMap;
    }

    public void printNames(){
        int i=1;
        System.out.println("Customers Names: ");
        for (Map.Entry<String,Double> entry : customerMap.entrySet()){
            System.out.println(i + ". " + entry.getKey());
            i++;
        }
    }
    public void printNamesAndBalance(){
        int i=1;
        System.out.println("Customers names and balance: ");
        for (Map.Entry<String,Double> entry : customerMap.entrySet()){
            System.out.println(i + ". " + entry.getKey() +": " + String.format("%.2f", entry.getValue()));
            i++;
        }
    }
}
