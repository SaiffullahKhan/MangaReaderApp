-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 10, 2024 at 11:57 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mangareader`
--

-- --------------------------------------------------------

--
-- Table structure for table `mangas`
--

CREATE TABLE `mangas` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `cover_image_url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mangas`
--

INSERT INTO `mangas` (`id`, `name`, `cover_image_url`) VALUES
(1, 'One Piece', 'http://192.168.1.18/mangareader/images/OnePiece.jpg'),
(2, 'My Hero Academia', 'http://192.168.1.18/mangareader/images/MHA.jpg'),
(3, 'Bleach', 'http://192.168.1.18/mangareader/images/Bleach.jpg'),
(4, 'test', 'http://192.168.1.18/mangareader/images/test.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `manga_images`
--

CREATE TABLE `manga_images` (
  `id` int(11) NOT NULL,
  `manga_id` int(11) NOT NULL,
  `image_url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `manga_images`
--

INSERT INTO `manga_images` (`id`, `manga_id`, `image_url`) VALUES
(1, 1, 'http://192.168.1.18/mangareader/images/OP_P1.jpg'),
(2, 1, 'http://192.168.1.18/mangareader/images/OP_P2.jpg'),
(3, 2, 'http://192.168.1.18/mangareader/images/MHA_P1.jpg'),
(4, 2, 'http://192.168.1.18/mangareader/images/MHA_P2.jpg'),
(5, 1, 'http://192.168.1.18/mangareader/images/OP_P3.jpg'),
(6, 1, 'http://192.168.1.18/mangareader/images/OP_P4.jpg'),
(7, 2, 'http://192.168.1.18/mangareader/images/MHA_P3.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userid` varchar(20) NOT NULL,
  `password` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userid`, `password`) VALUES
('aa', 'aa'),
('nice', 'nice'),
('ok', '1'),
('test', '1234'),
('test123', '123'),
('testing', 'aaa');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mangas`
--
ALTER TABLE `mangas`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `manga_images`
--
ALTER TABLE `manga_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `manga_id` (`manga_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `mangas`
--
ALTER TABLE `mangas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `manga_images`
--
ALTER TABLE `manga_images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `manga_images`
--
ALTER TABLE `manga_images`
  ADD CONSTRAINT `manga_images_ibfk_1` FOREIGN KEY (`manga_id`) REFERENCES `mangas` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
