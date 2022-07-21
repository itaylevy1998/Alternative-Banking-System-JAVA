package database.client;

import database.loan.Loans;

public interface CustomerInterface {
    void addMoney(double amount);
    void drawMoney(double amount);
    double getBalance();
    String getName();
    void addLoanToClient(Loans loan, boolean lenderOrBorrower);

}
