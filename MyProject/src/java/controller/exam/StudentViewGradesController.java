package controller.exam;

import controller.auth.BaseRequiredAuthenticationController;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Student;
import model.User;
import model.Grade;
import model.Exam;

@WebServlet("/viewGrades")
public class StudentViewGradesController extends BaseRequiredAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Student student)
            throws ServletException, IOException {
        Grade grade = new Grade();
        Exam exam = new Exam();              
        List<Grade> grades = grade.getExam(student.getId());
        ArrayList<Grade> exams = exam.getGrades();

        request.setAttribute("grades", grades);
        request.setAttribute("exams", exams);

        request.getRequestDispatcher("/WEB-INF/views/student/viewGrades.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Student student)
            throws ServletException, IOException {
        response.getWriter().println("POST requests are not supported for this page.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer)
            throws ServletException, IOException {
        response.getWriter().println("Access Denied: This page is only for students.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user, Lecturer lecturer)
            throws ServletException, IOException {
        response.getWriter().println("POST requests are not supported for this page.");
    }
}
