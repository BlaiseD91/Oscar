<?php
    $conn = mysqli_connect("localhost", "root", "")
        or die("Csatlakozási hiba");
    mysqli_select_db($conn, "oscar");

    $azon = $_REQUEST["azon"];
    $cim = $_REQUEST["cim"];
    $ev = $_REQUEST["ev"];
    $dij = $_REQUEST["dij"];
    $jelol = $_REQUEST["jelol"];

    $sql = "insert into filmek values ('".$azon."', '".$cim."', ".$ev.", ".$dij.", ".$jelol.");";


    mysqli_query($conn, $sql);
    mysqli_close($conn);
    header("Location:index.php");
?>