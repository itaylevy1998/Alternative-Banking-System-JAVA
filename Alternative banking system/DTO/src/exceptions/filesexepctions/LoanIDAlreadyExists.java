package exceptions.filesexepctions;

public class LoanIDAlreadyExists extends Exception{
    String loanID;

    public LoanIDAlreadyExists(String loanID) {
        this.loanID = loanID;
    }

    @Override
    public String toString() {
        return "A loan with an ID of '"+ loanID + "' already exists in the system!";
    }
}
