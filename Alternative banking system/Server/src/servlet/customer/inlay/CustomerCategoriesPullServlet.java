package servlet.customer.inlay;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.IOException;

@WebServlet(name = "CustomerCategoriesPullServlet", urlPatterns = {"/Categories-Pull-Servlet"})
public class CustomerCategoriesPullServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = ServerChecks.getUserName(request);
        //Session doesn't exist!
        if (userName == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ServerChecks.setMessageOnResponse(response.getWriter(), ServerChecks.NO_SESSION_FOUND);
            return;
        }
        Gson returnGson = new Gson();
        String returnJson = returnGson.toJson(EngineServlet.getEngine(getServletContext()).getCategoriesList().getCategoriesList());
        response.setContentType("application/json");
        ServerChecks.setMessageOnResponse(response.getWriter(), returnJson);
    }
}
