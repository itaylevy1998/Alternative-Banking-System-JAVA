package servlet.customer.payments;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.customers.PaymentUpdateDTO;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.payments.PaymentNotificationDTO;
import userinterface.utils.HttpUtil;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static userinterface.Constants.*;

@WebServlet(name = "CustomerPullPaymentsServlet", urlPatterns = {"/Customer-Pull-Payments-Servlet"})
public class CustomerPullPaymentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = ServerChecks.getUserName(request);
        //Session doesn't exist!
        if (userName == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.NO_SESSION_FOUND);
            return;
        }
        Engine engine = EngineServlet.getEngine(getServletContext());
        //User isn't customer!
        if (engine.isUserAdmin(userName)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.LIMITED_ACCESS);
            return;
        }
        //Server is in rewind!
        if (engine.getServerStatus().equals(REWIND)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.STATUS_PROBLEM);
            return;
        }
        List<ActiveRiskLoanDTO> loansForPayment = engine.getCustomerActiveRiskLoan(userName);
        List<PaymentNotificationDTO> paymentNotifications = engine.getNotifications(userName);
        List<ActiveRiskLoanDTO> riskLoans = loansForPayment.stream().filter(l -> l.getStatus().equals("Risk")).collect(Collectors.toList());
        List<ActiveRiskLoanDTO> closeActiveLoans = loansForPayment.stream().filter(l-> l.getStatus().equals("Active")).collect(Collectors.toList());
        List<ActiveRiskLoanDTO> makeActivePayment = closeActiveLoans.stream().filter(l -> l.getNextPaymentTime() == Engine.getTime()).collect(Collectors.toList());
        PaymentUpdateDTO paymentUpdateDTO = new PaymentUpdateDTO(closeActiveLoans, riskLoans, makeActivePayment, paymentNotifications);
        response.setContentType("application/json");
        ServerChecks.setMessageOnResponse(response.getWriter(),GSON_INSTANCE.toJson(paymentUpdateDTO));
    }
}
