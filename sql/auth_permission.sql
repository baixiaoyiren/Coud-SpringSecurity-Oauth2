/*
Navicat MySQL Data Transfer

Source Server         : 101.34.251.234
Source Server Version : 50739
Source Host           : 101.34.251.234:3306
Source Database       : oauth2

Target Server Type    : MYSQL
Target Server Version : 50739
File Encoding         : 65001

Date: 2022-11-13 03:16:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auth_permission
-- ----------------------------
DROP TABLE IF EXISTS `auth_permission`;
CREATE TABLE `auth_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='权限信息';

-- ----------------------------
-- Records of auth_permission
-- ----------------------------
INSERT INTO `auth_permission` VALUES ('5', 'insert', '????', '2022-11-07 10:03:39', '2022-11-07 10:03:39');
INSERT INTO `auth_permission` VALUES ('6', 'query', '????', '2022-11-07 10:03:44', '2022-11-07 10:03:44');
