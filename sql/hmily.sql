-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 101.34.251.234    Database: hmily
-- ------------------------------------------------------
-- Server version	5.7.39-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hmily_tcc_demo_bank1`
--

DROP TABLE IF EXISTS `hmily_tcc_demo_bank1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hmily_tcc_demo_bank1` (
  `trans_id` varchar(64) NOT NULL,
  `target_class` varchar(256) DEFAULT NULL,
  `target_method` varchar(128) DEFAULT NULL,
  `confirm_method` varchar(128) DEFAULT NULL,
  `cancel_method` varchar(128) DEFAULT NULL,
  `retried_count` tinyint(4) NOT NULL,
  `create_time` datetime NOT NULL,
  `last_time` datetime NOT NULL,
  `version` tinyint(4) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `invocation` longblob,
  `role` tinyint(4) NOT NULL,
  `pattern` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`trans_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hmily_tcc_demo_bank1`
--

LOCK TABLES `hmily_tcc_demo_bank1` WRITE;
/*!40000 ALTER TABLE `hmily_tcc_demo_bank1` DISABLE KEYS */;
/*!40000 ALTER TABLE `hmily_tcc_demo_bank1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hmily_tcc_demo_bank2`
--

DROP TABLE IF EXISTS `hmily_tcc_demo_bank2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hmily_tcc_demo_bank2` (
  `trans_id` varchar(64) NOT NULL,
  `target_class` varchar(256) DEFAULT NULL,
  `target_method` varchar(128) DEFAULT NULL,
  `confirm_method` varchar(128) DEFAULT NULL,
  `cancel_method` varchar(128) DEFAULT NULL,
  `retried_count` tinyint(4) NOT NULL,
  `create_time` datetime NOT NULL,
  `last_time` datetime NOT NULL,
  `version` tinyint(4) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `invocation` longblob,
  `role` tinyint(4) NOT NULL,
  `pattern` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`trans_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hmily_tcc_demo_bank2`
--

LOCK TABLES `hmily_tcc_demo_bank2` WRITE;
/*!40000 ALTER TABLE `hmily_tcc_demo_bank2` DISABLE KEYS */;
/*!40000 ALTER TABLE `hmily_tcc_demo_bank2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'hmily'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-13  3:22:11
