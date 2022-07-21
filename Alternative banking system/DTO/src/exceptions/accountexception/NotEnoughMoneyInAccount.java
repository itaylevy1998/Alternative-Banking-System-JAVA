package exceptions.accountexception;

public class NotEnoughMoneyInAccount extends Exception{
    private String customerName;

    public NotEnoughMoneyInAccount(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Not enough money in " + customerName + "'s account!";
    }
}
