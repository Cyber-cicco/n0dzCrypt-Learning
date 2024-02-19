-- MySQL dump 10.19  Distrib 10.3.39-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: digilearning
-- ------------------------------------------------------
-- Server version	10.3.39-MariaDB-0ubuntu0.20.04.2

create database d

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ADMINISTRATION_SESSION`
--

DROP TABLE IF EXISTS `ADMINISTRATION_SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ADMINISTRATION_SESSION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ID_SESSION` bigint(20) DEFAULT NULL,
  `ID_UTILISATEUR` bigint(20) DEFAULT NULL,
  `STATUS_RESPONSABLE_SESSION` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK3rck7fwxampt7apc541lnvo4d` (`ID_UTILISATEUR`),
  KEY `FK4ehfn60tm0fnku1e74cn6bvsq` (`ID_SESSION`),
  CONSTRAINT `ADMINISTRATION_SESSION_ibfk_1` FOREIGN KEY (`ID_SESSION`) REFERENCES `SESSION` (`ID`),
  CONSTRAINT `ADMINISTRATION_SESSION_ibfk_2` FOREIGN KEY (`ID_UTILISATEUR`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FK3rck7fwxampt7apc541lnvo4d` FOREIGN KEY (`ID_UTILISATEUR`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FK4ehfn60tm0fnku1e74cn6bvsq` FOREIGN KEY (`ID_SESSION`) REFERENCES `SESSION` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ADMINISTRATION_SESSION`
--

LOCK TABLES `ADMINISTRATION_SESSION` WRITE;
/*!40000 ALTER TABLE `ADMINISTRATION_SESSION` DISABLE KEYS */;
INSERT INTO `ADMINISTRATION_SESSION` VALUES (1,1001,1,0),(2,1002,1,0),(3,1003,1,0),(4,1004,1,0),(5,1005,1,0),(6,1006,1,0),(7,1007,1,0);
/*!40000 ALTER TABLE `ADMINISTRATION_SESSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FORMATION`
--

DROP TABLE IF EXISTS `FORMATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FORMATION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NOM` varchar(255) DEFAULT NULL,
  `NOM_COURT` varchar(50) DEFAULT NULL,
  `REFERENCE` varchar(10) DEFAULT NULL,
  `USER_MAJ` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=511 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FORMATION`
--

LOCK TABLES `FORMATION` WRITE;
/*!40000 ALTER TABLE `FORMATION` DISABLE KEYS */;
INSERT INTO `FORMATION` VALUES (501,'Développeur Java Fullstack',NULL,'',''),(505,'Développeur .NET',NULL,NULL,NULL),(506,'Développeur web Python',NULL,NULL,NULL),(507,'Big Data Python',NULL,NULL,NULL),(508,'Développeur web PHP',NULL,NULL,NULL),(509,'Développement WEB RNCP 5',NULL,NULL,NULL),(510,'Développement fullstack HTMX',NULL,NULL,NULL);
/*!40000 ALTER TABLE `FORMATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ROLE`
--

DROP TABLE IF EXISTS `ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ROLE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(255) DEFAULT NULL,
  `TYPE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ROLE`
--

LOCK TABLES `ROLE` WRITE;
/*!40000 ALTER TABLE `ROLE` DISABLE KEYS */;
INSERT INTO `ROLE` VALUES (1,'Administrateur','ROLE_ADMINISTRATEUR'),(2,'Planificateur','ROLE_PLANIFICATEUR'),(3,'Formateur','ROLE_FORMATEUR'),(4,'Stagiaire','ROLE_STAGIAIRE'),(5,'Visiteur','ROLE_VISITEUR'),(6,'Visiteur admin','ROLE_VISITEUR_ADMIN'),(7,'Contact','ROLE_CONTACT');
/*!40000 ALTER TABLE `ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ROLE_UTILISATEUR`
--

DROP TABLE IF EXISTS `ROLE_UTILISATEUR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ROLE_UTILISATEUR` (
  `ID_UTILISATEUR` bigint(20) NOT NULL,
  `ID_ROLE` bigint(20) NOT NULL,
  KEY `FKc4sx3xk0sftvon2jpj4ukc68d` (`ID_UTILISATEUR`),
  KEY `FKkp8he55mabr635hbw3aqc2hmf` (`ID_ROLE`),
  CONSTRAINT `FK_ROLE_UTILISATEUR1` FOREIGN KEY (`ID_ROLE`) REFERENCES `ROLE` (`ID`),
  CONSTRAINT `FK_ROLE_UTILISATEUR2` FOREIGN KEY (`ID_UTILISATEUR`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FKc4sx3xk0sftvon2jpj4ukc68d` FOREIGN KEY (`ID_UTILISATEUR`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FKkp8he55mabr635hbw3aqc2hmf` FOREIGN KEY (`ID_ROLE`) REFERENCES `ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ROLE_UTILISATEUR`
--

LOCK TABLES `ROLE_UTILISATEUR` WRITE;
/*!40000 ALTER TABLE `ROLE_UTILISATEUR` DISABLE KEYS */;
INSERT INTO `ROLE_UTILISATEUR` VALUES (1,1),(1,3),(2,4),(3,4),(4,4),(5,4),(6,4),(7,4),(8,4),(9,4),(10,4),(11,4),(12,4),(13,4),(14,4),(15,4),(16,3),(16,1),(17,3),(17,1),(18,3),(18,1),(19,3),(19,1),(20,3),(20,1),(21,3),(21,1),(22,3),(22,1),(23,3),(23,1),(24,3),(24,1);
/*!40000 ALTER TABLE `ROLE_UTILISATEUR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SESSION`
--

DROP TABLE IF EXISTS `SESSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SESSION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE_DEB` date DEFAULT NULL,
  `DATE_FIN` date DEFAULT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `ID_FOR` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SESSION2` (`ID_FOR`),
  CONSTRAINT `FK_SESSION2` FOREIGN KEY (`ID_FOR`) REFERENCES `FORMATION` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1008 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SESSION`
--

LOCK TABLES `SESSION` WRITE;
/*!40000 ALTER TABLE `SESSION` DISABLE KEYS */;
INSERT INTO `SESSION` VALUES (1001,'2023-09-01','2024-09-01','M01-Java-fullstack',501),(1002,'2023-07-01','2024-09-01','M02-DOTNET',505),(1003,'2023-08-01','2024-09-01','M03-Python',506),(1004,'2023-09-01','2024-09-01','M04-Big-data',507),(1005,'2023-10-01','2024-09-01','M05-PHP',508),(1006,'2023-10-01','2024-09-01','M06-Web',509),(1007,'2023-10-01','2024-09-01','M07-HTMX',510);
/*!40000 ALTER TABLE `SESSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SESSION_STAGIAIRE`
--

DROP TABLE IF EXISTS `SESSION_STAGIAIRE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SESSION_STAGIAIRE` (
  `ID_SES` bigint(20) NOT NULL,
  `ID_STAG` bigint(20) NOT NULL,
  UNIQUE KEY `UX_SESSION_STAGIAIRE` (`ID_SES`,`ID_STAG`),
  KEY `FKbo113uq3orpsbefnxf6kirb8o` (`ID_STAG`),
  CONSTRAINT `FK_SESSION_STAGIAIRE1` FOREIGN KEY (`ID_SES`) REFERENCES `SESSION` (`ID`),
  CONSTRAINT `FK_SESSION_STAGIAIRE2` FOREIGN KEY (`ID_STAG`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FKbo113uq3orpsbefnxf6kirb8o` FOREIGN KEY (`ID_STAG`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FKqss4vrnmwoh2itrf9f3hr0o3x` FOREIGN KEY (`ID_SES`) REFERENCES `SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SESSION_STAGIAIRE`
--

LOCK TABLES `SESSION_STAGIAIRE` WRITE;
/*!40000 ALTER TABLE `SESSION_STAGIAIRE` DISABLE KEYS */;
INSERT INTO `SESSION_STAGIAIRE` VALUES (1001,2),(1001,3),(1002,4),(1002,5),(1003,6),(1003,7),(1004,8),(1004,9),(1005,10),(1005,11),(1006,12),(1006,13),(1007,14),(1007,15);
/*!40000 ALTER TABLE `SESSION_STAGIAIRE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UTILISATEUR`
--

DROP TABLE IF EXISTS `UTILISATEUR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UTILISATEUR` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(50) NOT NULL,
  `NOM` varchar(50) NOT NULL,
  `PASSWORD` varchar(80) DEFAULT NULL,
  `PRENOM` varchar(30) NOT NULL,
  `TELEPHONE` varchar(15) DEFAULT NULL,
  `ADRESSE` varchar(100) DEFAULT NULL,
  `DATE_NAISSANCE` date DEFAULT NULL,
  `TOKEN_INIT` varchar(255) DEFAULT NULL,
  `USER_MAJ` varchar(255) DEFAULT NULL,
  `ID_SESSION` bigint(20) DEFAULT NULL,
  `OFFICE_EMAIL` varchar(100) DEFAULT NULL,
  `dl_banned` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_UTILISATEUR1` (`EMAIL`),
  UNIQUE KEY `UK_daucih6sx9vn631ggv857ec5m` (`EMAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UTILISATEUR`
--

LOCK TABLES `UTILISATEUR` WRITE;
/*!40000 ALTER TABLE `UTILISATEUR` DISABLE KEYS */;
INSERT INTO `UTILISATEUR` VALUES (1,'rbonnamy@diginamic.fr','Bonnamy','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Richard','0101010101',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'java1@diginamic.fr','Vincent1','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070707',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(3,'java2@diginamic.fr','Vincent2','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070707',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(4,'dotnet1@diginamic.fr','Vincent3','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070707',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(5,'dotnet2@diginamic.fr','Vincent4','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070707',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(6,'python1@diginamic.fr','Vincent5','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(7,'python2@diginamic.fr','Vincent6','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(8,'data1@diginamic.fr','Vincent7','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(9,'data2@diginamic.fr','Vincent8','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(10,'php1@diginamic.fr','Vincent9','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(11,'php2@diginamic.fr','Vincent10','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(12,'web1@diginamic.fr','Vincent11','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(13,'web2@diginamic.fr','Vincent12','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(14,'htmx1@diginamic.fr','Vincent13','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(15,'htmx2@diginamic.fr','Vincent14','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Vincent','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(16,'abel@diginamic.fr','Ciccoli','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Abel','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(17,'lucas@diginamic.fr','Preaux','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Lucas','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(18,'emmanuel@diginamic.fr','Cottarel','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Emmanuel','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(19,'robin@diginamic.fr','Hotton','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Robin','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(20,'valentin@diginamic.fr','Momin','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Valentin','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(21,'herve@diginamic.fr','Crevan','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Hervé','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(22,'yvan@diginamic.fr','Douënel','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Yvan','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(23,'nicolas@diginamic.fr','Soulay','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Nicolas','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL),(24,'sebastien@diginamic.fr','Thomas','$2a$10$YvK6Uc3SbqWKwevaPCRuHOpARtJv9yvzZjHf9euMU5tDWInIAYpSi','Sébastien','0707070706',NULL,'2024-02-13',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `UTILISATEUR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blacklist`
--

DROP TABLE IF EXISTS `blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blacklist` (
  `utilisateur_id` bigint(20) NOT NULL,
  `salon_id` bigint(20) NOT NULL,
  KEY `FK30k32u352ke79p1mrdvyj3wje` (`salon_id`),
  KEY `FKs8ovdvbebrxhgh3w4isly7e9y` (`utilisateur_id`),
  CONSTRAINT `FK30k32u352ke79p1mrdvyj3wje` FOREIGN KEY (`salon_id`) REFERENCES `dl_salon` (`id`),
  CONSTRAINT `FKs8ovdvbebrxhgh3w4isly7e9y` FOREIGN KEY (`utilisateur_id`) REFERENCES `UTILISATEUR` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blacklist`
--

LOCK TABLES `blacklist` WRITE;
/*!40000 ALTER TABLE `blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `blacklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_chapitre`
--

DROP TABLE IF EXISTS `dl_chapitre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_chapitre` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contenu` longtext DEFAULT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  `statusChapitre` tinyint(4) NOT NULL,
  `cours_id` bigint(20) DEFAULT NULL,
  `ordre` int(11) DEFAULT NULL,
  `lienVideo` varchar(1024) DEFAULT NULL,
  `contenuNonPublie` longtext DEFAULT NULL,
  `statusPublication` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqv4923y4wxkbcj9esuernp4l8` (`cours_id`),
  CONSTRAINT `FKqv4923y4wxkbcj9esuernp4l8` FOREIGN KEY (`cours_id`) REFERENCES `dl_cours` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_chapitre`
--

LOCK TABLES `dl_chapitre` WRITE;
/*!40000 ALTER TABLE `dl_chapitre` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_chapitre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_conversation`
--

DROP TABLE IF EXISTS `dl_conversation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_conversation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `libelleGroupe` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_conversation`
--

LOCK TABLES `dl_conversation` WRITE;
/*!40000 ALTER TABLE `dl_conversation` DISABLE KEYS */;
INSERT INTO `dl_conversation` VALUES (1,NULL),(2,NULL),(3,NULL),(4,NULL),(5,NULL),(6,NULL),(7,NULL),(8,NULL);
/*!40000 ALTER TABLE `dl_conversation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_cours`
--

DROP TABLE IF EXISTS `dl_cours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_cours` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sous_module_id` bigint(20) DEFAULT NULL,
  `titre` varchar(255) NOT NULL,
  `difficulte` int(11) DEFAULT NULL,
  `ordre` int(11) DEFAULT NULL,
  `description` mediumtext DEFAULT NULL,
  `dureeEstimee` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKobqm6x4582q04wnakti9hy7ml` (`sous_module_id`),
  CONSTRAINT `FKobqm6x4582q04wnakti9hy7ml` FOREIGN KEY (`sous_module_id`) REFERENCES `dl_sous_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_cours`
--

LOCK TABLES `dl_cours` WRITE;
/*!40000 ALTER TABLE `dl_cours` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_cours` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_cours_session`
--

DROP TABLE IF EXISTS `dl_cours_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_cours_session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `datePrevue` datetime(6) DEFAULT NULL,
  `cours_id` bigint(20) DEFAULT NULL,
  `session_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK58lq65ojqjajkbqdv8fe4qibs` (`cours_id`),
  KEY `FKjdqptvgg30q9msn4ouki0js5p` (`session_id`),
  CONSTRAINT `FK58lq65ojqjajkbqdv8fe4qibs` FOREIGN KEY (`cours_id`) REFERENCES `dl_cours` (`id`),
  CONSTRAINT `FKjdqptvgg30q9msn4ouki0js5p` FOREIGN KEY (`session_id`) REFERENCES `SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_cours_session`
--

LOCK TABLES `dl_cours_session` WRITE;
/*!40000 ALTER TABLE `dl_cours_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_cours_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_detail_cours`
--

DROP TABLE IF EXISTS `dl_detail_cours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_detail_cours` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) DEFAULT NULL,
  `chapitre_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1v4vqnejnh1ylppkk4iyvh8sq` (`chapitre_id`),
  CONSTRAINT `FK1v4vqnejnh1ylppkk4iyvh8sq` FOREIGN KEY (`chapitre_id`) REFERENCES `dl_chapitre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_detail_cours`
--

LOCK TABLES `dl_detail_cours` WRITE;
/*!40000 ALTER TABLE `dl_detail_cours` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_detail_cours` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_fil_discussion`
--

DROP TABLE IF EXISTS `dl_fil_discussion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_fil_discussion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `titre` varchar(255) DEFAULT NULL,
  `salon_id` bigint(20) DEFAULT NULL,
  `ferme` tinyint(1) DEFAULT NULL,
  `supprime` tinyint(1) NOT NULL,
  `utilisateur_id` bigint(20) NOT NULL,
  `epingle` tinyint(1) NOT NULL,
  `dateCreation` datetime NOT NULL,
  `description` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcau15ftfywm879pkcvlg7bt5s` (`salon_id`),
  KEY `FKi2cuhkgsdgnpf2of88u972tyk` (`utilisateur_id`),
  CONSTRAINT `FKcau15ftfywm879pkcvlg7bt5s` FOREIGN KEY (`salon_id`) REFERENCES `dl_salon` (`id`),
  CONSTRAINT `FKi2cuhkgsdgnpf2of88u972tyk` FOREIGN KEY (`utilisateur_id`) REFERENCES `UTILISATEUR` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_fil_discussion`
--

LOCK TABLES `dl_fil_discussion` WRITE;
/*!40000 ALTER TABLE `dl_fil_discussion` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_fil_discussion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_flag_cours`
--

DROP TABLE IF EXISTS `dl_flag_cours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_flag_cours` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `boomarked` bit(1) DEFAULT NULL,
  `finished` bit(1) DEFAULT NULL,
  `liked` bit(1) DEFAULT NULL,
  `cours_id` bigint(20) DEFAULT NULL,
  `stagiaire_id` bigint(20) DEFAULT NULL,
  `datePrevue` datetime DEFAULT NULL,
  `mandatory` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6e19t2sf0xefd6lfhm4564e4p` (`stagiaire_id`),
  KEY `FK8du4e1bgysgsilwptkafua0b5` (`cours_id`),
  CONSTRAINT `FK6e19t2sf0xefd6lfhm4564e4p` FOREIGN KEY (`stagiaire_id`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FK8du4e1bgysgsilwptkafua0b5` FOREIGN KEY (`cours_id`) REFERENCES `dl_cours` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_flag_cours`
--

LOCK TABLES `dl_flag_cours` WRITE;
/*!40000 ALTER TABLE `dl_flag_cours` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_flag_cours` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_message`
--

DROP TABLE IF EXISTS `dl_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contenu` varchar(255) DEFAULT NULL,
  `conversation_id` bigint(20) DEFAULT NULL,
  `emetteur_id` bigint(20) DEFAULT NULL,
  `dateEmission` datetime DEFAULT NULL,
  `next_id` bigint(20) DEFAULT NULL,
  `previous_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4ls1rox5e57wivaem2drswy59` (`next_id`),
  UNIQUE KEY `UK_pub3mtlofrvy27c3x5xqwcwws` (`previous_id`),
  KEY `FK22nbs8gg0kiw0crn4y66avop1` (`conversation_id`),
  KEY `FKgwrkoy6a4tlf6c9yvtxhcbgk` (`emetteur_id`),
  CONSTRAINT `FK22nbs8gg0kiw0crn4y66avop1` FOREIGN KEY (`conversation_id`) REFERENCES `dl_conversation` (`id`),
  CONSTRAINT `FKgwrkoy6a4tlf6c9yvtxhcbgk` FOREIGN KEY (`emetteur_id`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FKh26t60c07vc8plu5b9jf0qhvo` FOREIGN KEY (`previous_id`) REFERENCES `dl_message` (`id`),
  CONSTRAINT `FKnbuyn9bigrthhabsov5nkpciq` FOREIGN KEY (`emetteur_id`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FKqwxcdmqpv6umffvcdl771jert` FOREIGN KEY (`next_id`) REFERENCES `dl_message` (`id`),
  CONSTRAINT `dl_message_ibfk_1` FOREIGN KEY (`next_id`) REFERENCES `dl_message` (`id`),
  CONSTRAINT `dl_message_ibfk_2` FOREIGN KEY (`previous_id`) REFERENCES `dl_message` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_message`
--

LOCK TABLES `dl_message` WRITE;
/*!40000 ALTER TABLE `dl_message` DISABLE KEYS */;
INSERT INTO `dl_message` VALUES (1,'bonjour',1,2,'2024-02-13 12:39:25',NULL,NULL);
/*!40000 ALTER TABLE `dl_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_module`
--

DROP TABLE IF EXISTS `dl_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `libelle` (`libelle`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_module`
--

LOCK TABLES `dl_module` WRITE;
/*!40000 ALTER TABLE `dl_module` DISABLE KEYS */;
INSERT INTO `dl_module` VALUES (1,'Développement Backend Java - Spring boot','java-backend_1.png');
/*!40000 ALTER TABLE `dl_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_module_formation`
--

DROP TABLE IF EXISTS `dl_module_formation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_module_formation` (
  `id_module` bigint(20) NOT NULL,
  `id_formation` bigint(20) NOT NULL,
  KEY `FK1fm74nw6wf5e2kqdkwbwgbnch` (`id_formation`),
  KEY `FK7snjvvky9uljwv1ja952sgiwy` (`id_module`),
  CONSTRAINT `FK1fm74nw6wf5e2kqdkwbwgbnch` FOREIGN KEY (`id_formation`) REFERENCES `FORMATION` (`ID`),
  CONSTRAINT `FK7snjvvky9uljwv1ja952sgiwy` FOREIGN KEY (`id_module`) REFERENCES `dl_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_module_formation`
--

LOCK TABLES `dl_module_formation` WRITE;
/*!40000 ALTER TABLE `dl_module_formation` DISABLE KEYS */;
INSERT INTO `dl_module_formation` VALUES (1,501);
/*!40000 ALTER TABLE `dl_module_formation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_module_smodule`
--

DROP TABLE IF EXISTS `dl_module_smodule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_module_smodule` (
  `id_smodule` bigint(20) NOT NULL,
  `id_module` bigint(20) NOT NULL,
  KEY `FKo4s0rbwin71gkdqcu30kcofm2` (`id_module`),
  KEY `FKqjbjngdft52b5hmr473q3dgj2` (`id_smodule`),
  CONSTRAINT `FKo4s0rbwin71gkdqcu30kcofm2` FOREIGN KEY (`id_module`) REFERENCES `dl_module` (`id`),
  CONSTRAINT `FKqjbjngdft52b5hmr473q3dgj2` FOREIGN KEY (`id_smodule`) REFERENCES `dl_sous_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_module_smodule`
--

LOCK TABLES `dl_module_smodule` WRITE;
/*!40000 ALTER TABLE `dl_module_smodule` DISABLE KEYS */;
INSERT INTO `dl_module_smodule` VALUES (1,1);
/*!40000 ALTER TABLE `dl_module_smodule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_post`
--

DROP TABLE IF EXISTS `dl_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contenu` varchar(255) DEFAULT NULL,
  `dateEmission` datetime(6) DEFAULT NULL,
  `emetteur_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKimeqxtfmyxctslynfourp3x7r` (`emetteur_id`),
  CONSTRAINT `FKimeqxtfmyxctslynfourp3x7r` FOREIGN KEY (`emetteur_id`) REFERENCES `UTILISATEUR` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_post`
--

LOCK TABLES `dl_post` WRITE;
/*!40000 ALTER TABLE `dl_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_post_forum`
--

DROP TABLE IF EXISTS `dl_post_forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_post_forum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auteur_id` bigint(20) DEFAULT NULL,
  `contenu` varchar(2048) DEFAULT NULL,
  `dateEmission` datetime(6) DEFAULT NULL,
  `fil_discussion_id` bigint(20) DEFAULT NULL,
  `reponseA_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK23xnww5xp6nveemdye1njiacd` (`reponseA_id`),
  KEY `FK9cxivt7g88aot86n077xrqb4t` (`auteur_id`),
  KEY `FKtdccrmuv21n8iqf84rw4bqwxw` (`fil_discussion_id`),
  CONSTRAINT `FK23xnww5xp6nveemdye1njiacd` FOREIGN KEY (`reponseA_id`) REFERENCES `dl_post_forum` (`id`),
  CONSTRAINT `FK9cxivt7g88aot86n077xrqb4t` FOREIGN KEY (`auteur_id`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FKtdccrmuv21n8iqf84rw4bqwxw` FOREIGN KEY (`fil_discussion_id`) REFERENCES `dl_fil_discussion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_post_forum`
--

LOCK TABLES `dl_post_forum` WRITE;
/*!40000 ALTER TABLE `dl_post_forum` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_post_forum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_post_session`
--

DROP TABLE IF EXISTS `dl_post_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_post_session` (
  `session_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL,
  KEY `FKa76gtv4dw1o7wunx0o9cdsky6` (`post_id`),
  KEY `FKs3k65ftt0g0u0oi30d81nu9kt` (`session_id`),
  CONSTRAINT `FKa76gtv4dw1o7wunx0o9cdsky6` FOREIGN KEY (`post_id`) REFERENCES `dl_post` (`id`),
  CONSTRAINT `FKs3k65ftt0g0u0oi30d81nu9kt` FOREIGN KEY (`session_id`) REFERENCES `SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_post_session`
--

LOCK TABLES `dl_post_session` WRITE;
/*!40000 ALTER TABLE `dl_post_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_post_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_publication_question`
--

DROP TABLE IF EXISTS `dl_publication_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_publication_question` (
  `question_id` bigint(20) NOT NULL,
  `publication_id` bigint(20) NOT NULL,
  KEY `FKkokfe8p8u7trohw4j2s04o5ll` (`publication_id`),
  KEY `FKqgu3ip22puemmedgwq3pf5yt` (`question_id`),
  CONSTRAINT `FKb3u7leveoh0lh2nn8iv01lejh` FOREIGN KEY (`publication_id`) REFERENCES `dl_qcm_publication` (`id`),
  CONSTRAINT `FKkokfe8p8u7trohw4j2s04o5ll` FOREIGN KEY (`publication_id`) REFERENCES `dl_qcm_publication` (`id`),
  CONSTRAINT `FKmibwf5gfo20orrphnp48r4h5b` FOREIGN KEY (`question_id`) REFERENCES `dl_qcm_question` (`id`),
  CONSTRAINT `FKqgu3ip22puemmedgwq3pf5yt` FOREIGN KEY (`question_id`) REFERENCES `dl_qcm_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_publication_question`
--

LOCK TABLES `dl_publication_question` WRITE;
/*!40000 ALTER TABLE `dl_publication_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_publication_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_qcm_choix`
--

DROP TABLE IF EXISTS `dl_qcm_choix`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_qcm_choix` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) DEFAULT NULL,
  `valid` bit(1) DEFAULT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl2mb27x6yy3tvcu91oow3r4fb` (`question_id`),
  CONSTRAINT `FKl2mb27x6yy3tvcu91oow3r4fb` FOREIGN KEY (`question_id`) REFERENCES `dl_qcm_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_qcm_choix`
--

LOCK TABLES `dl_qcm_choix` WRITE;
/*!40000 ALTER TABLE `dl_qcm_choix` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_qcm_choix` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_qcm_passe`
--

DROP TABLE IF EXISTS `dl_qcm_passe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_qcm_passe` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `qcm_id` bigint(20) DEFAULT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  `archived` tinyint(1) NOT NULL,
  `datePassage` datetime NOT NULL,
  `id_publication` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjoog9b2jn9j0oydw2gfgdmd1m` (`qcm_id`),
  KEY `FKnga6an8bb45qjiu9nu2g3prii` (`utilisateur_id`),
  KEY `FKpwoo5jqpp51f3gjrh15nrp66t` (`id_publication`),
  CONSTRAINT `FKjoog9b2jn9j0oydw2gfgdmd1m` FOREIGN KEY (`qcm_id`) REFERENCES `dl_chapitre` (`id`),
  CONSTRAINT `FKnga6an8bb45qjiu9nu2g3prii` FOREIGN KEY (`utilisateur_id`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FKpwoo5jqpp51f3gjrh15nrp66t` FOREIGN KEY (`id_publication`) REFERENCES `dl_qcm_publication` (`id`),
  CONSTRAINT `fk_qcm_publication` FOREIGN KEY (`id_publication`) REFERENCES `dl_qcm_publication` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_qcm_passe`
--

LOCK TABLES `dl_qcm_passe` WRITE;
/*!40000 ALTER TABLE `dl_qcm_passe` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_qcm_passe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_qcm_publication`
--

DROP TABLE IF EXISTS `dl_qcm_publication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_qcm_publication` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `derniere` bit(1) DEFAULT NULL,
  `qcm_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo3991kji7vd48d22mehx5if3j` (`qcm_id`),
  CONSTRAINT `FKo3991kji7vd48d22mehx5if3j` FOREIGN KEY (`qcm_id`) REFERENCES `dl_chapitre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_qcm_publication`
--

LOCK TABLES `dl_qcm_publication` WRITE;
/*!40000 ALTER TABLE `dl_qcm_publication` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_qcm_publication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_qcm_question`
--

DROP TABLE IF EXISTS `dl_qcm_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_qcm_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) DEFAULT NULL,
  `qcm_id` bigint(20) DEFAULT NULL,
  `ordre` int(11) NOT NULL,
  `qcm_publie_id` bigint(20) DEFAULT NULL,
  `illustration` varchar(255) DEFAULT NULL,
  `commentaire` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9tc6v2535iceav5g8leo07m1m` (`qcm_id`),
  KEY `FKeuak6op8a47c2ok6e3ue1ugjx` (`qcm_publie_id`),
  CONSTRAINT `FK9tc6v2535iceav5g8leo07m1m` FOREIGN KEY (`qcm_id`) REFERENCES `dl_chapitre` (`id`),
  CONSTRAINT `FKeuak6op8a47c2ok6e3ue1ugjx` FOREIGN KEY (`qcm_publie_id`) REFERENCES `dl_chapitre` (`id`),
  CONSTRAINT `dl_qcm_question_dl_chapitre_id_fk` FOREIGN KEY (`qcm_publie_id`) REFERENCES `dl_chapitre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_qcm_question`
--

LOCK TABLES `dl_qcm_question` WRITE;
/*!40000 ALTER TABLE `dl_qcm_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_qcm_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_qcm_question_choix`
--

DROP TABLE IF EXISTS `dl_qcm_question_choix`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_qcm_question_choix` (
  `QCMQuestion_id` bigint(20) NOT NULL,
  `choix_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_gwo2bfodt47h6ab3l6tmfin5l` (`choix_id`),
  KEY `FK2jv55f7xd4pr3asslgpe2xuhw` (`QCMQuestion_id`),
  CONSTRAINT `FK2jv55f7xd4pr3asslgpe2xuhw` FOREIGN KEY (`QCMQuestion_id`) REFERENCES `dl_qcm_question` (`id`),
  CONSTRAINT `FKna7kifi65ovq8tqe6mmcr5yvl` FOREIGN KEY (`choix_id`) REFERENCES `dl_qcm_choix` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_qcm_question_choix`
--

LOCK TABLES `dl_qcm_question_choix` WRITE;
/*!40000 ALTER TABLE `dl_qcm_question_choix` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_qcm_question_choix` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_question`
--

DROP TABLE IF EXISTS `dl_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contenu` varchar(255) NOT NULL,
  `supprimee` bit(1) NOT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  `chapitre_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjq1l51wf4nshwwheglxjeqcdy` (`chapitre_id`),
  KEY `FKkxbjlay565h1lysk0sh0e4wbo` (`utilisateur_id`),
  CONSTRAINT `FKjq1l51wf4nshwwheglxjeqcdy` FOREIGN KEY (`chapitre_id`) REFERENCES `dl_chapitre` (`id`),
  CONSTRAINT `FKkxbjlay565h1lysk0sh0e4wbo` FOREIGN KEY (`utilisateur_id`) REFERENCES `UTILISATEUR` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_question`
--

LOCK TABLES `dl_question` WRITE;
/*!40000 ALTER TABLE `dl_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_relation_question`
--

DROP TABLE IF EXISTS `dl_relation_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_relation_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disliked` bit(1) DEFAULT NULL,
  `liked` bit(1) DEFAULT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK963dsd05d9oe5y1f92kc7ds4h` (`utilisateur_id`),
  KEY `FKnb3swcm9yediexax82erkrx0s` (`question_id`),
  CONSTRAINT `FK963dsd05d9oe5y1f92kc7ds4h` FOREIGN KEY (`utilisateur_id`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FKnb3swcm9yediexax82erkrx0s` FOREIGN KEY (`question_id`) REFERENCES `dl_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_relation_question`
--

LOCK TABLES `dl_relation_question` WRITE;
/*!40000 ALTER TABLE `dl_relation_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_relation_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_relation_reponse`
--

DROP TABLE IF EXISTS `dl_relation_reponse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_relation_reponse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `disliked` bit(1) DEFAULT NULL,
  `liked` bit(1) DEFAULT NULL,
  `reponse_id` bigint(20) DEFAULT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6ag1pfv55ifvqeeu6k3ubfodi` (`reponse_id`),
  KEY `FK8lvbsitmq1wvbcfgvusfi4uy1` (`utilisateur_id`),
  CONSTRAINT `FK6ag1pfv55ifvqeeu6k3ubfodi` FOREIGN KEY (`reponse_id`) REFERENCES `dl_reponse` (`id`),
  CONSTRAINT `FK8lvbsitmq1wvbcfgvusfi4uy1` FOREIGN KEY (`utilisateur_id`) REFERENCES `UTILISATEUR` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_relation_reponse`
--

LOCK TABLES `dl_relation_reponse` WRITE;
/*!40000 ALTER TABLE `dl_relation_reponse` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_relation_reponse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_reponse`
--

DROP TABLE IF EXISTS `dl_reponse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_reponse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contenu` varchar(1024) NOT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  `supprimee` bit(1) NOT NULL,
  `auteur_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpo05hpshnfd2j2aal883u9d2c` (`question_id`),
  KEY `FKf3fsvktg4chbdhhygoy7uv38p` (`auteur_id`),
  CONSTRAINT `FKf3fsvktg4chbdhhygoy7uv38p` FOREIGN KEY (`auteur_id`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FKpo05hpshnfd2j2aal883u9d2c` FOREIGN KEY (`question_id`) REFERENCES `dl_question` (`id`),
  CONSTRAINT `dl_reponse_UTILISATEUR__fk` FOREIGN KEY (`auteur_id`) REFERENCES `UTILISATEUR` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_reponse`
--

LOCK TABLES `dl_reponse` WRITE;
/*!40000 ALTER TABLE `dl_reponse` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_reponse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_resultat_choix`
--

DROP TABLE IF EXISTS `dl_resultat_choix`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_resultat_choix` (
  `resultat_id` bigint(20) NOT NULL,
  `choix_id` bigint(20) NOT NULL,
  KEY `FK2h28var9qy1lbqv30crmeu64p` (`resultat_id`),
  KEY `FKk7vwofy4r02udmqv9xyq12iao` (`choix_id`),
  CONSTRAINT `FK2h28var9qy1lbqv30crmeu64p` FOREIGN KEY (`resultat_id`) REFERENCES `dl_resultat_question` (`id`),
  CONSTRAINT `FKk7vwofy4r02udmqv9xyq12iao` FOREIGN KEY (`choix_id`) REFERENCES `dl_qcm_choix` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_resultat_choix`
--

LOCK TABLES `dl_resultat_choix` WRITE;
/*!40000 ALTER TABLE `dl_resultat_choix` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_resultat_choix` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_resultat_question`
--

DROP TABLE IF EXISTS `dl_resultat_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_resultat_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `qcmPasse_id` bigint(20) DEFAULT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgkviaxonlo2r5j3jvurp5woyw` (`qcmPasse_id`),
  KEY `FKgjennmtjtkcqvmq7re9b5w1jv` (`question_id`),
  CONSTRAINT `FKgjennmtjtkcqvmq7re9b5w1jv` FOREIGN KEY (`question_id`) REFERENCES `dl_qcm_question` (`id`),
  CONSTRAINT `FKgkviaxonlo2r5j3jvurp5woyw` FOREIGN KEY (`qcmPasse_id`) REFERENCES `dl_qcm_passe` (`id`),
  CONSTRAINT `dl_resultat_question_dl_qcm_question_id_fk` FOREIGN KEY (`question_id`) REFERENCES `dl_qcm_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_resultat_question`
--

LOCK TABLES `dl_resultat_question` WRITE;
/*!40000 ALTER TABLE `dl_resultat_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_resultat_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_salon`
--

DROP TABLE IF EXISTS `dl_salon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_salon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `statusForum` tinyint(4) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `sujet_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl5rv9oj3fcvx35dj8wk3x9n2s` (`sujet_id`),
  CONSTRAINT `FKl5rv9oj3fcvx35dj8wk3x9n2s` FOREIGN KEY (`sujet_id`) REFERENCES `dl_sujet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_salon`
--

LOCK TABLES `dl_salon` WRITE;
/*!40000 ALTER TABLE `dl_salon` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_salon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_salon_session`
--

DROP TABLE IF EXISTS `dl_salon_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_salon_session` (
  `session_id` bigint(20) NOT NULL,
  `salon_id` bigint(20) NOT NULL,
  KEY `FKomq1hf63wro4os29uw44dvtp1` (`salon_id`),
  KEY `FKqro5omtaruckcb4ybhkh0w12f` (`session_id`),
  CONSTRAINT `FKomq1hf63wro4os29uw44dvtp1` FOREIGN KEY (`salon_id`) REFERENCES `dl_salon` (`id`),
  CONSTRAINT `FKqro5omtaruckcb4ybhkh0w12f` FOREIGN KEY (`session_id`) REFERENCES `SESSION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_salon_session`
--

LOCK TABLES `dl_salon_session` WRITE;
/*!40000 ALTER TABLE `dl_salon_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_salon_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_sous_module`
--

DROP TABLE IF EXISTS `dl_sous_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_sous_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `titre` varchar(50) NOT NULL,
  `photo` varchar(50) DEFAULT NULL,
  `ordre` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_sous_module`
--

LOCK TABLES `dl_sous_module` WRITE;
/*!40000 ALTER TABLE `dl_sous_module` DISABLE KEYS */;
INSERT INTO `dl_sous_module` VALUES (1,'Java impératif',NULL,NULL);
/*!40000 ALTER TABLE `dl_sous_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_sujet`
--

DROP TABLE IF EXISTS `dl_sujet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_sujet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_sujet`
--

LOCK TABLES `dl_sujet` WRITE;
/*!40000 ALTER TABLE `dl_sujet` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_sujet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_travaux_pratique`
--

DROP TABLE IF EXISTS `dl_travaux_pratique`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_travaux_pratique` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correction` longtext DEFAULT NULL,
  `instructions` longtext DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_travaux_pratique`
--

LOCK TABLES `dl_travaux_pratique` WRITE;
/*!40000 ALTER TABLE `dl_travaux_pratique` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_travaux_pratique` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_utilisateur_conversation`
--

DROP TABLE IF EXISTS `dl_utilisateur_conversation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_utilisateur_conversation` (
  `utilisateur_id` bigint(20) NOT NULL,
  `conversation_id` bigint(20) NOT NULL,
  KEY `FK6kvr5j6sm3q64fu9notdx49li` (`conversation_id`),
  KEY `FKjkln4noag4pakv4f2f0oyy0sw` (`utilisateur_id`),
  CONSTRAINT `FK6kvr5j6sm3q64fu9notdx49li` FOREIGN KEY (`conversation_id`) REFERENCES `dl_conversation` (`id`),
  CONSTRAINT `FKjkln4noag4pakv4f2f0oyy0sw` FOREIGN KEY (`utilisateur_id`) REFERENCES `UTILISATEUR` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_utilisateur_conversation`
--

LOCK TABLES `dl_utilisateur_conversation` WRITE;
/*!40000 ALTER TABLE `dl_utilisateur_conversation` DISABLE KEYS */;
INSERT INTO `dl_utilisateur_conversation` VALUES (2,1),(1,1),(1,2),(3,2),(1,3),(5,3),(1,4),(4,4),(1,5),(6,5),(1,6),(7,6),(1,7),(8,7),(1,8),(9,8);
/*!40000 ALTER TABLE `dl_utilisateur_conversation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_utilisateur_cours`
--

DROP TABLE IF EXISTS `dl_utilisateur_cours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_utilisateur_cours` (
  `id_utilisateur` bigint(20) NOT NULL,
  `id_cours` bigint(20) NOT NULL,
  KEY `FK3s16tpc2cjf85lemgxmnm192h` (`id_utilisateur`),
  KEY `FKgy4sqe8wdrbwoqyirc0ne6trl` (`id_cours`),
  CONSTRAINT `FK3s16tpc2cjf85lemgxmnm192h` FOREIGN KEY (`id_utilisateur`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FKgy4sqe8wdrbwoqyirc0ne6trl` FOREIGN KEY (`id_cours`) REFERENCES `dl_cours` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_utilisateur_cours`
--

LOCK TABLES `dl_utilisateur_cours` WRITE;
/*!40000 ALTER TABLE `dl_utilisateur_cours` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_utilisateur_cours` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dl_utilisateur_session`
--

DROP TABLE IF EXISTS `dl_utilisateur_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dl_utilisateur_session` (
  `utilisateur_id` bigint(20) NOT NULL,
  `session_id` bigint(20) NOT NULL,
  KEY `FK9vfa9pex4mr11tlbohsulvpfr` (`session_id`),
  KEY `FKdnctllhhkk622eplncrn0phdw` (`utilisateur_id`),
  CONSTRAINT `FK9vfa9pex4mr11tlbohsulvpfr` FOREIGN KEY (`session_id`) REFERENCES `SESSION` (`ID`),
  CONSTRAINT `FKdnctllhhkk622eplncrn0phdw` FOREIGN KEY (`utilisateur_id`) REFERENCES `UTILISATEUR` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dl_utilisateur_session`
--

LOCK TABLES `dl_utilisateur_session` WRITE;
/*!40000 ALTER TABLE `dl_utilisateur_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `dl_utilisateur_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salon_moderateurs`
--

DROP TABLE IF EXISTS `salon_moderateurs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salon_moderateurs` (
  `moderateur_id` bigint(20) NOT NULL,
  `salon_id` bigint(20) NOT NULL,
  KEY `FK69ewod7rsilq7l3i2eyswuxh7` (`salon_id`),
  KEY `FK6qg3chjwchaj06cwab4g3j2a3` (`moderateur_id`),
  CONSTRAINT `FK69ewod7rsilq7l3i2eyswuxh7` FOREIGN KEY (`salon_id`) REFERENCES `dl_salon` (`id`),
  CONSTRAINT `FK6qg3chjwchaj06cwab4g3j2a3` FOREIGN KEY (`moderateur_id`) REFERENCES `UTILISATEUR` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salon_moderateurs`
--

LOCK TABLES `salon_moderateurs` WRITE;
/*!40000 ALTER TABLE `salon_moderateurs` DISABLE KEYS */;
/*!40000 ALTER TABLE `salon_moderateurs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `whitelist`
--

DROP TABLE IF EXISTS `whitelist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `whitelist` (
  `utilisateur_id` bigint(20) NOT NULL,
  `salon_id` bigint(20) NOT NULL,
  KEY `FK1jnrnh5pst18vw23xqj1sk13j` (`utilisateur_id`),
  KEY `FKdf2x3s0i3iedvuf53d51394le` (`salon_id`),
  CONSTRAINT `FK1jnrnh5pst18vw23xqj1sk13j` FOREIGN KEY (`utilisateur_id`) REFERENCES `UTILISATEUR` (`ID`),
  CONSTRAINT `FKdf2x3s0i3iedvuf53d51394le` FOREIGN KEY (`salon_id`) REFERENCES `dl_salon` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `whitelist`
--

LOCK TABLES `whitelist` WRITE;
/*!40000 ALTER TABLE `whitelist` DISABLE KEYS */;
/*!40000 ALTER TABLE `whitelist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-13 14:30:05
