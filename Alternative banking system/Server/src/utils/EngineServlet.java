package utils;

import database.Engine;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import static userinterface.Constants.INT_PARAMETER_ERROR;

public class EngineServlet {
    private static final String ENGINE_ATTRIBUTE_NAME = "Engine";
    private static final Object EngineLock = new Object();

    public static Engine getEngine(ServletContext servletContext) {
        synchronized (EngineLock) {
            if (servletContext.getAttribute(ENGINE_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(ENGINE_ATTRIBUTE_NAME, new Engine());
            }
        }
        return (Engine) servletContext.getAttribute(ENGINE_ATTRIBUTE_NAME);
    }
    public static int getIntParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return INT_PARAMETER_ERROR;
    }
}

