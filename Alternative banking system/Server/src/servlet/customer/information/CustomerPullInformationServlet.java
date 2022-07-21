package servlet.customer.information;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.customers.CustomerInfoDTO;
import objects.customers.CustomersRelatedInfoDTO;
import objects.loans.*;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static userinterface.Constants.REWIND;
import static userinterface.Constants.USERNAME;

@WebServlet(name = "CustomerPullInformationServlet", urlPatterns = {"/Customer-Pull-Information-Servlet"})
public class CustomerPullInformationServlet extends HttpServlet {
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

        List<NewLoanDTO> allRelatedLoans = engine.getLoansInfo(userName);
        List<NewLoanDTO> newLoans = new ArrayList<>();
        allRelatedLoans.stream().filter(l -> l.getStatus().equals("New")).forEach(l -> newLoans.add(l));
        List<PendingLoanDTO> pendingLoans = new ArrayList<>();
        allRelatedLoans.stream().filter(l -> l.getStatus().equals("Pending")).forEach(l-> pendingLoans.add((PendingLoanDTO) l));
        List<ActiveRiskLoanDTO> activeLoans = new ArrayList<>();
        allRelatedLoans.stream().filter(l -> l.getStatus().equals("Active")).forEach(l-> activeLoans.add((ActiveRiskLoanDTO) l));
        List<ActiveRiskLoanDTO> riskLoans = new ArrayList<>();
        allRelatedLoans.stream().filter(l -> l.getStatus().equals("Risk")).forEach(l-> riskLoans.add((ActiveRiskLoanDTO) l));
        List<FinishedLoanDTO> finishedLoans = new ArrayList<>();
        allRelatedLoans.stream().filter(l -> l.getStatus().equals("Finished")).forEach(l-> finishedLoans.add((FinishedLoanDTO) l));
        CustomerInfoDTO customerInfoDTO = engine.getCustomerInfo(userName);
        String serverStatus = engine.getServerStatus();
        Integer currentYaz = Engine.getTime();
        CustomersRelatedInfoDTO loanAndCustomerInfoDTO = new CustomersRelatedInfoDTO(newLoans, pendingLoans, activeLoans,
                riskLoans,finishedLoans, customerInfoDTO, serverStatus, currentYaz.toString());
        response.setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(loanAndCustomerInfoDTO);
        ServerChecks.setMessageOnResponse(response.getWriter(), json);
    }
}
