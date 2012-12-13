delimiter $$

CREATE DATABASE `study_space_parameters` /*!40100 DEFAULT CHARACTER SET utf8 */$$

CREATE TABLE `parameters` (
  `study_id` int(11) NOT NULL AUTO_INCREMENT,
  `studySpaceName` varchar(45) NOT NULL,
  `server_url` varchar(45) NOT NULL,
  `serverApp` varchar(45) NOT NULL,
  `sharedFiles_linkName` varchar(45) NOT NULL,
  `dirName` varchar(45) NOT NULL,
  `dbuser` varchar(45) NOT NULL,
  `dbpass` varchar(45) NOT NULL,
  `dbname` varchar(45) NOT NULL,
  `proj_title` varchar(45) NOT NULL,
  `db_crypt_key` varchar(45) NOT NULL,
  PRIMARY KEY (`study_id`,`studySpaceName`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8$$

