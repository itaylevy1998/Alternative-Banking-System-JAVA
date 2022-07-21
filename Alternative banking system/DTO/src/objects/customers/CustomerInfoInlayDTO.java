package objects.customers;

import com.sun.javafx.binding.ExpressionHelper;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;

import java.util.EventListener;

public class CustomerInfoInlayDTO {
    private boolean withDrawException;
    private String result;
    private int openLoans;


    public CustomerInfoInlayDTO(boolean withDrawException, String result, int openLoans) {
        this.withDrawException = withDrawException;
        this.result = result;
        this.openLoans = openLoans;
    }

    public boolean isWithDrawException() {
        return withDrawException;
    }

    public String getResult() {
        return result;
    }

    public int getOpenLoans() {
        return openLoans;
    }

    public void setWithDrawException(boolean withDrawException) {
        this.withDrawException = withDrawException;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setOpenLoans(int openLoans) {
        this.openLoans = openLoans;
    }
}
