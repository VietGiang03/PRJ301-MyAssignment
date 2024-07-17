<%-- 
    Document   : create
    Created on : May 29, 2024, 4:10:38 PM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Entry</title>
        <style>
            body {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
            }
            form {
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 300px;
            }
            form input[type="text"],
            form input[type="date"],
            form select {
                width: 100%;
                padding: 8px;
                margin: 8px 0;
                box-sizing: border-box;
            }
            form input[type="submit"] {
                width: 100%;
                padding: 10px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            form input[type="submit"]:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <form action="create" method="POST"> 
            <label for="name">Name:</label>
            <input type="text" id="name" name="name"/> <br/>
            <label>Gender:</label> <br/>
            <input type="radio" id="male" name="gender" value="male"/>
            <label for="male">Male</label>
            <input type="radio" id="female" name="gender" value="female"/>
            <label for="female">Female</label> <br/>
            <label for="dob">Dob:</label>
            <input type="date" id="dob" name="dob"/> <br/>
            <label for="did">Dept:</label>
            <select id="did" name="did">
                <c:forEach items="${requestScope.depts}" var="e">
                    <option value="${e.id}">${e.name}</option>
                </c:forEach>
            </select>
            <br/>
            <input type="submit" value="Save"/> 
        </form>
    </body>
</html>
