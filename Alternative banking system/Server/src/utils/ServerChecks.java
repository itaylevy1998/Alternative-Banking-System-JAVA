package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static userinterface.Constants.INT_PARAMETER_ERROR;
import static userinterface.Constants.USERNAME;

public class ServerChecks {
    public static final String NO_SESSION_FOUND = "There is no available session for this user! access denied!";
    public static final String LIMITED_ACCESS = "This user does not have permission to complete this action!";
    public static final String STATUS_PROBLEM = "The current server status does not allow this action!";

    public static String getUserName(HttpServletRequest request){
        if(request.getSession(false) == null){
            return null;
        } else{
            return String.valueOf(request.getSession(false).getAttribute(USERNAME));
        }
    }

    public static void setMessageOnResponse(PrintWriter out, String data){
        out.println(data);
        out.flush();
        out.close();
    }


}
