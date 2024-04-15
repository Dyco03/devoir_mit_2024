<?php
    //prendre l'id
    $id = null;
    if(isset($_GET["id"])){
        $id = $_GET["id"];
        $id = intval($id);
        echo $id;
    }
    //recuperer les donnés des formulaires
    $bookName = null; $bookEdition = null; $bookPrice = null;
    if(isset($_POST["bookName"]) && isset($_POST["bookEdition"]) && isset($_POST["bookPrice"]) ){
        $bookName = $_POST["bookName"];
        $bookEdition = $_POST["bookEdition"];
        $bookPrice = $_POST["bookPrice"];
    }

    //bd
    $mysqli = new mysqli("localhost","root","","book") or die("Impossible de se connecter");
    $state = $mysqli->prepare("UPDATE book_data SET bookName=?,bookEdition=?,bookPrice=? where id=?");
    $state->bind_param("sssi",$bookName,$bookEdition,$bookPrice,$id);
    $state->execute();
    $count = 1;
    print("<style>");
            print("h2 {text-align: center;}");
            print("a {display: block; text-align: center; margin-top: 20px; color: #007bff; text-decoration: none;}");
            print("a:hover {text-decoration: underline;}");
            print("</style>");
            if(isset($count)) {
                print("<h2>Edit Book Successful</h2>");
            } else {
                print("<h2>Edit Book Unsuccessful</h2>");
            }
            print("<a href='../vue/home.html'>Home</a>");
            print("<a href='../php/ListRegister.php'>Book List</a>");


?>