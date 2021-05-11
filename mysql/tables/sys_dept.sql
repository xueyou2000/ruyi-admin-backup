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

 Date: 11/05/2021 13:59:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` int(11) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '0' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `leader_id` int(11) DEFAULT NULL COMMENT '负责人编号',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8 COMMENT='部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (100, 0, '0', 'XueYou科技', 0, 'XueYou', 1, '15527568707', 'xueyoucd@gmail.com', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (101, 100, '0,0', '长沙分公司', 1, 'xy', 0, '18888888888', 'xy@qq.com', '0', '0', '', '2020-12-29 15:03:13', '', NULL);
INSERT INTO `sys_dept` VALUES (102, 101, '0,0,101', '研发部', 1, 'xy', NULL, '18888888888', 'xy@qq.com', '0', '0', '', '2020-12-30 17:48:42', '', '2020-12-30 17:48:42');
INSERT INTO `sys_dept` VALUES (103, 101, '0,0,101', '测试部', 2, 'xy', NULL, '18888888888', 'xy@qq.com', '0', '0', '', '2020-12-30 17:49:03', '', '2020-12-30 17:49:03');
INSERT INTO `sys_dept` VALUES (104, 101, '0,0,101', '财务部', 3, 'xy', NULL, '18888888888', 'xy@qq.com', '0', '0', '', '2020-12-30 17:49:22', '', '2020-12-30 17:49:22');
INSERT INTO `sys_dept` VALUES (105, 100, '0,100', '武汉总公司', 0, 'xy', NULL, '18888888888', 'xy@qq.com', '0', '0', '', '2020-12-30 17:49:45', '', '2020-12-30 17:49:45');
INSERT INTO `sys_dept` VALUES (106, 105, '0,100,105', '研发部', 1, 'xy', NULL, '18888888888', 'xy@qq.com', '0', '0', '', '2020-12-30 17:50:20', '', '2020-12-30 17:50:20');
INSERT INTO `sys_dept` VALUES (107, 105, '0,100,105', '测试部', 2, 'xy', NULL, '13888888888', 'xy@qq.com', '0', '0', '', '2020-12-30 17:50:38', '', '2020-12-30 17:50:38');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
