<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Grades</title>
</head>
<body>
    <h1>Your Grades</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Exam</th>
                <th>Weight</th>
                <th>Score</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="exam" items="${exams}">
                <c:forEach var="grade" items="${grades}">
                    <c:if test="${exam.id == grade.exam.id}">
                        <tr>
                            <td>${exam.assessment.name}</td>
                            <td>${exam.assessment.weight}</td>
                            <td>${grade.score}</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
