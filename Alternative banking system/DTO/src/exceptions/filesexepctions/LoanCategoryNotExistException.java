package exceptions.filesexepctions;

public class LoanCategoryNotExistException extends Exception implements ExcepctionInterface{
    private String category;
    private String id;


    public LoanCategoryNotExistException(String category, String id){
        this.category = category;
        this.id = id;
    }
    @Override
    public void printMessage() {
        System.out.println("For loan " + '"' + id  + '"' +" the category: '" + category + "' does not exist.");
    }

    @Override
    public String toString() {
        return "For loan " + '"' + id  + '"' +" the category: '" + category + "' does not exist.";
    }
}
