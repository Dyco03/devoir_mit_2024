<?php
    //prendre l'id
    $id = null;
    if(isset($_GET["id"])){
        $id = $_GET["id"];
        $id = intval($id);
    }

    $table = null; $redirection = null;
    if(isset($_GET["table"])){
        $table = $_GET["table"];

    }
    $mysqli = new mysqli("localhost","root","","book") or die("Impossible de se connecter");
    $state = $mysqli->prepare("DELETE FROM book_data where id=?");
    $state->bind_param("i",$id);
    $state->execute();
    header("Location: ListRegister.php");   
   

?>