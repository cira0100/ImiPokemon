-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 22, 2022 at 12:08 AM
-- Server version: 5.7.31
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pokemon`
--

-- --------------------------------------------------------

--
-- Table structure for table `ability`
--

DROP TABLE IF EXISTS `ability`;
CREATE TABLE IF NOT EXISTS `ability` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `monsterId` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  `power` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
CREATE TABLE IF NOT EXISTS `history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player` int(11) NOT NULL,
  `pokemon` int(11) NOT NULL,
  `time` date NOT NULL,
  `result` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `monster`
--

DROP TABLE IF EXISTS `monster`;
CREATE TABLE IF NOT EXISTS `monster` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `hp` int(11) NOT NULL,
  `base64Image` varchar(10000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `monster`
--

INSERT INTO `monster` (`id`, `name`, `description`, `hp`, `base64Image`) VALUES
(1, 'zika', 'zika', 5000, 'slika'),
(2, 'zika', 'zika', 5000, 'slika'),
(3, 'zika', 'zika', 5000, 'slika');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `pokemonId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `admin`, `pokemonId`) VALUES
(3, 'cirakg', '$2a$10$tElFyUJsnb9xWuV3PRWOW.HPZ8qwLebQEpfu61eqozUcxC3ydEv7.', 1, NULL),
(2, 'cirakg1', '$2a$10$TJHJfYbgZO97r0CJi2eKpeEhOW8wKEO5BGXQBpR33js3R5rM98Y3W', 0, NULL),
(4, 'cirakg2', '$2a$10$LQsWtrn7OqrNU8wSSfg/6uo47lzQqw27T3ubUDRqFQVZhI6F0uBli', 0, NULL),
(5, 'cirakg5', '$2a$10$cO0oAPi5O4hoVQWTHuw0fOXXBYLqwJ2V5qrHy32ZjpmR9fTpNBGHy', 1, NULL),
(6, 'cirakg10', '$2a$10$IiK5l7wFvi/KPliiCpNMCOAuehXvJFofN6ARotiSN.5FuRpjke32G', 0, NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
