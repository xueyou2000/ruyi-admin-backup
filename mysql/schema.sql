/*==============================================================*/
/* Table: sys_dept                                               */
/*==============================================================*/

DROP TABLE IF EXISTS sys_dept;

create table sys_dept
(
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
    `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time`datetime null default CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time`datetime null default CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`dept_id`) USING BTREE
) CHARSET=utf8 comment='部门';


/*==============================================================*/
/* Table: sys_menu                                              */
/*==============================================================*/

DROP TABLE IF EXISTS sys_menu;

CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单Id',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_name` varchar(50) DEFAULT NULL COMMENT '父菜单名称',
  `parent_id` int(11) unsigned DEFAULT '0' COMMENT '父菜单ID',
  `menu_key` varchar(100) DEFAULT NULL COMMENT '菜单url',
  `component` varchar(50) DEFAULT NULL COMMENT '前端组件',
  `target` varchar(50) DEFAULT NULL COMMENT '打开方式',
  `order_num` varchar(50) DEFAULT '0' COMMENT '显示顺序',
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
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='菜单';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/

DROP TABLE IF EXISTS sys_role;

create table sys_role
(
    `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色Id',
    `role_name` varchar(30) NOT NULL COMMENT '角色名称',
    `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
    `role_sort` int(4) NOT NULL COMMENT '显示顺序',
    `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限）',
    `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time`datetime null default CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time`datetime null default CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`role_id`) USING BTREE
) CHARSET=utf8 comment='角色';

/*==============================================================*/
/* Table: sys_role_dept                                         */
/*==============================================================*/

DROP TABLE IF EXISTS sys_role_dept;

create table sys_role_dept
(
    `role_id` int(11) NOT NULL COMMENT '角色ID',
    `dept_id` int(11) NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`role_id`,`dept_id`) USING BTREE
) CHARSET=utf8 comment='角色部门关联表';

/*==============================================================*/
/* Table: sys_role_menu                                         */
/*==============================================================*/

DROP TABLE IF EXISTS sys_role_menu;

create table sys_role_menu
(
    `role_id` int(11) NOT NULL COMMENT '角色ID',
    `menu_id` int(11) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) CHARSET=utf8 comment='角色菜单关联表';



/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/

DROP TABLE IF EXISTS sys_user;

create table sys_user
(
    `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
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
    `admin` tinyint(1) unsigned DEFAULT 0 COMMENT '是否超级管理员',
    `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
    `login_ip` varchar(50) DEFAULT '' COMMENT '最后登陆IP',
    `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
    `admin` varchar(30) DEFAULT 'FALSE' COMMENT '是否超级管理员',
    `remark` varchar(500) DEFAULT '' COMMENT '备注',
    `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time`datetime null default CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time`datetime null default CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`user_id`) USING BTREE
) CHARSET=utf8 comment='用户';


/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/

DROP TABLE IF EXISTS sys_user_role;

create table sys_user_role
(
    `user_id` int(11) NOT NULL COMMENT '用户ID',
    `role_id` int(11) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) CHARSET=utf8 comment='用户角色关联表';


/*==============================================================*/
/* Table: sys_login_info                                        */
/*==============================================================*/

DROP TABLE IF EXISTS sys_login_info;

