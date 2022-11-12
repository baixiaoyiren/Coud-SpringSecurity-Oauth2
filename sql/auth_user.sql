/*
Navicat MySQL Data Transfer

Source Server         : 101.34.251.234
Source Server Version : 50739
Source Host           : 101.34.251.234:3306
Source Database       : oauth2

Target Server Type    : MYSQL
Target Server Version : 50739
File Encoding         : 65001

Date: 2022-11-13 03:17:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL COMMENT '户主姓名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '账户是否可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_user_username_IDX` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户信息';

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES ('4', 'modebing', '$2a$10$9KFz6ylqoG16H5uVkR9.2ObvU.ZWBXny.hIFxTAhgx0e9onr7D5ny', '2022-11-07 10:03:39', '2022-11-07 10:03:39', '1');
