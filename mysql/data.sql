LOCK TABLES `sys_dept` WRITE;
ALTER TABLE `sys_dept`
    DISABLE KEYS;

INSERT INTO `sys_dept` (`dept_id`, `parent_id`, `ancestors`, `dept_name`, `order_num`, `leader`, `leader_id`, `phone`,
                        `email`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (100, 0, '0', 'XueYou科技', 0, 'XueYou', 1, '15527568707', 'xueyoucd@gmail.com', '0', '0', 'admin',
        '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00');

ALTER TABLE `sys_dept`
    ENABLE KEYS;

UNLOCK TABLES;


INSERT INTO `sys_user` (`user_id`, `dept_id`, `login_name`, `user_name`, `user_type`, `email`, `phonenumber`, `sex`,
                        `avatar`, `password`, `salt`, `status`, `del_flag`, `login_ip`, `login_date`, `create_by`,
                        `create_time`, `update_by`, `update_time`, `remark`, `admin`)
VALUES (1, 100, 'admin', 'XueYou', 'SYSTEM', 'xueyoucd@gmail.com', '15527568707', '1', '',
        '002744371637157251013aa5118906d6', 'zzx9Hm', '0', '0', '127.0.0.1', '2018-03-16 11:33:00', 'admin',
        '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00', '内置超级用户', 'TRUE');

INSERT INTO `sys_role` (`role_id`, `role_name`, `role_key`, `role_sort`, `data_scope`, `status`, `del_flag`,
                        `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1, '超级管理员', 'admin', '1', '1', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2018-03-16 11:33:00')