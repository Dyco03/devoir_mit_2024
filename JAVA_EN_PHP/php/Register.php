<?php
    //requete
    $query = "INSERT INTO book_data(bookName,bookEdition,bookPrice) VALUES(?,?,?)";

    //GET THE book info
    $bookName = $_POST["bookName"];
    $bookEdition = $_POST["bookEdition"];
    $bookPrice = $_POST["bookPrice"];
    //generer la connexion
    $mysqli = new mysqli("localhost","root","","book") or die("Impossible de se connecter");
    $state = $mysqli->prepare("INSERT INTO book_data(bookName,bookEdition,bookPrice) VALUES(?,?,?)");
    $state->bind_param("sss",$bookName,$bookEdition,$bookPrice);
    $count = $state->execute();

    print("<style>");
    print("h2 {text-align: center;}");
    print("a {display: block; text-align: center; margin-top: 20px; color: #007bff; text-decoration: none;}");
    print("a:hover {text-decoration: underline;}");
    print("</style>");

    if(isset($count)) {
        print("<h2>Registration Successful</h2>");
    } else {
        print("<h2>Registration Failed</h2>");
    }
    print("<a href='../vue/home.html'>Home</a>");
    print("<a href='ListRegister.php'>Book List</a>");



?>