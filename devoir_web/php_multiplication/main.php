<?php session_start(); 
    session_destroy();
?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Entrer Valeur</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            background-image: url("image.jpg");
            background-size: cover;
        }

        .conteneur {
            background-image : url(image.webp);
            background-size: cover;
            width: 50%;
            margin: 100px auto;
            background-color: #fff;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
        }

        .input-group {
            margin-bottom: 20px;
        }

        .input-group label {
            display: block;
            margin-bottom: 5px;
            color: #222;
            font-weight: bold;
        }

        .input-group input[type="number"] {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #0056b3;
        }

    </style>
</head>
<body>
    <div class="conteneur">
        <h1>Entrez les valeurs de A et B</h1>
        <form action="table.php" method="POST">
            <div class="input-group">
                <label for="a">A :</label>
                <input type="number" id="a" name="a" placeholder="Entrez la valeur de A" required>
            </div>
            <div class="input-group">
                <label for="b">B :</label>
                <input type="number" id="b" name="b" placeholder="Entrez la valeur de B" required>
            </div>
            <button type="submit">Valider</button>
        </form>
    </div>
</body>
</html>
