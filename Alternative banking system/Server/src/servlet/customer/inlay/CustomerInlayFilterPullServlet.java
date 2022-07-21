package servlet.customer.inlay;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import objects.customers.loanInfo.CustomerFilterLoansDTO;
import objects.loans.NewLoanDTO;
import objects.loans.PendingLoanDTO;
import sun.misc.IOUtils;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;
import static userinterface.Constants.CHECK_INLAY_INPUT_RESOURCE;
import static userinterface.Constants.USERNAME;

@WebServlet(name = "CustomerInlayFilterPullServlet", urlPatterns = {"/Customer-Inlay-Filter-Pull-Servlet"})
public class CustomerInlayFilterPullServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userName = ServerChecks.getUserName(request);
            Engine engine = EngineServlet.getEngine(getServletContext());
            String MinYaz = request.getParameter("minYAZ");
            String MinInterest = request.getParameter("minInterest");
            String MaxOpenLoans = request.getParameter("maxOpenLoans");
            Integer minYaz;
            Integer minInterest;
            Integer maxOpenLoans;
            if (MinYaz.equals("")) {
                minYaz = 0;
            } else {
                minYaz = Integer.parseInt(MinYaz);
            }
            if (MinInterest.equals("")) {
                minInterest = 0;
            } else {
                minInterest = Integer.parseInt(MinInterest);
            }
            if (MaxOpenLoans.equals("")) {
                maxOpenLoans = -2;
            } else {
                maxOpenLoans = Integer.parseInt(MaxOpenLoans);
            }
            if (minYaz < 0 || minInterest < 0 || (maxOpenLoans < 0 && maxOpenLoans != -2)) {
                throw new NumberFormatException();
            }
            Gson gson = new Gson();
            String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            if(json.equals("")){
                throw new Exception();
            }
            List<String> categories= Arrays.asList(gson.fromJson(json, String[].class));
            if(categories.size() == 0){
                categories = engine.getCategoriesList().getCategoriesList();
            }
            List<NewLoanDTO> filteredList = engine.getFilteredLoans(categories, minInterest, minYaz, userName, maxOpenLoans);
            List<NewLoanDTO> newLoans = new ArrayList<>();
            filteredList.stream().filter(l -> l.getStatus().equals("New")).forEach(l -> newLoans.add(l));
            List<PendingLoanDTO> pendingLoans = new ArrayList<>();
            filteredList.stream().filter(l -> l.getStatus().equals("Pending")).forEach(l -> pendingLoans.add((PendingLoanDTO) l));
            CustomerFilterLoansDTO loans = new CustomerFilterLoansDTO(newLoans,pendingLoans);
            Gson returnGson = new Gson();
            String returnJson = returnGson.toJson(loans);
            response.setContentType("application/json");
            ServerChecks.setMessageOnResponse(response.getWriter(), returnJson);
        } catch (NumberFormatException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(), "One of the query parameters is invalid!");
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(), "Invalid JSON of categories!");
        }

    }
}
