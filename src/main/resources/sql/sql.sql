USE test;

/* 测试表 */
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT '' COMMENT '姓名',
  `age` int(3) DEFAULT '0' COMMENT '年龄',
  `sex` tinyint(1) NOT NULL DEFAULT '1' COMMENT '性别(1.男 2.女)',
  `address` varchar(200) DEFAULT '' COMMENT '地址',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态(0.待处理 1.已处理)',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
insert into `user` (`id`, `name`, `age`, `sex`, `address`, `status`, `create_time`, `update_time`, `remark`) values('1','rocking','23','1','测试','0','2017-05-10 15:40:29','2017-05-27 17:39:19','备注');
insert into `user` (`id`, `name`, `age`, `sex`, `address`, `status`, `create_time`, `update_time`, `remark`) values('2','jack','23','1','测试','0','2017-05-10 15:40:33','2017-05-27 17:39:19','');
insert into `user` (`id`, `name`, `age`, `sex`, `address`, `status`, `create_time`, `update_time`, `remark`) values('3','lili','35','2','测试','0','2017-05-10 15:40:31','2017-05-27 17:39:19','');
insert into `user` (`id`, `name`, `age`, `sex`, `address`, `status`, `create_time`, `update_time`, `remark`) values('4','lucy','43','2','测试','0','2017-05-10 15:40:35','2017-05-27 17:39:19','');
insert into `user` (`id`, `name`, `age`, `sex`, `address`, `status`, `create_time`, `update_time`, `remark`) values('5','mali','54','1','测试','0','2017-05-10 15:40:36','2017-05-27 17:39:19','');
insert into `user` (`id`, `name`, `age`, `sex`, `address`, `status`, `create_time`, `update_time`, `remark`) values('6','liuwu','28','1','测试','0','2017-05-27 16:33:50','2017-05-27 17:39:19','');
insert into `user` (`id`, `name`, `age`, `sex`, `address`, `status`, `create_time`, `update_time`, `remark`) values('7','liuxinyi','1','2','','0','2017-05-27 16:33:52','2017-05-27 17:39:19','');


/* 权限系统表 */
CREATE TABLE `back_user` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_name` varchar(20) NOT NULL DEFAULT '用户名',
  `account_name` varchar(20) NOT NULL DEFAULT '账号',
  `password` varchar(100) NOT NULL DEFAULT '密码',
  `credentials_salt` varchar(100) DEFAULT NULL COMMENT '盐',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `locked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否锁定，0：否，1：是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户表';

CREATE TABLE `back_role` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `state` tinyint(1) DEFAULT NULL COMMENT '状态',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `role_key` varchar(50) DEFAULT NULL COMMENT '标识',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台角色表';

CREATE TABLE `back_resources` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `parent_id` bigint(30) DEFAULT NULL COMMENT '父id',
  `res_key` varchar(50) DEFAULT NULL COMMENT '资源标识',
  `type` tinyint(2) DEFAULT NULL COMMENT '类型',
  `res_url` varchar(200) DEFAULT NULL COMMENT 'url',
  `level` int(4) DEFAULT NULL COMMENT '优先级',
  `icon` varchar(100) DEFAULT NULL,
  `is_hide` tinyint(1) DEFAULT '0' COMMENT '是否隐藏，0：否，1：是',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

CREATE TABLE `back_user_role` (
  `user_id` bigint(30) NOT NULL,
  `role_id` bigint(30) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色中间表';

CREATE TABLE `back_res_role` (
  `res_id` bigint(30) NOT NULL DEFAULT '0',
  `role_id` bigint(30) NOT NULL DEFAULT '0',
  PRIMARY KEY (`role_id`,`res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源角色中间表';

CREATE TABLE `back_user_login` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(30) DEFAULT NULL COMMENT '用户id',
  `account_name` varchar(20) DEFAULT NULL COMMENT '账号名称',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `login_ip` varchar(40) DEFAULT NULL COMMENT '登录IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户登录记录表';

/* 操作日志表 */
CREATE TABLE `operation_log` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `oper_user_id` bigint(30) NOT NULL COMMENT '操作人',
  `oper_user_name` varchar(40) NOT NULL COMMENT '操作人姓名',
  `target_uid` bigint(30) DEFAULT NULL COMMENT '被操作人用户ID',
  `ip_address` varchar(16) NOT NULL COMMENT 'ip地址',
  `oper_type` int(1) NOT NULL DEFAULT '0' COMMENT '日志类型 1.后台用户日志 2.工单日志 3.充值支付日志 4.资源日志(权限管理) 5.资讯管理日志 6.代理日志 7.财务管理日志 8.运营管理日志',
  `content` varchar(250) DEFAULT NULL COMMENT '操作内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作记录';

-- 初始化数据
USE test;
insert into `back_user` (`id`, `user_name`, `account_name`, `password`, `credentials_salt`, `description`, `locked`, `create_time`) values('1','管理员','admin','108edbb3345a9940af23fff42b8c2fc2','cc06b590828f0522653da99440533187','','0','2016-09-28 15:14:39');
insert into `back_role` (`id`, `state`, `name`, `role_key`, `description`) values('1','0','超级管理员','SUPER','超级管理员');
insert into `back_role` (`id`, `state`, `name`, `role_key`, `description`) values('2','0','网站编辑','Normal','网站编辑');
insert into `back_role` (`id`, `state`, `name`, `role_key`, `description`) values('3','0','部门主管','High','中级权限');
insert into `back_role` (`id`, `state`, `name`, `role_key`, `description`) values('4','0','普通员工','Normal','初级权限');
insert into `back_role` (`id`, `state`, `name`, `role_key`, `description`) values('5','0','客服','kf','客服部门使用的账号');
insert into `back_role` (`id`, `state`, `name`, `role_key`, `description`) values('6','0','运营','yy','运营部门使用的账号');
insert into `back_role` (`id`, `state`, `name`, `role_key`, `description`) values('7','0','网编','WB','网站编辑使用的账号');
insert into `back_role` (`id`, `state`, `name`, `role_key`, `description`) values('8','0','市场','SC','市场部门使用的账号');
insert into `back_role` (`id`, `state`, `name`, `role_key`, `description`) values('9','0','测试','test','测试人员');




