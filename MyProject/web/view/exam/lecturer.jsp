<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lecturer Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
                background-color: #e9ecef;
            }
            .container {
                background: #fff;
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 500px; /* Tăng chiều rộng tối đa */
                text-align: center;
            }
            .form-title {
                font-size: 24px;
                margin-bottom: 20px;
                font-weight: bold;
                color: #333;
            }
            form {
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            select, input[type="submit"] {
                margin: 10px 0;
                padding: 12px;
                border-radius: 5px;
                border: 1px solid #ccc;
                width: 100%;
                box-sizing: border-box;
                background-color: #f8f9fa;
                font-size: 16px;
            }
            input[type="checkbox"] {
                margin-right: 10px;
            }
            label {
                display: flex;
                align-items: center;
                margin: 10px 0;
                font-size: 16px;
                color: #555;
            }
            /* Đổi màu cho các nút */
            input[type="submit"] {
                background-color: #007bff;
                color: #fff;
                border: none;
                cursor: pointer;
                font-size: 18px;
            }
            input[type="submit"]:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <c:if test="${requestScope.exams eq null}">
                <c:if test="${requestScope.courses.size() > 0}">
                    <div class="form-title">Select a Course</div>
                    <form action="lecturer" method="POST">
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
                </c:if>
            </c:if>
            <c:if test="${requestScope.exams ne null}">
                <div class="form-title">Select Exams to Take</div>
                <form action="take" method="GET">
                    <input type="hidden" name="cid" value="${param.cid}"/>
                    <c:forEach items="${requestScope.exams}" var="e">
                        <label>
                            <input type="checkbox" name="eid" value="${e.id}" /> 
                            ${e.assessment.name} - (${e.from}: ${e.assessment.weight}%)
                        </label>
                    </c:forEach>
                    <input type="submit" value="Take Exams"/>
                </form>
            </c:if>
        </div>
    </body>
</html>
