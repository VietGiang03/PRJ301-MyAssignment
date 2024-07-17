<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View login</title>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: Arial, Helvetica, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            background: #f0f0f0;
        }

        .container {
            text-align: center;
            background: #fff;
            padding: 40px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.5);
            border-radius: 10px;
            position: relative;
        }

        .account-info {
            position: absolute;
            top: 10px;
            right: 10px;
            background: #03a9f4;
            color: #fff;
            padding: 10px;
            border-radius: 5px;
        }

        h1 {
            margin-bottom: 20px;
        }

        .link-button {
            display: block;
            margin: 20px 0;
            padding: 10px 20px;
            background: #03a9f4;
            color: #fff;
            text-decoration: none;
            font-size: 18px;
            border-radius: 5px;
            transition: background 0.3s;
        }

        .link-button:hover {
            background: #0288d1;
        }

        .search-box {
            margin: 20px 0;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            width: 80%;
        }

        .logout-button {
            margin: 20px 0;
            padding: 10px 20px;
            background: #ff5252;
            color: #fff;
            text-decoration: none;
            font-size: 18px;
            border-radius: 5px;
            transition: background 0.3s;
        }

        .logout-button:hover {
            background: #ff1744;
        }
    </style>
</head>
<body>
  <div class="header">
        <h1>Welcome to FPT</h1>
        <input type="text" class="search-box" placeholder="Search...">
        <a href="/MyProject/exam/lecturer" class="link-button">Grade</a>
        <a href="/MyProject/emp/list" class="link-button">Student List</a>
        <a href="/MyProject/login" class="logout-button">Logout</a>
    </div>
</body>
</html>
