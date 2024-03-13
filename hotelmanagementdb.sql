-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 11, 2024 at 11:43 AM
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
-- Database: `hotelmanagementdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin1111');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `Id` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Sex` varchar(6) NOT NULL,
  `Tel` varchar(50) NOT NULL,
  `Gmail` varchar(100) NOT NULL,
  `Book-date` date NOT NULL,
  `In-date` date NOT NULL,
  `Out-date` date NOT NULL,
  `Room-type` varchar(100) NOT NULL,
  `Room` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`Id`, `Name`, `Sex`, `Tel`, `Gmail`, `Book-date`, `In-date`, `Out-date`, `Room-type`, `Room`) VALUES
(1, 'domo', 'Male', '123', '123', '2024-02-02', '2024-02-03', '2024-02-09', 'Single Room', 133),
(2, 'test', 'Female', '123', '123', '2024-01-31', '2024-02-01', '2024-02-02', 'Single Room', 123),
(3, 'test', 'Male', '123', '123', '2024-02-02', '2024-02-03', '2024-02-16', 'Single Room', 133),
(4, 'test', 'Female', '1233', '1331', '2024-01-31', '2024-02-02', '2024-02-03', 'Single Room', 213),
(5, 'test2', 'Male', '1334', '14141', '2024-01-31', '2024-02-03', '2024-02-09', 'Single Room', 13),
(6, 'test3', 'Female', '1234', 'oudompov@gmail.test', '2024-02-01', '2024-02-02', '2024-02-03', 'Suite', 4123),
(7, 'test', 'Female', '13414414', '1421', '2024-01-30', '2024-02-02', '2024-02-03', 'Single Room', 1233),
(8, 'test4', 'Female', '011111111', 'test@gmail.com', '2024-02-01', '2024-02-03', '2024-02-09', 'Double Room', 122),
(9, 'test', 'Male', '11', 'fff', '2024-01-30', '2024-02-01', '2024-02-09', 'Single Room', 11),
(10, '11', 'Female', '111', '111', '2024-02-03', '2024-02-16', '2024-02-10', 'Single Room', 11),
(11, 'test', 'Male', '12', '314', '2024-02-23', '2024-02-29', '2024-03-09', 'Single Room', 122),
(12, 'test111', 'Female', '134', 'fafaf', '2024-02-01', '2024-02-02', '2024-02-03', 'Single Room', 111),
(13, 'test', 'Male', '123', 'dom@test', '2024-02-02', '2024-02-03', '2024-02-08', 'Single Room', 123),
(14, 'test', 'Female', '123', 'testt', '2024-02-03', '2024-02-14', '2024-03-09', 'Suite', 11),
(15, 'test', 'Male', '111', '141', '2024-02-01', '2024-02-20', '2024-02-03', 'Suite', 11),
(16, 'test', 'Male', '111', '141', '2024-02-01', '2024-02-20', '2024-02-03', 'Suite', 11),
(17, 'test101', 'Female', '0122222222', 'test101@gmail.com', '2024-01-31', '2024-02-02', '2024-02-03', 'Suite', 101),
(19, 'test1111', 'Female', 'afffa', 'afffdfd', '2024-02-08', '2024-02-15', '2024-03-01', 'Single Room', 444);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
