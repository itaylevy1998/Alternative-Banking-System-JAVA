package servlet.chat;

import database.chat.ChatManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.EngineServlet;
import utils.ServerChecks;

import static userinterface.Constants.CHAT_PARAMETER;

@WebServlet(name = "GetUserChatServlet", urlPatterns = {"/pages/chatroom/sendChat"})
public class SendChatServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ChatManager chatManager = EngineServlet.getEngine(getServletContext()).getChatManager();
        String username = ServerChecks.getUserName(request);
        if (username == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        String userChatString = request.getParameter(CHAT_PARAMETER);
        if (userChatString != null && !userChatString.isEmpty()) {
            synchronized (this) {
                chatManager.addChatString(userChatString, username);
            }
        }
    }

}
