<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Student Courses and Exams</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="${pageContext.request.contextPath}/css/viewstudent.css" rel="stylesheet" type="text/css"/>
        <title>Select Course</title>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: Arial, sans-serif;
            background-color: #f0f0f0; /* Background color */
        }

        .centered-container {
            background: rgba(255, 255, 255, 0.9); /* Semi-transparent background */
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Light shadow */
            border-radius: 10px; /* Rounded corners */
            text-align: center;
        }

        .form-title {
            margin-bottom: 20px;
            font-size: 24px;
            font-weight: bold;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        select, input[type="submit"] {
            margin-top: 10px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            width: 100%;
            max-width: 300px;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
    </head>
    <body>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <html>
        <head>

        </head>
        <body>

            <div class="container mt-5 pt-5">
                <c:if test="${requestScope.exams eq null}">
                    <c:if test="${requestScope.courses.size() > 0}">
                        <div class="centered-container">
                            <div class="form-title">Select a Course</div>
                            <form action="student" method="POST">
                                <input type="hidden" name="lid" value="${param.lid}"/>
                                <select name="cid">
                                    <c:forEach items="${requestScope.courses}" var="c">
                                        <option value="${c.id}">
                                            ${c.name}
                                        </option>
                                    </c:forEach>
                                </select>
                                <input type="submit" value="View Exams"/>
                            </form>
                        </div>
                    </c:if>
                </c:if>

                <c:if test="${not empty requestScope.exams}">
                    <h2>Exam Scores</h2>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead class="thead-dark">
                                <tr>
                                    <th>Exam Name</th>
                                    <th>Date</th>
                                    <th>Weight</th>
                                    <th>Score</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.exams}" var="e">
                                    <tr>
                                        <td>${e.assessment.name}</td>
                                        <td>${e.from}</td>
                                        <td>${e.assessment.weight}%</td>
                                        <td>${e.grade.score}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <h3>Total Weighted Score: ${requestScope.totalWeightedScore}</h3>
                    <h3>Status: ${requestScope.passed}</h3>
                </c:if>

            </div>

        </body>
    </html>