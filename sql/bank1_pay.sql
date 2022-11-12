-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 101.34.251.234    Database: bank1_pay
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
-- Table structure for table `account_pay`
--

DROP TABLE IF EXISTS `account_pay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_pay` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL,
  `account_no` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `pay_amount` double DEFAULT NULL COMMENT '充值余额',
  `result` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '充值结果:success，fail',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_pay`
--

LOCK TABLES `account_pay` WRITE;
/*!40000 ALTER TABLE `account_pay` DISABLE KEYS */;
INSERT INTO `account_pay` VALUES ('2dc54089-3eef-4073-a78f-03f1db0f793e','2',5,'success'),('77732fb4-d300-432d-9df0-3d3e32f6fe3b','2',5,'success'),('7857b45f-ac62-4860-95e2-39eba9205217','2',5,'success'),('b6a9006a-4574-4bf2-b7b4-872c10c9b756','2',2,'success'),('d795f3a3-d32f-4790-a549-88a26ce3632e','2',4,'success'),('e9a1150e-40eb-43ca-939b-ca414720a276','2',5,'success');
/*!40000 ALTER TABLE `account_pay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'bank1_pay'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-13  3:22:06
