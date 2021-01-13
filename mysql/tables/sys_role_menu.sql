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

 Date: 04/01/2021 11:23:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 4);
INSERT INTO `sys_role_menu` VALUES (3, 7);
INSERT INTO `sys_role_menu` VALUES (3, 12);
INSERT INTO `sys_role_menu` VALUES (3, 49);
INSERT INTO `sys_role_menu` VALUES (3, 50);
INSERT INTO `sys_role_menu` VALUES (3, 51);
INSERT INTO `sys_role_menu` VALUES (3, 52);
INSERT INTO `sys_role_menu` VALUES (3, 53);
INSERT INTO `sys_role_menu` VALUES (3, 54);
INSERT INTO `sys_role_menu` VALUES (3, 55);
INSERT INTO `sys_role_menu` VALUES (3, 56);
INSERT INTO `sys_role_menu` VALUES (3, 57);
INSERT INTO `sys_role_menu` VALUES (3, 58);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
