/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.wall;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Comment;
import model.Post;

/**
 *
 * @author sonnt
 */
public class ListPostController extends HttpServlet {
   
   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("view/wall/list.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ArrayList<Post> posts = new ArrayList<>();
        String[] postids = request.getParameterValues("postid");
        String[] commentids = request.getParameterValues("commentid");
        for (String postid : postids) {
            int pid = Integer.parseInt(postid);
            Post p = new Post();
            p.setId(pid);
            p.setContent(request.getParameter("postcontent"+ p.getId()));
            
            for (String commentid : commentids) {
                String parentid = request.getParameter("parentid"+commentid);
                if(parentid.equals(postid))
                {
                    Comment c = new Comment();
                    c.setId(Integer.parseInt(commentid));
                    c.setContent(request.getParameter("commentcontent"+ commentid));
                    c.setPost(p);
                    p.getComments().add(c);
                }
            }
            posts.add(p);
        }
        request.setAttribute("posts", posts);
        request.getRequestDispatcher("view/wall/list_result.jsp").forward(request, response);
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
