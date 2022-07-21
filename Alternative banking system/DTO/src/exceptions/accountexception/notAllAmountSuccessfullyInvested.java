package exceptions.accountexception;

public class notAllAmountSuccessfullyInvested extends Exception{
    private double returnToAccount;
    private double actuallyInvested;

    public notAllAmountSuccessfullyInvested(double returnToAccount, double sumOfLoans) {
        this.returnToAccount = returnToAccount;
        this.actuallyInvested = sumOfLoans;
    }
    public void print(){
        System.out.println("A total sum of " + String.format("%.2f", actuallyInvested) +" was successfully invested. The other " + String.format("%.2f", returnToAccount) + " has been returned to your account.");
    }

}
