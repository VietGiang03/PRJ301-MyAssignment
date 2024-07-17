<%-- 
    Document   : list
    Created on : May 29, 2024, 4:10:34 PM
    Author     : sonng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee List</title>
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
            table {
                border-collapse: collapse;
                width: 50%;
                background-color: #fff;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            th, td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: center;
            }
            th {
                background-color: #007bff;
                color: white;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            tr:hover {
                background-color: #ddd;
            }
            a {
                text-decoration: none;
                color: #007bff;
                font-weight: bold;
            }
            a:hover {
                text-decoration: underline;
            }
            input[type="button"] {
                padding: 5px 10px;
                margin: 2px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            input[type="button"]:hover {
                background-color: #0056b3;
            }
            .create-link {
                display: block;
                margin: 20px 0;
                text-align: center;
            }
        </style>
        <script>
            function deleteEmp(id) {
                var result = confirm('Are you sure?');
                if(result) {
                    window.location.href='remove?id='+id;
                }
            }
        </script>
    </head>
    <body>
        <table>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Actions</th>
            </tr>
            <c:forEach items="${requestScope.emps}" var="e">
                <tr>
                    <td>${e.id}</td>
                    <td><a href="detail?id=${e.id}">${e.name}</a></td>
                    <td>
                        <input type="button" value="Update" onclick="window.location.href='update?id=${e.id}'"/>
                        <input type="button" value="Remove" onclick="deleteEmp(${e.id})"/>
                    </td>
                </tr>   
            </c:forEach>
        </table>
        <div class="create-link">
            <a href="create">Create</a>
        </div>
    </body>
</html>