CREATE TABLE `sys_login_info` (
  `info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `login_name` varchar(50) DEFAULT '' COMMENT '登录账号',
  `user_id` int(11) NOT NULL,
  `ipaddr` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='登陆日志';


/*==============================================================*/
/* Table: sys_oper_log                                          */
/*==============================================================*/

DROP TABLE IF EXISTS sys_oper_log;

create table sys_oper_log
(
    `oper_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `title` varchar(50) DEFAULT '' COMMENT '模块标题',
    `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    `method` varchar(100) DEFAULT '' COMMENT '方法名称',
    `request_method` varchar(10) DEFAULT NULL,
    `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
    `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
    `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
    `oper_ip` varchar(50) DEFAULT '' COMMENT '主机地址',
    `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
    `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
    `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
    `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
    `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
    PRIMARY KEY (`oper_id`) USING BTREE
) CHARSET=utf8 comment='操作日志';


/*==============================================================*/
/* Table: sys_dict_type                                          */
/*==============================================================*/

DROP TABLE IF EXISTS sys_dict_type;

CREATE TABLE `sys_dict_type` (
  `dict_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` varchar(10) DEFAULT 'TRUE' COMMENT '状态（TRUE正常 FALSE停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE KEY `dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典类型表';


/*==============================================================*/
/* Table: sys_dict_data                                          */
/*==============================================================*/

DROP TABLE IF EXISTS sys_dict_data;


CREATE TABLE `sys_dict_data` (
  `dict_code` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` varchar(10) DEFAULT 'FALSE' COMMENT '是否默认（TRUE是 FALSE否）',
  `status` varchar(10) DEFAULT 'TRUE' COMMENT '状态（TRUE正常 FALSE停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典数据表';


/*==============================================================*/
/* Table: sys_job_log                                           */
/*==============================================================*/

DROP TABLE IF EXISTS sys_job_log;

CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 COMMENT='定时任务调度日志表';


/*==============================================================*/
/* Table: sys_job                                               */
/*==============================================================*/

DROP TABLE IF EXISTS sys_job;

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
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';




/*==============================================================*/
/* Table: qrtz_job_details                                      */
/*==============================================================*/

DROP TABLE IF EXISTS qrtz_job_details;

CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `job_class_name` varchar(250) NOT NULL,
  `is_durable` varchar(1) NOT NULL,
  `is_nonconcurrent` varchar(1) NOT NULL,
  `is_update_data` varchar(1) NOT NULL,
  `requests_recovery` varchar(1) NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: qrtz_triggers                                         */
/*==============================================================*/

DROP TABLE IF EXISTS qrtz_triggers;

CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `next_fire_time` bigint(13) DEFAULT NULL,
  `prev_fire_time` bigint(13) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) NOT NULL,
  `trigger_type` varchar(8) NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) DEFAULT NULL,
  `calendar_name` varchar(200) DEFAULT NULL,
  `misfire_instr` smallint(2) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: qrtz_simprop_triggers                                 */
/*==============================================================*/

DROP TABLE IF EXISTS qrtz_simprop_triggers;

CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `str_prop_1` varchar(512) DEFAULT NULL,
  `str_prop_2` varchar(512) DEFAULT NULL,
  `str_prop_3` varchar(512) DEFAULT NULL,
  `int_prop_1` int(11) DEFAULT NULL,
  `int_prop_2` int(11) DEFAULT NULL,
  `long_prop_1` bigint(20) DEFAULT NULL,
  `long_prop_2` bigint(20) DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) DEFAULT NULL,
  `bool_prop_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: qrtz_simple_triggers                                  */
/*==============================================================*/

DROP TABLE IF EXISTS qrtz_simple_triggers;

CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: qrtz_scheduler_state                                  */
/*==============================================================*/

DROP TABLE IF EXISTS qrtz_scheduler_state;

CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: qrtz_paused_trigger_grps                              */
/*==============================================================*/

DROP TABLE IF EXISTS qrtz_paused_trigger_grps;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: qrtz_locks                                            */
/*==============================================================*/

DROP TABLE IF EXISTS qrtz_locks;


CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL,
  `lock_name` varchar(40) NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: qrtz_fired_triggers                                   */
/*==============================================================*/

DROP TABLE IF EXISTS qrtz_fired_triggers;

CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `entry_id` varchar(95) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) NOT NULL,
  `job_name` varchar(200) DEFAULT NULL,
  `job_group` varchar(200) DEFAULT NULL,
  `is_nonconcurrent` varchar(1) DEFAULT NULL,
  `requests_recovery` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: qrtz_cron_triggers                                    */
/*==============================================================*/

DROP TABLE IF EXISTS qrtz_cron_triggers;

CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `cron_expression` varchar(200) NOT NULL,
  `time_zone_id` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: qrtz_calendars                                        */
/*==============================================================*/

DROP TABLE IF EXISTS qrtz_calendars;

CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL,
  `calendar_name` varchar(200) NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: qrtz_blob_triggers                                    */
/*==============================================================*/

DROP TABLE IF EXISTS qrtz_blob_triggers;

CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
