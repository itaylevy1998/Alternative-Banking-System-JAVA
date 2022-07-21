package servlet.customer.buySellLoans;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.loans.LoansForSaleDTO;
import utils.EngineServlet;

import java.io.IOException;
import java.util.stream.Collectors;

import static userinterface.Constants.GSON_INSTANCE;
import static userinterface.Constants.USERNAME;

@WebServlet(name = "CustomerBuyLoanServlet", urlPatterns = {"/Customer-Buy-Loan-Servlet"})
public class CustomerBuyLoanServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            engine.sellLoan(GSON_INSTANCE.fromJson(json, LoansForSaleDTO.class), String.valueOf(request.getSession().getAttribute(USERNAME)));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(e);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
