-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: healthy_diet
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrator` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1,'1','1','1111');
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `post_id` int DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `timestamp` date DEFAULT NULL,
  `is_offending` int DEFAULT '0',
  PRIMARY KEY (`comment_id`),
  KEY `user_id` (`user_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `normal_user` (`user_id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diet_analysis`
--

DROP TABLE IF EXISTS `diet_analysis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diet_analysis` (
  `analysis_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `calories` int DEFAULT NULL,
  `suggestion` varchar(512) DEFAULT NULL,
  `fat` double DEFAULT NULL,
  `protein` double DEFAULT NULL,
  `carbohydrates` double DEFAULT NULL,
  `dietary_fibre` double DEFAULT NULL,
  `sugar` double DEFAULT NULL,
  `vitamin_D` double DEFAULT NULL,
  `sodium` double DEFAULT NULL,
  `potassium` double DEFAULT NULL,
  `calcium` double DEFAULT NULL,
  `iron` double DEFAULT NULL,
  PRIMARY KEY (`analysis_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `diet_analysis_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `normal_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diet_analysis`
--

LOCK TABLES `diet_analysis` WRITE;
/*!40000 ALTER TABLE `diet_analysis` DISABLE KEYS */;
/*!40000 ALTER TABLE `diet_analysis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_item`
--

DROP TABLE IF EXISTS `exercise_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_item` (
  `exercise_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `calories_per_hour` int DEFAULT NULL,
  PRIMARY KEY (`exercise_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_item`
--

LOCK TABLES `exercise_item` WRITE;
/*!40000 ALTER TABLE `exercise_item` DISABLE KEYS */;
INSERT INTO `exercise_item` VALUES (1,'羽毛球',326),(2,'乒乓球',217),(3,'跑步（快）',550),(4,'平板支撑',507),(5,'快走',289),(6,'走路（慢）',94),(7,'跑步（慢）',398),(8,'爬楼梯',391),(9,'跳绳',782),(10,'游泳',398),(11,'自行车',420),(12,'健身操',362),(13,'踏板操',471),(14,'瑜伽',144),(15,'篮球',398),(16,'足球',434),(17,'排球',340),(18,'网球',289),(19,'橄榄球',384),(20,'棒球',217),(21,'曲棍球',492),(22,'攀岩',616),(23,'爬山',616),(24,'滑板',289),(25,'滑雪',434),(26,'击剑',362);
/*!40000 ALTER TABLE `exercise_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_record`
--

DROP TABLE IF EXISTS `exercise_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_record` (
  `exercise_record_id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `duration` time DEFAULT NULL,
  `exercise_id` int DEFAULT NULL,
  `burned_caloris` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`exercise_record_id`),
  KEY `exercise_id` (`exercise_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `exercise_record_ibfk_1` FOREIGN KEY (`exercise_id`) REFERENCES `exercise_item` (`exercise_id`),
  CONSTRAINT `exercise_record_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `normal_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_record`
--

LOCK TABLES `exercise_record` WRITE;
/*!40000 ALTER TABLE `exercise_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `exercise_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_in_recipe`
--

DROP TABLE IF EXISTS `food_in_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_in_recipe` (
  `fo_re_id` int NOT NULL AUTO_INCREMENT,
  `recipe_id` int DEFAULT NULL,
  `food_id` int DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  PRIMARY KEY (`fo_re_id`),
  KEY `recipe_id` (`recipe_id`),
  KEY `food_id` (`food_id`),
  CONSTRAINT `food_in_recipe_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`recipe_id`),
  CONSTRAINT `food_in_recipe_ibfk_2` FOREIGN KEY (`food_id`) REFERENCES `food_item` (`foodid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_in_recipe`
--

LOCK TABLES `food_in_recipe` WRITE;
/*!40000 ALTER TABLE `food_in_recipe` DISABLE KEYS */;
/*!40000 ALTER TABLE `food_in_recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_item`
--

DROP TABLE IF EXISTS `food_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_item` (
  `foodid` int NOT NULL AUTO_INCREMENT,
  `calories` int DEFAULT NULL,
  `fat` double DEFAULT NULL,
  `protein` double DEFAULT NULL,
  `carbohydrates` double DEFAULT NULL,
  `dietary_fiber` double DEFAULT NULL,
  `potassium` double DEFAULT NULL,
  `sodium` double DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`foodid`)
) ENGINE=InnoDB AUTO_INCREMENT=208 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_item`
--

LOCK TABLES `food_item` WRITE;
/*!40000 ALTER TABLE `food_item` DISABLE KEYS */;
INSERT INTO `food_item` VALUES (1,361,3.1,9,75.1,1.6,284,4.3,'谷类及制品','小米'),(2,338,1.3,11.9,75.2,10.8,289,6.8,'谷类及制品','小麦'),(3,348,0.7,10.1,76,1.6,157,150,'谷类及制品','挂面（标准粉）'),(4,236,1,7.8,49.8,1.5,129,165.2,'谷类及制品','馒头（蒸，标准粉）'),(5,346,0.9,7.9,77.2,0.6,112,1.8,'谷类及制品','稻米（大米代表值）'),(6,350,1,7.3,78.3,0.8,137,1.5,'谷类及制品','糯米（江米）'),(7,112,1.2,4,22.8,2.9,238,1.1,'谷类及制品','玉米（鲜）'),(8,338,0.2,10.1,77.4,6,356,2.1,'谷类及制品','燕麦'),(9,81,0.2,2.6,17.8,1.1,347,5.9,'薯类、淀粉及制品','马铃薯（土豆、洋芋）'),(10,373,0,0.2,93,0.1,35,10.8,'薯类、淀粉及制品','藕粉'),(11,390,16,35,34.2,15.5,1503,2.2,'干豆类及制品','黄豆（大豆）'),(12,401,2.6,10,84.3,0,339,0,'干豆类及制品','豆腐花[豆腐粉]'),(13,84,5.3,6.6,3.4,0,118,5.6,'干豆类及制品','豆腐（代表值）'),(14,31,1.6,3,1.2,0,117,3.7,'干豆类及制品','豆浆'),(15,262,16,24.5,5.5,1,94,20.6,'干豆类及制品','千张（百页）'),(16,194,12.5,16.5,4.2,0.9,42,373.8,'干豆类及制品','素鸡'),(17,329,0.8,21.6,62,6.4,787,3.2,'干豆类及制品','绿豆（干）'),(18,324,0.6,20.2,63.4,7.7,860,2.2,'干豆类及制品','赤小豆（干）[红，红小豆]'),(19,336,1.2,19.3,65.6,7.1,737,6.8,'干豆类及制品','豇豆（干）'),(20,334,1.1,20.3,65.8,10.4,823,9.7,'干豆类及制品','豌豆（干）'),(21,16,0.1,0.7,4,0,167,54.3,'蔬菜类及制品','白萝卜（鲜）（莱菔）'),(22,22,0.1,1,4.6,0.8,110,62.7,'蔬菜类及制品','红萝卜（卞萝卜）'),(23,16,0.2,0.7,3.6,1,14,117.5,'蔬菜类及制品','白萝卜（圆）'),(24,32,0.2,1,8.1,0,119,120.7,'蔬菜类及制品','胡萝卜'),(25,111,0.4,8.8,19.5,3.1,391,4,'蔬菜类及制品','蚕豆（鲜）'),(26,34,0.2,2.5,6.7,2.1,207,3.4,'蔬菜类及制品','豆角'),(27,31,0.4,2,5.7,1.5,123,8.6,'蔬菜类及制品','四季豆（菜豆）'),(28,131,5,13.1,10.5,4,478,3.9,'蔬菜类及制品','毛豆（鲜）（青豆，菜用大豆）'),(29,30,0.3,2.5,4.9,1.4,116,8.8,'蔬菜类及制品','荷兰豆'),(30,32,0.2,2.7,5.8,1.8,145,4.6,'蔬菜类及制品','豇豆（长）'),(31,47,1.6,4.5,4.5,1.5,160,7.2,'蔬菜类及制品','黄豆芽'),(32,16,0.1,1.7,2.6,1.2,32,25.8,'蔬菜类及制品','绿豆芽'),(33,23,0.2,1.1,4.9,1.3,142,5.4,'蔬菜类及制品','茄子（代表值）'),(34,22,0.3,0.8,5.2,0,154,7,'蔬菜类及制品','辣椒（尖，青）'),(35,25,0.2,1.8,6.2,1.8,19,8.7,'蔬菜类及制品','秋葵（黄秋葵，羊角豆）'),(36,16,0.2,0.8,2.9,0.5,102,4.9,'蔬菜类及制品','黄瓜（鲜）（胡瓜）'),(37,21,0.1,1,4.9,1.4,256,2.5,'蔬菜类及制品','苦瓜（鲜）（凉瓜，癞瓜）'),(38,10,0.2,0.3,2.4,0,57,2.8,'蔬菜类及制品','冬瓜'),(39,20,0.2,1.3,4,0,121,3.7,'蔬菜类及制品','丝瓜'),(40,128,0.2,4.5,27.6,1.1,302,19.6,'蔬菜类及制品','大蒜（白皮，鲜）（蒜头）'),(41,26,0.4,1.6,4.9,1.4,143,10.4,'蔬菜类及制品','葱（小葱，鲜）'),(42,28,0.3,1.6,5.8,2.2,110,8.9,'蔬菜类及制品','大葱'),(43,342,0.4,5.5,81.9,5.7,740,31.7,'蔬菜类及制品','洋葱（白皮，脱水）'),(44,25,0.4,2.4,4.5,0,241,5.8,'蔬菜类及制品','韭菜'),(45,20,0.2,1.6,3.4,0.9,134,68.9,'蔬菜类及制品','大白菜（代表值）'),(46,13,0.2,1.9,2.4,0,278,19.3,'蔬菜类及制品','娃娃菜'),(47,24,0.2,1.5,4.6,1,124,27.2,'蔬菜类及制品','圆白菜（卷心菜）'),(48,20,0.2,1.7,4.2,2.1,206,39.2,'蔬菜类及制品','菜花（白色）（花椰菜）'),(49,27,0.6,3.5,3.7,0,179,46.7,'蔬菜类及制品','西兰花（绿菜花）'),(50,28,0.3,2.6,4.5,1.7,311,85.2,'蔬菜类及制品','菠菜（鲜）（赤根菜）'),(51,22,0.2,1.2,4.5,1.2,206,159,'蔬菜类及制品','芹菜茎'),(52,33,0.4,1.8,6.2,1.2,272,48.5,'蔬菜类及制品','香菜（鲜）（芫荽）'),(53,15,0.1,1,2.8,0.6,212,36.5,'蔬菜类及制品','莴笋（鲜）（莴苣）'),(54,17,0.1,0.6,4.8,2.2,15,313.3,'蔬菜类及制品','西芹（西洋芹菜，美芹）'),(55,12,0.4,1.6,1.1,0,91,16.1,'蔬菜类及制品','生菜（叶用莴苣）'),(56,63,0.1,2.1,19.5,0,469,42.4,'蔬菜类及制品','鱼腥草（根）'),(57,32,0.4,2.4,6,2.7,195,4.3,'菌藻类','金针菇（鲜，智力菇）'),(58,24,0.1,2.7,4.1,2.1,312,8.3,'菌藻类','蘑菇（鲜，鲜蘑）'),(59,265,1.5,12.1,65.6,29.9,757,48.5,'菌藻类','木耳（干，黑木耳，云耳）'),(60,26,0.3,2.2,5.2,3.3,20,1.4,'菌藻类','香菇（鲜，香蕈，冬菇）'),(61,274,1.2,20,61.7,31.6,464,11.2,'菌藻类','香菇（干，香蕈，冬菇）'),(62,261,1.4,10,67.3,30.4,1588,82.1,'菌藻类','银耳（干）（白木耳）'),(63,13,0.1,1.2,2.1,0.5,246,8.6,'菌藻类','海带（鲜，江白菜）'),(64,250,1.1,26.7,44.1,21.6,1796,710.5,'菌藻类','紫菜（干）'),(65,219,1.7,25,41.5,31.1,335,4411.6,'菌藻类','裙带菜（干）[海芥菜、海木耳]'),(66,53,0.2,0.4,13.7,1.7,83,1.3,'水果类及制品','苹果（代表值）'),(67,51,0.1,0.3,13.1,2.6,85,1.7,'水果类及制品','梨（代表值）'),(68,42,0.1,0.6,10.1,1,127,1.7,'水果类及制品','桃（代表值）'),(69,37,0.2,0.7,8.7,0.9,144,3.8,'水果类及制品','李子'),(70,42,0.1,0.7,10.3,0.7,155,3.2,'水果类及制品','西梅'),(71,38,0.1,0.9,9.1,1.3,226,2.3,'水果类及制品','杏'),(72,125,0.3,1.1,30.5,1.9,375,1.2,'水果类及制品','枣（鲜）'),(73,46,0.2,1.1,10.2,0.3,232,8,'水果类及制品','樱桃'),(74,45,0.3,0.4,10.3,1,127,1.9,'水果类及制品','葡萄（代表值）'),(75,72,0.2,1.3,18.5,4.9,231,0.7,'水果类及制品','石榴（代表值）'),(76,73,0.1,0.4,18.5,1.4,151,0.8,'水果类及制品','柿'),(77,57,0.4,1.7,13.8,4.1,32,2,'水果类及制品','桑葚（代表值）'),(78,255,0.2,1.8,62.8,2.6,339,6.4,'水果类及制品','柿饼'),(79,61,0.6,0.8,14.5,2.6,144,10,'水果类及制品','中华猕猴桃（毛叶猕猴桃）'),(80,32,0.2,1,7.1,1.1,131,4.2,'水果类及制品','草莓（洋梅，凤阳草莓）'),(81,48,0.2,0.8,11.1,0.6,159,1.2,'水果类及制品','橙'),(82,44,0.4,0.8,10.3,1.4,177,1.3,'水果类及制品','蜜橘'),(83,37,1.2,1.1,6.2,1.3,209,1.1,'水果类及制品','柠檬'),(84,41,0.2,0.8,9.5,0.4,119,3,'水果类及制品','柚（文旦）'),(85,43,0.1,0.5,10.8,1.3,113,0.8,'水果类及制品','菠萝（凤梨，地菠萝）'),(86,71,0.2,0.9,16.6,0.5,151,1.7,'水果类及制品','荔枝'),(87,34,0.2,0.6,8.3,1.3,138,2.8,'水果类及制品','芒果（抹猛果，望果）'),(88,30,0.2,0.8,6.7,1,149,0.7,'水果类及制品','杨梅（树梅，山杨梅）'),(89,31,0.2,0.6,7.4,1.2,128,1.4,'水果类及制品','杨桃'),(90,241,12.1,4,31.3,4.7,475,55.6,'水果类及制品','椰子'),(91,40,0.2,0.8,9.3,0.8,122,4,'水果类及制品','枇杷'),(92,55,0.2,1.1,13.3,1.6,20,2.7,'水果类及制品','火龙果（仙蜜果，红龙果）'),(93,150,3.3,2.6,28.3,1.7,261,2.9,'水果类及制品','榴莲'),(94,93,0.2,1.4,21.999998,1.2,256,0.8,'水果类及制品','香蕉（甘蕉）'),(95,34,0.1,0.5,7.9,0.2,190,26.7,'水果类及制品','哈蜜瓜'),(96,26,0.1,0.4,6.2,0.4,139,8.8,'水果类及制品','甜瓜（香瓜）'),(97,31,0.3,0.5,6.8,0.2,97,3.3,'水果类及制品','西瓜（代表值）'),(98,164,0.3,4.9,36.7,2.3,400,11.5,'坚果种子类','菠萝蜜'),(99,646,58.8,14.9,19.1,9.5,385,6.4,'坚果种子类','核桃（干，胡桃）'),(100,214,1.5,4.8,46,1.2,0,0,'坚果种子类','栗子（熟，板栗）'),(101,643,58.5,14.1,21.4,12.4,612,3,'坚果种子类','松子（炒）'),(102,578,45.4,22.5,23.9,8,106,8.3,'坚果种子类','杏仁'),(103,658,64.5,8.3,21.3,0,4,855.5,'坚果种子类','山核桃（熟，小核桃）'),(104,186,1.6,4.4,39.6,2,0,0,'坚果种子类','栗子（板栗）'),(105,615,50.9,24,20.4,10.4,680,35.7,'坚果种子类','腰果（熟）'),(106,631,53,20.6,21.9,8.2,735,756.4,'坚果种子类','开心果'),(107,601,48,21.7,23.8,6.3,563,34.8,'坚果种子类','花生（炒）'),(108,625,52.8,22.6,17.3,4.8,491,1322,'坚果种子类','葵花子（炒，咸）'),(109,582,46.1,36,7.9,4.1,672,15.8,'坚果种子类','南瓜子（炒，白瓜子）'),(110,582,44.8,32.7,14.2,4.5,612,187.7,'坚果种子类','西瓜子（炒）'),(111,331,30.1,15.1,0,0,218,56.8,'畜肉类及制品','猪肉(代表值,fat 30g)'),(112,260,18.8,22.6,0,0,54,101,'畜肉类及制品','猪蹄 '),(113,196,18.7,6.9,0,0,44,116.3,'畜肉类及制品','猪大肠'),(114,176,11.1,19.1,0,0,58,68.2,'畜肉类及制品','猪耳 '),(115,110,5.1,15.2,0.7,0,171,75.1,'畜肉类及制品','猪肚 '),(116,55,0.3,12.2,0.9,0,56,56,'畜肉类及制品','猪血'),(117,126,4.7,19.2,1.8,0,235,68.6,'畜肉类及制品','猪肝'),(118,181,9,22.3,2.6,0,294,51.2,'畜肉类及制品','腊肉(培根)'),(119,229,15.9,9.4,12,0,146,981.9,'畜肉类及制品','午餐肉(北京)'),(120,378,8.8,28,46.6,0,381,1638.2,'畜肉类及制品','猪肉脯 '),(121,212,10.4,14,15.6,0,217,771.2,'畜肉类及制品','火腿肠 '),(122,160,8.7,20,0.5,0,212,64.1,'畜肉类及制品','牛肉(代表值,fat 9g)'),(123,25,0,6,0.2,0,1,81,'畜肉类及制品','牛蹄筋(泡发)'),(124,72,1.6,14.5,0,0,162,60.6,'畜肉类及制品','牛肚 '),(125,139,6.5,18.5,1.6,0,300,89.9,'畜肉类及制品','羊肉(代表值,fat 7g)'),(126,206,10.3,26,2.4,0,205,484.8,'畜肉类及制品','羊肉串(烤)'),(127,116,3.2,21.5,0.4,0,325,46.9,'畜肉类及制品','驴肉(瘦) '),(128,116,4.6,16.8,1.8,0,140,47.4,'畜肉类及制品','狗肉'),(129,102,2.2,19.7,0.9,0,284,45.1,'畜肉类及制品','兔肉'),(130,145,6.7,20.3,0.9,0,249,62.8,'禽肉类及制品','鸡(代表值)'),(131,254,16.4,23.9,2.7,0,108,169,'禽肉类及制品','鸡爪 '),(132,146,7.2,20.2,0,0,221,73.6,'禽肉类及制品','鸡腿 '),(133,202,11.5,19,5.5,0,205,50.8,'禽肉类及制品','鸡翅 '),(134,118,1.9,24.6,0.6,0,333,44.8,'禽肉类及制品','鸡胸脯肉 '),(135,279,17.3,20.3,10.5,0,232,755,'禽肉类及制品','炸鸡块[肯德基]'),(136,240,19.7,15.5,0.2,0,191,69,'禽肉类及制品','鸭(代表值)'),(137,146,6.1,16.5,6.3,0,100,53.6,'禽肉类及制品','鸭翅 '),(138,150,1.9,26.9,6.2,0,28,61.1,'禽肉类及制品','鸭掌 '),(139,129,7.8,14.2,0.4,0,136,32,'禽肉类及制品','鸭肠'),(140,245,19.7,16.6,0.4,0,44,81.5,'禽肉类及制品','鸭舌[鸭条]'),(141,108,0.4,13.6,12.4,0,166,173.6,'禽肉类及制品','鸭血(白鸭)'),(142,436,38.4,16.6,6,0,247,83,'禽肉类及制品','北京烤鸭'),(143,118,2.8,19.2,4,0,272,74.8,'禽肉类及制品','鸡肫[鸡胗]'),(144,92,1.3,17.9,2.1,0,284,69.2,'禽肉类及制品','鸭肫 '),(145,251,19.9,17.9,0,0,232,58.8,'禽肉类及制品','鹅'),(146,110,3.1,20.2,0.2,0,204,48.4,'禽肉类及制品','鹌鹑'),(147,352,34.1,11.3,0,0,163,653.8,'禽肉类及制品','乳鸽 '),(148,65,3.6,3.3,4.9,0,180,63.7,'乳类及制品','纯牛奶(代表值,全脂)'),(149,34,0.3,3.5,4.6,0,200,127.3,'乳类及制品','纯牛奶(代表值,脱脂)'),(150,66,2.6,2.6,8.1,0,140,47.3,'乳类及制品','调制乳(全脂,学生奶)'),(151,67,3.7,3.4,5.1,0,127,120.3,'乳类及制品','鲜牛奶(代表值,全脂)'),(152,482,22.3,19.9,50.5,0,777,352,'乳类及制品','全脂奶粉(代表值)'),(153,139,8.6,13.1,2.4,0,154,131.5,'蛋类及制品','鸡蛋(代表值)'),(154,180,13,12.6,3.1,0,135,106,'蛋类及制品','鸭蛋 '),(155,171,10.7,14.2,4.5,0,152,542.7,'蛋类及制品','松花蛋(鸭蛋)[皮蛋] '),(156,196,15.6,11.1,2.8,0,74,90.6,'蛋类及制品','鹅蛋 '),(157,160,11.1,12.8,2.1,0,138,106.6,'蛋类及制品','鹌鹑蛋'),(158,113,5.2,16.6,0,0,312,46,'鱼虾蟹贝类','草鱼'),(159,109,4.1,17.6,0.5,0,334,53.7,'鱼虾蟹贝类','鲤鱼[鲤拐子]'),(160,96,2,17.9,1.7,0,282,74.8,'鱼虾蟹贝类','泥鳅 '),(161,104,3.6,17.8,0,0,277,57.5,'鱼虾蟹贝类','鲢鱼[白鲢、胖子、连子鱼]'),(162,108,2.7,17.1,3.8,0,290,41.2,'鱼虾蟹贝类','鲫鱼[喜头鱼、海附鱼]'),(163,117,4.2,19.9,0,0,295,68.6,'鱼虾蟹贝类','鳜鱼[桂鱼、花鲫鱼]'),(164,127,4.9,17.7,3.1,0,280,150.1,'鱼虾蟹贝类','带鱼[白带鱼、刀鱼]'),(165,97,2.5,17.7,0.8,0,260,120.3,'鱼虾蟹贝类','黄鱼(大黄花鱼)'),(166,93,0.8,18.6,2.8,0,215,165.2,'鱼虾蟹贝类','对虾 '),(167,90,1.1,18.9,1,0,257,190,'鱼虾蟹贝类','龙虾 '),(168,101,1.4,18.2,3.9,0,250,172,'鱼虾蟹贝类','基围虾 '),(169,198,2.6,43.7,0,0,550,4891.9,'鱼虾蟹贝类','虾米[海米、虾仁]'),(170,153,2.2,30.7,2.5,0,617,5057.7,'鱼虾蟹贝类','虾皮'),(171,156,7.7,21,0.6,0,224,187.8,'鱼虾蟹贝类','大闸蟹(母)'),(172,60,0.6,11.1,2.6,0,122,339,'鱼虾蟹贝类','扇贝(鲜)'),(173,57,1.5,10.9,0,0,375,270,'鱼虾蟹贝类','生蚝'),(174,62,1.1,10.1,2.8,0,140,425.7,'鱼虾蟹贝类','蛤蜊(代表值)'),(175,128,4.7,13.4,8,4,275,825.2,'鱼虾蟹贝类','墨鱼丸 '),(176,463,33.7,6.1,34.8,1,89,485.8,'小吃、甜饼','春卷'),(177,336,0.7,7.6,83.8,9.1,117,85.5,'小吃、甜饼','煎饼'),(178,37,0.3,0.2,8.9,0.6,5,2.8,'小吃、甜饼','凉粉'),(179,167,1.7,4.8,33.3,0.2,10,163.7,'小吃、甜饼','凉面'),(180,154,0.6,3.3,34.7,0.8,81,56.4,'小吃、甜饼','年糕'),(181,289,10.7,5.2,44,1.1,95,154.8,'小吃、甜饼','面窝'),(182,152,2.4,4.2,28.7,0.2,46,165.8,'小吃、甜饼','热干面'),(183,237,10.2,6,31,0.6,21,207,'小吃、甜饼','三鲜豆皮'),(184,92,3.8,4,11.8,0,38,310.4,'小吃、甜饼','过桥米线'),(185,347,5.1,8.6,67.1,0.4,77,67.8,'小吃、甜饼','蛋糕(均值)'),(186,386,3.9,11.7,76.9,0.8,105,100,'小吃、甜饼','蛋黄酥'),(187,481,21.8,7.1,65.1,1.1,90,33.9,'小吃、甜饼','桃酥'),(188,506,30.4,5.9,55.1,3,155,114.2,'小吃、甜饼','沙琪玛蛋酥'),(189,351,7.4,12.4,67.3,8.6,306,20.9,'速食食品','麦片'),(190,390,3.7,7.2,82.3,0.4,52,1.7,'速食食品','玉米片(即食粥)'),(191,472,21.1,9.5,61.6,0.7,134,1144,'速食食品','方便面'),(192,312,5.1,8.3,58.6,0.5,88,230.4,'速食食品','面包(均值)'),(193,433,12.7,9,71.7,1.1,85,204.1,'速食食品','饼干(均值)'),(194,612,48.4,4,41.9,1.9,620,60.9,'速食食品','马铃薯片(油炸)[油炸土豆片]'),(195,28,0.1,0.2,6.5,0,1,22.7,'饮料类','百令可乐'),(196,20,0,0,5.1,0,3,8.1,'饮料类','橙汁汽水'),(197,38,0,0,9.5,0,0,3.3,'饮料类','柠檬汽水'),(198,294,1.1,26.7,59.2,14.8,1934,13.6,'饮料类','红茶'),(199,296,2.3,34.2,50.3,15.6,1661,28.2,'饮料类','绿茶'),(200,281,1.2,27.1,58.1,17.7,1643,8,'饮料类','花茶'),(201,304,1.3,22.8,65,14.7,1462,7.8,'饮料类','铁观音茶'),(202,47,0.2,0.8,10.5,0,0,20.4,'饮料类','冰棍'),(203,127,5.3,2.4,17.3,0,125,54.2,'饮料类','冰淇淋'),(204,32,0,0.4,0,0,47,11.4,'含酒精饮料','啤酒(均值)'),(205,72,0,0.1,0,0,33,1.6,'含酒精饮料','葡萄酒(均值)'),(206,66,0,1.6,0,0,26,5.2,'含酒精饮料','黄酒'),(207,351,0,0,0,0,0,0.5,'含酒精饮料','二锅头(58度)');
/*!40000 ALTER TABLE `food_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_record`
--

DROP TABLE IF EXISTS `food_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_record` (
  `food_record_id` int NOT NULL AUTO_INCREMENT,
  `record_time` date NOT NULL,
  `user_id` int DEFAULT NULL,
  `food_id` int DEFAULT NULL,
  `food_weight` double DEFAULT NULL,
  `calories` int DEFAULT NULL,
  `fat` double DEFAULT NULL,
  `protein` double DEFAULT NULL,
  `carbohydrates` double DEFAULT NULL,
  `sodium` double DEFAULT NULL,
  `potassium` double DEFAULT NULL,
  `dietary_fiber` double DEFAULT NULL,
  PRIMARY KEY (`food_record_id`),
  KEY `user_id` (`user_id`),
  KEY `food_id` (`food_id`),
  CONSTRAINT `food_record_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `normal_user` (`user_id`),
  CONSTRAINT `food_record_ibfk_2` FOREIGN KEY (`food_id`) REFERENCES `food_item` (`foodid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_record`
--

LOCK TABLES `food_record` WRITE;
/*!40000 ALTER TABLE `food_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `food_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `normal_user`
--

DROP TABLE IF EXISTS `normal_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `normal_user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `profile_picture` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `weight` int DEFAULT NULL,
  `age` int DEFAULT NULL,
  `height` int DEFAULT NULL,
  `isblocked` int DEFAULT '0',
  `phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `normal_user`
--

LOCK TABLES `normal_user` WRITE;
/*!40000 ALTER TABLE `normal_user` DISABLE KEYS */;
INSERT INTO `normal_user` VALUES (1,'test','1','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',50,20,160,0,'12345678909'),(2,'string','string','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',0,0,0,0,'string'),(11,'123','1','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',NULL,NULL,NULL,0,''),(14,'1123','12','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',NULL,NULL,NULL,0,'23456'),(15,'abc','a','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,1,'0000'),(16,'abcd','a','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'10000'),(18,'abcde','a','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'1234'),(19,'aaaa','a','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'123456'),(20,'a','a','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'1234567'),(21,'bc','a','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'123456789'),(22,'bcd','a','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'1111'),(23,'bcde','a','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'11112'),(24,'bcdef','a','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'111122'),(25,'bcdaef','a','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'1121122'),(26,'bcdaefd','a','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'112112234'),(27,'ddd','1234','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'7777'),(28,'ddd1','1234','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'7776'),(29,'ddd12','1234','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'77766'),(30,'abv','1','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,21,1,0,'12345'),(31,'we','1','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'11111'),(34,'wert','1','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'123456709654'),(35,'9876','12','https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800',1,1,1,0,'9876');
/*!40000 ALTER TABLE `normal_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `data` varchar(400) DEFAULT NULL,
  `sent` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`notification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `post_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `user_id` int DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `tags` varchar(20) DEFAULT NULL,
  `timestamp` date DEFAULT NULL,
  `is_offending` int DEFAULT '0',
  PRIMARY KEY (`post_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `normal_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe` (
  `recipe_id` int NOT NULL AUTO_INCREMENT,
  `recipe_name` varchar(20) NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`recipe_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `recipe_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `normal_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe`
--

LOCK TABLES `recipe` WRITE;
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reminder`
--

DROP TABLE IF EXISTS `reminder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reminder` (
  `reminder_id` int NOT NULL AUTO_INCREMENT,
  `type` int NOT NULL,
  `time` time NOT NULL,
  `user_id` int DEFAULT NULL,
  `is_on` int DEFAULT '1',
  PRIMARY KEY (`reminder_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `reminder_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `normal_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reminder`
--

LOCK TABLES `reminder` WRITE;
/*!40000 ALTER TABLE `reminder` DISABLE KEYS */;
INSERT INTO `reminder` VALUES (1,1,'19:00:00',1,1),(3,2,'19:20:00',11,1);
/*!40000 ALTER TABLE `reminder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'healthy_diet'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-23 20:08:32
