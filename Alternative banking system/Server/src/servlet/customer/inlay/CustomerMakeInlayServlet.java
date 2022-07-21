package servlet.customer.inlay;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.loans.NewLoanDTO;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static userinterface.Constants.*;

@WebServlet(name = "CustomerMakeInlayServlet", urlPatterns = {"/Customer-Make-Inlay-Servlet"})
public class CustomerMakeInlayServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = ServerChecks.getUserName(request);
        Engine engine = EngineServlet.getEngine(getServletContext());
        Gson gson = GSON_INSTANCE;
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            List<String> newLoanDTOList = Arrays.asList(gson.fromJson(json, String[].class));
            if(newLoanDTOList.size() == 0){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ServerChecks.setMessageOnResponse(response.getWriter(), "You didn't choose a loan to invest!");
                return;
            }
            Integer amount = Integer.parseInt(request.getParameter(AMOUNT));
            String MaxOwnership = request.getParameter("maxOwnership");
            Integer maxOwnership;
            if(MaxOwnership.equalsIgnoreCase("")){
                maxOwnership = 100;
            } else{
                maxOwnership = Integer.parseInt(MaxOwnership);
            }
            synchronized (this){
                try {
                    engine.checkLoansStatus(newLoanDTOList, userName);
                    engine.splitMoneyBetweenLoans(newLoanDTOList,amount, userName,maxOwnership );
                    ServerChecks.setMessageOnResponse(response.getWriter(), "Inlay completed successfully!");
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    ServerChecks.setMessageOnResponse(response.getWriter(), e.getMessage());
                }
            }
        } catch (NumberFormatException e){

        }
        catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(), "Problem with JSON!");
        }

    }
}
