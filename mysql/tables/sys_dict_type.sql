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

 Date: 04/01/2021 11:25:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `dict_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` varchar(10) DEFAULT 'TRUE' COMMENT '状态（TRUE正常 FALSE停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE KEY `dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典类型表';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type` VALUES (12, '用户性别', 'sys_user_sex', 'TRUE', '', '2020-12-22 14:16:41', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (13, '操作类型', 'sys_oper_type', 'TRUE', '', '2020-12-22 14:33:54', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (14, '任务分组', 'sys_job_group', 'TRUE', '', '2020-12-22 14:37:05', '', NULL, '	任务分组列表');
INSERT INTO `sys_dict_type` VALUES (17, '操作状态', 'sys_oper_status', 'TRUE', '', '2020-12-24 10:01:31', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (18, '定时任务错误策略', 'job_misfire_polic', 'TRUE', '', '2020-12-25 11:02:34', '', NULL, '定时任务执行失败时的操作策略');
INSERT INTO `sys_dict_type` VALUES (19, '定时任务并发状态', 'job_concurrent', 'TRUE', '', '2020-12-25 11:03:49', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (20, '定时任务状态', 'job_status', 'TRUE', '', '2020-12-25 11:05:16', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (21, '定时任务执行结果', 'job_run_status', 'TRUE', '', '2020-12-25 13:34:44', '', NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
