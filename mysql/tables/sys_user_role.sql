/*
 Navicat Premium Data Transfer

 Source Server         : 公司开发(26)
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 192.168.1.26:3306
 Source Schema         : my-test

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 11/05/2021 14:00:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (1, 2);
INSERT INTO `sys_user_role` VALUES (1344529622942920705, 3);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
