/*
 Navicat Premium Data Transfer

 Source Server         : 公司开发
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 192.168.1.25:3306
 Source Schema         : my-test

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 04/01/2021 11:24:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `file_name` varchar(64) NOT NULL DEFAULT '' COMMENT '文件名',
  `file_suffix` varchar(10) NOT NULL DEFAULT '' COMMENT '文件后缀名',
  `url` varchar(200) NOT NULL COMMENT 'URL地址',
  `service` tinyint(2) NOT NULL DEFAULT '1' COMMENT '云存储服务商',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='文件';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------
BEGIN;
INSERT INTO `sys_oss` VALUES (1, '老司机带带我', '.jpeg', 'https://wind-1252218071.cos.ap-shanghai.myqcloud.com/wind/20190813/67da893fa1c9487c9e7978d32065f814.jpeg', 2, 'XueYou', '2020-12-28 17:13:14', '', NULL);
INSERT INTO `sys_oss` VALUES (49, 'vCc8D4fGJw.jpg', '.jpg', 'http://365wifi.oss-cn-zhangjiakou.aliyuncs.com/cabinet/1609147721/97182b34754e4dc093ed225c67ae5876.jpg', 2, 'admin', '2020-12-28 17:28:42', '', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
