package servlet.customer;

import database.Engine;
import exceptions.filesexepctions.LoanCategoryNotExistException;
import exceptions.filesexepctions.LoanIDAlreadyExists;
import exceptions.filesexepctions.TimeOfPaymentNotDivideEqualyException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.EngineServlet;
import utils.ServerChecks;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

import static userinterface.Constants.*;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload-file"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadServlet extends HttpServlet{

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

            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            Collection<Part> parts = request.getParts();
            //User entered more than 1 file!
            if(parts.size() > 1){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ServerChecks.setMessageOnResponse(response.getWriter(), "User can only load 1 file at a time!");
                return;
            }
            StringBuilder fileContent = new StringBuilder();
            for (Part part : parts) {
                if(part.getSize() == 0){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    ServerChecks.setMessageOnResponse(response.getWriter(), "No file has been selected to be loaded!");
                    return;
                }
                fileContent.append(readFromInputStream(part.getInputStream()));
            }
            if(fileContent.equals("")){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ServerChecks.setMessageOnResponse(response.getWriter(), "No file has been selected to be loaded!");
                return;
            }
            InputStream file = new ByteArrayInputStream(fileContent.toString().getBytes(StandardCharsets.UTF_8));
            try {
                EngineServlet.getEngine(getServletContext()).loadFile(file, userName);
                out.println("File loaded successfully!");
            }catch (LoanCategoryNotExistException e){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ServerChecks.setMessageOnResponse(response.getWriter(), e.toString());
            }catch (TimeOfPaymentNotDivideEqualyException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ServerChecks.setMessageOnResponse(response.getWriter(), e.toString());
            } catch (LoanIDAlreadyExists e){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ServerChecks.setMessageOnResponse(response.getWriter(), e.toString());
            }
            catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ServerChecks.setMessageOnResponse(response.getWriter(), "Unknown file error!");
            }
        }

        private String readFromInputStream(InputStream inputStream) {
            return new Scanner(inputStream).useDelimiter("\\Z").next();
        }
}






