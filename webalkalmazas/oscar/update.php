<!DOCTYPE html>
<html lang="hu">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <title>Oscar</title>
</head>
<body class="bg-dark">
    <div class="container pt-3">
        <h1 class="text-warning">Oscar-díjas filmek</h1>
        <div class="mt-5 p-3 border border-3 rounded-3 border-warning">
            <h5 class="text-white">Adatmódosítás</h5>
            <?php
                $conn = mysqli_connect("localhost", "root", "")
                    or die("Csatlakozási hiba");
                mysqli_select_db($conn, "oscar");
                $azon = $_REQUEST['azon'];
                $sql = "SELECT * FROM filmek WHERE azon='".$azon."';";
                $adat = mysqli_query($conn, $sql);
                $film = mysqli_fetch_assoc($adat);
                mysqli_close($conn);
            ?>
            <form action="dataupdate.php" method="post">
                <div class="mb-3">
                    <label for="azon" class="form-label text-white">Azonosító (nem módosítható):</label>
                    <input value="<?php print($film['azon']); ?>" readonly class="form-control bg-secondary text-white border border-2 border-warning" id="azon" name="azon">
                </div>
                <div class="mb-3">
                    <label for="cim" class="form-label text-white">A film címe:</label>
                    <input value="<?php print($film['cim']); ?>" type="text" class="form-control bg-secondary text-white border border-2 border-warning" id="cim" name="cim">
                </div>
                <div class="mb-3">
                    <label for="ev" class="form-label text-white">Díjazás éve:</label>
                    <input value="<?php print($film['ev']); ?>" type="number" class="form-control bg-secondary text-white border border-2 border-warning" id="ev" name="ev">
                </div>
                <div class="mb-3">
                    <label for="dij" class="form-label text-white">Díjak száma:</label>
                    <input value="<?php print($film['dij']); ?>" type="number" class="form-control bg-secondary text-white border border-2 border-warning" id="dij" name="dij">
                </div>
                <div class="mb-3">
                    <label for="jelol" class="form-label text-white">Jelölések száma:</label>
                    <input value="<?php print($film['jelol']); ?>" type="number" class="form-control bg-secondary text-white border border-2 border-warning" id="jelol" name="jelol">
                </div>
                <button type="submit" class="btn btn-warning fw-bold">Módosítás</button>
            </form>
        </div>

    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>