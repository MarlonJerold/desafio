-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: fnrh
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.25-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `changelog_fnrhadm`
--

DROP TABLE IF EXISTS `changelog_fnrhadm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `changelog_fnrhadm` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `changelog_fnrhadm`
--

LOCK TABLES `changelog_fnrhadm` WRITE;
/*!40000 ALTER TABLE `changelog_fnrhadm` DISABLE KEYS */;
/*!40000 ALTER TABLE `changelog_fnrhadm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `changelog_fnrhadmlock`
--

DROP TABLE IF EXISTS `changelog_fnrhadmlock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `changelog_fnrhadmlock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `changelog_fnrhadmlock`
--

LOCK TABLES `changelog_fnrhadmlock` WRITE;
/*!40000 ALTER TABLE `changelog_fnrhadmlock` DISABLE KEYS */;
INSERT INTO `changelog_fnrhadmlock` VALUES (1,_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `changelog_fnrhadmlock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fnrh`
--

DROP TABLE IF EXISTS `fnrh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fnrh` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reserva_id` int(11) NOT NULL,
  `principal` varchar(1) NOT NULL,
  `nome` varchar(60) NOT NULL,
  `sobrenome` varchar(60) NOT NULL,
  `nascimento` date DEFAULT NULL,
  `email` varchar(250) DEFAULT NULL,
  `ddi_telefone` varchar(4) DEFAULT NULL,
  `ddd_telefone` varchar(5) DEFAULT NULL,
  `nr_telefone` varchar(20) DEFAULT NULL,
  `ddi_celular` varchar(4) DEFAULT NULL,
  `ddd_celular` varchar(5) DEFAULT NULL,
  `nr_celular` varchar(20) DEFAULT NULL,
  `profissao` varchar(100) DEFAULT NULL,
  `cd_pais_nacionalidade` varchar(2) DEFAULT NULL,
  `genero_id` int(11) DEFAULT NULL,
  `tipodocumento_id` int(11) DEFAULT NULL,
  `numero_documento` varchar(30) DEFAULT NULL,
  `orgao_documento` varchar(20) DEFAULT NULL,
  `logradouro_res` varchar(137) DEFAULT NULL,
  `numero_res` varchar(10) DEFAULT NULL,
  `complemento_res` varchar(100) DEFAULT NULL,
  `cd_pais_res` varchar(3) DEFAULT NULL,
  `cd_uf_res` varchar(3) DEFAULT NULL,
  `cd_cidade_res` varchar(7) DEFAULT NULL COMMENT 'código da cidade no IBGE',
  `nm_cidade_res` varchar(100) DEFAULT NULL,
  `cep_res` varchar(8) DEFAULT NULL,
  `cd_pais_de` varchar(3) DEFAULT NULL,
  `cd_uf_de` varchar(3) DEFAULT NULL,
  `cd_cidade_de` int(11) DEFAULT NULL,
  `nm_cidade_de` varchar(100) DEFAULT NULL,
  `cd_pais_para` varchar(3) DEFAULT NULL,
  `cd_uf_para` varchar(3) DEFAULT NULL,
  `cd_cidade_para` varchar(7) DEFAULT NULL,
  `nm_cidade_para` varchar(100) DEFAULT NULL,
  `motivo_id` int(11) DEFAULT NULL,
  `meio_id` int(11) DEFAULT NULL,
  `observacao` varchar(2000) DEFAULT NULL,
  `dt_insert` datetime NOT NULL,
  `dt_update` datetime NOT NULL,
  `id_contahotel` int(11) NOT NULL,
  `id_hospede` int(11) NOT NULL,
  `nm_uf_de` varchar(100) DEFAULT NULL,
  `nm_uf_para` varchar(100) DEFAULT NULL,
  `nm_uf_res` varchar(100) DEFAULT NULL,
  `preenchimento_completo` int(1) NOT NULL DEFAULT 0,
  `bairro_res` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_fnrh_reserva1_idx` (`reserva_id`),
  KEY `fk_fnrh_genero1_idx` (`genero_id`),
  KEY `fk_fnrh_tipodocumento1_idx` (`tipodocumento_id`),
  KEY `fk_fnrh_motivo1_idx` (`motivo_id`),
  KEY `fk_fnrh_meio1_idx` (`meio_id`),
  KEY `FK3008FEE4FEEBE7` (`tipodocumento_id`),
  KEY `FK3008FEB7DDB46E` (`motivo_id`),
  KEY `FK3008FE4D7A52CD` (`genero_id`),
  KEY `FK3008FEF0227549` (`meio_id`),
  CONSTRAINT `FK3008FE4D7A52CD` FOREIGN KEY (`genero_id`) REFERENCES `genero` (`id`),
  CONSTRAINT `FK3008FEB7DDB46E` FOREIGN KEY (`motivo_id`) REFERENCES `motivo` (`id`),
  CONSTRAINT `FK3008FEE4FEEBE7` FOREIGN KEY (`tipodocumento_id`) REFERENCES `tipodocumento` (`id`),
  CONSTRAINT `FK3008FEF0227549` FOREIGN KEY (`meio_id`) REFERENCES `meio` (`id`),
  CONSTRAINT `fk_fnrh_genero1` FOREIGN KEY (`genero_id`) REFERENCES `genero` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_fnrh_meio1` FOREIGN KEY (`meio_id`) REFERENCES `meio` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_fnrh_motivo1` FOREIGN KEY (`motivo_id`) REFERENCES `motivo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_fnrh_reserva1` FOREIGN KEY (`reserva_id`) REFERENCES `reserva` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_fnrh_tipodocumento1` FOREIGN KEY (`tipodocumento_id`) REFERENCES `tipodocumento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fnrh`
--

LOCK TABLES `fnrh` WRITE;
/*!40000 ALTER TABLE `fnrh` DISABLE KEYS */;
/*!40000 ALTER TABLE `fnrh` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_fnrh_sequence`
              BEFORE INSERT ON `fnrh` FOR EACH ROW BEGIN
                IF NEW.`id` = 0 THEN
                  UPDATE rr_sequences
                    SET current_value = LAST_INSERT_ID(current_value + increment)
                    WHERE name = 'fnrh';
                  SET NEW.`id` = LAST_INSERT_ID();
                END IF;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_fnrh_insert`
              AFTER insert ON `fnrh` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_fnrh`(concat_ws('|', 'id', NEW.`id`), null, 'I');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_fnrh_update`
              AFTER update ON `fnrh` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_fnrh`(concat_ws('|', 'id', OLD.`id`), concat_ws('|', 'id', NEW.`id`), 'U');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_fnrh_delete`
              AFTER delete ON `fnrh` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_fnrh`(concat_ws('|', 'id', OLD.`id`), null, 'D');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `fnrh_documento`
--

DROP TABLE IF EXISTS `fnrh_documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fnrh_documento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipodocumento_id` int(11) NOT NULL,
  `numero_documento` varchar(30) NOT NULL,
  `orgao_documento` varchar(20) DEFAULT NULL,
  `fnrh_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_doc_fnrh_idx` (`fnrh_id`),
  KEY `FK442DB0B3E4FEEBE7` (`tipodocumento_id`),
  KEY `FK442DB0B340CA140D` (`fnrh_id`),
  CONSTRAINT `FK442DB0B340CA140D` FOREIGN KEY (`fnrh_id`) REFERENCES `fnrh` (`id`),
  CONSTRAINT `FK442DB0B3E4FEEBE7` FOREIGN KEY (`tipodocumento_id`) REFERENCES `tipodocumento` (`id`),
  CONSTRAINT `fk_doc_fnrh` FOREIGN KEY (`fnrh_id`) REFERENCES `fnrh` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fnrh_documento`
--

LOCK TABLES `fnrh_documento` WRITE;
/*!40000 ALTER TABLE `fnrh_documento` DISABLE KEYS */;
/*!40000 ALTER TABLE `fnrh_documento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_fnrh_documento_sequence`
              BEFORE INSERT ON `fnrh_documento` FOR EACH ROW BEGIN
                IF NEW.`id` = 0 THEN
                  UPDATE rr_sequences
                    SET current_value = LAST_INSERT_ID(current_value + increment)
                    WHERE name = 'fnrh_documento';
                  SET NEW.`id` = LAST_INSERT_ID();
                END IF;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_fnrh_documento_insert`
              AFTER insert ON `fnrh_documento` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_fnrh_documento`(concat_ws('|', 'id', NEW.`id`), null, 'I');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_fnrh_documento_update`
              AFTER update ON `fnrh_documento` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_fnrh_documento`(concat_ws('|', 'id', OLD.`id`), concat_ws('|', 'id', NEW.`id`), 'U');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_fnrh_documento_delete`
              AFTER delete ON `fnrh_documento` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_fnrh_documento`(concat_ws('|', 'id', OLD.`id`), null, 'D');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `genero`
--

DROP TABLE IF EXISTS `genero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genero` (
  `id` int(11) NOT NULL,
  `tp_lang` varchar(10) NOT NULL,
  `cdgenero` varchar(2) NOT NULL,
  `nome` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_genero` (`tp_lang`,`cdgenero`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genero`
--

LOCK TABLES `genero` WRITE;
/*!40000 ALTER TABLE `genero` DISABLE KEYS */;
INSERT INTO `genero` VALUES (1,'pt','M','Masculino'),(2,'pt','F','Feminino'),(3,'en','M','Male'),(4,'en','F','Female'),(5,'es','M','Masculino'),(6,'es','F','Femenino');
/*!40000 ALTER TABLE `genero` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_genero_insert`
              AFTER insert ON `genero` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_genero`(concat_ws('|', 'id', NEW.`id`), null, 'I');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_genero_update`
              AFTER update ON `genero` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_genero`(concat_ws('|', 'id', OLD.`id`), concat_ws('|', 'id', NEW.`id`), 'U');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_genero_delete`
              AFTER delete ON `genero` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_genero`(concat_ws('|', 'id', OLD.`id`), null, 'D');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nmhotel` varchar(100) NOT NULL,
  `cdhotelerp` varchar(10) NOT NULL COMMENT 'id do hotel no sistema de origem',
  `tp_ativo` varchar(1) NOT NULL,
  `sigla` varchar(4) DEFAULT NULL,
  `nmfantasia` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel`
--

LOCK TABLES `hotel` WRITE;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
INSERT INTO `hotel` VALUES (1,'BEACH PARK HOT TUR S/A-SCP MARIUBA PARK RESORT','1','T','MA','SUÍTES'),(2,'BEACH PARK HOT TUR S/A-SCP ACQUA RESORT','123381','T','AR','ACQUA'),(3,'BEACH PARK HOT TUR S/A-SCP WELLNESS','408113','T','WE','WELLNESS'),(4,'BEACH PARK OCEANI RESORT','448696','T','OC','OCEANI');
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_hotel_sequence`
              BEFORE INSERT ON `hotel` FOR EACH ROW BEGIN
                IF NEW.`id` = 0 THEN
                  UPDATE rr_sequences
                    SET current_value = LAST_INSERT_ID(current_value + increment)
                    WHERE name = 'hotel';
                  SET NEW.`id` = LAST_INSERT_ID();
                END IF;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_hotel_insert`
              AFTER insert ON `hotel` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_hotel`(concat_ws('|', 'id', NEW.`id`), null, 'I');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_hotel_update`
              AFTER update ON `hotel` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_hotel`(concat_ws('|', 'id', OLD.`id`), concat_ws('|', 'id', NEW.`id`), 'U');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_hotel_delete`
              AFTER delete ON `hotel` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_hotel`(concat_ws('|', 'id', OLD.`id`), null, 'D');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `meio`
--

DROP TABLE IF EXISTS `meio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tp_lang` varchar(10) NOT NULL,
  `cd_meio` varchar(2) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cd_meio_erp` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_meio` (`tp_lang`,`cd_meio`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meio`
--

LOCK TABLES `meio` WRITE;
/*!40000 ALTER TABLE `meio` DISABLE KEYS */;
INSERT INTO `meio` VALUES (1,'pt','AV','Avião','A'),(2,'pt','AU','Automóvel','C'),(3,'pt','ON','Ônibus','O'),(4,'pt','MO','Motocicleta','O'),(5,'pt','NA','Navio - Barco','N'),(6,'pt','TR','Trem','O'),(7,'pt','OU','Outro','O'),(8,'en','AV','Plane','A'),(9,'en','AU','Car','C'),(10,'en','ON','Bus','O'),(11,'en','MO','Motorcycle','O'),(12,'en','NA','Ship - Ferry Boat','N'),(13,'en','TR','Train','O'),(14,'en','OU','Other','O'),(15,'es','AV','Plano','A'),(16,'es','AU','Coche','C'),(17,'es','ON','Autobús','O'),(18,'es','MO','Motocicleta','O'),(19,'es','NA','Barco','N'),(20,'es','TR','Tren','O'),(21,'es','OU','Otro','O');
/*!40000 ALTER TABLE `meio` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_meio_sequence`
              BEFORE INSERT ON `meio` FOR EACH ROW BEGIN
                IF NEW.`id` = 0 THEN
                  UPDATE rr_sequences
                    SET current_value = LAST_INSERT_ID(current_value + increment)
                    WHERE name = 'meio';
                  SET NEW.`id` = LAST_INSERT_ID();
                END IF;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_meio_insert`
              AFTER insert ON `meio` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_meio`(concat_ws('|', 'id', NEW.`id`), null, 'I');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_meio_update`
              AFTER update ON `meio` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_meio`(concat_ws('|', 'id', OLD.`id`), concat_ws('|', 'id', NEW.`id`), 'U');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_meio_delete`
              AFTER delete ON `meio` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_meio`(concat_ws('|', 'id', OLD.`id`), null, 'D');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `motivo`
--

DROP TABLE IF EXISTS `motivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `motivo` (
  `id` int(11) NOT NULL,
  `tp_lang` varchar(10) NOT NULL,
  `cd_motivo` varchar(3) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cd_motivo_erp` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_motivo` (`tp_lang`,`cd_motivo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `motivo`
--

LOCK TABLES `motivo` WRITE;
/*!40000 ALTER TABLE `motivo` DISABLE KEYS */;
INSERT INTO `motivo` VALUES (1,'pt','LAZ','Lazer - Férias','L'),(2,'pt','NEG','Negócios','N'),(3,'pt','CON','Congresso - Feira','O'),(4,'pt','PAR','Parentes - Amigos','O'),(5,'pt','EST','Estudos - Cursos','O'),(6,'pt','REL','Religião','O'),(7,'pt','SAU','Saúde','O'),(8,'pt','COM','Compras','O'),(9,'pt','OUT','Outro','O'),(10,'en','LAZ','Leisure - Vacation','L'),(11,'en','NEG','Business','N'),(12,'en','CON','Convention - Fair','O'),(13,'en','PAR','Relatives - Friends','O'),(14,'en','EST','Studies - Courses','O'),(15,'en','REL','Religion','O'),(16,'en','SAU','Health','O'),(17,'en','COM','Shopping','O'),(18,'en','OUT','Other','O'),(19,'es','LAZ','Ocio - Vacaciones','L'),(20,'es','NEG','Negocios','N'),(21,'es','CON','Congreso - Fair','O'),(22,'es','PAR','Relatives - Amigos','O'),(23,'es','REL','Religión','O'),(24,'es','SAU','Salud','O'),(25,'es','COM','Compras','O'),(26,'es','OUT','Otro','O');
/*!40000 ALTER TABLE `motivo` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_motivo_insert`
              AFTER insert ON `motivo` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_motivo`(concat_ws('|', 'id', NEW.`id`), null, 'I');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_motivo_update`
              AFTER update ON `motivo` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_motivo`(concat_ws('|', 'id', OLD.`id`), concat_ws('|', 'id', NEW.`id`), 'U');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_motivo_delete`
              AFTER delete ON `motivo` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_motivo`(concat_ws('|', 'id', OLD.`id`), null, 'D');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `reserva`
--

DROP TABLE IF EXISTS `reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserva` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) NOT NULL,
  `email` varchar(250) DEFAULT NULL,
  `uuid` varchar(45) NOT NULL COMMENT 'chave para localizar o e-mail enviado.',
  `dt_checkin_previsto` date NOT NULL,
  `dt_checkout_previsto` date NOT NULL,
  `tp_status_integra` varchar(3) NOT NULL COMMENT 'Importado [IMP];\nEmail enviado [MAI];\nErro no envio do e-mail [ERM];\nEm preenchimento [PRE];\nDigitado pelo hóspede [DIG];\nExportado [EXP];\nErro na exportação [ER',
  `dt_insert` datetime NOT NULL,
  `dt_update` datetime NOT NULL,
  `localizador` int(11) DEFAULT NULL,
  `id_reserva_erp` int(12) NOT NULL,
  `num_reserva_erp` int(12) NOT NULL,
  `origem` varchar(200) DEFAULT NULL COMMENT 'Origem da Reserva',
  `origem_cod` char(2) DEFAULT NULL COMMENT 'Código da Origem da Reserva',
  PRIMARY KEY (`id`),
  KEY `fk__hotel_idx` (`hotel_id`),
  KEY `ix__id_reserva_erp` (`id_reserva_erp`),
  KEY `FK41640CB8E082DC87` (`hotel_id`),
  CONSTRAINT `FK41640CB8E082DC87` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`),
  CONSTRAINT `fk__hotel` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserva`
--

LOCK TABLES `reserva` WRITE;
/*!40000 ALTER TABLE `reserva` DISABLE KEYS */;
/*!40000 ALTER TABLE `reserva` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_reserva_sequence`
              BEFORE INSERT ON `reserva` FOR EACH ROW BEGIN
                IF NEW.`id` = 0 THEN
                  UPDATE rr_sequences
                    SET current_value = LAST_INSERT_ID(current_value + increment)
                    WHERE name = 'reserva';
                  SET NEW.`id` = LAST_INSERT_ID();
                END IF;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_reserva_insert`
              AFTER insert ON `reserva` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_reserva`(concat_ws('|', 'id', NEW.`id`), null, 'I');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_reserva_update`
              AFTER update ON `reserva` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_reserva`(concat_ws('|', 'id', OLD.`id`), concat_ws('|', 'id', NEW.`id`), 'U');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_reserva_delete`
              AFTER delete ON `reserva` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_reserva`(concat_ws('|', 'id', OLD.`id`), null, 'D');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `rr_logged_events`
--

DROP TABLE IF EXISTS `rr_logged_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rr_logged_events` (
  `activity` varchar(255) DEFAULT NULL,
  `change_table` varchar(255) DEFAULT NULL,
  `diff_type` varchar(255) DEFAULT NULL,
  `change_key` varchar(255) DEFAULT NULL,
  `left_change_type` varchar(255) DEFAULT NULL,
  `right_change_type` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `long_description` varchar(1000) DEFAULT NULL,
  `event_time` datetime DEFAULT NULL,
  `diff_dump` varchar(2000) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rr_logged_events`
--

LOCK TABLES `rr_logged_events` WRITE;
/*!40000 ALTER TABLE `rr_logged_events` DISABLE KEYS */;
/*!40000 ALTER TABLE `rr_logged_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rr_pending_changes`
--

DROP TABLE IF EXISTS `rr_pending_changes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rr_pending_changes` (
  `change_table` varchar(255) DEFAULT NULL,
  `change_key` varchar(255) DEFAULT NULL,
  `change_new_key` varchar(255) DEFAULT NULL,
  `change_type` varchar(255) DEFAULT NULL,
  `change_time` datetime DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rr_pending_changes`
--

LOCK TABLES `rr_pending_changes` WRITE;
/*!40000 ALTER TABLE `rr_pending_changes` DISABLE KEYS */;
/*!40000 ALTER TABLE `rr_pending_changes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rr_running_flags`
--

DROP TABLE IF EXISTS `rr_running_flags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rr_running_flags` (
  `active` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rr_running_flags`
--

LOCK TABLES `rr_running_flags` WRITE;
/*!40000 ALTER TABLE `rr_running_flags` DISABLE KEYS */;
/*!40000 ALTER TABLE `rr_running_flags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rr_sequences`
--

DROP TABLE IF EXISTS `rr_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rr_sequences` (
  `name` varchar(255) DEFAULT NULL,
  `current_value` int(11) DEFAULT NULL,
  `increment` int(11) DEFAULT NULL,
  `offset` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rr_sequences`
--

LOCK TABLES `rr_sequences` WRITE;
/*!40000 ALTER TABLE `rr_sequences` DISABLE KEYS */;
/*!40000 ALTER TABLE `rr_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipodocumento`
--

DROP TABLE IF EXISTS `tipodocumento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipodocumento` (
  `id` int(11) NOT NULL,
  `tp_lang` varchar(10) NOT NULL,
  `cdtipodocumento` varchar(10) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `mascara` varchar(20) DEFAULT NULL,
  `obrigaorgao` varchar(1) NOT NULL,
  `id_tipodocerp` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_tipodocumento` (`tp_lang`,`cdtipodocumento`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipodocumento`
--

LOCK TABLES `tipodocumento` WRITE;
/*!40000 ALTER TABLE `tipodocumento` DISABLE KEYS */;
INSERT INTO `tipodocumento` VALUES (1,'pt','CPF','CPF','999.999.999-99','N',-2),(2,'en','CPF','CPF (Brazil residents)','999.999.999-99','N',-2),(3,'en','PAS','Passport',NULL,'N',1005),(4,'pt','PAS','Passaporte (apenas residentes fora do Brasil)',NULL,'N',1005),(5,'en','RG','Identity Card',NULL,'S',3),(6,'pt','RG','Carteira de Identidade',NULL,'S',3),(7,'en','CIE','Ballot foreign identity',NULL,'N',7),(8,'pt','CIE','Cédula de Identidade Estrangeira',NULL,'N',7),(9,'en','CN','Birth Certificate',NULL,'N',8),(10,'pt','CN','Certidão de Nascimento',NULL,'N',8),(11,'es','CPF','CPF','999.999.999-99','N',-2),(12,'es','PAS','Pasaporte (residentes en las afueras de Brasil)',NULL,'N',1005),(13,'es','RG','Carnet de identidad',NULL,'S',3),(14,'es','CIE','Certificado de Relaciones Exteriores de la Identidad',NULL,'N',7),(15,'es','CN','Certificado de nacimiento',NULL,'N',8);
/*!40000 ALTER TABLE `tipodocumento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_tipodocumento_insert`
              AFTER insert ON `tipodocumento` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_tipodocumento`(concat_ws('|', 'id', NEW.`id`), null, 'I');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_tipodocumento_update`
              AFTER update ON `tipodocumento` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_tipodocumento`(concat_ws('|', 'id', OLD.`id`), concat_ws('|', 'id', NEW.`id`), 'U');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`rubyrep`@`172.16.25.114`*/ /*!50003 TRIGGER `rr_tipodocumento_delete`
              AFTER delete ON `tipodocumento` FOR EACH ROW BEGIN
                DECLARE number_attempts INT DEFAULT 0;
                DECLARE failed INT;
                DECLARE CONTINUE HANDLER FOR 1305 BEGIN
                  DO SLEEP(0.05);
                  SET failed = 1;
                  SET number_attempts = number_attempts + 1;
                END;
                REPEAT
                  SET failed = 0;
                  CALL `rr_tipodocumento`(concat_ws('|', 'id', OLD.`id`), null, 'D');
                UNTIL failed = 0 OR number_attempts >= 40 END REPEAT;
              END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping routines for database 'fnrh'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-07 11:57:51
