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

 Date: 11/05/2021 14:00:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色部门关联表';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_dept` VALUES (3, 103);
INSERT INTO `sys_role_dept` VALUES (3, 104);
INSERT INTO `sys_role_dept` VALUES (3, 107);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
