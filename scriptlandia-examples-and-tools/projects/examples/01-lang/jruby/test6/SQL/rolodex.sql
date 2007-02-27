-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.19-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema rolodex_development
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ rolodex_development;
USE rolodex_development;

--
-- Table structure for table `rolodex_development`.`addresses`
--

DROP TABLE IF EXISTS `addresses`;
CREATE TABLE `addresses` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `street` varchar(45) default NULL,
  `street2` varchar(45) default NULL,
  `city` varchar(45) NOT NULL default '',
  `state` char(2) NOT NULL default '',
  `zip` varchar(5) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rolodex_development`.`addresses`
--

/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` (`id`,`street`,`street2`,`city`,`state`,`zip`) VALUES 
 (5,'1234 Elm St.','Apt 5','Minneapolis','MN','55455'),
 (6,'1600 Pennsylvania Avenue','','Washington','DC','12345');
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;


--
-- Table structure for table `rolodex_development`.`contact_types`
--

DROP TABLE IF EXISTS `contact_types`;
CREATE TABLE `contact_types` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rolodex_development`.`contact_types`
--

/*!40000 ALTER TABLE `contact_types` DISABLE KEYS */;
INSERT INTO `contact_types` (`id`,`name`) VALUES 
 (1,'Friends'),
 (2,'Business'),
 (3,'Family'),
 (4,'Coworkers');
/*!40000 ALTER TABLE `contact_types` ENABLE KEYS */;


--
-- Table structure for table `rolodex_development`.`contacts`
--

DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `firstname` varchar(45) default NULL,
  `lastname` varchar(45) NOT NULL default 'Unknown',
  `nickname` varchar(45) default NULL,
  `contact_type_id` int(10) unsigned NOT NULL default '0',
  `address_id` int(10) unsigned default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rolodex_development`.`contacts`
--

/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` (`id`,`firstname`,`lastname`,`nickname`,`contact_type_id`,`address_id`) VALUES 
 (5,'Jim','White','Jimbo',3,5),
 (6,'George','Bush','W',2,6);
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
