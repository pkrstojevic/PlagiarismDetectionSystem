PlagiarismDetectionSystem
=========================

Diplomski rad.


-- Host: localhost:3306
-- Generation Time: Aug 25, 2014 at 12:35 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `db_pds`
--

-- --------------------------------------------------------

--
-- Table structure for table `academic_expertise`
--

CREATE TABLE IF NOT EXISTS `academic_expertise` (
  `academic_expertise_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) COLLATE utf8_bin NOT NULL,
  `description` varchar(500) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`academic_expertise_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=4 ;

--
-- Dumping data for table `academic_expertise`
--

INSERT INTO `academic_expertise` (`academic_expertise_id`, `name`, `description`) VALUES
(1, 'Informacioni sistemi i tehnologije', ''),
(2, 'Ekonomija', ''),
(3, 'Pravo', '');

-- --------------------------------------------------------

--
-- Table structure for table `documents`
--

CREATE TABLE IF NOT EXISTS `documents` (
  `document_id` int(11) NOT NULL AUTO_INCREMENT,
  `document_title` text COLLATE utf8_bin,
  `file_name` varchar(200) COLLATE utf8_bin NOT NULL,
  `file_size` varchar(50) COLLATE utf8_bin NOT NULL,
  `author_firstname` varchar(100) COLLATE utf8_bin NOT NULL,
  `author_lastname` varchar(100) COLLATE utf8_bin NOT NULL,
  `copyright_year` int(11) NOT NULL,
  `uploaded` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `keywords` text CHARACTER SET utf8 NOT NULL,
  `academic_expertise_id` int(11) NOT NULL,
  PRIMARY KEY (`document_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=65 ;

--
-- Dumping data for table `documents`
--

INSERT INTO `documents` (`document_id`, `document_title`, `file_name`, `file_size`, `author_firstname`, `author_lastname`, `copyright_year`, `uploaded`, `keywords`, `academic_expertise_id`) VALUES
(64, 'Softver za prepoznavanje plagijata u seminarskim radovima', '08252014123317.txt', '52843', 'Marko', 'Markovi?', 2010, '2014-08-24 22:00:00', 'php	ukoliko	primer	podataka	veoma	aplikacije	samo	odnosno	web	id	username	', 1),
(63, 'Diplomski rad - Softver za prepoznavanje plagijata u akademskim radovima', '08252014123144.txt', '52843', 'Predrag', 'Krstojevi?', 2014, '2014-08-24 22:00:00', 'php	ukoliko	primer	podataka	veoma	aplikacije	samo	odnosno	web	id	username	', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
