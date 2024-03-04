<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Multiple Values Input</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-image : url("image.jpg");
        background-size : cover;    
        margin: 0;
        padding: 0;
    }
    .conteneur {
        background-image : url("image.webp");
        background-size : cover;
        max-width: 600px;
        margin: 50px auto;
        background-color: #fff;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }
    h2 {
        text-align: center;
        margin-bottom: 20px;
    }
    .input-group {
        margin-bottom: 20px;
    }
    .input-group label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }
    .input-group input[type="number"] {
        width: calc(50% - 10px);
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }
    .input-group input[type="submit"] {
        width: calc(50% - 10px);
        padding: 8px;
        border: none;
        border-radius: 4px;
        background-color: #007bff;
        color: #fff;
        cursor: pointer;
    }
    .input-group input[type="submit"]:hover {
        background-color: #0056b3;
    }
</style>
</head>
<body>

<div class="conteneur">
    <h2>Enter la valeur de a et b</h2>
    <form action="table.php" method="post">
        <div class="input-group">
            <label for="value1">a:</label>
            <input type="number" id="value1" name="a_mod" required>
        </div>
        <div class="input-group">
            <label for="value2">b:</label>
            <input type="number" id="value2" name="b_mod" required>
        </div>
        <!-- Champs cachés pour envoyer l'index -->
        <input type="hidden" name="index_mod" value=<?php echo $_GET["index_mod"]?>>
        <input type="submit" value="Submit">
    </form>
</div>

</body>
</html>
