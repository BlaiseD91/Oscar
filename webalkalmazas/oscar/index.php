<!DOCTYPE html>
<html lang="hu">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
    <title>Oscar</title>
</head>
<body class="bg-dark bg-gradient">
    <div class="container pt-3">
        <h1 class="text-warning">Oscar-díjjal kitüntetett filmek</h1>
        <form class="mt-5 p-3 border border-3 rounded-3 border-primary" action="add.php" method="post">
            <h5 class="text-white">Új adat feltöltése</h5>
            <div class="mb-3" id="form">
                <input type="text" class="form-control m-" id="azon" placeholder="Azonosító" name="azon">
                <input type="text" class="form-control" id="cim" placeholder="A film címe" name="cim">
                <input type="number" class="form-control" id="ev" placeholder="A díjazás éve" name="ev">
                <input type="number" class="form-control" id="dij" placeholder="Elnyert díjak száma" name="dij">
                <input type="number" class="form-control" id="jelol" placeholder="Jelölések száma" name="jelol">
            </div>
            <button type="submit" class="btn btn-primary">Feltöltés</button>
        </form>
        <div class="mt-5" id="adatbazis">
            <h5 class="text-warning">Az adatbázisban szereplő adatok</h5>
            <table class="table">
                <thead>
                    <tr class="text-white">
                        <th scope="col">#</td>
                        <th scope="col">Cím</td>
                        <th scope="col">Év</td>
                        <th scope="col">Díjak</td>
                        <th scope="col">Jelölések</td>
                        <th scope="col">Műveletek</td>
                    </tr>
                </thead>
                <tbody>
                    <?php
                        $conn = mysqli_connect("localhost", "root", "")
                            or die("Csatlakozási hiba");
                        mysqli_select_db($conn, "oscar");

                        $sql = "SELECT * FROM filmek;";

                        $filmek = mysqli_query($conn, $sql);
                        mysqli_close($conn);
                        while($sor=mysqli_fetch_assoc($filmek)){
                    ?>
                        <tr class="text-white">
                            <td><?php print($sor["azon"]); ?></th>
                            <td><?php print($sor["cim"]); ?></td>
                            <td><?php print($sor["ev"]); ?></td>
                            <td><?php print($sor["dij"]); ?></td>
                            <td><?php print($sor["jelol"]); ?></td>
                            <td>
                                <a href="update.php?azon=<?php print $sor['azon'] ?>" class="btn btn-warning"><img src="img/update.svg" alt="Módosítás"></a>                     
                                <a href="delete.php?azon=<?php print $sor['azon'] ?>" class="btn btn-danger"><img src="img/delete.svg" alt="Törlés"></a>
                            </td>
                        </tr>
                    <?php 
                        }
                    ?>
                </tbody>
            </table>
        </div>
    </div>



    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>