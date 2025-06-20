<?php

// Check for post data
if (isset($_POST["id"])) {
    $id = $_POST['id'];
    $pw = $_POST['pw'];

    // Include db connect class
    require_once __DIR__ . '/db_connect.php';
    // Connecting to db
    $db = new DB_CONNECT();
    $db->connect();

    // Insert new user into users table
    $sqlCommand = "INSERT INTO users (userid, password) VALUES ('$id', '$pw')";
    $result = mysqli_query($db->myconn, $sqlCommand);

    if ($result) {
        echo ("Success");
    } else {
        echo ("Error");
    }
} else {
    echo ("Error");
}
?>