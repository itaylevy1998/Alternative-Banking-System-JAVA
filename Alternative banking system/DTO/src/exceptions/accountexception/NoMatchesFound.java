package exceptions.accountexception;

public class NoMatchesFound extends Exception {
    public void printMessage(){
        System.out.println("There are no loans that matches your filters. ");
    }
}
