package servlet.customer.createLoan;

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

@WebServlet(name = "CreateLoanServlet", urlPatterns = {"/Create-Loan-Servlet"})
public class CreateLoanServlet extends HttpServlet {
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
            String loanID = request.getParameter("loanID");
            String category = request.getParameter("category");
            Integer timePerPayment = Integer.parseInt(request.getParameter("timePerPayment"));
            Integer loanInterest = Integer.parseInt(request.getParameter("loanInterest"));
            Integer loanDuration = Integer.parseInt(request.getParameter("loanDuration"));
            Double amount = Double.parseDouble(request.getParameter(AMOUNT));
            if(timePerPayment <= 0 || loanInterest <= 0 || loanDuration <= 0 || amount <= 0){
                throw new NumberFormatException();
            }
            if(loanDuration % timePerPayment != 0){
                throw new Exception("Loan duration and payments interval don't divide equally!");
            }
            synchronized (this) {
                if (engine.getLoanByName(loanID) != null){
                    throw new Exception("A loan with the name you have chosen is already exists!");
                } else{
                    if(!engine.getCategoriesList().getCategoriesList().contains(category)){
                        throw new Exception("The selected category doesn't exist in the system!");
                    } else{
                        engine.createNewLoan(userName, category, loanID, loanDuration, loanInterest, timePerPayment, amount);
                        ServerChecks.setMessageOnResponse(response.getWriter(), "Loan created successfully!");
                    }
                }
            }
        } catch (NumberFormatException e){
            ServerChecks.setMessageOnResponse(response.getWriter(), "One of the query parameters is missing or invalid!");
        } catch (Exception e){
            ServerChecks.setMessageOnResponse(response.getWriter(), e.getMessage());
        }
    }

}
