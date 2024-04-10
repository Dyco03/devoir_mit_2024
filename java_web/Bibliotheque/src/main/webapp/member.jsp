<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Library Member Registration</title>
<link rel="stylesheet" href="css/member.css">
</head>
<body>
<h2>Library Member Registration</h2>
<form action="MemberServlet" method="post">
    <table>
       <tr>
        <td>First Name:</td>
        <td><input type="text" name="firstName" required></td>
       </tr>
       <tr>
        <td>Last Name:</td>
        <td><input type="text" name="lastName" required></td>
       </tr>
       <tr>
        <td>Email:</td>
        <td><input type="email" name="email" required></td>
       </tr>
       <tr>
        <td>Address:</td>
        <td><input type="text" name="address" required></td>
       </tr>
       <tr>
        <td colspan="2"><input type="submit" value="Register"></td>
       </tr>
    </table>
    
</form>
	<a href='home.jsp'>Home</a>
	<a href='MemberListServlet'>Member List</a>
    <a href='ListRegisterServlet'>Book List</a>
    <a href='ListBorrowServlet'>Show Borrow List</a>
</body>
</html>
