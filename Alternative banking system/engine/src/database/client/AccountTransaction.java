package database.client;

import java.io.Serializable;

public class AccountTransaction implements Serializable {
    private int timeOfTransaction;
    private double transactionAmount;
    private char incomeOrExpense;
    private double balanceBefore;
    private double balanceAfter;

    public AccountTransaction(int timeOfTransaction, double transactionAmount, char incomeOrExpense, double balanceBefore, double balanceAfter) {
        this.timeOfTransaction = timeOfTransaction;
        this.transactionAmount = transactionAmount;
        this.incomeOrExpense = incomeOrExpense;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
    }

    public int getTimeOfTransaction() {
        return timeOfTransaction;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public char getIncomeOrExpense() {
        return incomeOrExpense;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }
}
