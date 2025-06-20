<?php

// Check if POST data is set for both 'id' and 'pw'
if (isset($_POST["id"]) && isset($_POST["pw"])) {
    // Retrieve the POST data
    $id = $_POST['id'];
    $pw = $_POST['pw'];

    // Include the database connection class
    require_once __DIR__ . '/db_connect.php';

    // Create a new instance of the DB_CONNECT class and connect to the database
    $db = new DB_CONNECT();
    $db->connect();

    // Prepare an SQL statement with placeholders for the userid and password
    $stmt = $db->myconn->prepare("SELECT * FROM users WHERE userid = ? AND password = ?");

    // Bind the user-provided 'id' and 'pw' to the prepared statement
    // The 'ss' indicates that both parameters are strings
    $stmt->bind_param("ss", $id, $pw);

    // Execute the prepared statement
    $stmt->execute();

    // Get the result set from the executed statement
    $result = $stmt->get_result();

    // Check if any rows were returned (i.e., if the userid and password matched a user)
    if ($result->num_rows > 0) {
        // Output success message if a matching user is found
        echo ("Success");
    } else {
        // Output failure message if no matching user is found
        echo ("failed");
    }

    // Close the prepared statement to free up resources
    $stmt->close();
}
?>
