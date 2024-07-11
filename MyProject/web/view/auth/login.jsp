<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transparent Login Form</title>
    <link rel="stylesheet" href="login.css">
</head>
<body>
    <div class="background"></div>
    <div class="login-form">
        <h2>Login</h2>
        <form action="login" method="post">
            <div class="input-box">
                <input type="text" name="username" required>
                <label>Username</label>
            </div>
            <div class="input-box">
                <input type="password" name="password" required>
                <label>Password</label>
            </div>
            <button type="submit">Login</button>
        </form>
    </div>
</body>
</html>
