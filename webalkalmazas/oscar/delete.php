<?php
    $conn = mysqli_connect("localhost", "root", "")
        or die("Csatlakozási hiba");
    mysqli_select_db($conn, "oscar");

    $azon = $_REQUEST["azon"];

    $sql = "DELETE FROM filmek WHERE azon='".$azon."';";


    mysqli_query($conn, $sql);
    mysqli_close($conn);
    header("Location:index.php");
?>