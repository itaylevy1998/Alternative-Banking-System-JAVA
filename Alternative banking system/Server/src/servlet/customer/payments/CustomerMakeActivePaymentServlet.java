package servlet.customer.payments;

import database.Engine;
import exceptions.accountexception.NotEnoughMoneyInAccount;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.IOException;

import static userinterface.Constants.*;

@WebServlet(name = "CustomerMakeActivePaymentServlet", urlPatterns = {"/Customer-Make-Active-Payment-Servlet"})
public class CustomerMakeActivePaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        String loanID = request.getParameter("loanID");
        try {
            engine.checkLoanBeforePayment(loanID, userName, "Active");
            engine.makeActivePayment(loanID, userName);
            ServerChecks.setMessageOnResponse(response.getWriter(), "Payment completed successfully!");
        } catch (NotEnoughMoneyInAccount e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(), e.toString());
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(), e.getMessage());
        }
    }
}
