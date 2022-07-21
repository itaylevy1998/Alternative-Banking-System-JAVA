package objects.loans;

public class LenderMap {
    private String Name;
    private Double Amount;

    public String getName() {
        return Name;
    }

    public Double getAmount() {
        return Amount;
    }

    public LenderMap(String name, Double amount) {
        Name = name;
        Amount = amount;
    }
}
