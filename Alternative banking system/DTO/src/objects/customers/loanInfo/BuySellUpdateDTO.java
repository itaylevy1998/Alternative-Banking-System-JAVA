package objects.customers.loanInfo;

import objects.loans.LoansForSaleDTO;

import java.util.List;

public class BuySellUpdateDTO {
    List<LoansForSaleDTO> loansAvailableToBuy;
    List<String> loansAvailableToSell;

    public BuySellUpdateDTO(List<LoansForSaleDTO> loansAvailableToBuy, List<String> loansAvailableToSell) {
        this.loansAvailableToBuy = loansAvailableToBuy;
        this.loansAvailableToSell = loansAvailableToSell;
    }

    public List<LoansForSaleDTO> getLoansAvailableToBuy() {return loansAvailableToBuy;}
    public List<String> getLoansAvailableToSell() {return loansAvailableToSell;}
}
