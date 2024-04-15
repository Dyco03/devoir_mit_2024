<?php
function performSearch($searchKeyword){
    // Préparer la requete de recherche avec le terme de recherche
    $mysqli = new mysqli("localhost","root","","book") or die("Impossible de se connecter");
    $state = $mysqli->prepare("SELECT * FROM book_data WHERE bookName = ?");
    $state->bind_param("s",$searchKeyword);
    $state->execute();
    $rs = $state->get_result();
    // Afficher les résultats de la recherche
    displayResultSet($rs);
}

function showAllBooks(){
    $mysqli = new mysqli("localhost","root","","book") or die("Impossible de se connecter");
    $state = $mysqli->prepare("SELECT * FROM book_data");
    $state->execute();
    $rs = $state->get_result();
    //afficher
    displayResultSet($rs);
}

function displayResultSet($rs){
    // Afficher les résultats 
    print("<style>");
    print("table {border-collapse: collapse; width: 80%; margin: 20px auto;}");
    print("th, td {border: 1px solid #ddd; padding: 8px; text-align: left;}");
    print("th {background-color: #f2f2f2;}");
    print("th, td {font-family: Arial, sans-serif;}");
    print("a {text-decoration: none; color: #007bff;}");
    print("a:hover {text-decoration: underline;}");
    print("</style>");
    print("<table>");
    print("<tr>");
    print("<th>Book Id</th>");
    print("<th>Book Name</th>");
    print("<th>Book Edition</th>");
    print("<th>Book Price</th>");
    print("<th>Book Status</th>");
    print("<th>Edit</th>");
    print("<th>Delete</th>");
    print("</tr>");
    while($line = $rs->fetch_array()) {
        print("<tr>");
        print("<td>".$line[0]."</td>");
        print("<td>".$line[1]."</td>");
        print("<td>".$line[2]."</td>");
        print("<td>".$line[3]."</td>");
        print("<td>".$line[4]."</td>");
        print("<td><a href='EditsBook.php?id=".$line[0]."'>Edit</a></td>");
        print("<td><a href='Delete.php?id=".$line[0]."&table=book_data&redirection=ListRegister.php'>Delete</a></td>");
        print("</tr>");
    }
    print("</table>");
}

// Récupérer le terme de recherche depuis la requete
$searchKeyword = null;
if(isset($_POST["search"])){
    $searchKeyword = $_POST["search"];
}

// Afficher le formulaire de recherche
print("<form method='post' action='ListRegister.php'>");
print("<input type='text' name='search' placeholder='Search book'>");
print("<input type='submit' value='Search'>");
print("<a href='ListRegister.php'>Show All</a>");
print("</form>");
// Vérifier si un terme de recherche a été fourni
if ($searchKeyword != null) {
    // Si il y a correspondance, effectuer une recherche
    performSearch($searchKeyword);
} else {
    // Sinon, afficher tous les livres
    showAllBooks();
}
print("<a href='../vue/home.html'>Home</a>");

?>
