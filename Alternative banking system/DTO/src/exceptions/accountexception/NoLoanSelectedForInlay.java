package exceptions.accountexception;

public class NoLoanSelectedForInlay extends Exception {
    public void print(){
        System.out.println("No loan has been selected for the inlay. ");
    }
}
