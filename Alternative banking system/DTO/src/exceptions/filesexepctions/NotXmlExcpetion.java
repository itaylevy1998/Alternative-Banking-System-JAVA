package exceptions.filesexepctions;

public class NotXmlExcpetion extends Exception implements ExcepctionInterface{
    public void printMessage(){
        System.out.println("File ending is not .xml , Please make sure that the file's type is xml");
    }
}
