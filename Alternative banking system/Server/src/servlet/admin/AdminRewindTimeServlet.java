package servlet.admin;

import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.IOException;

import static userinterface.Constants.NOTREWIND;

@WebServlet(name = "AdminRewindTimeServlet", urlPatterns = {"/Admin-Rewind-Time-Servlet"})
public class AdminRewindTimeServlet extends HttpServlet {
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
        //User isn't admin!
        if (!engine.isUserAdmin(userName)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.LIMITED_ACCESS);
            return;
        }
        //Server is not in rewind!
        if (engine.getServerStatus().equals(NOTREWIND)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.STATUS_PROBLEM);
            return;
        }
        String requestParameter = request.getParameter("TimeToMove");
        try{
            Integer selectedYaz = Integer.parseInt(requestParameter);
            //Yaz isn't valid!
            if (selectedYaz < 1 || selectedYaz > engine.getTimeToReturn()) {
                throw new NumberFormatException();
            }
            getServletContext().setAttribute("Engine", engine.loadSelcetedYaz("Engine", selectedYaz.toString()));
            ServerChecks.setMessageOnResponse(response.getWriter(), "Successfully rewinded to Yaz " + selectedYaz);

        } catch (NumberFormatException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(), "Please enter a valid Yaz!");
            return;
        } catch (Exception e) {

        }
    }
}


