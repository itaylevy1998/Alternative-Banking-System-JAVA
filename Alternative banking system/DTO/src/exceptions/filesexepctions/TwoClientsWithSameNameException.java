package exceptions.filesexepctions;

public class TwoClientsWithSameNameException extends Exception implements ExcepctionInterface{
    public String name;

    public TwoClientsWithSameNameException(String name){
        this.name = name;
    }
    public void printMessage(){
        System.out.println("File is invalid! There are two clients with the name: "+ name);
    }

    @Override
    public String toString() {
        return "File is invalid! There are two clients with the name: "+ name;
    }
}
