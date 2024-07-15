package controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import model.Student;
import model.User;

/**
 * Servlet implementation class ViewGradesController
 */
@WebServlet("/viewGrades")
public class ViewGradesController extends BaseRequiredStudentAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Student student)
            throws ServletException, IOException {
        // Logic to retrieve and display the student's grades
        // For example:
        // List<Grade> grades = GradeDAO.getGradesByStudentId(student.getId());
        // request.setAttribute("grades", grades);
        // request.getRequestDispatcher("/viewGrades.jsp").forward(request, response);

        // For now, let's just display a simple message
        response.setContentType("../view/exam/viewstudent.jsp");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Grades for " + student.getName() + "</h1>");
        out.println("<p>Grade: A</p>"); // Example grade
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Student student)
            throws ServletException, IOException {
        // Restrict POST method to prevent any modifications
        response.setContentType("../view/exam/viewstudent.jsp");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Access Denied</h1>");
        out.println("<p>You do not have permission to edit grades.</p>");
        out.println("</body></html>");
    }
}
