package servlet.common;

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
import static userinterface.Constants.USERNAME;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Engine engine = EngineServlet.getEngine(getServletContext());
        String userName = request.getParameter(USERNAME);
        String isAdmin = request.getParameter("isAdmin");
        if(!isAdmin.equalsIgnoreCase("true") && !isAdmin.equalsIgnoreCase("false")){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ServerChecks.setMessageOnResponse(response.getWriter(), "You must choose client type!");
            return;
        }
        //user already exists
        synchronized (this){
            if(engine.isNameExists(userName)){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ServerChecks.setMessageOnResponse(response.getWriter(), "This user name is already in use!");
            }
            else{
                if(engine.getServerStatus().equals(REWIND)){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    ServerChecks.setMessageOnResponse(response.getWriter(), "Can't login because admin uses rewind!");
                } else{
                    if(isAdmin.equals("true")){
                        if(!engine.isAdminExist()){
                            engine.setAdminExist(true);
                            request.getSession(true).setAttribute(USERNAME, userName);
                            engine.setAdminName(userName);
                            ServerChecks.setMessageOnResponse(response.getWriter(), userName + " has logged in successfully!");
                        } else{
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            ServerChecks.setMessageOnResponse(response.getWriter(), "Admin is already logged in!");
                        }
                    } else{
                        engine.addCustomer(userName,false);
                        request.getSession(true).setAttribute(USERNAME, userName);
                        ServerChecks.setMessageOnResponse(response.getWriter(), userName + " has logged in successfully!");
                    }
                }

            }
        }
    }
}
