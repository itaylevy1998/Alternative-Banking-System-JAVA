package servlet.customer.buySellLoans;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.customers.loanInfo.BuySellUpdateDTO;
import objects.loans.LoansForSaleDTO;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.IOException;
import java.util.List;

import static userinterface.Constants.*;

@WebServlet(name = "CustomerBuySellPullServlet", urlPatterns = {"/Customer-BuySell-Pull-Servlet"})
public class CustomerBuySellPullServlet extends HttpServlet {

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
        List<String> loansAvailableToSell = engine.getLoansAvailableToSell(userName);
        List<LoansForSaleDTO> loansAvailableToBuy = engine.getLoansAvailableToBuy(userName);
        BuySellUpdateDTO buySellLoans = new BuySellUpdateDTO(loansAvailableToBuy,loansAvailableToSell);
        response.setContentType("application/json");
        ServerChecks.setMessageOnResponse(response.getWriter(), GSON_INSTANCE.toJson(buySellLoans));
    }
}
