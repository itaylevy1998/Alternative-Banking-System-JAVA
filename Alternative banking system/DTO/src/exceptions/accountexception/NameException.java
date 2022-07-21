package exceptions.accountexception;

public class NameException extends Exception{
    private String name;

    public NameException(String name) {
        this.name = name;
    }

    public void printMessage(){
        System.out.println("Incorrect name input! The customer: " + name + " does not exist in the system.");
    }
}
