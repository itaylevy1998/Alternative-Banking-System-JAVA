package servlet.customer.information;

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

import static userinterface.Constants.*;

@WebServlet(name = "CustomerWithdrawMoneyServlet", urlPatterns = {"/Customer-Withdraw-Money-Servlet"})
public class CustomerWithdrawMoneyServlet extends HttpServlet {
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
            Double amount = Double.parseDouble(request.getParameter(AMOUNT));
            if(amount <= 0){
                throw new Exception();
            }
            Gson gson = GSON_INSTANCE;
            try {
                engine.drawMoneyFromAccount(engine.getCustomerByName(userName), amount);
                response.setContentType("application/json");
                ServerChecks.setMessageOnResponse(response.getWriter(),gson.toJson(engine.getCustomerInfo(userName)));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ServerChecks.setMessageOnResponse(response.getWriter(), e.toString());
            }

        } catch (Exception e){
            ServerChecks.setMessageOnResponse(response.getWriter(),"Invalid amount. Please enter a positive number!");
        }
    }
}
