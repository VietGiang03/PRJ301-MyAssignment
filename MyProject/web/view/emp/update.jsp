<%-- 
    Document   : update
    Created on : May 29, 2024, 4:10:41 PM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="update" method="POST">
            <input type="hidden" name="id" value="${param.id}"/>
            Name: <input type="text" name="name" value="${requestScope.e.name}"/> <br/>
            Gender: <input type="radio" 
                           ${requestScope.e.gender?"checked=\"checked\"":""}
                           name="gender" value="male"/> Male
            <input type="radio"
                   ${!requestScope.e.gender?"checked=\"checked\"":""}
                   name="gender" value="female"/> Female <br/>
            Dob: <input type="date" name="dob" value="${requestScope.e.dob}"/> <br/>
            Dept: <select name="did">
                <c:forEach items="${requestScope.depts}" var="d">
                    <option
                        ${d.id eq requestScope.e.dept.id?"selected=\"selected\"":""}
                        value="${d.id}">${d.name}</option>
                </c:forEach>
            </select>
            <br/>
            <input type="submit" value="Save"/> 
        </form>
    </body>
</html>
