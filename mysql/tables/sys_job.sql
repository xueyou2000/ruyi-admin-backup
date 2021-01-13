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

 Date: 04/01/2021 11:25:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
BEGIN;
INSERT INTO `sys_job` VALUES (114, '测试无参', 'default', 'taskTest.noParams', '0 0/10 * * * ?', '3', '1', '1', '', '2020-12-25 13:38:10', 'admin', '2020-12-30 14:43:24', '');
INSERT INTO `sys_job` VALUES (115, '测试单参', 'default', 'taskTest.params(\'XueYou\')', '0 0/10 * * * ?', '3', '1', '1', '', '2020-12-25 13:38:12', 'admin', '2020-12-25 13:52:27', '');
INSERT INTO `sys_job` VALUES (116, '测试多参', 'default', 'taskTest.multipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0 0/10 * * * ?', '3', '1', '1', '', '2020-12-25 13:38:13', 'admin', '2020-12-25 13:52:25', '');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
