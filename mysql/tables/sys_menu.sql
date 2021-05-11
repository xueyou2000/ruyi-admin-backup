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

 Date: 11/05/2021 14:00:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单Id',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_name` varchar(50) DEFAULT NULL COMMENT '父菜单名称',
  `parent_id` int(11) unsigned DEFAULT '0' COMMENT '父菜单ID',
  `menu_key` varchar(100) DEFAULT NULL COMMENT '菜单url',
  `component` varchar(50) DEFAULT NULL COMMENT '前端组件',
  `target` varchar(50) DEFAULT NULL COMMENT '打开方式',
  `order_num` int(11) DEFAULT '0' COMMENT '显示顺序',
  `menu_type` varchar(10) NOT NULL COMMENT '菜单类型',
  `visible` varchar(10) DEFAULT '0' COMMENT '菜单可视状态',
  `perms` varchar(50) DEFAULT NULL COMMENT '权限字符',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `path` varchar(150) DEFAULT NULL COMMENT '链接地址',
  `redirect` varchar(150) DEFAULT NULL COMMENT '重定向地址',
  `hidden_children` varchar(30) DEFAULT 'FALSE' COMMENT '是否隐藏子菜单',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, 'welcome', NULL, 0, '/', 'PageView', NULL, 1, 'M', '0', NULL, 'dashboard', NULL, NULL, 'FALSE', '', '2020-12-17 17:16:30', '', '2020-12-18 17:57:03', NULL);
