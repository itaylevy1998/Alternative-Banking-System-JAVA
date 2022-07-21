package servlet.customer.buySellLoans;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static userinterface.Constants.*;

@WebServlet(name = "CustomerSellLoansServlet", urlPatterns = {"/Customer-Sell-Loans-Servlet"})
public class CustomerSellLoansServlet extends HttpServlet {
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


        try{
            String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            List<String> loansToSell = Arrays.asList(GSON_INSTANCE.fromJson(json, String[].class));
            if(loansToSell.size() == 0){
                throw new Exception("No loan has been selected!");
            }
            engine.setLoansForSale(userName,loansToSell);
            ServerChecks.setMessageOnResponse(response.getWriter(), "Selected loans has moved to the transfer list!!");
        }
        catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(), e.getMessage());
            //response.getWriter().println("One or more loans are not active anymore so it can't be sold!");
        }
    }
}
