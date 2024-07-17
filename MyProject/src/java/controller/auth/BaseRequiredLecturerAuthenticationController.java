package controller.auth;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Feature;
import model.Lecturer;
import model.Role;
import model.User;

public abstract class BaseRequiredLecturerAuthenticationController extends HttpServlet {
   
    private boolean isAuthenticated(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return false;
        } else {
            Lecturer lecturer = user.getLecturer();
            if (lecturer == null) {
                String currentURL = request.getServletPath();
                for (Role role : user.getRoles()) {
                    for (Feature feature : role.getFeatures()) {
                        if (feature.getUrl().equals(currentURL)) {
                            return true;
                        }
                    }
                }
                return false;
            } else {
                return true;
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (isAuthenticated(request)) {
            User user = (User) request.getSession().getAttribute("user");
            Lecturer lecturer = user.getLecturer();
            doGet(request, response, user, lecturer);
        } else {
            response.getWriter().println("access denied!");
        }
    }
    
    protected abstract void doGet(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer)
            throws ServletException, IOException;
    
    protected abstract void doPost(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer)
            throws ServletException, IOException;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (isAuthenticated(request)) {
            User user = (User) request.getSession().getAttribute("user");
            Lecturer lecturer = user.getLecturer();
            doPost(request, response, user, lecturer);
        } else {
            response.getWriter().println("access denied!");
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}