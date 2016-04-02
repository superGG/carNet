/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-04-01 22:16:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `privilege`
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
-- Table structure for `role`
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
-- Table structure for `roleprivilege`
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
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `loginid` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `userimg` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1','superGG','宋文光','18320489492','q459714770','e10adc3949ba59abbe56e057f20f883e','../user/a9e1ca3e-db0d-493d-9d25-f1e057bbb942.jpg');



-- ----------------------------
-- Table structure for `userrole`
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




-- ----------------------------
-- Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `carid` bigint(20) NOT NULL,
  `userid` bigint(20) NOT NULL,
  `stationname` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `brandname` varchar(255) NOT NULL,
  `agreementtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` VARCHAR(255) NOT NULL,
  `units` bigint(5) NOT NULL DEFAULT '0',
  `price` DOUBLE NOT NULL,
  `number` DOUBLE NOT NULL,
  `amounts` DOUBLE NOT NULL,
  `state` bigint(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11912 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------

INSERT INTO `order` VALUES ('11911','1','1','湖光岩东加油站','广东省湛江市麻章区湖光镇广东海洋大学主校区','中石油','2016-03-14 12:00:29','92#','1','6.77','200.0','200.0','1');




-- ----------------------------
-- Table structure for `car`
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) NOT NULL,
  `mark` varchar(255) NOT NULL,
  `brand` varchar(255) NOT NULL,
  `models` varchar(255) NOT NULL,
  `platenumber` varchar(255) NOT NULL,
  `vin` varchar(255) NOT NULL,
  `enginenumber` varchar(255) NOT NULL,
  `rank` VARCHAR(255) NOT NULL,
  `mileage` FLOAT(50) NOT NULL,
  `oilbox` FLOAT(50) NOT NULL,
  `speed` FLOAT(50) NOT NULL,
  `tachometer` FLOAT(50) NOT NULL,
  `oil` FLOAT(50) NOT NULL,
  `temperature` FLOAT(50) NOT NULL,
  `engineproperty` bit(1) NOT NULL DEFAULT b'0',
  `transmission` bit(1) NOT NULL DEFAULT b'0' ,
  `carlight` bit(1) NOT null default b'0' ,
  `lon` DOUBLE NOT null,
  `lat` DOUBLE NOT null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11912 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES ('1','1','../mark/a9e1ca3e-db0d-493d-9d25-f1e057bbb942.jpg','奥迪','R8','粤L88888','1234567','1234567','2门2座','0.0','50.0','0.0','0.0','49.9','50','\0','\0','\0','123.3','143.5');


-- ----------------------------
-- Table structure for `models`
-- ----------------------------
DROP TABLE IF EXISTS `models`;
CREATE TABLE `models` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brandid` bigint(20) not NULL,
  `modelsname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11912 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of models
-- ----------------------------
INSERT INTO `models` VALUES ('1','1','R8');

-- ----------------------------
-- Table structure for `brand`
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brandname` varchar(255) DEFAULT NULL,
  `mark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11912 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of brand
-- ----------------------------
INSERT INTO `brand` VALUES ('1','奥迪','../mark/a9e1ca3e-db0d-493d-9d25-f1e057bbb942.jpg');



