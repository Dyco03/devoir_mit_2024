<?php
session_start();

// Vérifier si la variable de session pour 'table' existe
if (!isset($_SESSION['table'])) {
    // Si non, initialiser 'table' avec un tableau vide
    $_SESSION['table'] = array();
}

echo $_POST["a_mod"];
$a = $_POST['a'];
$b = $_POST['b'];
$a_mod = $_POST['a_mod'];
$b_mod = $_POST['b_mod'];
$index_mod = $_POST['index_mod'];
$test = $_GET["t"]; //test si la page a déjà été rechargée
$color = array("pair","impair"); // pour la parité

if ($test == 1) {   //si une valeur a ete deja supprime
    $table = $_SESSION['table'];
    unset($table[$_GET['index']]);
    $_SESSION['table'] = $table;
}
elseif (isset($index_mod)) //su une valeur a ete  deja modifier
{
    $table = $_SESSION['table'];
    $table[$index_mod]['a'] = $a_mod;
    $table[$index_mod]['b'] = $b_mod;
    $table[$index_mod]['valeur'] = $a_mod * $b_mod;
    $_SESSION['table'] = $table;
} 
else {
    if (empty($_SESSION['table'])) {
        $table = $_SESSION['table'];
        for ($i = 0; $i <= $b; $i++) {
            $table[] = (
                array("a"=>$a, "b"=>$i, "valeur"=>$a*$i)
            );
        }
        $_SESSION['table'] = $table;
    }
}

$table = $_SESSION['table'];
?>
<html>
    <head>
    <link href="table.css" rel="stylesheet"/>
    </head>
    
    <body>
        <div>
            <button class="button" onclick="changerPage()">Entrer une valeur</button>
        </div>
        <section class="tableau">
            <table border=1>
                <caption>Multiplication</caption>
                <tr>
                    <th>a</th>
                    <th>*</th>
                    <th>b</th>
                    <th>=</th>
                    <th>Réponse</th>
                    <th>Action</th>
                </tr>
               
        <?php
            if(count($table) > 0)
            {
                foreach ($table as $i => $row) {
                    echo '<tr class='.$color[($row["a"]*$row["b"])%2].'>
                        <td>'.$row["a"].'</td>
                        <th>*</th>
                        <td>'.$row["b"].'</td>
                        <th>=</th>
                        <td>'.$row["valeur"].'</td>
                        <td>
                            <a id="modif" href="modifier.php?index_mod='.$i.'">modifier</a>
                            <a id="delete" href="table.php?t=1&index='.$i.'">supprimer</a>
                        </td>
                    </tr>';
                }
            }
            else {
                echo '<tr>
                        <td colspan="6"><strong>Le tableau est vide</strong></td>
                      </tr>';
            }
        ?>
        
            </table>

        </section>

    </body
</html>  
<script>
function changerPage(){
    window.location.href= "main.php"
}
</script>
