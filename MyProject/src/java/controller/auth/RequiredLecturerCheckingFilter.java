package controller.auth;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import model.Lecturer;
import model.User;

public class RequiredLecturerCheckingFilter implements Filter {
    
    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String url = httpRequest.getServletPath();
        if(!url.equals("/login") || !url.equals("/logout")) {
            if(isAuthenticated(httpRequest)) {
                chain.doFilter(request, response);
            } else {
                response.getWriter().println("access denied!");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if(user == null) {
            return false;
        } else {
            Lecturer lecturer = user.getLecturer();
            return lecturer != null;
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("RequiredLecturerCheckingFilter()");
        }
        return ("RequiredLecturerCheckingFilter(" + filterConfig + ")");
    }
}