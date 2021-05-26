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

 Date: 11/05/2021 13:59:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `dict_code` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_lang_label` varchar(100) DEFAULT '' COMMENT '字典国际化标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` varchar(10) DEFAULT 'FALSE' COMMENT '是否默认（TRUE是 FALSE否）',
  `status` varchar(10) DEFAULT 'TRUE' COMMENT '状态（TRUE正常 FALSE停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_data` VALUES (48, 1, '男', '1', 'sys_user_sex', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:27:24', '', '2020-12-23 14:33:02', '性别男');
INSERT INTO `sys_dict_data` VALUES (49, 2, '女', '2', 'sys_user_sex', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:27:44', '', '2020-12-23 14:33:05', '性别女');
INSERT INTO `sys_dict_data` VALUES (50, 3, '未知', '3', 'sys_user_sex', '', NULL, 'TRUE', 'TRUE', '', '2020-12-22 14:28:02', '', '2020-12-23 14:33:08', '	性别未知');
INSERT INTO `sys_dict_data` VALUES (51, 1, '新增', '1', 'sys_oper_type', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:34:29', '', '2020-12-23 14:33:13', '新增操作');
INSERT INTO `sys_dict_data` VALUES (52, 2, '修改', '2', 'sys_oper_type', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:34:51', '', '2020-12-23 14:33:16', '修改操作');
INSERT INTO `sys_dict_data` VALUES (53, 3, '删除', '3', 'sys_oper_type', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:35:03', '', '2020-12-23 14:33:18', '删除操作');
INSERT INTO `sys_dict_data` VALUES (54, 4, '授权', '4', 'sys_oper_type', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:35:19', '', '2020-12-23 14:33:21', '授权操作');
INSERT INTO `sys_dict_data` VALUES (55, 5, '导出', '5', 'sys_oper_type', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:35:33', '', '2020-12-23 14:33:25', '导出操作');
INSERT INTO `sys_dict_data` VALUES (56, 6, '导入', '6', 'sys_oper_type', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:35:52', '', '2020-12-23 14:33:28', '导入操作');
INSERT INTO `sys_dict_data` VALUES (57, 7, '强退', '7', 'sys_oper_type', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:36:04', '', '2020-12-23 14:33:31', '强退操作');
INSERT INTO `sys_dict_data` VALUES (58, 8, '生成代码', '8', 'sys_oper_type', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:36:21', '', '2020-12-23 14:33:33', '生成操作');
INSERT INTO `sys_dict_data` VALUES (59, 9, '清空数据', '9', 'sys_oper_type', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:36:35', '', '2020-12-23 14:33:38', '清空操作');
INSERT INTO `sys_dict_data` VALUES (60, 1, '默认', 'default', 'sys_job_group', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:37:31', '', '2020-12-23 14:32:47', '默认分组');
INSERT INTO `sys_dict_data` VALUES (61, 2, '系统', 'system', 'sys_job_group', '', NULL, 'FALSE', 'TRUE', '', '2020-12-22 14:37:46', '', '2020-12-23 14:32:52', '系统分组');
INSERT INTO `sys_dict_data` VALUES (65, 0, '成功', '0', 'sys_oper_status', NULL, NULL, 'FALSE', 'TRUE', '', '2020-12-24 10:01:44', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (66, 0, '失败', '1', 'sys_oper_status', '#ff4d4f', NULL, 'FALSE', 'TRUE', '', '2020-12-24 10:02:40', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (67, 1, '立即执行', '1', 'job_misfire_polic', '', NULL, 'FALSE', 'TRUE', '', '2020-12-25 11:03:04', '', '2020-12-25 11:03:30', '');
INSERT INTO `sys_dict_data` VALUES (68, 2, '执行一次', '2', 'job_misfire_polic', '', NULL, 'FALSE', 'TRUE', '', '2020-12-25 11:03:11', '', '2020-12-25 11:03:27', '');
INSERT INTO `sys_dict_data` VALUES (69, 3, '放弃执行', '3', 'job_misfire_polic', NULL, NULL, 'TRUE', 'TRUE', '', '2020-12-25 11:03:24', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (70, 0, '允许', '0', 'job_concurrent', NULL, NULL, 'FALSE', 'TRUE', '', '2020-12-25 11:04:48', '', NULL, '允许并发执行定时任务');
INSERT INTO `sys_dict_data` VALUES (71, 1, '禁止', '1', 'job_concurrent', NULL, NULL, 'TRUE', 'TRUE', '', '2020-12-25 11:05:04', '', NULL, '禁止并发执行定时任务');
INSERT INTO `sys_dict_data` VALUES (72, 0, '正常', '0', 'job_status', NULL, NULL, 'TRUE', 'TRUE', '', '2020-12-25 11:05:30', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (73, 1, '暂停', '1', 'job_status', NULL, NULL, 'FALSE', 'TRUE', '', '2020-12-25 11:05:36', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (74, 0, '成功', '0', 'job_run_status', NULL, NULL, 'FALSE', 'TRUE', '', '2020-12-25 13:34:55', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (75, 0, '失败', '1', 'job_run_status', NULL, NULL, 'FALSE', 'TRUE', '', '2020-12-25 13:35:00', '', NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
