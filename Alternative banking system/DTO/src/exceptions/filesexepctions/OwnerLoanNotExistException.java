package exceptions.filesexepctions;

public class OwnerLoanNotExistException extends Exception implements ExcepctionInterface{
    private String ownerName;
    private String loanId;

    public OwnerLoanNotExistException(String ownerName, String loanId) {
        this.ownerName = ownerName;
        this.loanId = loanId;
    }

    @Override
    public void printMessage() {
        System.out.println("The owner: " + ownerName + "of loan: " + '"' + loanId + '"'  + "does not exist.");
    }

    @Override
    public String toString() {
        return ("The owner: " + ownerName + "of loan: " + '"' + loanId + '"'  + "does not exist.");
    }
}
