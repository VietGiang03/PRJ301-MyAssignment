package controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Lecturer;
import model.Student;
import model.User;

/**
 * Authentication Controller for both Lecturers and Students.
 * Handles authentication and authorization based on user roles.
 */
public abstract class BaseRequiredAuthenticationController extends HttpServlet {

    private boolean isLecturerAuthenticated(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user != null && user.getLecturer() != null;
    }

    private boolean isStudentAuthenticated(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user != null && user.getStudent() != null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if (isLecturerAuthenticated(request)) {
            if (isStudentRestrictedPath(path)) {
                response.getWriter().println("Access Denied: Students are not allowed to access this page.");
                return;
            }
            User user = (User) request.getSession().getAttribute("user");
            doGet(request, response, user, user.getLecturer());
        } else if (isStudentAuthenticated(request)) {
            if (isLecturerRestrictedPath(path)) {
                response.getWriter().println("Access Denied: Lecturers are not allowed to access this page.");
                return;
            }
            User user = (User) request.getSession().getAttribute("user");
            doGet(request, response, user, user.getStudent());
        } else {
            response.getWriter().println("Access Denied: Please log in.");
        }
    }

    protected abstract void doGet(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer)
            throws ServletException, IOException;

    protected abstract void doGet(HttpServletRequest request, HttpServletResponse response, User user, Student student)
            throws ServletException, IOException;

    protected abstract void doPost(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer)
            throws ServletException, IOException;

    protected abstract void doPost(HttpServletRequest request, HttpServletResponse response, User user, Student student)
            throws ServletException, IOException;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if (isLecturerAuthenticated(request)) {
            if (isStudentRestrictedPath(path)) {
                response.getWriter().println("Access Denied: Students are not allowed to access this page.");
                return;
            }
            User user = (User) request.getSession().getAttribute("user");
            doPost(request, response, user, user.getLecturer());
        } else if (isStudentAuthenticated(request)) {
            if (isLecturerRestrictedPath(path)) {
                response.getWriter().println("Access Denied: Lecturers are not allowed to access this page.");
                return;
            }
            User user = (User) request.getSession().getAttribute("user");
            doPost(request, response, user, user.getStudent());
        } else {
            response.getWriter().println("Access Denied: Please log in.");
        }
    }

    private boolean isStudentRestrictedPath(String path) {
        // Define paths that are restricted to students
        // Add paths that are meant only for lecturers
        return path.startsWith("/lecturer-only");
    }

    private boolean isLecturerRestrictedPath(String path) {
        // Define paths that are restricted to lecturers
        // Add paths that are meant only for students
        return path.startsWith("/student-only");
    }

    @Override
    public String getServletInfo() {
        return "Authentication Controller for Both Lecturers and Students";
    }
}
