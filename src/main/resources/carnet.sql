/*
Navicat MySQL Data Transfer

Source Server         : superGG
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : carnet

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-05-01 14:12:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brandName` varchar(255) DEFAULT NULL,
  `mark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of brand
-- ----------------------------
INSERT INTO `brand` VALUES ('1', '奥迪', 'img/aodi.jpg');
INSERT INTO `brand` VALUES ('2', '奔驰', 'img/benchi.jpg');
INSERT INTO `brand` VALUES ('3', '保时捷', 'img/baoshijie.jpg');
INSERT INTO `brand` VALUES ('4', '法拉利', 'img/falali.jpg');
INSERT INTO `brand` VALUES ('5', '宝马', 'img/baoma.jpg');
INSERT INTO `brand` VALUES ('6', '玛莎拉蒂', 'img/mashaladi.jpg');
INSERT INTO `brand` VALUES ('7', '兰博基尼', 'img/lanbojini.jpg');
INSERT INTO `brand` VALUES ('8', '阿斯顿马丁', 'img/asidunmading.jpg');

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `mark` varchar(255) NOT NULL,
  `brand` varchar(255) NOT NULL,
  `models` varchar(255) NOT NULL,
  `plateNumber` varchar(255) DEFAULT NULL,
  `vin` varchar(255) NOT NULL,
  `engineNumber` varchar(255) NOT NULL,
  `rank` varchar(255) NOT NULL,
  `mileage` double NOT NULL,
  `oilBox` double NOT NULL,
  `temperature` double NOT NULL,
  `oil` double NOT NULL,
  `engineProperty` bit(1) NOT NULL DEFAULT b'1',
  `transmission` bit(1) NOT NULL DEFAULT b'1',
  `carLight` bit(2) NOT NULL DEFAULT b'10',
  `carState` bit(1) NOT NULL DEFAULT b'1',
  `carAlarm` bit(1) NOT NULL DEFAULT b'0',
  `alarmMessage` bit(1) NOT NULL DEFAULT b'1',
  `propertyMessage` bit(1) NOT NULL DEFAULT b'1',
  `stateMessage` bit(1) NOT NULL DEFAULT b'1',
  `currentCar` bit(1) NOT NULL DEFAULT b'0',
  `lon` double NOT NULL,
  `lat` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11914 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES ('1', '1', 'img/a9e1ca3e-db0d-493d-9d25-f1e057bbb942.jpg', '奥迪', 'R8', '粤L88888', '1234567', '1234567', '2门2座', '0', '50', '49.9', '50', '', '', '', '', '', '', '', '', '', '123.3', '143.5');
INSERT INTO `car` VALUES ('11912', '1', 'img/a9e1ca3e-db0d-493d-9d25-f1e057bbb942.jpg', '奥迪', 'A5', null, '1234567', '7654321', '2门2座', '32.1', '50', '48', '46.9', '', '', '', '\0', '\0', '', '', '', '', '123.4', '321.6');
INSERT INTO `car` VALUES ('11913', '1', 'img/a9e1ca3e-db0d-493d-9d25-f1e057bbb942.jpg', '奥迪', 'A5', '粤G88888', '1234567', '7654321', '2门2座', '32.1', '50', '48', '46.9', '', '', '', '\0', '\0', '', '', '', '', '123.4', '321.6');

-- ----------------------------
-- Table structure for models
-- ----------------------------
DROP TABLE IF EXISTS `models`;
CREATE TABLE `models` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brandId` bigint(20) NOT NULL,
  `modelsName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of models
-- ----------------------------
INSERT INTO `models` VALUES ('1', '1', 'R8');
INSERT INTO `models` VALUES ('2', '2', 'SLS级AMG');
INSERT INTO `models` VALUES ('3', '2', 'SL 63 AMG');
INSERT INTO `models` VALUES ('4', '2', 'GT');
INSERT INTO `models` VALUES ('5', '3', 'Boxster GTS');
INSERT INTO `models` VALUES ('6', '3', 'Turbo Cabriolet');
INSERT INTO `models` VALUES ('7', '3', 'Style Edition');
INSERT INTO `models` VALUES ('8', '4', 'Evoluzione');
INSERT INTO `models` VALUES ('9', '5', 'Z4');
INSERT INTO `models` VALUES ('10', '6', 'MC12');
INSERT INTO `models` VALUES ('11', '6', 'Birdcage 75th');
INSERT INTO `models` VALUES ('12', '7', 'Reventon');
INSERT INTO `models` VALUES ('13', '8', '0ne-77');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `carId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `stationName` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `brandName` varchar(255) NOT NULL,
  `agreementTime` datetime NOT NULL,
  `type` varchar(255) NOT NULL,
  `units` bigint(5) NOT NULL DEFAULT '0',
  `price` double NOT NULL,
  `number` double NOT NULL,
  `amounts` double NOT NULL,
  `state` bigint(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11923 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('11911', '1', '1', '湖光岩东加油站', '广东省湛江市麻章区湖光镇广东海洋大学主校区', '中石油', '2016-03-14 12:00:29', '92#', '1', '6.77', '200', '200', '1');
INSERT INTO `order` VALUES ('11912', '1', '1', '湖光岩东加油站', '广东省湛江市麻章区湖光镇广东海洋大学主校区', '中石油', '2016-03-14 12:00:29', '92#', '1', '6.88', '200', '200', '1');
INSERT INTO `order` VALUES ('11913', '1', '1', '湖光岩东加油站', '广东省湛江市麻章区湖光镇广东海洋大学主校区', '中石油', '2016-03-14 12:00:29', '92#', '1', '6.88', '200', '200', '1');
INSERT INTO `order` VALUES ('11914', '1', '1', '湖光岩东加油站', '广东省湛江市麻章区湖光镇广东海洋大学主校区', '中石油', '2016-03-14 12:00:29', '92#', '1', '6.88', '200', '200', '1');
INSERT INTO `order` VALUES ('11915', '1', '1', '湖光岩东加油站', '广东省湛江市麻章区湖光镇广东海洋大学主校区', '中石油', '2016-03-14 12:00:29', '92#', '1', '6.88', '200', '200', '1');
INSERT INTO `order` VALUES ('11916', '1', '1', '湖光岩东加油站', '广东省湛江市麻章区湖光镇广东海洋大学主校区', '中石油', '2016-03-14 12:00:29', '92#', '1', '6.88', '200', '200', '1');
INSERT INTO `order` VALUES ('11917', '1', '1', '湖光岩东加油站', '广东省湛江市麻章区湖光镇广东海洋大学主校区', '中石油', '2016-03-14 12:00:29', '92#', '1', '6.88', '200', '200', '1');
INSERT INTO `order` VALUES ('11918', '1', '1', '湖光岩东加油站', '广东省湛江市麻章区湖光镇广东海洋大学主校区', '中石油', '2016-03-14 12:00:29', '92#', '1', '6.88', '200', '200', '1');
INSERT INTO `order` VALUES ('11919', '1', '1', '湖光岩东加油站', '广东省湛江市麻章区湖光镇广东海洋大学主校区', '中石油', '2016-03-14 12:00:29', '92#', '1', '6.88', '200', '200', '1');
INSERT INTO `order` VALUES ('11920', '1', '1', '湖光岩东加油站', '广东省湛江市麻章区湖光镇广东海洋大学主校区', '中石油', '2016-03-14 12:00:29', '92#', '1', '6.88', '200', '200', '1');
INSERT INTO `order` VALUES ('11921', '1', '1', '湖光岩东加油站', '广东省湛江市麻章区湖光镇广东海洋大学主校区', '中石油', '2016-03-14 12:00:29', '92#', '1', '6.88', '200', '200', '1');
INSERT INTO `order` VALUES ('11922', '1', '1', '湖光岩东加油站', '广东省湛江市麻章区湖光镇广东海洋大学主校区', '中石油', '2016-03-14 12:00:29', '92#', '1', '6.88', '200', '200', '1');

-- ----------------------------
-- Table structure for privilege
-- ----------------------------
DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `privilegeName` varchar(255) DEFAULT NULL,
  `privilegeCode` varchar(255) NOT NULL,
  `matchUrl` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of privilege
-- ----------------------------
INSERT INTO `privilege` VALUES ('1', '测试权限', 'user:*', '/api/users');
INSERT INTO `privilege` VALUES ('2', '测试权限2', 'user:xxx', '/api/users/xxx');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(255) DEFAULT NULL,
  `describ` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '总经理', '我是总经理');

-- ----------------------------
-- Table structure for roleprivilege
-- ----------------------------
DROP TABLE IF EXISTS `roleprivilege`;
CREATE TABLE `roleprivilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleid` varchar(255) DEFAULT NULL,
  `privilegeid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roleprivilege
-- ----------------------------
INSERT INTO `roleprivilege` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `realName` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `loginid` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `userImg` varchar(255) DEFAULT NULL,
  `relatedPhone` varchar(255) DEFAULT NULL,
  `safePassword` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'superGG', '宋文光', '13820489493', 'q459714770', 'e10adc3949ba59abbe56e057f20f883e', '../user/a9e1ca3e-db0d-493d-9d25-f1e057bbb942.jpg', null, null);
INSERT INTO `user` VALUES ('2', null, null, null, 'q410654146', '7c4a8d09ca3762af61e59520943dc26494f8941b', '../carNet_user/38f35222-f179-4e59-9fea-b291b321e565.png', null, null);

-- ----------------------------
-- Table structure for userrole
-- ----------------------------
DROP TABLE IF EXISTS `userrole`;
CREATE TABLE `userrole` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` varchar(255) DEFAULT NULL,
  `roleid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userrole
-- ----------------------------
INSERT INTO `userrole` VALUES ('1', '1', '1');
