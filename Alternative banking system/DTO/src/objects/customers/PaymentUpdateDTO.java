package objects.customers;

import objects.loans.ActiveRiskLoanDTO;
import objects.loans.payments.PaymentNotificationDTO;

import java.util.List;

public class PaymentUpdateDTO {
    List<ActiveRiskLoanDTO> closeActiveLoans;
    List<ActiveRiskLoanDTO> riskLoans;
    List<ActiveRiskLoanDTO> makeActivePayment;
    List<PaymentNotificationDTO> paymentNotifications;

    public PaymentUpdateDTO(List<ActiveRiskLoanDTO> closeActiveLoans, List<ActiveRiskLoanDTO> riskLoans, List<ActiveRiskLoanDTO> makeActivePayment, List<PaymentNotificationDTO> paymentNotifications) {
        this.closeActiveLoans = closeActiveLoans;
        this.riskLoans = riskLoans;
        this.makeActivePayment = makeActivePayment;
        this.paymentNotifications = paymentNotifications;
    }

    public List<ActiveRiskLoanDTO> getCloseActiveLoans() {
        return closeActiveLoans;
    }

    public List<ActiveRiskLoanDTO> getRiskLoans() {
        return riskLoans;
    }

    public List<ActiveRiskLoanDTO> getMakeActivePayment() {
        return makeActivePayment;
    }

    public List<PaymentNotificationDTO> getPaymentNotifications() {
        return paymentNotifications;
    }
}
