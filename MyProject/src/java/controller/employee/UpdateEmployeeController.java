/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.employee;

import controller.auth.BaseRequiredLecturerAuthenticationController;
import dal.DepartmentDBContext;
import dal.EmployeeDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import model.Department;
import model.Employee;
import model.Lecturer;
import model.User;

/**
 *
 * @author sonng
 */
public class UpdateEmployeeController extends BaseRequiredLecturerAuthenticationController {
   
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   
    protected void doGet(HttpServletRequest request, HttpServletResponse response,User user, Lecturer lecturer)
    throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        EmployeeDBContext edb = new EmployeeDBContext();
        Employee e = edb.get(id);
        request.setAttribute("e", e );
        
        DepartmentDBContext db = new DepartmentDBContext();
        ArrayList<Department> depts = db.list();
        request.setAttribute("depts", depts);
        request.getRequestDispatcher("../view/emp/update.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response,User user, Lecturer lecturer)
    throws ServletException, IOException {
        String name = request.getParameter("name");
        boolean gende = request.getParameter("gender").equals("male");
        Date dob = Date.valueOf(request.getParameter("dob"));
        int did = Integer.parseInt(request.getParameter("did"));
        
        Department d = new Department();
        d.setId(did);
        
        Employee e = new Employee();
        e.setId(Integer.parseInt(request.getParameter("id")));
        e.setName(name);
        e.setGender(gende);
        e.setDob(dob);
        e.setDept(d);
        
        EmployeeDBContext db = new EmployeeDBContext();
        db.update(e);
        
        response.sendRedirect("detail?id="+e.getId());
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

   

}
