<?php
// Include the database connection class
require_once __DIR__ . '/db_connect.php';

// Create an instance of the DB_CONNECT class
$db = new DB_CONNECT();
$conn = $db->connect(); // Get the database connection

// Fetch manga details
$sql = "SELECT id, name, cover_image_url FROM mangas";
$result = $conn->query($sql);

$books = [];
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $books[] = $row;
    }
}

echo json_encode($books);

$db->close($conn); // Close the database connection
?>
