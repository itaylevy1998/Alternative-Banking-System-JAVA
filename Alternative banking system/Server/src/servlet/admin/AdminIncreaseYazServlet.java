package servlet.admin;

import com.google.gson.Gson;
import database.Engine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServerChecks;
import utils.EngineServlet;

import java.io.IOException;

import static userinterface.Constants.REWIND;

@WebServlet(name = "AdminIncreaseYazServlet", urlPatterns = {"/Admin-Increase-Yaz-Servlet"})
public class AdminIncreaseYazServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = ServerChecks.getUserName(request);
        //Session doesn't exist!
        if(userName == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.NO_SESSION_FOUND);
            return;
        }
        Engine engine = EngineServlet.getEngine(getServletContext());
        //User isn't admin!
        if(!engine.isUserAdmin(userName)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.LIMITED_ACCESS);
            return;
        }
        //Server is in rewind!
        if(engine.getServerStatus().equals(REWIND)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServerChecks.setMessageOnResponse(response.getWriter(),ServerChecks.STATUS_PROBLEM);
            return;
        }

        engine.moveTImeForward2();
        response.setContentType("application/json");
        Gson gson = new Gson();
        Integer yaz = Engine.getTime();
        String json = gson.toJson(yaz.toString());
        ServerChecks.setMessageOnResponse(response.getWriter(), json);
    }
}
