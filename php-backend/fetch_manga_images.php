<?php
// Include the database connection class
require_once __DIR__ . '/db_connect.php';

// Create an instance of the DB_CONNECT class
$db = new DB_CONNECT();
$conn = $db->connect(); // Get the database connection

$manga_id = isset($_GET['manga_id']) ? intval($_GET['manga_id']) : 0; // Manga ID passed as a query parameter

$mangaId = $_GET['mangaId'];
$sql = "SELECT image_url FROM manga_images WHERE manga_id = $mangaId";
$result = $conn->query($sql);

$images = array();
while ($row = $result->fetch_assoc()) {
    $images[] = $row;
}

echo json_encode($images);

$conn->close();
?>