-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 22, 2024 at 05:01 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ql_do_an_thuc_tap`
--

-- --------------------------------------------------------

--
-- Table structure for table `giangvien`
--

CREATE TABLE `giangvien` (
  `ID` int(11) NOT NULL,
  `NameGV` varchar(50) DEFAULT NULL,
  `Level` varchar(50) DEFAULT NULL,
  `Position` varchar(50) DEFAULT NULL,
  `SDT` int(11) DEFAULT NULL,
  `ID_Project` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `giangvien`
--

INSERT INTO `giangvien` (`ID`, `NameGV`, `Level`, `Position`, `SDT`, `ID_Project`) VALUES
(1, 'Trần Bình', 'Tiến sĩ', 'giảng viên', 1234, 4);

-- --------------------------------------------------------

--
-- Table structure for table `noithuctap`
--

CREATE TABLE `noithuctap` (
  `ID` int(11) NOT NULL,
  `ID_Project` int(11) DEFAULT NULL,
  `InternshipAddress` varchar(50) DEFAULT NULL,
  `TimeLine` varchar(50) DEFAULT NULL,
  `Instructor` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `noithuctap`
--

INSERT INTO `noithuctap` (`ID`, `ID_Project`, `InternshipAddress`, `TimeLine`, `Instructor`) VALUES
(1, 4, 'Cầu giấy', '1 tháng', 'Hồng thất công');

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE `project` (
  `ID` int(11) NOT NULL,
  `NameProject` varchar(50) DEFAULT NULL,
  `Deadline` datetime DEFAULT NULL,
  `Instructor` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`ID`, `NameProject`, `Deadline`, `Instructor`) VALUES
(1, 'Quản lý khách sạn', '2000-01-10 00:00:00', 'Ngô Tuấn'),
(4, 'Web đặt chuyến du lịch', '2000-01-21 00:00:00', 'Ngô Tuấn'),
(5, 'Web bán điện thoại', '2000-01-29 00:00:00', 'Trần Đại Nghĩa'),
(6, 'Web tài xỉu', '2000-01-02 00:00:00', 'Long');

-- --------------------------------------------------------

--
-- Table structure for table `sinhvien`
--

CREATE TABLE `sinhvien` (
  `ID` int(11) NOT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `DateOfBirth` datetime DEFAULT NULL,
  `Sex` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Address` varchar(50) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Class` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ID_Project` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sinhvien`
--

INSERT INTO `sinhvien` (`ID`, `Name`, `DateOfBirth`, `Sex`, `Address`, `Email`, `Class`, `ID_Project`) VALUES
(1, 'Trần Văn Quân update', '2024-01-10 00:00:00', 'nam', 'HN', 'quan@gmail.com', 'IT01', 4),
(3, 'Nguyễn Tiến Long', '2024-01-02 00:00:00', 'nam', 'HN', 'quan@gmail.com', 'IT01', 1),
(4, 'Đinh Văn Trường', '2024-01-01 00:00:00', 'nam', 'HN', 'truong@gmail.com', 'IT01', 4);

-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
--

CREATE TABLE `taikhoan` (
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `taikhoan`
--

INSERT INTO `taikhoan` (`username`, `password`) VALUES
('admin', '123');

-- --------------------------------------------------------

--
-- Table structure for table `thongke`
--

CREATE TABLE `thongke` (
  `ID` int(11) NOT NULL,
  `ID_Project` int(11) DEFAULT NULL,
  `Instructor` varchar(50) DEFAULT NULL,
  `PointGV` float DEFAULT NULL,
  `PointHD` float DEFAULT NULL,
  `PointHDC` float DEFAULT NULL,
  `TongDiem` float DEFAULT NULL,
  `PointTB` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `thongke`
--

INSERT INTO `thongke` (`ID`, `ID_Project`, `Instructor`, `PointGV`, `PointHD`, `PointHDC`, `TongDiem`, `PointTB`) VALUES
(1, 4, 'Minh Long', 8, 9, 8, 10, 9);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `giangvien`
--
ALTER TABLE `giangvien`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_Project` (`ID_Project`);

--
-- Indexes for table `noithuctap`
--
ALTER TABLE `noithuctap`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_Project` (`ID_Project`);

--
-- Indexes for table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `sinhvien`
--
ALTER TABLE `sinhvien`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_Project` (`ID_Project`);

--
-- Indexes for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `thongke`
--
ALTER TABLE `thongke`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_Project` (`ID_Project`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `giangvien`
--
ALTER TABLE `giangvien`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `noithuctap`
--
ALTER TABLE `noithuctap`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `project`
--
ALTER TABLE `project`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `sinhvien`
--
ALTER TABLE `sinhvien`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `thongke`
--
ALTER TABLE `thongke`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `giangvien`
--
ALTER TABLE `giangvien`
  ADD CONSTRAINT `giangvien_ibfk_1` FOREIGN KEY (`ID_Project`) REFERENCES `project` (`ID`);

--
-- Constraints for table `noithuctap`
--
ALTER TABLE `noithuctap`
  ADD CONSTRAINT `noithuctap_ibfk_1` FOREIGN KEY (`ID_Project`) REFERENCES `project` (`ID`);

--
-- Constraints for table `sinhvien`
--
ALTER TABLE `sinhvien`
  ADD CONSTRAINT `sinhvien_ibfk_1` FOREIGN KEY (`ID_Project`) REFERENCES `project` (`ID`);

--
-- Constraints for table `thongke`
--
ALTER TABLE `thongke`
  ADD CONSTRAINT `thongke_ibfk_1` FOREIGN KEY (`ID_Project`) REFERENCES `project` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
