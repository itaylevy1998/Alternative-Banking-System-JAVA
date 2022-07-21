package database.loan;

public interface LoansInterface extends Comparable<Loans>{
    void payment();
    void updateStatusBeforeActive();
    void changeToPending();
    void returnToActive();
    int compareTo(Loans loan);

}
