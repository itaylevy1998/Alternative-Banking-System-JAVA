package exceptions.accountexception;

public class WithDrawMoneyException extends Exception{
    private double balance;
    private double transaction;

    public WithDrawMoneyException(double balance, double transaction) {
        this.balance = balance;
        this.transaction = transaction;
    }

    public void printMessage(){
        System.out.println("This customer cannot draw " + String.format("%.2f", transaction) + " from his account because he only has: " + String.format("%.2f", balance) +". Transaction failed.");
    }

    @Override
    public String toString() {
        return "Not enough money in customer's account!";
    }

    public void printInvestMessage(){
        System.out.println("This customer cannot invest an amount of " + String.format("%.2f", balance) + " because he only has: " + String.format("%.2f", balance) +". Transaction denied.");
    }
}