INSERT INTO `sys_menu` VALUES (2, 'system', NULL, 0, '/system', 'PageView', '', 2, 'M', '0', '', 'setting', '', '', 'FALSE', '', '2020-12-18 10:05:46', '', '2020-12-18 10:05:46', '');
INSERT INTO `sys_menu` VALUES (3, 'system.menu', NULL, 2, '/system/permission', '', NULL, 3, 'C', '0', 'system:menu:view', 'menu', NULL, NULL, 'FALSE', '', '2020-12-18 10:09:13', '', '2020-12-18 10:09:13', NULL);
INSERT INTO `sys_menu` VALUES (4, 'system.user', NULL, 2, '/system/user', NULL, NULL, 1, 'C', '0', 'system:user:view', 'user', NULL, NULL, 'FALSE', '', '2020-12-18 10:22:41', '', '2020-12-18 10:22:41', NULL);
INSERT INTO `sys_menu` VALUES (5, 'monitor.online-user', NULL, 6, '/monitor/online', NULL, NULL, 1, 'C', '0', 'monitor:online:view', NULL, NULL, NULL, 'FALSE', '', '2020-12-18 10:26:45', '', '2020-12-25 10:59:42', NULL);
INSERT INTO `sys_menu` VALUES (6, 'monitor', NULL, 0, '/monitor', NULL, NULL, 3, 'M', '0', NULL, 'video-camera', NULL, NULL, 'FALSE', '', '2020-12-18 11:06:49', '', '2020-12-18 11:06:49', NULL);
INSERT INTO `sys_menu` VALUES (7, 'system.role', NULL, 2, '/system/role', NULL, NULL, 2, 'C', '0', 'system:role:view', 'safety', NULL, NULL, 'FALSE', '', '2020-12-18 11:34:52', '', '2020-12-22 14:04:44', NULL);
INSERT INTO `sys_menu` VALUES (8, 'monitor.job', NULL, 6, '/monitor/job', NULL, NULL, 2, 'C', '0', 'monitor:job:view', NULL, NULL, NULL, 'FALSE', '', '2020-12-18 11:35:20', '', '2020-12-25 10:59:49', NULL);
INSERT INTO `sys_menu` VALUES (11, 'monitor.operlog', NULL, 6, '/monitor/operLog', NULL, NULL, 3, 'C', '0', 'monitor:operlog:view', NULL, NULL, NULL, 'FALSE', '', '2020-12-18 11:38:46', '', '2020-12-25 10:59:57', NULL);
INSERT INTO `sys_menu` VALUES (12, 'system.dept', NULL, 2, '/system/dept', NULL, NULL, 4, 'C', '0', 'system:dept:view', NULL, NULL, NULL, 'FALSE', '', '2020-12-18 11:39:04', '', '2020-12-22 14:04:56', NULL);
INSERT INTO `sys_menu` VALUES (13, 'monitor.loginLog', NULL, 6, '/monitor/loginLog', '', '', 4, 'C', '0', 'monitor:loginlog:view', '', '', '', 'FALSE', '', '2020-12-18 11:39:21', '', '2020-12-25 11:00:04', '');
INSERT INTO `sys_menu` VALUES (14, 'system.dict', NULL, 2, '/system/dict', NULL, NULL, 6, 'C', '0', 'system:dict:view', 'snippets', NULL, NULL, 'FALSE', '', '2020-12-18 11:39:42', '', '2020-12-22 14:05:23', NULL);
INSERT INTO `sys_menu` VALUES (15, 'system.config', NULL, 2, '/system/config', NULL, NULL, 7, 'C', '0', 'system:config:view', 'control', NULL, NULL, 'FALSE', '', '2020-12-18 11:40:08', '', '2020-12-22 14:05:34', NULL);
INSERT INTO `sys_menu` VALUES (16, 'system.notice', NULL, 2, '/system/notice', NULL, NULL, 8, 'C', '0', 'system:notice:view', 'sound', NULL, NULL, 'FALSE', '', '2020-12-18 11:40:32', '', '2020-12-22 14:05:38', NULL);
INSERT INTO `sys_menu` VALUES (17, 'system.oss', NULL, 2, '/system/oss', NULL, NULL, 10, 'C', '0', 'system:oss:view', 'file', NULL, NULL, 'FALSE', '', '2020-12-18 11:40:54', '', '2020-12-30 11:45:15', NULL);
INSERT INTO `sys_menu` VALUES (20, 'system.menu.list', NULL, 3, NULL, NULL, NULL, 1, 'F', '0', 'system:menu:list', NULL, NULL, NULL, 'FALSE', '', '2020-12-22 11:29:40', '', '2020-12-22 13:47:31', NULL);
INSERT INTO `sys_menu` VALUES (21, 'system.menu.add', NULL, 3, NULL, NULL, NULL, 2, 'F', '0', 'system:menu:add', NULL, NULL, NULL, 'FALSE', '', '2020-12-22 13:45:27', '', '2020-12-22 13:47:22', NULL);
INSERT INTO `sys_menu` VALUES (22, 'system.menu.update', NULL, 3, NULL, NULL, NULL, 3, 'F', '0', 'system:menu:update', NULL, NULL, NULL, 'FALSE', '', '2020-12-22 13:46:28', '', '2020-12-22 13:47:13', NULL);
INSERT INTO `sys_menu` VALUES (23, 'system.menu.remove', NULL, 3, NULL, NULL, NULL, 4, 'F', '0', 'system:menu:remove', NULL, NULL, NULL, 'FALSE', '', '2020-12-22 13:46:58', '', '2020-12-22 13:46:58', NULL);
INSERT INTO `sys_menu` VALUES (24, 'system.dict.list', NULL, 14, NULL, NULL, NULL, 1, 'F', '0', 'system:dict:list', NULL, NULL, NULL, 'FALSE', '', '2020-12-22 13:51:45', '', '2020-12-22 13:51:45', NULL);
INSERT INTO `sys_menu` VALUES (25, 'system.dict.add', NULL, 14, NULL, NULL, NULL, 2, 'F', '0', 'system:dict:add', NULL, NULL, NULL, 'FALSE', '', '2020-12-22 13:52:05', '', '2020-12-22 13:52:05', NULL);
INSERT INTO `sys_menu` VALUES (26, 'system.dict.update', NULL, 14, NULL, NULL, NULL, 3, 'F', '0', 'system:dict:update', NULL, NULL, NULL, 'FALSE', '', '2020-12-22 13:52:23', '', '2020-12-22 13:52:23', NULL);
INSERT INTO `sys_menu` VALUES (27, 'system.dict.remove', NULL, 14, NULL, NULL, NULL, 4, 'F', '0', 'system:dict:remove', NULL, NULL, NULL, 'FALSE', '', '2020-12-22 13:52:51', '', '2020-12-22 13:52:51', NULL);
INSERT INTO `sys_menu` VALUES (28, 'system.dict.export', NULL, 14, NULL, NULL, NULL, 5, 'F', '0', 'system:dict:export', NULL, NULL, NULL, 'FALSE', '', '2020-12-22 13:53:10', '', '2020-12-22 13:53:10', NULL);
INSERT INTO `sys_menu` VALUES (29, 'monitor.online.list', NULL, 5, NULL, NULL, NULL, 1, 'F', '0', 'monitor:online:list', NULL, NULL, NULL, 'FALSE', '', '2020-12-30 11:32:12', '', '2020-12-30 11:32:12', NULL);
INSERT INTO `sys_menu` VALUES (30, 'monitor.online.forceLogout', NULL, 5, NULL, NULL, NULL, 2, 'F', '0', 'monitor:online:forceLogout', NULL, NULL, NULL, 'FALSE', '', '2020-12-30 11:32:53', '', '2020-12-30 11:33:14', NULL);
INSERT INTO `sys_menu` VALUES (31, 'monitor.job.list', NULL, 8, NULL, NULL, NULL, 1, 'F', '0', 'monitor:job:list', NULL, NULL, NULL, 'FALSE', '', '2020-12-30 11:37:15', '', '2020-12-30 11:37:30', NULL);
INSERT INTO `sys_menu` VALUES (32, 'monitor.job.add', NULL, 8, NULL, NULL, NULL, 2, 'F', '0', 'monitor:job:add', NULL, NULL, NULL, 'FALSE', '', '2020-12-30 11:37:49', '', '2020-12-30 11:37:49', NULL);
INSERT INTO `sys_menu` VALUES (33, 'monitor.job.update', NULL, 8, '', '', '', 3, 'F', '0', 'monitor:job:update', '', '', '', 'FALSE', '', '2020-12-30 11:38:07', '', '2020-12-30 11:54:42', '');
INSERT INTO `sys_menu` VALUES (34, 'monitor.job.remove', NULL, 8, '', '', '', 4, 'F', '0', 'monitor:job:remove', '', '', '', 'FALSE', '', '2020-12-30 11:38:19', '', '2020-12-30 11:54:24', '');
INSERT INTO `sys_menu` VALUES (35, 'monitor.job.changeStatus', NULL, 8, '', '', '', 6, 'F', '0', 'monitor:job:changeStatus', '', '', '', 'FALSE', '', '2020-12-30 11:38:34', '', '2020-12-30 11:38:34', '');
INSERT INTO `sys_menu` VALUES (36, 'monitor.operlog.list', NULL, 11, '', '', '', 1, 'F', '0', 'monitor:operlog:list', '', '', '', 'FALSE', '', '2020-12-30 11:40:29', '', '2020-12-30 11:40:29', '');
INSERT INTO `sys_menu` VALUES (37, 'monitor.operlog.remove', NULL, 11, '', '', '', 2, 'F', '0', 'monitor:operlog:remove', '', '', '', 'FALSE', '', '2020-12-30 11:40:43', '', '2020-12-30 11:40:43', '');
INSERT INTO `sys_menu` VALUES (38, 'monitor.operlog.export', NULL, 11, '', '', '', 3, 'F', '0', 'monitor:operlog:export', '', '', '', 'FALSE', '', '2020-12-30 11:40:56', '', '2020-12-30 11:40:56', '');
INSERT INTO `sys_menu` VALUES (39, 'monitor.loginLog.list', NULL, 13, '', '', '', 1, 'F', '0', 'monitor:loginlog:list', '', '', '', 'FALSE', '', '2020-12-30 11:41:50', '', '2020-12-30 11:41:50', '');
INSERT INTO `sys_menu` VALUES (40, 'monitor.loginLog.remove', NULL, 13, '', '', '', 2, 'F', '0', 'monitor:loginlog:remove', '', '', '', 'FALSE', '', '2020-12-30 11:42:08', '', '2020-12-30 11:42:08', '');
INSERT INTO `sys_menu` VALUES (41, 'monitor.loginLog.export', NULL, 13, '', '', '', 3, 'F', '0', 'monitor:loginlog:export', '', '', '', 'FALSE', '', '2020-12-30 11:42:25', '', '2020-12-30 11:42:25', '');
INSERT INTO `sys_menu` VALUES (42, 'system.oss.add', NULL, 17, 'system:oss:add', NULL, NULL, 1, 'F', '0', 'system:oss:add', NULL, NULL, NULL, 'FALSE', '', '2020-12-30 11:49:00', '', '2020-12-30 11:51:18', NULL);
INSERT INTO `sys_menu` VALUES (43, 'system.oss.update', NULL, 17, 'system:oss:update', NULL, NULL, 2, 'F', '0', 'system:oss:update', NULL, NULL, NULL, 'FALSE', '', '2020-12-30 11:49:13', '', '2020-12-30 11:51:26', NULL);
INSERT INTO `sys_menu` VALUES (44, 'system.oss.remove', NULL, 17, 'system:oss:remove', NULL, NULL, 3, 'F', '0', 'system:oss:remove', NULL, NULL, NULL, 'FALSE', '', '2020-12-30 11:49:27', '', '2020-12-30 11:51:33', NULL);
INSERT INTO `sys_menu` VALUES (45, 'system.oss.config', NULL, 17, 'system:oss:config', NULL, NULL, 4, 'F', '0', 'system:oss:config', NULL, NULL, NULL, 'FALSE', '', '2020-12-30 11:49:41', '', '2020-12-30 11:51:38', NULL);
INSERT INTO `sys_menu` VALUES (46, 'system.config.add', NULL, 15, '', '', '', 1, 'F', '0', 'system:config:add', '', '', '', 'FALSE', '', '2020-12-30 11:50:51', '', '2020-12-30 11:50:51', '');
INSERT INTO `sys_menu` VALUES (47, 'system.config.update', NULL, 15, NULL, NULL, NULL, 2, 'F', '0', 'system:config:update', NULL, NULL, NULL, 'FALSE', '', '2020-12-30 11:51:11', '', '2020-12-30 11:51:11', NULL);
INSERT INTO `sys_menu` VALUES (49, 'system.user.add', NULL, 4, NULL, NULL, NULL, 1, 'F', '0', 'system:user:add', NULL, NULL, NULL, 'FALSE', '', '2020-12-31 15:37:15', '', '2020-12-31 15:37:15', NULL);
INSERT INTO `sys_menu` VALUES (50, 'system.user.update', NULL, 4, NULL, NULL, NULL, 2, 'F', '0', 'system:user:update', NULL, NULL, NULL, 'FALSE', '', '2020-12-31 15:38:08', '', '2020-12-31 15:38:08', NULL);
INSERT INTO `sys_menu` VALUES (51, 'system.user.remove', NULL, 4, NULL, NULL, NULL, 3, 'F', '0', 'system:user:remove', NULL, NULL, NULL, 'FALSE', '', '2020-12-31 15:38:26', '', '2020-12-31 15:38:26', NULL);
INSERT INTO `sys_menu` VALUES (52, 'system.user.resetPwd', NULL, 4, NULL, NULL, NULL, 4, 'F', '0', 'system:user:resetPwd', NULL, NULL, NULL, 'FALSE', '', '2020-12-31 15:39:01', '', '2020-12-31 15:39:01', NULL);
INSERT INTO `sys_menu` VALUES (53, 'system.role.add', NULL, 7, NULL, NULL, NULL, 1, 'F', '0', 'system:role:add', NULL, NULL, NULL, 'FALSE', '', '2020-12-31 15:39:26', '', '2020-12-31 15:39:26', NULL);
INSERT INTO `sys_menu` VALUES (54, 'system.role.update', NULL, 7, NULL, NULL, NULL, 2, 'F', '0', 'system:role:update', NULL, NULL, NULL, 'FALSE', '', '2020-12-31 15:39:43', '', '2020-12-31 15:39:43', NULL);
INSERT INTO `sys_menu` VALUES (55, 'system.role.remove', NULL, 7, NULL, NULL, NULL, 3, 'F', '0', 'system:role:remove', NULL, NULL, NULL, 'FALSE', '', '2020-12-31 15:39:55', '', '2020-12-31 15:39:55', NULL);
INSERT INTO `sys_menu` VALUES (56, 'system.dept.add', NULL, 12, NULL, NULL, NULL, 1, 'F', '0', 'system:dept:add', NULL, NULL, NULL, 'FALSE', '', '2020-12-31 15:40:19', '', '2020-12-31 15:40:19', NULL);
INSERT INTO `sys_menu` VALUES (57, 'system.dept.update', NULL, 12, NULL, NULL, NULL, 2, 'F', '0', 'system:dept:update', NULL, NULL, NULL, 'FALSE', '', '2020-12-31 15:40:33', '', '2020-12-31 15:40:33', NULL);
INSERT INTO `sys_menu` VALUES (58, 'system.dept.remove', NULL, 12, NULL, NULL, NULL, 3, 'F', '0', 'system:dept:remove', NULL, NULL, NULL, 'FALSE', '', '2020-12-31 15:40:51', '', '2020-12-31 15:40:51', NULL);
INSERT INTO `sys_menu` VALUES (59, 'system.role.view', NULL, 7, '', '', '', 0, 'F', '0', 'system:role:view', '', '', '', 'FALSE', '', '2021-05-10 14:42:39', '', '2021-05-10 14:42:39', '');
INSERT INTO `sys_menu` VALUES (60, 'system.user.view', NULL, 4, NULL, NULL, NULL, 0, 'F', '0', 'system:user:view', NULL, NULL, NULL, 'FALSE', '', '2021-05-10 14:44:02', '', '2021-05-10 14:44:02', NULL);
INSERT INTO `sys_menu` VALUES (61, 'system.dept.view', NULL, 12, NULL, NULL, NULL, 0, 'F', '0', 'system:dept:view', NULL, NULL, NULL, 'FALSE', '', '2021-05-10 14:48:07', '', '2021-05-10 14:48:07', NULL);
INSERT INTO `sys_menu` VALUES (62, 'system.config.view', NULL, 15, NULL, NULL, NULL, 0, 'F', '0', 'system:config:view', NULL, NULL, NULL, 'FALSE', '', '2021-05-10 14:48:50', '', '2021-05-10 14:48:50', NULL);
INSERT INTO `sys_menu` VALUES (63, 'system.oss.view', NULL, 17, NULL, NULL, NULL, 0, 'F', '0', 'system:oss:view', NULL, NULL, NULL, 'FALSE', '', '2021-05-10 14:49:23', '', '2021-05-10 14:49:23', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
