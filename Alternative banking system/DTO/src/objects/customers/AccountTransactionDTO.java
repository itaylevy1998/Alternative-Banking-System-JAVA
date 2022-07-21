package objects.customers;

public class AccountTransactionDTO {
    private int timeOfTransaction;
    private Double transactionAmount;
    private char incomeOrExpense;
    private double balanceBefore;
    private double balanceAfter;
    private String realAmount;

    public String getRealAmount() {
        return incomeOrExpense+transactionAmount.toString();
    }

    public char getIncomeOrExpense() {
        return incomeOrExpense;
    }

    public int getTimeOfTransaction() {
        return timeOfTransaction;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public AccountTransactionDTO(int timeOfTransaction, double transactionAmount, char incomeOrExpense, double balanceBefore, double balanceAfter) {
        this.timeOfTransaction = timeOfTransaction;
        this.transactionAmount = transactionAmount;
        this.incomeOrExpense = incomeOrExpense;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
    }
    public void print(){
        System.out.println("Time of transaction: " + timeOfTransaction);
        System.out.println("The transaction: " + incomeOrExpense + " " + String.format("%.2f", transactionAmount));
        System.out.println("Balance before the transaction: " + String.format("%.2f", balanceBefore));
        System.out.println("Balance after the transaction: " + String.format("%.2f", balanceAfter));
    }
}
