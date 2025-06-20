<?php
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$conn = $db->connect();

// Retrieve the manga_id and new_name from the POST request
$manga_id = isset($_POST['manga_id']) ? intval($_POST['manga_id']) : 0;
$new_name = isset($_POST['new_name']) ? $conn->real_escape_string($_POST['new_name']) : '';

if ($manga_id > 0 && !empty($new_name)) {
    // Update the manga name in the database
    $sql = "UPDATE mangas SET name = '$new_name' WHERE id = $manga_id";
    if ($conn->query($sql) === TRUE) {
        echo "Book updated successfully";
    } else {
        echo "Error updating book: " . $conn->error;
    }
} else {
    echo "Invalid manga ID or new name";
}

$conn->close();
?>
