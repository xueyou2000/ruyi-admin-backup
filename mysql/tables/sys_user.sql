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

 Date: 04/01/2021 11:23:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `login_name` varchar(30) NOT NULL COMMENT '登录账号',
  `user_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(10) DEFAULT 'SYSTEM' COMMENT '用户类型）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像路径',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `salt` varchar(20) DEFAULT '' COMMENT '盐加密',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `admin` varchar(30) DEFAULT 'FALSE' COMMENT '是否超级管理员',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1344529622942920706 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 100, 'admin', 'XueYou', NULL, 'xueyoucd@gmail.com', '15527568707', '1', '', '425740ca6414a6ef758f816f34d1031e', 'T3F2YZ', '0', '0', '127.0.0.1', '2021-01-04 11:19:20', '内置超级用户', NULL, '2018-03-16 11:33:00', NULL, NULL, 'TRUE');
INSERT INTO `sys_user` VALUES (1344529622942920705, 107, 'test', '测试小子', NULL, '', '', '0', '', 'f4de7dbf94428bad1304270712cbc4ef', 'zdJm1Y', '0', '0', '127.0.0.1', '2020-12-31 16:03:41', '', NULL, '2020-12-31 14:23:36', NULL, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
