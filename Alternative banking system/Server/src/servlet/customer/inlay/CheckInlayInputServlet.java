package servlet.customer.inlay;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.customers.CustomerInfoInlayDTO;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.IOException;

import static userinterface.Constants.*;

@WebServlet(name = "CheckInlayInputServlet", urlPatterns = {"/Check-Inlay-Input-Servlet"})
public class CheckInlayInputServlet extends HttpServlet {
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
            String number = request.getParameter(AMOUNT);
            double amount = Double.parseDouble(number);
            String MaxOwnership = request.getParameter("maxOwnership");
            double maxOwnership;
            if(MaxOwnership.equalsIgnoreCase("")){
                maxOwnership = 100;
            } else{
                maxOwnership = Double.parseDouble(MaxOwnership);
            }
            if(maxOwnership <= 0 || maxOwnership > 100){
                throw new NumberFormatException();
            }
            engine.checkAmountOfInvestment(userName, amount);
            request.getServletContext().getRequestDispatcher(CUSTOMER_MAKE_INLAY_RESOURCE).forward(request,response);
        } catch (NumberFormatException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(), "One of the query parameters is invalid!");
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ServerChecks.setMessageOnResponse(response.getWriter(), e.toString());
        }
    }
}
