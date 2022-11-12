/*
Navicat MySQL Data Transfer

Source Server         : 101.34.251.234
Source Server Version : 50739
Source Host           : 101.34.251.234:3306
Source Database       : oauth2

Target Server Type    : MYSQL
Target Server Version : 50739
File Encoding         : 65001

Date: 2022-11-13 03:17:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `enabled` varchar(100) DEFAULT '0' COMMENT '账户是否可用',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户角色信息';

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------
INSERT INTO `auth_user_role` VALUES ('2', '4', '2', '2022-11-07 10:03:47', '2022-11-07 10:03:47', '0');
