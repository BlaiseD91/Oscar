<?php
    $conn = mysqli_connect("localhost", "root", "")
        or die("Csatlakozási hiba");
    mysqli_select_db($conn, "oscar");

    $azon = $_REQUEST["azon"];
    $cim = $_REQUEST["cim"];
    $ev = $_REQUEST["ev"];
    $dij = $_REQUEST["dij"];
    $jelol = $_REQUEST["jelol"];

    $sql = "UPDATE filmek SET cim = '".$cim."', ev =".$ev.", dij =".$dij.", jelol =".$jelol."  WHERE azon = '".$azon."';";
    
    mysqli_query($conn, $sql);
    mysqli_close($conn);
    header("Location:index.php");
?>