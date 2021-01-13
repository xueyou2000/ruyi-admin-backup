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

 Date: 04/01/2021 11:24:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_login_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_info`;
CREATE TABLE `sys_login_info` (
  `info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `login_name` varchar(50) DEFAULT '' COMMENT '登录账号',
  `user_id` bigint(11) DEFAULT NULL,
  `ipaddr` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` varchar(30) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登陆日志';

SET FOREIGN_KEY_CHECKS = 1;
