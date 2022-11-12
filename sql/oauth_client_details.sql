/*
Navicat MySQL Data Transfer

Source Server         : 101.34.251.234
Source Server Version : 50739
Source Host           : 101.34.251.234:3306
Source Database       : oauth2

Target Server Type    : MYSQL
Target Server Version : 50739
File Encoding         : 65001

Date: 2022-11-13 03:17:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(48) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('admin', 'uaa-server', '$2a$10$9KFz6ylqoG16H5uVkR9.2ObvU.ZWBXny.hIFxTAhgx0e9onr7D5ny', 'all', 'password,refresh_token,code', 'http://www.baidu.com', null, '20', '259200', null, null);
