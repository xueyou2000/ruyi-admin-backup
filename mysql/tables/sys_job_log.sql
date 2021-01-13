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

 Date: 04/01/2021 11:25:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `job_id` bigint(20) NOT NULL,
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 COMMENT='定时任务调度日志表';

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_job_log` VALUES (83, '测试无参', 'default', 'taskTest.noParams', '测试无参 总共耗时：0毫秒', '0', '', NULL, '2020-12-25 11:45:49', NULL, NULL, 114);
INSERT INTO `sys_job_log` VALUES (84, '测试无参', 'default', 'taskTest.noParams', '测试无参 总共耗时：0毫秒', '0', '', NULL, '2020-12-25 11:45:50', NULL, NULL, 114);
INSERT INTO `sys_job_log` VALUES (85, '测试无参', 'default', 'taskTest.noParams', '测试无参 总共耗时：0毫秒', '0', '', NULL, '2020-12-25 11:45:51', NULL, NULL, 114);
INSERT INTO `sys_job_log` VALUES (86, '测试无参', 'default', 'taskTest.noParams', '测试无参 总共耗时：0毫秒', '0', '', NULL, '2020-12-25 11:45:52', NULL, NULL, 114);
INSERT INTO `sys_job_log` VALUES (87, '测试单参', 'default', 'taskTest.params(\"XueYou\")', '测试单参 总共耗时：0毫秒', '1', 'NumberFormatException: For input string: \"\"XueYou\"\"', NULL, '2020-12-25 11:46:04', NULL, NULL, 115);
INSERT INTO `sys_job_log` VALUES (88, '测试单参', 'default', 'taskTest.params(\'XueYou\')', '测试单参 总共耗时：0毫秒', '0', '', NULL, '2020-12-25 11:46:05', NULL, NULL, 115);
INSERT INTO `sys_job_log` VALUES (89, '测试多参', 'default', 'taskTest.multipleParams(\'ry\', true, 2000L, 316.50D, 100)', '测试多参 总共耗时：0毫秒', '0', '', NULL, '2020-12-25 13:37:16', NULL, NULL, 116);
INSERT INTO `sys_job_log` VALUES (90, '测试无参', 'default', 'taskTest.noParams', '测试无参 总共耗时：0毫秒', '0', '', NULL, '2020-12-25 13:40:00', NULL, NULL, 114);
INSERT INTO `sys_job_log` VALUES (91, '测试单参', 'default', 'taskTest.params(\'XueYou\')', '测试单参 总共耗时：0毫秒', '0', '', NULL, '2020-12-25 13:40:01', NULL, NULL, 115);
INSERT INTO `sys_job_log` VALUES (92, '测试多参', 'default', 'taskTest.multipleParams(\'ry\', true, 2000L, 316.50D, 100)', '测试多参 总共耗时：0毫秒', '0', '', NULL, '2020-12-25 13:40:01', NULL, NULL, 116);
INSERT INTO `sys_job_log` VALUES (93, '测试无参', 'default', 'taskTest.noParams', '测试无参 总共耗时：0毫秒', '0', '', NULL, '2020-12-25 13:50:00', NULL, NULL, 114);
INSERT INTO `sys_job_log` VALUES (94, '测试单参', 'default', 'taskTest.params(\'XueYou\')', '测试单参 总共耗时：0毫秒', '0', '', NULL, '2020-12-25 13:50:01', NULL, NULL, 115);
INSERT INTO `sys_job_log` VALUES (95, '测试多参', 'default', 'taskTest.multipleParams(\'ry\', true, 2000L, 316.50D, 100)', '测试多参 总共耗时：0毫秒', '0', '', NULL, '2020-12-25 13:50:01', NULL, NULL, 116);
INSERT INTO `sys_job_log` VALUES (96, '测试无参', 'default', 'taskTest.noParams', '测试无参 总共耗时：0毫秒', '0', '', NULL, '2020-12-30 14:42:53', NULL, NULL, 114);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
