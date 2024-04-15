<?php
    //prendre l'id
    $id = null;
    if(isset($_GET["id"])){
        $id = $_GET["id"];
    }

    //bd
    $mysqli = new mysqli("localhost","root","","book") or die("Impossible de se connecter");
    $state = $mysqli->prepare("SELECT bookName,bookEdition,bookPrice FROM book_data where id=?");
    $state->bind_param("s",$id);
    $state->execute();
    $rs = $state->get_result();
    $line = $rs->fetch_array();
    //afficher
    print("<style>");
    print("body {font-family: Arial, sans-serif;}");
    print("table { margin: 20px auto; }");
    print("td {padding: 10px;}");
    print("input[type='text'], input[type='submit'], input[type='reset'] {font-size : large;width: calc(100% - 20px); padding: 10px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 5px;}");
    print("input[type='submit'], input[type='reset'] {font-size : large;background-color: #007bff; color: #fff; border: none; cursor: pointer;}");
    print("input[type='submit']:hover, input[type='reset']:hover {font-size : large;background-color: #0056b3;}");
    print("a {\n"
            . "    display: block;\n"
            . "    text-align: center;\n"
            . "    text-decoration: none;\n"
            . "    color: #007bff;\n"
            . "    margin-top: 10px;\n"
            . "}\n"
            . "\n"
            . "a:hover {\n"
            . "    text-decoration: underline;\n"
            . "}");
    print("</style>");
    print("<form action='Edit.php?id=".$id."' method='post'>");
    print("<table>");
    print("<tr>");
    print("<td>Book Name</td>");
    print("<td><input type='text' name='bookName' value='".$line[0]."'></td>");
    print("</tr>");
    print("<tr>");
    print("<td>Book Edition</td>");
    print("<td><input type='text' name='bookEdition' value='".$line[1]."'></td>");
    print("</tr>");
    print("<tr>");
    print("<td>Book Price</td>");
    print("<td><input type='text' name='bookPrice' value='".$line[2]."'></td>");
    print("</tr>");
    print("<tr>");
    print("<td><input type='submit' value='Edit'></td>");
    print("<td><input type='reset' value='cancel'></td>");
    print("</tr>");
    print("</table>");
    print("</form>");

    print("<a href='../vue/home.html'>Home</a>");

?>