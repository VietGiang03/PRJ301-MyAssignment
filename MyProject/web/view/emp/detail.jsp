<%-- 
    Document   : detail
    Created on : May 29, 2024, 4:10:44 PM
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
        <c:if test="${requestScope.e ne null}">
            Id: ${e.id} <br/>
            Name: ${e.name} <br/>
            Gender: ${gender?"Male":"Female"} <br/>
            Dob:${e.dob} <br/>
            Department: ${e.dept.name}
        </c:if>
        <c:if test="${requestScope.e eq null}">
            employee does not exist!
        </c:if>
    </body>
</html>
