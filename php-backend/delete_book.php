<?php
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$conn = $db->connect();

$manga_id = isset($_POST['manga_id']) ? intval($_POST['manga_id']) : 0; // Ensure 'manga_id' is used

if ($manga_id > 0) {
    $conn->begin_transaction();

    try {
        // Delete images associated with the manga
        $sql = "DELETE FROM manga_images WHERE manga_id = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $manga_id);
        if (!$stmt->execute()) {
            throw new Exception("Error deleting images: " . $stmt->error);
        }

        // Delete the manga
        $sql = "DELETE FROM mangas WHERE id = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $manga_id);
        if (!$stmt->execute()) {
            throw new Exception("Error deleting manga: " . $stmt->error);
        }

        // Commit transaction
        $conn->commit();
        echo "Record deleted successfully";
    } catch (Exception $e) {
        // Rollback transaction
        $conn->rollback();
        echo "Error: " . $e->getMessage();
    }

    $stmt->close();
} else {
    echo "Invalid manga ID";
}

$conn->close();
?>
