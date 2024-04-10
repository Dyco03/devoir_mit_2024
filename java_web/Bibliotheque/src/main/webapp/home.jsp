<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Registration</title>
<link rel="stylesheet" href="css/home.css">
</head>
<body>
<div class="form-container">
    <h2>Book Registration</h2>
    <form action="RegisterServlet" method="post">
        <table class="table table-hover">
           <tr>
            <td>Book Name</td>
            <td><input type="text" name="bookName" required></td>
           </tr>
           <tr>
            <td>Book Edition</td>
            <td><input type="text" name="bookEdition" required></td>
           </tr>
           <tr>
            <td>Book Price</td>
            <td><input type="text" name="bookPrice" requireds></td>
           </tr>
           <tr>
            <td><input type="submit" value="register"></td>
            <td><input type="reset" value="cancel"></td>
           </tr>
        </table>
    </form>
</div>

<div class="section">
    <h3>Actions:</h3>
    <ul>
        <li><a href='member.jsp'>Add Member</a></li>
        <li><a href='borrow.jsp'>To Borrow Book</a></li>
    </ul>
</div>

<div class="section">
    <h3>Listes:</h3>
    <ul>
        <li><a href='MemberListServlet'>Show Member List</a></li>
        <li><a href='ListRegisterServlet'>Show Book List</a></li>
        <li><a href='ListBorrowServlet'>Show Borrow List</a></li>
    </ul>
</div>
</body>
</html>
