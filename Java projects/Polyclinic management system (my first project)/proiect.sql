CREATE DATABASE  IF NOT EXISTS `lant_de_policlinici` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `lant_de_policlinici`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: lant_de_policlinici
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asistent_medical`
--

DROP TABLE IF EXISTS `asistent_medical`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `asistent_medical` (
  `CNP_asistent` varchar(45) NOT NULL,
  `tip` varchar(45) DEFAULT NULL,
  `grad` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CNP_asistent`),
  UNIQUE KEY `CNP_UNIQUE` (`CNP_asistent`),
  CONSTRAINT `fk1_asistent_medical` FOREIGN KEY (`CNP_asistent`) REFERENCES `utilizator` (`cnp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asistent_medical`
--

LOCK TABLES `asistent_medical` WRITE;
/*!40000 ALTER TABLE `asistent_medical` DISABLE KEYS */;
INSERT INTO `asistent_medical` VALUES ('0000000000005','Laborator','Principal');
/*!40000 ALTER TABLE `asistent_medical` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_fiscal`
--

DROP TABLE IF EXISTS `bon_fiscal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bon_fiscal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cnp_medic` varchar(45) DEFAULT NULL,
  `total` varchar(45) DEFAULT NULL,
  `specialitate` varchar(45) DEFAULT NULL,
  `unitate_medicala` varchar(45) DEFAULT NULL,
  `data` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_fiscal`
--

LOCK TABLES `bon_fiscal` WRITE;
/*!40000 ALTER TABLE `bon_fiscal` DISABLE KEYS */;
INSERT INTO `bon_fiscal` VALUES (1,'0000000000007','495.0','osteodensitometrie','2','2019-01-31'),(2,'0000000000007','7415.0','stomatologie','2','2018-12-18'),(3,'0000000000007','760.0','urologie','2','2018-12-18');
/*!40000 ALTER TABLE `bon_fiscal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competente`
--

DROP TABLE IF EXISTS `competente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `competente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nume_competenta` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competente`
--

LOCK TABLES `competente` WRITE;
/*!40000 ALTER TABLE `competente` DISABLE KEYS */;
INSERT INTO `competente` VALUES (1,'ecografie'),(2,'endoscopie'),(3,'digestivă'),(4,'ecocardiografie'),(5,'cardiologie intervențională'),(6,'bronhoscopie'),(7,'EEG'),(8,'EMG'),(9,'dializă'),(10,'chirurgie laparoscopică'),(11,'chirurgie toracică'),(12,'chirurgie spinală'),(13,'litotriție extracorporeală'),(14,'explorare computer tomograf / imagistică prin rezonanță magnetică');
/*!40000 ALTER TABLE `competente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `concediu_angajat`
--

DROP TABLE IF EXISTS `concediu_angajat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `concediu_angajat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CNP_angajat` varchar(45) DEFAULT NULL,
  `perioada_inceput` date DEFAULT NULL,
  `perioada_sfarsit` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_concediu_angajat_idx` (`CNP_angajat`),
  CONSTRAINT `fk1_concediu_angajat` FOREIGN KEY (`CNP_angajat`) REFERENCES `utilizator` (`cnp`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concediu_angajat`
--

LOCK TABLES `concediu_angajat` WRITE;
/*!40000 ALTER TABLE `concediu_angajat` DISABLE KEYS */;
INSERT INTO `concediu_angajat` VALUES (1,'0000000000007','2019-02-26','2019-02-28'),(2,'0000000000007','2018-12-05','2018-12-10');
/*!40000 ALTER TABLE `concediu_angajat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `investigatie_raport`
--

DROP TABLE IF EXISTS `investigatie_raport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `investigatie_raport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_serviciu_medical` int(11) DEFAULT NULL,
  `id_raport` int(11) DEFAULT NULL,
  `rezultat` varchar(45) DEFAULT NULL,
  `platit` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk2_investigatie_raport_idx` (`id_raport`),
  KEY `fk1_investigatie_raport_idx` (`id_serviciu_medical`),
  CONSTRAINT `fk1_investigatie_raport` FOREIGN KEY (`id_serviciu_medical`) REFERENCES `servicii_medicale` (`id`),
  CONSTRAINT `fk2_investigatie_raport` FOREIGN KEY (`id_raport`) REFERENCES `raport_pacient` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `investigatie_raport`
--

LOCK TABLES `investigatie_raport` WRITE;
/*!40000 ALTER TABLE `investigatie_raport` DISABLE KEYS */;
INSERT INTO `investigatie_raport` VALUES (1,173,1,'aaaaaaaaaaaa','da'),(2,176,1,'aaaaaaaaaaaaaaaaa','da'),(3,177,1,'aaaaaaaaaaa','da'),(4,174,1,'bbbbbbbb','da'),(5,273,2,'','da'),(6,49,2,'','da'),(7,50,2,'','da'),(8,51,2,'','da'),(9,52,2,'','da'),(10,197,2,'','da'),(11,98,3,'','da'),(12,49,3,'','da'),(13,219,3,'','da'),(14,221,3,'','da'),(15,225,3,'','da'),(16,231,3,'','da'),(17,232,3,'','da');
/*!40000 ALTER TABLE `investigatie_raport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `istoric_salarii`
--

DROP TABLE IF EXISTS `istoric_salarii`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `istoric_salarii` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CNP_angajat` varchar(45) DEFAULT NULL,
  `luna` int(45) DEFAULT NULL,
  `an` int(45) DEFAULT NULL,
  `salariu` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk1_istoric_salariu_idx` (`CNP_angajat`),
  CONSTRAINT `fk1_istoric_salariu` FOREIGN KEY (`CNP_angajat`) REFERENCES `utilizator` (`cnp`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `istoric_salarii`
--

LOCK TABLES `istoric_salarii` WRITE;
/*!40000 ALTER TABLE `istoric_salarii` DISABLE KEYS */;
INSERT INTO `istoric_salarii` VALUES (1,'0000000000001',11,2018,5500),(2,'0000000000001',12,2018,5250),(3,'0000000000002',11,2018,2100),(4,'0000000000002',12,2018,3150),(5,'0000000000003',11,2018,1250),(6,'0000000000003',12,2018,2625),(7,'0000000000004',11,2018,23750),(8,'0000000000004',12,2018,26250),(9,'0000000000005',11,2018,3000),(10,'0000000000005',12,2018,3150),(11,'0000000000006',11,2018,2600),(12,'0000000000006',12,2018,4200),(13,'0000000000007',10,2018,300),(14,'0000000000007',11,2018,3300),(15,'0000000000007',12,2018,2550);
/*!40000 ALTER TABLE `istoric_salarii` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medic`
--

DROP TABLE IF EXISTS `medic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `medic` (
  `CNP_medic` varchar(45) NOT NULL,
  `grad` varchar(45) DEFAULT NULL,
  `cod_parafa` varchar(45) DEFAULT NULL,
  `titlu_stiintific` varchar(45) DEFAULT NULL,
  `post_didactic` varchar(45) DEFAULT NULL,
  `comision` float(100,2) DEFAULT NULL,
  PRIMARY KEY (`CNP_medic`),
  UNIQUE KEY `CNP_UNIQUE` (`CNP_medic`),
  CONSTRAINT `fk1_medic` FOREIGN KEY (`CNP_medic`) REFERENCES `utilizator` (`cnp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medic`
--

LOCK TABLES `medic` WRITE;
/*!40000 ALTER TABLE `medic` DISABLE KEYS */;
INSERT INTO `medic` VALUES ('0000000000001','Specialist','1','-','-',15.00),('0000000000006','Primar','2','-','-',12.00),('0000000000007','Specialist','3','-','-',14.00);
/*!40000 ALTER TABLE `medic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medic_competente`
--

DROP TABLE IF EXISTS `medic_competente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `medic_competente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CNP_medic` varchar(45) DEFAULT NULL,
  `id_competente` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk2_medic_competente` (`id_competente`),
  KEY `fk1_medic_competente_idx` (`CNP_medic`),
  CONSTRAINT `fk1_medic_competente` FOREIGN KEY (`CNP_medic`) REFERENCES `medic` (`cnp_medic`),
  CONSTRAINT `fk2_medic_competente` FOREIGN KEY (`id_competente`) REFERENCES `competente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medic_competente`
--

LOCK TABLES `medic_competente` WRITE;
/*!40000 ALTER TABLE `medic_competente` DISABLE KEYS */;
INSERT INTO `medic_competente` VALUES (7,'0000000000006',9),(8,'0000000000006',11),(9,'0000000000006',13),(10,'0000000000007',9),(11,'0000000000007',10),(12,'0000000000007',11),(13,'0000000000007',13),(14,'0000000000007',14),(15,'0000000000001',1);
/*!40000 ALTER TABLE `medic_competente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medic_specialitate`
--

DROP TABLE IF EXISTS `medic_specialitate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `medic_specialitate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CNP_medic` varchar(45) DEFAULT NULL,
  `id_specialitate` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk2_medic_specialitate` (`id_specialitate`),
  KEY `fk1_medic_specialitate_idx` (`CNP_medic`),
  CONSTRAINT `fk1_medic_specialitate` FOREIGN KEY (`CNP_medic`) REFERENCES `medic` (`cnp_medic`),
  CONSTRAINT `fk2_medic_specialitate` FOREIGN KEY (`id_specialitate`) REFERENCES `specialitati` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medic_specialitate`
--

LOCK TABLES `medic_specialitate` WRITE;
/*!40000 ALTER TABLE `medic_specialitate` DISABLE KEYS */;
INSERT INTO `medic_specialitate` VALUES (17,'0000000000006',2),(18,'0000000000006',3),(19,'0000000000006',4),(20,'0000000000006',8),(21,'0000000000006',11),(22,'0000000000006',18),(23,'0000000000007',15),(24,'0000000000007',17),(25,'0000000000007',18),(26,'0000000000007',19),(27,'0000000000007',20),(28,'0000000000001',1),(29,'0000000000001',2);
/*!40000 ALTER TABLE `medic_specialitate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orar_angajat`
--

DROP TABLE IF EXISTS `orar_angajat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orar_angajat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CNP_angajat` varchar(45) DEFAULT NULL,
  `id_unitate_medicala` int(11) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `zi` varchar(45) DEFAULT NULL,
  `perioada_inceput` varchar(45) DEFAULT NULL,
  `perioada_sfarsit` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk2_orar_specific_angajat_idx` (`id_unitate_medicala`),
  KEY `fk1_orar_specific_angajat_idx` (`CNP_angajat`),
  CONSTRAINT `fk1_orar_specific_angajat` FOREIGN KEY (`CNP_angajat`) REFERENCES `utilizator` (`cnp`),
  CONSTRAINT `fk2_orar_specific_angajat` FOREIGN KEY (`id_unitate_medicala`) REFERENCES `unitati_medicale` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orar_angajat`
--

LOCK TABLES `orar_angajat` WRITE;
/*!40000 ALTER TABLE `orar_angajat` DISABLE KEYS */;
INSERT INTO `orar_angajat` VALUES (1,'1111111111111',NULL,NULL,'Luni',NULL,NULL),(2,'1111111111111',NULL,NULL,'Marti',NULL,NULL),(3,'1111111111111',NULL,NULL,'Miercuri',NULL,NULL),(4,'1111111111111',NULL,NULL,'Joi',NULL,NULL),(5,'1111111111111',NULL,NULL,'Vineri',NULL,NULL),(6,'1111111111111',NULL,NULL,'Sambata',NULL,NULL),(7,'1111111111111',NULL,NULL,'Duminica',NULL,NULL),(8,'0000000000001',1,NULL,'Luni','08:00:00','15:00:00'),(9,'0000000000001',1,NULL,'Marti','08:00:00','15:00:00'),(10,'0000000000001',1,NULL,'Miercuri','08:00:00','15:00:00'),(11,'0000000000001',1,NULL,'Joi','08:00:00','15:00:00'),(12,'0000000000001',1,NULL,'Vineri','08:00:00','15:00:00'),(13,'0000000000001',0,NULL,'Sambata','-','-'),(14,'0000000000001',0,NULL,'Duminica','-','-'),(15,'0000000000002',1,NULL,'Luni','08:00:00','16:00:00'),(16,'0000000000002',1,NULL,'Marti','08:00:00','16:00:00'),(17,'0000000000002',1,NULL,'Miercuri','08:00:00','16:00:00'),(18,'0000000000002',1,NULL,'Joi','08:00:00','16:00:00'),(19,'0000000000002',1,NULL,'Vineri','08:00:00','16:00:00'),(20,'0000000000002',0,NULL,'Sambata','-','-'),(21,'0000000000002',0,NULL,'Duminica','-','-'),(22,'0000000000003',2,NULL,'Luni','09:00:00','16:00:00'),(23,'0000000000003',2,NULL,'Marti','09:00:00','16:00:00'),(24,'0000000000003',2,NULL,'Miercuri','09:00:00','16:00:00'),(25,'0000000000003',2,NULL,'Joi','09:00:00','16:00:00'),(26,'0000000000003',2,NULL,'Vineri','09:00:00','16:00:00'),(27,'0000000000003',0,NULL,'Sambata','-','-'),(28,'0000000000003',0,NULL,'Duminica','-','-'),(29,'0000000000004',2,NULL,'Luni','08:00:00','16:00:00'),(30,'0000000000004',2,NULL,'Marti','08:00:00','16:00:00'),(31,'0000000000004',2,NULL,'Miercuri','08:00:00','16:00:00'),(32,'0000000000004',2,NULL,'Joi','08:00:00','16:00:00'),(33,'0000000000004',2,NULL,'Vineri','08:00:00','16:00:00'),(34,'0000000000004',0,NULL,'Sambata','-','-'),(35,'0000000000004',0,NULL,'Duminica','-','-'),(36,'0000000000005',1,NULL,'Luni','08:00:00','14:00:00'),(37,'0000000000005',1,NULL,'Marti','08:00:00','14:00:00'),(38,'0000000000005',1,NULL,'Miercuri','08:00:00','14:00:00'),(39,'0000000000005',1,NULL,'Joi','08:00:00','14:00:00'),(40,'0000000000005',1,NULL,'Vineri','08:00:00','14:00:00'),(41,'0000000000005',0,NULL,'Sambata','-','-'),(42,'0000000000005',0,NULL,'Duminica','-','-'),(43,'0000000000006',0,NULL,'Luni','-','-'),(44,'0000000000006',1,NULL,'Marti','08:00:00','16:00:00'),(45,'0000000000006',1,NULL,'Miercuri','08:00:00','16:00:00'),(46,'0000000000006',1,NULL,'Joi','08:00:00','16:00:00'),(47,'0000000000006',1,NULL,'Vineri','08:00:00','16:00:00'),(48,'0000000000006',1,NULL,'Sambata','08:00:00','13:00:00'),(49,'0000000000006',0,NULL,'Duminica','-','-'),(50,'0000000000007',2,NULL,'Luni','08:00:00','15:00:00'),(51,'0000000000007',2,NULL,'Marti','08:00:00','15:00:00'),(52,'0000000000007',2,NULL,'Miercuri','08:00:00','15:00:00'),(53,'0000000000007',2,NULL,'Joi','08:00:00','15:00:00'),(54,'0000000000007',2,NULL,'Vineri','08:00:00','15:00:00'),(55,'0000000000007',0,NULL,'Sambata','-','-'),(56,'0000000000007',0,NULL,'Duminica','-','-'),(57,'0000000000002',1,'2019-01-15',NULL,'08:00:00','12:00:00'),(60,'0000000000006',1,'2019-01-15',NULL,'08:00:00','12:00:00'),(61,'0000000000006',1,'2019-01-23',NULL,'08:00:00','15:00:00'),(62,'0000000000006',1,'2019-02-11',NULL,'08:00:00','15:00:00');
/*!40000 ALTER TABLE `orar_angajat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orar_policlinica`
--

DROP TABLE IF EXISTS `orar_policlinica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orar_policlinica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_unitate_medicala` int(11) DEFAULT NULL,
  `zi` varchar(45) DEFAULT NULL,
  `ora_deschidere` time DEFAULT NULL,
  `ora_inchidere` time DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_orar_policlinica_idx` (`id_unitate_medicala`),
  CONSTRAINT `fk1_orar_policlinica` FOREIGN KEY (`id_unitate_medicala`) REFERENCES `unitati_medicale` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orar_policlinica`
--

LOCK TABLES `orar_policlinica` WRITE;
/*!40000 ALTER TABLE `orar_policlinica` DISABLE KEYS */;
INSERT INTO `orar_policlinica` VALUES (1,1,'luni','08:00:00','16:00:00'),(2,2,'luni','08:00:00','16:00:00'),(3,3,'luni','08:00:00','16:00:00'),(4,4,'luni','08:00:00','16:00:00'),(5,1,'marti','08:00:00','16:00:00'),(6,2,'marti','08:00:00','16:00:00'),(7,3,'marti','08:00:00','16:00:00'),(8,4,'marti','08:00:00','16:00:00'),(9,1,'miercuri','08:00:00','16:00:00'),(10,2,'miercuri','08:00:00','16:00:00'),(11,3,'miercuri','08:00:00','16:00:00'),(12,4,'miercuri','08:00:00','16:00:00'),(13,1,'joi','08:00:00','16:00:00'),(14,2,'joi','08:00:00','16:00:00'),(15,3,'joi','08:00:00','16:00:00'),(16,4,'joi','08:00:00','16:00:00'),(17,1,'vineri','08:00:00','16:00:00'),(18,2,'vineri','08:00:00','16:00:00'),(19,3,'vineri','08:00:00','16:00:00'),(20,4,'vineri','08:00:00','16:00:00'),(21,1,'sambata','08:00:00','13:00:00'),(22,2,'sambata','08:00:00','14:00:00'),(23,3,'sambata','08:00:00','13:00:00'),(24,4,'sambata','08:00:00','15:00:00'),(25,1,'duminica',NULL,NULL),(26,2,'duminica',NULL,NULL),(27,3,'duminica',NULL,NULL),(28,4,'duminica',NULL,NULL);
/*!40000 ALTER TABLE `orar_policlinica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pacient`
--

DROP TABLE IF EXISTS `pacient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `pacient` (
  `CNP` varchar(13) NOT NULL,
  `nume` varchar(45) DEFAULT NULL,
  `prenume` varchar(45) DEFAULT NULL,
  `adresa` varchar(45) DEFAULT NULL,
  `telefon` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CNP`),
  UNIQUE KEY `CNP_UNIQUE` (`CNP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pacient`
--

LOCK TABLES `pacient` WRITE;
/*!40000 ALTER TABLE `pacient` DISABLE KEYS */;
INSERT INTO `pacient` VALUES ('1000000000000','aaa','aaa','Str. strada','0746853912'),('1000000000001','bbbb','bbbb','bbb','074566985369');
/*!40000 ALTER TABLE `pacient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programare`
--

DROP TABLE IF EXISTS `programare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `programare` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nume_pacient` varchar(45) DEFAULT NULL,
  `prenume_pacient` varchar(45) DEFAULT NULL,
  `perioada_inceput` time DEFAULT NULL,
  `perioada_final` time DEFAULT NULL,
  `data_calendaristica` date DEFAULT NULL,
  `CNP_medic` varchar(45) DEFAULT NULL,
  `id_serviciu` int(11) DEFAULT NULL,
  `id_unitate` int(11) DEFAULT NULL,
  `prezentare` varchar(11) DEFAULT NULL,
  `CNP_pacient` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_programare_idx` (`id_serviciu`),
  KEY `fk2_programare_idx` (`id_unitate`),
  KEY `fk3_programare_idx` (`CNP_medic`),
  CONSTRAINT `fk1_programare` FOREIGN KEY (`id_serviciu`) REFERENCES `servicii_medicale` (`id`),
  CONSTRAINT `fk2_programare` FOREIGN KEY (`id_unitate`) REFERENCES `unitati_medicale` (`id`),
  CONSTRAINT `fk3_programare` FOREIGN KEY (`CNP_medic`) REFERENCES `medic` (`cnp_medic`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programare`
--

LOCK TABLES `programare` WRITE;
/*!40000 ALTER TABLE `programare` DISABLE KEYS */;
INSERT INTO `programare` VALUES (1,'Mure','Dan','08:15:00','08:45:00','2019-02-05','0000000000006',29,1,'nu',NULL),(2,'Aaaaa','Aaaa','08:00:00','08:30:00','2019-02-05','0000000000007',263,2,'nu',NULL),(3,'aaa','aaa','10:00:00','10:30:00','2019-01-31','0000000000007',257,2,'da','1000000000000'),(4,'aaa','bbb','11:35:00','12:05:00','2019-02-01','0000000000007',203,2,'nu',NULL);
/*!40000 ALTER TABLE `programare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `raport_pacient`
--

DROP TABLE IF EXISTS `raport_pacient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `raport_pacient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CNP_pacient` varchar(45) DEFAULT NULL,
  `nume_medic` varchar(45) DEFAULT NULL,
  `prenume_medic` varchar(45) DEFAULT NULL,
  `CNP_medic` varchar(45) DEFAULT NULL,
  `nume_medic_recomandare` varchar(45) DEFAULT NULL,
  `prenume_medic_recomandare` varchar(45) DEFAULT NULL,
  `nume_asistent_medical` varchar(45) DEFAULT NULL,
  `prenume_asistent_medical` varchar(45) DEFAULT NULL,
  `data_consultatiei` varchar(45) DEFAULT NULL,
  `simptome` text,
  `diagnostic` text,
  `recomandari` text,
  `parafa` varchar(45) DEFAULT NULL,
  `tip_raport` varchar(45) DEFAULT NULL,
  `terminat` varchar(45) DEFAULT NULL,
  `specialitate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_pacient_idx` (`CNP_pacient`),
  CONSTRAINT `fk_pacient` FOREIGN KEY (`CNP_pacient`) REFERENCES `pacient` (`cnp`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `raport_pacient`
--

LOCK TABLES `raport_pacient` WRITE;
/*!40000 ALTER TABLE `raport_pacient` DISABLE KEYS */;
INSERT INTO `raport_pacient` VALUES (1,'1000000000000','Carbune','Elena','0000000000007','','','','','2019-01-31','','','','3','RM','da','osteodensitometrie'),(2,'1000000000000','Carbune','Elena','0000000000007','','','','','2019-01-31','','','','3','RM','da','urologie'),(3,'1000000000001','Carbune','Elena','0000000000007','','','','','2019-01-31','','','','3','RM','da','stomatologie');
/*!40000 ALTER TABLE `raport_pacient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicii_medicale`
--

DROP TABLE IF EXISTS `servicii_medicale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `servicii_medicale` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `specialitate` varchar(45) DEFAULT NULL,
  `nume_investigatie` text,
  `durata` int(11) DEFAULT NULL,
  `pret` float(10,2) DEFAULT NULL,
  `competenta_necesara` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=279 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicii_medicale`
--

LOCK TABLES `servicii_medicale` WRITE;
/*!40000 ALTER TABLE `servicii_medicale` DISABLE KEYS */;
INSERT INTO `servicii_medicale` VALUES (1,'analize de laborator','Bicarbonat',4,60.00,'asistent medical'),(2,'analize de laborator','Calciu ionic - ser',1,12.00,'asistent medical'),(3,'analize de laborator','Calciu seric - ser',1,12.00,'asistent medical'),(4,'analize de laborator','Calciu urinar',1,12.00,'asistent medical'),(5,'analize de laborator','Clor alte produse',1,10.00,'asistent medical'),(6,'analize de laborator','Clor in LCR',0,10.00,'asistent medical'),(7,'analize de laborator','Clor seric',1,12.00,'asistent medical'),(8,'analize de laborator','Clor urinar',1,19.00,'asistent medical'),(9,'analize de laborator','Cupru seric',1,49.00,'asistent medical'),(10,'analize de laborator','Cupru urinar',15,65.00,'asistent medical'),(11,'analize de laborator','Fier seric',1,13.00,'asistent medical'),(12,'analize de laborator','Fosfor seric',1,13.00,'asistent medical'),(13,'analize de laborator','Fosfor urinar',1,22.00,'asistent medical'),(14,'analize de laborator','Iod total - ser',15,250.00,'asistent medical'),(15,'analize de laborator','Magneziu seric',1,12.00,'asistent medical'),(16,'analize de laborator','Magneziu urinar',1,19.00,'asistent medical'),(17,'analize de laborator','Potasiu seric',1,12.00,'asistent medical'),(18,'analize de laborator','Potasiu urinar',1,19.00,'asistent medical'),(19,'analize de laborator','Seleniu- ser fara gel activator',14,150.00,'asistent medical'),(20,'analize de laborator','Sodiu seric',1,12.00,'asistent medical'),(21,'analize de laborator','Sodiu urinar',1,19.00,'asistent medical'),(22,'analize de laborator','Zinc- ser',8,65.00,'asistent medical'),(23,'analize de laborator','Adenozin deaminaza, ADA-lichid pleural,LCR,ser',12,300.00,'asistent medical'),(24,'analize de laborator','Alaninaminotransferaza (analize de laborator,GPT/ALT) - ser',1,10.00,'asistent medical'),(25,'analize de laborator','Aldolaza -ser',7,89.00,'asistent medical'),(26,'analize de laborator','Alfa 1 antitripsina -ser',9,55.00,'asistent medical'),(27,'analize de laborator','Amilaza in lichid peritoneal',1,12.00,'asistent medical'),(28,'analize de laborator','Amilaza in lichid pleural',1,12.00,'asistent medical'),(29,'anestezie - terapie intensiva','A.G.I.O.T.- anestezie generala balansata cu pivot inhalator Sevoflurane',30,650.00,'-'),(30,'anestezie - terapie intensiva','MAC - monitorizare de specialitate + perfuzie / ora',30,150.00,'-'),(31,'anestezie - terapie intensiva','TIVA - anestezie generala iv',30,400.00,'-'),(32,'anestezie - terapie intensiva','V.I.M.A. - anestezie generala cu IOT si pivot inhalator',30,500.00,'-'),(33,'chirurgie generala','Atitudine primara in arsuri',30,130.00,'-'),(34,'chirurgie generala','Cateterism uretro-vezical',30,150.00,'-'),(35,'chirurgie generala','Cura chirurgicala formatiune lipomatoasa mare',30,350.00,'-'),(36,'chirurgie generala','Cura chirurgicala formatiune lipomatoasa mica',30,200.00,'-'),(37,'chirurgie generala','Electrocauterizare /electrorezectie / buc.',30,270.00,'-'),(38,'chirurgie generala','Extractie corp strain',30,80.00,'-'),(39,'chirurgie generala','Extragere sonda Cook',30,350.00,'-'),(40,'chirurgie generala','Fimoza-desfacerea aderentelor balano-preputiale ( chirurgie generala,manevra chirurgicala)',30,150.00,'-'),(41,'chirurgie generala','Flebectomie',30,450.00,'-'),(42,'chirurgie generala','Instilatie vezicala',30,80.00,'-'),(43,'chirurgie generala','Parafimoza',30,750.00,'-'),(44,'dermatologie','Avulsie partiala/totala unghiala',30,200.00,'-'),(45,'dermatologie','Biopsie cutanata (dermatologie,prelevare)',30,70.00,'-'),(46,'dermatologie','BIOREVITALIZARE CU JALUPRO',30,340.00,'-'),(47,'dermatologie','BIOREVITALIZARE CU JALUPRO HMW (dermatologie,high molecular weitght)',30,610.00,'-'),(48,'dermatologie','Chiuretare moluscum contagiosum/leziune',30,30.00,'-'),(49,'dermatologie','Consultatie de control medic primar/ specialist',30,80.00,'-'),(50,'dermatologie','Consultatie medic primar',30,140.00,'-'),(51,'dermatologie','Consultatie medic specialist',30,110.00,'-'),(52,'dermatologie','Consultatie profesor',30,220.00,'-'),(53,'dermatologie','Crioterapie (dermatologie,aplicatie histofreezer)/leziune',30,30.00,'-'),(54,'dermatologie','Dermatoscopie/leziune',30,40.00,'-'),(55,'dermatologie','Electrocoagulare leziune/veruca sau vegetatii veneriene',30,110.00,'-'),(56,'dermatologie','Electrocoagulare/excizie verucoame sau vegetatii mari/molluscum contagiosum/cmp',30,110.00,'-'),(57,'dermatologie','Eliminare tatuaj/cmp',30,130.00,'-'),(58,'dermatologie','Examinare cu lampa Wood',30,50.00,'-'),(59,'diabet, boli de nutritie si metabolism','Consultatie control medic primar',30,80.00,'-'),(60,'diabet, boli de nutritie si metabolism','Consultatie control medic specialist',30,70.00,'-'),(61,'diabet, boli de nutritie si metabolism','Consultatie medic primar',30,140.00,'-'),(62,'diabet, boli de nutritie si metabolism','Consultatie medic specialist',30,110.00,'-'),(63,'diabet, boli de nutritie si metabolism','Consultatie profesor',30,220.00,'-'),(64,'diabet, boli de nutritie si metabolism','Stabilire dieta (pretul se adauga pretului consultatiei de Boli de Nutritie)',30,60.00,'-'),(65,'endocrinologie','Consultatie de control medic primar/ specialist',30,80.00,'-'),(66,'endocrinologie','Consultatie medic primar',30,140.00,'-'),(67,'endocrinologie','Consultatie medic specialist',30,110.00,'-'),(68,'endocrinologie','Consultatie profesor',30,220.00,'-'),(69,'explorari functionale','Audiometrie',30,40.00,'-'),(70,'explorari functionale','EEG standard computerizat',30,100.00,'-'),(71,'explorari functionale','Electrocardiograma de efort',30,180.00,'-'),(72,'explorari functionale','Electrocardiograma de repaus',30,50.00,'-'),(73,'explorari functionale','Holter ECG',30,120.00,'-'),(74,'explorari functionale','Interpretare ECG repaus',30,10.00,'-'),(75,'explorari functionale','Interpretare EEG',30,50.00,'-'),(76,'explorari functionale','Interpretare holter ECG',30,50.00,'-'),(77,'explorari functionale','Interpretare Holter tensiune arteriala',30,40.00,'-'),(78,'explorari functionale','Mapping EEG',30,100.00,'-'),(79,'explorari functionale','Montare Holter tensiune arteriala',30,135.00,'-'),(80,'explorari functionale','Spirometrie',30,50.00,'-'),(81,'explorari functionale','Spirometrie + test bronhomotor',30,85.00,'-'),(82,'explorari functionale','Timpanometrie',30,50.00,'-'),(83,'gastroenterologie','Alcoolizarea ulcerului hemoragic',60,170.00,'endoscopie digestiva terapeutica'),(84,'gastroenterologie','Analgo-sedare S1',60,80.00,'endoscopie digestiva terapeutica'),(85,'gastroenterologie','Analgo-sedare S2',60,100.00,'endoscopie digestiva terapeutica'),(86,'gastroenterologie','Analgo-sedare S3',60,120.00,'endoscopie digestiva terapeutica'),(87,'gastroenterologie','Analgo-sedare S4',60,150.00,'endoscopie digestiva terapeutica'),(88,'gastroenterologie','Analgo-sedare S5',60,170.00,'endoscopie digestiva terapeutica'),(89,'gastroenterologie','Analgo-sedare S6',60,200.00,'endoscopie digestiva terapeutica'),(90,'gastroenterologie','Analgo-sedare S7',60,240.00,'endoscopie digestiva terapeutica'),(91,'gastroenterologie','Bandarea varicelor esofagiene',60,450.00,'endoscopie digestiva terapeutica'),(92,'gastroenterologie','Colonoscopie totala (gastroenterologie,endoscopie digestiva inferioara)',60,350.00,'endoscopie digestiva terapeutica'),(93,'gastroenterologie','Electrocoagularea leziunilor hemoragice',60,130.00,'endoscopie digestiva terapeutica'),(94,'gastroenterologie','Endoscopie digestiva superioara',60,270.00,'endoscopie digestiva terapeutica'),(95,'gastroenterologie','Examen proctoscopic',60,80.00,'endoscopie digestiva terapeutica'),(96,'gastroenterologie','Extractia de corpi straini',60,310.00,'endoscopie digestiva terapeutica'),(97,'medicina generala','Aerosoli (include substanta)',30,40.00,'asistent medical'),(98,'medicina generala','Anestezie locala',30,35.00,'asistent medical'),(99,'medicina generala','Aspiratie secretii cu aspirator chirurgical',30,35.00,'asistent medical'),(100,'medicina generala','Deplasare la domiciliu (recoltare sange,alte produse biologice)',30,100.00,'asistent medical'),(101,'medicina generala','Extractie fire sutura',30,40.00,'asistent medical'),(102,'medicina generala','Fimoza - desfacerea aderentelor balano-preputiale (manevra non chirurgicala)',30,60.00,'asistent medical'),(103,'medicina generala','Infiltratie periarticulara/intraarticulara cu Diprophos',30,100.00,'asistent medical'),(104,'medicina generala','Infiltratie periarticulara/intraarticulara cu substanta pacientului',30,60.00,'asistent medical'),(105,'medicina generala','Injectie intradermica',30,25.00,'asistent medical'),(106,'medicina generala','Injectie intramusculara',30,25.00,'asistent medical'),(107,'medicina generala','Injectie intravenoasa',30,30.00,'asistent medical'),(108,'medicina generala','Injectie subcutanata',30,25.00,'asistent medical'),(109,'medicina generala','Montare cateter venos periferic',30,65.00,'asistent medical'),(110,'medicina generala','Pansament exudativ',30,50.00,'asistent medical'),(111,'medicina generala','Pansament simplu (neexudativ)',30,40.00,'asistent medical'),(112,'neurologie','Blink reflex (reflexul de clipire)',30,100.00,'EMG'),(113,'neurologie','EMG - examen cu ac',30,300.00,'EMG'),(114,'neurologie','EMG - viteza de conducere nervoasa',30,240.00,'EMG'),(115,'neurologie','PEV auditive',30,270.00,'EMG'),(116,'neurologie','PEV somestezice',30,270.00,'EMG'),(117,'neurologie','PEV vizuale',30,270.00,'EMG'),(118,'neurologie','Teste de sistem nervos� autonom',30,230.00,'EMG'),(119,'o.r.l.','Ablatie papilom mucoasa buco-faringiana',30,55.00,'-'),(120,'o.r.l.','Acumetrie instrumentala',30,30.00,'-'),(121,'o.r.l.','Anemizari ale pituitarei',30,30.00,'-'),(122,'o.r.l.','Aspiratie auriculara',30,40.00,'-'),(123,'o.r.l.','Chimiocauterizare pata vasculara',30,50.00,'-'),(124,'o.r.l.','Control permeabilitate tubara',30,35.00,'-'),(125,'o.r.l.','Examen cavum prin ridicarea valului palatin',30,30.00,'-'),(126,'o.r.l.','Examen vestibular',30,20.00,'-'),(127,'o.r.l.','Extractie corpi straini nazali',30,60.00,'-'),(128,'obstetrica-ginecologie','Aplicare sterilet',30,150.00,'-'),(129,'obstetrica-ginecologie','Aplicatie PESAR',30,75.00,'-'),(130,'obstetrica-ginecologie','Colposcopie',30,170.00,'-'),(131,'obstetrica-ginecologie','Condyloame perineale, perianale, vulvare, vaginale, cervicale-Vaporizare Laser/0.5cmp',30,100.00,'-'),(132,'obstetrica-ginecologie','Condyloame si/sau leziuni peniene-PIN-Vaporizare Laser/0.5 cmp',30,100.00,'-'),(133,'obstetrica-ginecologie','Consultatie cuplu',30,135.00,'-'),(134,'obstetrica-ginecologie','Consultatie de control obstetrica-ginecologie',30,70.00,'-'),(135,'obstetrica-ginecologie','Consultatie de san (senologie)',30,130.00,'-'),(136,'obstetrica-ginecologie','Consultatie ginecologie',30,130.00,'-'),(155,'oftalmologie','Biometrie',30,60.00,'-'),(156,'oftalmologie','Biomicroscopie',30,75.00,'-'),(157,'oftalmologie','Blefarochalasis - cura chirurgicala',30,670.00,'-'),(158,'oftalmologie','Camp vizual',30,90.00,'-'),(159,'oftalmologie','Consult oftalmologic complet, inclusiv prescriere de ochelari',30,130.00,'-'),(160,'oftalmologie','Cura chirurgicala a colobomului',30,900.00,'-'),(161,'oftalmologie','Cura chirurgicala a tumorilor palpebrale - reconstructie',30,1400.00,'-'),(162,'oftalmologie','Dioptron',30,50.00,'-'),(163,'oftalmologie','Drenaj canal lacrimal',30,200.00,'-'),(164,'ortopedie-traumatologie','Atela deget',30,40.00,'-'),(165,'ortopedie-traumatologie','Bandaj Dessault',30,125.00,'-'),(166,'ortopedie-traumatologie','Bandaj Robert-Jones',30,145.00,'-'),(167,'ortopedie-traumatologie','Bandaj universal brat umar',30,100.00,'-'),(168,'ortopedie-traumatologie','Bandaj Watson-Jones',30,90.00,'-'),(169,'ortopedie-traumatologie','Consultatie de control medic primar/ specialist',30,80.00,'-'),(170,'ortopedie-traumatologie','Consultatie medic primar',30,140.00,'-'),(171,'ortopedie-traumatologie','Consultatie medic specialist',30,110.00,'-'),(172,'ortopedie-traumatologie','Consultatie profesor',30,220.00,'-'),(173,'osteodensitometrie','Osteodensitometrie 2 zone',30,145.00,'patologie osoasa hormonometabolica'),(174,'osteodensitometrie','Osteodensitometrie 3 zone',30,190.00,'patologie osoasa hormonometabolica'),(175,'osteodensitometrie','Osteodensitometrie DXA antebrat',30,80.00,'patologie osoasa hormonometabolica'),(176,'osteodensitometrie','Osteodensitometrie DXA col femural',30,80.00,'patologie osoasa hormonometabolica'),(177,'osteodensitometrie','Osteodensitometrie DXA coloana lombara',30,80.00,'patologie osoasa hormonometabolica'),(178,'psihologie','Aviz psihologic',30,80.00,'-'),(179,'psihologie','Consiliere psihologica adult',30,135.00,'-'),(180,'psihologie','Consiliere psihologica copil',30,135.00,'-'),(181,'psihologie','Consultatie psihologie la domiciliu',30,195.00,'-'),(182,'psihologie','Examen logopedic',30,120.00,'-'),(183,'psihologie','Parenting',30,150.00,'-'),(184,'psihologie','Profil psihologic - atentie si memorie',30,70.00,'-'),(185,'psihologie','Profil psihologic - personalitate',30,80.00,'-'),(186,'psihologie','Profil psihologic - QI',30,80.00,'-'),(187,'psihologie','Psihodiagnostic',30,120.00,'-'),(188,'psihologie','Psihoterapie',30,130.00,'-'),(189,'psihologie','Psihoterapie de cuplu',30,180.00,'-'),(190,'psihologie','Recuperare logopedica',30,80.00,'-'),(191,'radiologie - imagistica medicala','Biometrie fetala',30,125.00,'ecografie generala'),(192,'radiologie - imagistica medicala','Doppler sarcina 10-14 sapt. (velocimetrie pe ductul venos si flux tricuspidian)',30,100.00,'ecografie generala'),(193,'radiologie - imagistica medicala','Eco muschi (nu parti moi), tendoane, articulatii, formatiuni tumorale (chiste, abcese, tumori)',30,150.00,'ecografie generala'),(194,'radiologie - imagistica medicala','Ecografie abdomen inferior (vezica urinara, uter, ovare)',30,130.00,'ecografie generala'),(195,'radiologie - imagistica medicala','Ecografie abdomen superior (ficat, colecist, pancreas, rinichi, splina)',30,150.00,'ecografie generala'),(196,'radiologie - imagistica medicala','Ecografie abdomen total ( superior+inferior)',30,220.00,'ecografie generala'),(197,'radiologie - imagistica medicala','Ecografie aparat urinar',30,120.00,'ecografie generala'),(198,'radiologie - imagistica medicala','Ecografie articulatii',30,140.00,'ecografie generala'),(199,'radiologie - imagistica medicala','Ecografie de organ ( ficat/colecist/pancreas/splina/rinichi stg/rinichi dr)',30,100.00,'ecografie generala'),(200,'radiologie - imagistica medicala','Ecografie Doppler aorta abdominala, trunchi celiac, artera mezenterica',30,125.00,'ecografie generala'),(201,'radiologie - imagistica medicala','Ecografie Doppler artere renale',30,235.00,'ecografie generala'),(202,'radiologie - imagistica medicala','Ecografie Doppler de axe carotidiene si vertebrale extracranian si artere subclavii',30,180.00,'ecografie generala'),(203,'radiologie - imagistica medicala','Ecografie Doppler transcranian',30,190.00,'ecografie generala'),(204,'radiologie - imagistica medicala','Ecografie Doppler vena cava inferioara si iliace',30,120.00,'ecografie generala'),(205,'radiologie - imagistica medicala','Ecografie Doppler - Cord',30,220.00,'ecografie generala'),(206,'radiologie - imagistica medicala','Ecografie Doppler - Sistem arterial periferic membre superioare/inferioare',30,200.00,'ecografie generala'),(207,'radiologie - imagistica medicala','Ecografie Doppler - Sistem arterial periferic membru superior/inferior',30,150.00,'ecografie generala'),(208,'radiologie - imagistica medicala','Ecografie Doppler - Sistem venos periferic membre superioare/inferioare',30,200.00,'ecografie generala'),(209,'radiologie - imagistica medicala','Ecografie Doppler - Sistem venos periferic membru superior/inferior',30,150.00,'ecografie generala'),(210,'radiologie - imagistica medicala','Ecografie mamara (bilateral)',30,200.00,'ecografie generala'),(211,'radiologie - imagistica medicala','Ecografie mamara (unilateral)',30,120.00,'ecografie generala'),(212,'radiologie - imagistica medicala','Ecografie parti moi (muschi, gat, glande parotide, sublinguale, submaxilare)',30,120.00,'ecografie generala'),(213,'radiologie - imagistica medicala','Ecografie pelvina',30,120.00,'ecografie generala'),(214,'stomatologie','Anestezie locala',30,10.00,''),(215,'stomatologie','Consultatie de control medic primar/ specialist',30,80.00,''),(216,'stomatologie','Consultatie de specialitate (Conservativa,chirurgie,ortodontie)',30,90.00,''),(217,'stomatologie','Consultatie ortodontie',30,100.00,''),(218,'stomatologie','Disjunctor',30,1500.00,''),(219,'stomatologie','Tratament urgenta (pansament calmant, drenaj endodontic)',30,100.00,''),(220,'stomatologie','Albire endodontica',30,300.00,'cosmetica dentara'),(221,'stomatologie','Albire profesionala',30,650.00,'cosmetica dentara'),(222,'stomatologie','Bijuterii cosmetice dentare simple',30,150.00,'cosmetica dentara'),(223,'stomatologie','Activare aparat fix - 1 arcada',30,100.00,'ortodontie'),(224,'stomatologie','Activare aparat lingual -1 arcada',30,100.00,'ortodontie'),(225,'stomatologie','Activare aparat mobil',30,50.00,'ortodontie'),(226,'stomatologie','Adaptare ARC facial',30,100.00,'ortodontie'),(227,'stomatologie','Adaptare arc intraoral',30,100.00,'ortodontie'),(228,'stomatologie','Adaptare masca faciala',30,500.00,'ortodontie'),(229,'stomatologie','Amprentare 1 arcada',30,50.00,'ortodontie'),(230,'stomatologie','Aparat de contentie fix (retainer colet)',30,600.00,'ortodontie'),(231,'stomatologie','Aparat fix ceramic (1 arcada)',30,3800.00,'ortodontie'),(232,'stomatologie','Aparat fix metalic (1arcada)',30,2700.00,'ortodontie'),(233,'stomatologie','Aparat fix partial ceramic',30,2500.00,'ortodontie'),(234,'stomatologie','Aparat fix partial metalic',30,1800.00,'ortodontie'),(254,'toate specialitatile','Certificat medical format A5 cu timbru fiscal',30,100.00,'-'),(255,'toate specialitatile','Certificat medical necesar prelungirii permisului port-arma',30,400.00,'-'),(256,'toate specialitatile','Certificat necesar obtinerii permisului port-arma',30,415.00,'-'),(257,'toate specialitatile','Certificat prenuptial (o persoana)',30,200.00,'-'),(258,'toate specialitatile','Consult medic specialist/primar si completare chestionar medical in limba romana',30,100.00,'-'),(259,'toate specialitatile','Consult medic specialist/primar si completare chestionar medical intr-o limba de circulatie internationala',30,150.00,'-'),(260,'toate specialitatile','Consultatie conferentiar/profesor',30,220.00,'-'),(261,'toate specialitatile','Consultatie control medic specialist',30,70.00,'-'),(262,'toate specialitatile','Consultatie de control conferentiar/profesor',30,110.00,'-'),(263,'toate specialitatile','Consultatie de control medic primar',30,80.00,'-'),(264,'toate specialitatile','Consultatie de control - medicina generala/medicina de familie',30,70.00,'-'),(265,'toate specialitatile','Consultatie la domiciliu medic specialist/primar',30,400.00,'-'),(266,'toate specialitatile','Consultatie medic primar',30,140.00,'-'),(267,'toate specialitatile','Consultatie medic specialist',30,110.00,'-'),(268,'toate specialitatile','Consultatie medicina generala/medicina de familie',30,110.00,'-'),(269,'toate specialitatile','Fisa medicala necesara obtinerii/preschimbarii permisului de conducere auto',30,250.00,'-'),(270,'toate specialitatile','Fisa medicala necesara sustinerii examenului/concursului de notar public',30,200.00,'-'),(271,'toate specialitatile','Fisa medicala pentru atestarea calitatii de detectiv particular',30,200.00,'-'),(272,'toate specialitatile','Traducere raport medical (1 pg)',30,40.00,'-'),(273,'urologie','Consult urologic de control profesor',30,90.00,'-'),(274,'urologie','Consultatie de control medic primar/ specialist',30,80.00,'-'),(275,'urologie','Consultatie medic primar',30,140.00,'-'),(276,'urologie','Consultatie medic specialist',30,110.00,'-'),(277,'urologie','Consultatie profesor',30,220.00,'-'),(278,'urologie','Ecografie aparat urinar',30,120.00,'-');
/*!40000 ALTER TABLE `servicii_medicale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialitati`
--

DROP TABLE IF EXISTS `specialitati`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `specialitati` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nume_specialitate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialitati`
--

LOCK TABLES `specialitati` WRITE;
/*!40000 ALTER TABLE `specialitati` DISABLE KEYS */;
INSERT INTO `specialitati` VALUES (1,'analize de laborator'),(2,'anestezie - terapie intensiva'),(3,'chirurgie generala'),(4,'dermatologie'),(5,'diabet, boli de nutritie si metabolism'),(6,'endocrinologie'),(7,'explorari functionale'),(8,'gastroenterologie'),(9,'medicina generala'),(10,'neurologie'),(11,'o.r.l.'),(12,'obstetrica-ginecologie'),(13,'oftalmologie'),(14,'ortopedie-traumatologie'),(15,'osteodensitometrie'),(16,'psihologie'),(17,'radiologie - imagistica medicala'),(18,'stomatologie'),(19,'toate specialitatile'),(20,'urologie');
/*!40000 ALTER TABLE `specialitati` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unitati_medicale`
--

DROP TABLE IF EXISTS `unitati_medicale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `unitati_medicale` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `denumire` varchar(45) DEFAULT NULL,
  `adresa` varchar(45) DEFAULT NULL,
  `descriere` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unitati_medicale`
--

LOCK TABLES `unitati_medicale` WRITE;
/*!40000 ALTER TABLE `unitati_medicale` DISABLE KEYS */;
INSERT INTO `unitati_medicale` VALUES (0,NULL,NULL,NULL),(1,'Unitate 1','str. Moţilor nr. 20','analize de laborator,anestezie - terapie intensiva,chirurgie generala,dermatologie,diabet, boli de nutritie si metabolism'),(2,'Unitate 2','str. Moţilor nr. 19','endocrinologie,explorari functionale,explorari functionale,medicina generala,neurologie'),(3,'Unitate 3','str. Moţilor nr. 18','o.r.l.,obstetrica-ginecologie,oftalmologie,ortopedie-traumatologie,osteodensitometrie'),(4,'Unitate 4','str. Moţilor nr. 17','psihologie,radiologie - imagistica medicala,stomatologie,urologie');
/*!40000 ALTER TABLE `unitati_medicale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilizator`
--

DROP TABLE IF EXISTS `utilizator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `utilizator` (
  `CNP` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nume` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `prenume` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `username` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `tip` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `functia` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `adresa` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `telefon` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `cont_IBAN` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `nr_contract` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `salariu` float(100,2) DEFAULT NULL,
  `ore` int(11) DEFAULT NULL,
  `data_angajarii` date DEFAULT NULL,
  PRIMARY KEY (`CNP`),
  UNIQUE KEY `CNP_UNIQUE` (`CNP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilizator`
--

LOCK TABLES `utilizator` WRITE;
/*!40000 ALTER TABLE `utilizator` DISABLE KEYS */;
INSERT INTO `utilizator` VALUES ('0000000000001','Carbune','Ioana','medic','‰:íjG1Öhû¶wžÿtí','Angajat','Medic','Anies nr.66','074561235','carbune_ioana@yahoo.com','RO123876614DSA','1',5000.00,200,'2018-11-01'),('0000000000002','Popescu','Andrei','receptioner','Ó!þ1:Tˆ×£ìS¡5ýE*','Angajat','Receptioner','str.Plopilor nr.5','074568165','popescu_andrei@yahoo.com','RO45681321621','2',3000.00,200,'2018-11-13'),('0000000000003','Muresan','Dan','expert','ïh+=\')èÃs»´;…','Angajat','Expert financiar','str.Theodor Mihali','0746153888','dan_muresan@yahoo.com','RO02318979','4',2500.00,200,'2018-11-18'),('0000000000004','Farcas','Alexandru','inspector','a:ûà°“m4G8eg','Angajat','Inspector resurse umane','str. Plopului','0745896123','alex@yahoo.com','RO486417481364864ds','4',25000.00,200,'2018-11-06'),('0000000000005','Bilan','Razvan','asistent','3],A»ëT3î&]Î_ëíÿ','Angajat','Asistent medical','str. Eminescu','0784597135','razvan@yahoo.com','RO5264164196156fsdf','5',3000.00,200,'2018-11-05'),('0000000000006','Muresan','Florin','medic2','’Í–Eß;MM´e™Š¦\nÛ','Angajat','Medic','Str. Aaaa','0754896135','florin@yahoo.com','RObfvjl544164185545','6',4000.00,200,'2018-11-14'),('0000000000007','Carbune','Elena','medic3','üÍkÔHZpó—CÝ,#Ä','Angajat','Medic','Str.Bbb','07854698234','elena@yahoo.com','df6463164164131','7',3000.00,200,'2018-10-30'),('1111111111111','admin','admin','admin','jFCÙÜ-Å[„Ô^\Zþ¢','Super-administrator','Receptioner','0','0','0','0','0',0.00,0,'2000-01-01');
/*!40000 ALTER TABLE `utilizator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'lant_de_policlinici'
--
/*!50003 DROP PROCEDURE IF EXISTS `adauga_raport` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `adauga_raport`( cnp_pacient varchar(45), medic_nume varchar(45), medic_prenume varchar(45), nume_medic_rec varchar(45), prenume_medic_rec varchar(45), asistent_nume varchar(45), asistent_prenume varchar(45), data_c varchar(45), simp varchar(45), diag varchar(45), rec varchar(45))
BEGIN

	insert into raport_pacient(CNP_pacient, nume_medic, prenume_medic,nume_medic_recomandare,prenume_medic_recomandare, nume_asistent_medical, prenume_asistent_medical, data_consultatiei,simptome, diagnostic,recomandari)
    values
    (cnp_pacient, medic_nume, medic_prenume,nume_medic_rec,prenume_medic_rec,asistent_nume, asistent_prenume,data_c, simp,diag,rec);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cautare_programare` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `cautare_programare`( doctorLastName varchar(45),  doctorFirstName varchar(45),  doctorCNP varchar(13) ,  patientLastName varchar(45), 
 patientFirstName varchar(45),  date varchar(45),  service varchar(45),stare varchar(45))
BEGIN
	
    select   u.nume, u.prenume,p.nume_pacient, p.prenume_pacient, s.nume_investigatie, p.id_unitate,p.data_calendaristica, p.perioada_inceput,u.CNP,p.prezentare,p.CNP_pacient from programare p
    join utilizator u on p.CNP_medic=u.CNP
    join servicii_medicale s on s.id=p.id_serviciu
    where u.nume like concat(doctorLastName,'%') and u.prenume like concat(doctorFirstName,'%') and p.CNP_medic like concat(doctorCNP,'%') and p.nume_pacient like concat(patientLastName,'%') 
    and p.prenume_pacient like concat(patientFirstName,'%') and p.data_calendaristica like concat (date,'%') and s.nume_investigatie like concat(service,'%') and p.prezentare like concat(stare,'%');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cauta_raportNeterminat` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `cauta_raportNeterminat`(CNP_medic1 varchar(45),tip_raport1 varchar(45),CNP1 varchar(45),nume1 varchar(45),prenume1 varchar(45))
BEGIN
	select r.id,r.CNP_pacient, p.nume,p.prenume from pacient p join
    raport_pacient r on r.CNP_pacient=p.CNP
    where r.terminat is NULL and tip_raport=tip_raport1 and CNP_pacient like concat(CNP1,'%') and p.nume like  concat(nume1,'%') and p.prenume like concat(prenume1,'%') and r.CNP_medic=CNP_medic1 order by r.id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cauta_raport_pacient` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `cauta_raport_pacient`(CNP1 varchar(45),nume1 varchar(45),prenume1 varchar(45))
BEGIN
	select r.id,r.CNP_pacient, p.nume,p.prenume from pacient p join
    raport_pacient r on r.CNP_pacient=p.CNP
    where  CNP_pacient like concat(CNP1,'%') and p.nume like  concat(nume1,'%') and p.prenume like concat(prenume1,'%');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `date_raport` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `date_raport`(id_r int(11))
BEGIN
	select p.nume, p.prenume, r.CNP_pacient, r.nume_medic, r.prenume_medic,r.CNP_medic, r.nume_medic_recomandare,
    r.prenume_medic_recomandare,r.nume_asistent_medical,r.prenume_asistent_medical,
    r.data_consultatiei,r.simptome,r.diagnostic,r.recomandari, r.parafa, r.terminat ,r.tip_raport,r.specialitate
    from raport_pacient r
    join pacient p on p.CNP=r.CNP_pacient
    where r.id=id_r;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `gaseste_medic` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `gaseste_medic`(nume_investigatie1 text)
BEGIN
select u.nume, u.prenume,u.CNP
from utilizator u
join medic m on u.CNP=m.CNP_medic
join medic_specialitate s on s.CNP_medic=m.CNP_medic
join specialitati sp on sp.id=s.id_specialitate
where nume_specialitate=(select specialitate from servicii_medicale where nume_investigatie=nume_investigatie1);


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_angajat` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_angajat`(CNP1 varchar(13),nume1 varchar(45),prenume1 varchar(45),username1 varchar(45),password1 varchar(45),tip1 varchar(45),functia1 varchar(45),adresa1 varchar(45),telefon1 varchar(45),
email1 varchar(45),iban1 varchar(45),nrcontrac1 varchar(45),salariu1 float(100,2),ore1 int(11),dataangajari1 date)
begin
insert into utilizator(CNP,nume,prenume,username,password,tip,functia,adresa,telefon,email,cont_IBAN,nr_contract,salariu,ore,data_angajarii)
values(CNP1,nume1,prenume1,username1,aes_encrypt(password1,'key1234'),tip1,functia1,adresa1,telefon1,email1,iban1,nrcontrac1,salariu1,ore1,dataangajari1);

insert into orar_angajat(CNP_angajat,zi) values(CNP1,"Luni");
insert into orar_angajat(CNP_angajat,zi) values(CNP1,"Marti");
insert into orar_angajat(CNP_angajat,zi) values(CNP1,"Miercuri");
insert into orar_angajat(CNP_angajat,zi) values(CNP1,"Joi");
insert into orar_angajat(CNP_angajat,zi) values(CNP1,"Vineri");
insert into orar_angajat(CNP_angajat,zi) values(CNP1,"Sambata");
insert into orar_angajat(CNP_angajat,zi) values(CNP1,"Duminica");

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_asistent_medical` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_asistent_medical`(CNP_asistent1 varchar(45),tip1 varchar(45),grad1 varchar(45))
begin
insert into asistent_medical(CNP_asistent,tip,grad) values (CNP_asistent1,tip1,grad1);

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_dateMedic` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_dateMedic`(CNP_medic1 varchar(45), grad1 varchar(45),cod_parafa1 varchar(45),titlu_stiintific1 varchar(45),post_didactic1 varchar(45),comision1 float(100,0))
begin
insert into medic(CNP_medic, grad,cod_parafa,titlu_stiintific,post_didactic,comision) values (CNP_medic1, grad1,cod_parafa1,titlu_stiintific1,post_didactic1,comision1);

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_medic_competente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_medic_competente`(CNP_medic1 varchar(45),id_competente1 int(11))
begin
insert into medic_competente(CNP_medic,id_competente) values (CNP_medic1,id_competente1);

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_medic_specialitate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_medic_specialitate`(CNP_medic1 varchar(45),id_specialitate1 int(11))
begin
insert into medic_specialitate(CNP_medic,id_specialitate) values (CNP_medic1,id_specialitate1);

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_OrarSpecific` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_OrarSpecific`(CNP1 varchar(13),id_unitate_medicala1 int(11),data date,perioada_inceput1 time,perioada_sfarsit1 time)
begin
insert into orar_angajat(CNP_angajat,id_unitate_medicala,data,perioada_inceput,perioada_sfarsit) values (CNP1,id_unitate_medicala1 ,data,perioada_inceput1,perioada_sfarsit1);

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_programare` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_programare`(nume_pacient1 varchar(45),prenume_pacient1 varchar(45),perioada_inceput1 time,perioada_final1 time,data_calendaristica1 date,CNP_medic1 varchar(45),id_serviciu1 int(45),id_unitate1 int(45),prezentare1 varchar(45))
BEGIN
insert into programare(nume_pacient,prenume_pacient,perioada_inceput,perioada_final,data_calendaristica,CNP_medic,id_serviciu,id_unitate,prezentare) values
(nume_pacient1,prenume_pacient1,perioada_inceput1,perioada_final1,data_calendaristica1,CNP_medic1,id_serviciu1,id_unitate1,prezentare1);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_raport` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_raport`(CNP_pacient1 varchar(45),nume_medic1 varchar(45),prenume_medic1 varchar(45),CNP_medic1 varchar(45),nume_medic_recomandare1 varchar(45),prenume_medic_recomandare1 varchar(45),
                    nume_asistent_medical1 varchar(45),prenume_asistent_medical1 varchar(45),data_consultatiei1 varchar(45),
                     simptome1 text,diagnostic1 text,recomandari1 text,parafa1 varchar(45),tip_raport1 varchar(45),specialitate1 varchar(45))
BEGIN
insert into raport_pacient(CNP_pacient,nume_medic,prenume_medic,CNP_medic,nume_medic_recomandare,prenume_medic_recomandare,
					nume_asistent_medical,prenume_asistent_medical,data_consultatiei,
                    simptome,diagnostic,recomandari,parafa,tip_raport,specialitate) values (CNP_pacient1,nume_medic1,prenume_medic1,CNP_medic1 ,nume_medic_recomandare1,prenume_medic_recomandare1,
                    nume_asistent_medical1,prenume_asistent_medical1,data_consultatiei1,
                     simptome1,diagnostic1,recomandari1,parafa1,tip_raport1,specialitate1);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `medic_competentee` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `medic_competentee`(CNP1 varchar(45))
begin
select c.id from competente c
join medic_competente mc on c.id=mc.id_competente
where mc.CNP_medic=CNP1;

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `medic_specialitati` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `medic_specialitati`(CNP1 varchar(45))
begin
select s.id  from specialitati s
join medic_specialitate ms on s.id=ms.id_specialitate
where ms.CNP_medic=CNP1;

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `medic_specialitatiDenumire` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `medic_specialitatiDenumire`(CNP1 varchar(45))
BEGIN
select s.nume_specialitate from specialitati s
join medic_specialitate ms on s.id=ms.id_specialitate
where ms.CNP_medic=CNP1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `servicii_raport` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `servicii_raport`(id int(11))
BEGIN
	select s.nume_investigatie, i.rezultat, i.platit,s.pret, i.id_serviciu_medical from servicii_medicale s join
    investigatie_raport i on s.id=i.id_serviciu_medical 
    where i.id_raport=id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `serviciu_platit` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `serviciu_platit`(raport_id int(11),serviciu_id int(11))
BEGIN
	update investigatie_raport i
    set
    i.platit='da'
    where i.id_raport=raport_id and i.id_serviciu_medical=serviciu_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sterge_tot` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sterge_tot`()
BEGIN
set foreign_key_checks=0;
truncate utilizator;
truncate raport_pacient;
truncate programare;
truncate pacient;
truncate orar_angajat;
truncate medic_competente;
truncate medic_specialitate;
truncate medic;
truncate istoric_salarii;
truncate investigatie_raport;
truncate concediu_angajat;
truncate bon_fiscal;
truncate asistent_medical;
set foreign_key_checks=1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sterge_Utilizator` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sterge_Utilizator`(CNP1 varchar(45))
begin
delete	from orar_angajat where Cnp_angajat=CNP1;
delete from istoric_salarii where CNP_angajat=CNP1;
delete from medic_competente where CNP_medic=CNP1;
delete from medic_specialitate where CNP_medic=CNP1;
delete from medic where CNP_medic=CNP1;
delete from asistent_medical where CNP_asistent=CNP1;
delete from concediu_angajat where CNP_angajat=CNP1;
delete from utilizator where CNP=CNP1;

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_asistent_medical` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_asistent_medical`(CNP_asistent1 varchar(45),CNP_asistent2 varchar(45),tip1 varchar(45),grad1 varchar(45))
begin
update asistent_medical
set CNP_asistent=CNP_asistent2,
tip=tip1,
grad=grad1
where CNP_asistent=CNP_asistent1;

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_dateMedic` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_dateMedic`(CNP_medic1 varchar(45),CNP_medic2 varchar(45), grad1 varchar(45),cod_parafa1 varchar(45),titlu_stiintific1 varchar(45),post_didactic1 varchar(45),comision1 float(100,0))
begin

update medic 
set CNP_medic=CNP_medic2, 
grad=grad1,
cod_parafa=cod_parafa1,
titlu_stiintific=titlu_stiintific1,
post_didactic=post_didactic1,
comision=comision1

where CNP_medic=CNP_medic1;

delete from medic_competente where CNP_medic=CNP_medic1;
delete from medic_specialitate where CNP_medic=CNP_medic1;

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_raport` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_raport`(id1 int(11),CNP_pacient1 varchar(45),nume_medic1 varchar(45),prenume_medic1 varchar(45),CNP_medic1 varchar(45),nume_medic_recomandare1 varchar(45),prenume_medic_recomandare1 varchar(45),
                    nume_asistent_medical1 varchar(45),prenume_asistent_medical1 varchar(45),
                     simptome1 text,diagnostic1 text,recomandari1 text,parafa1 varchar(45),tip_raport1 varchar(45))
BEGIN
update raport_pacient 
set CNP_pacient=CNP_pacient1,
nume_medic=nume_medic1,
prenume_medic=prenume_medic1,
CNP_medic=CNP_medic1,
nume_medic_recomandare=nume_medic_recomandare1,
prenume_medic_recomandare=prenume_medic_recomandare1,
nume_asistent_medical=nume_asistent_medical1,
prenume_asistent_medical=prenume_asistent_medical1,
simptome=simptome1,
diagnostic=diagnostic1,
recomandari=recomandari1,
parafa=parafa1,
tip_raport=tip_raport1
where id=id1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_utilizator` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_utilizator`(CNP1 varchar(13),CNP2 varchar(13),nume1 varchar(45),prenume1 varchar(45),username1 varchar(45),password1 varchar(45)
,tip1 varchar(45),functia1 varchar(45),adresa1 varchar(45),telefon1 varchar(45),email1 varchar(45),iban1 varchar(45),nrcontrac1 varchar(45),salariu1 float(100,2),ore1 int(11),dataangajari1 date)
begin



update utilizator 
set CNP=CNP2,
nume=nume1,
prenume=prenume1,
username=username1,
password=aes_encrypt(password1,'key1234'),
tip=tip1,
functia=functia1,
adresa=adresa1,
telefon=telefon1,
email=email1,
cont_IBAN=iban1,
nr_contract=nrcontrac1,
salariu=salariu1,
ore=ore1,
data_angajarii=dataangajari1
where CNP=CNP1;

update  istoric_salarii
set CNP_angajat=CNP2
where CNP_angajat=CNP1;

update orar_angajat
set CNP_angajat=CNP2
where CNP_angajat=CNP1;

update concediu_angajat
set CNP_angajat=CNP2
where CNP_angajat=CNP1; 



end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-31 21:27:19
