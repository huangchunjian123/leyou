/*
 Navicat Premium Data Transfer

 Source Server         : 134
 Source Server Type    : MySQL
 Source Server Version : 50537
 Source Host           : 120.26.127.134:3306
 Source Schema         : admin_system

 Target Server Type    : MySQL
 Target Server Version : 50537
 File Encoding         : 65001

 Date: 22/08/2018 20:36:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `head_portrait` varchar(300) DEFAULT NULL COMMENT '头像',
  `account` varchar(50) DEFAULT NULL COMMENT '管理员帐号',
  `name` varchar(50) DEFAULT NULL COMMENT '管理员姓名',
  `password` varchar(200) DEFAULT NULL COMMENT '管理员密码',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `is_enable` varchar(10) DEFAULT NULL COMMENT '是否启用',
  `admin_type` int(11) DEFAULT NULL COMMENT '管理员类型',
  `description` varchar(1000) DEFAULT '' COMMENT '描述',
  `login_num` int(11) DEFAULT '0' COMMENT '登录次数',
  `last_login` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(30) DEFAULT NULL COMMENT '最后登录IP',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
BEGIN;
INSERT INTO `sys_admin` VALUES (1, 'upload/images/20171020/20171020181056.jpg', 'yzz', '杨贞泽', '02CF6485D51F95F1109A0C937EC7BB80', '0', '', '', NULL, 1, '', 6, '2017-10-20 18:30:48', '0:0:0:0:0:0:0:1', '2017-10-20 18:10:56');
COMMIT;

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dic_parent_id` int(11) DEFAULT NULL,
  `dic_name` varchar(255) DEFAULT NULL,
  `dic_code` varchar(255) DEFAULT NULL,
  `dic_value` varchar(255) DEFAULT NULL,
  `create_name` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_name` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
BEGIN;
INSERT INTO `sys_dictionary` VALUES (1, NULL, '性别', 'gender', NULL, '杨贞泽', '2017-04-28 11:09:24', '杨贞泽', '2017-04-28 11:09:24');
INSERT INTO `sys_dictionary` VALUES (6, 1, '男', NULL, '0', '杨贞泽', '2017-05-02 21:24:21', '杨贞泽', '2017-05-02 21:24:21');
INSERT INTO `sys_dictionary` VALUES (7, 1, '女', NULL, '1', '杨贞泽', '2017-05-02 21:24:30', '杨贞泽', '2017-05-02 21:24:30');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` varchar(32) DEFAULT '' COMMENT '系统用户ID',
  `from` varchar(255) DEFAULT NULL COMMENT '来源 url',
  `ip` varchar(30) DEFAULT NULL COMMENT '客户端IP',
  `url` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL COMMENT '记录时间',
  `err_msg` text COMMENT '异常信息',
  `err_code` int(10) DEFAULT '0' COMMENT '状态码，0：正常',
  `class_name` varchar(200) DEFAULT NULL COMMENT 'controller类名',
  `method_name` varchar(64) DEFAULT NULL COMMENT '方法名',
  `start_time` datetime DEFAULT NULL COMMENT '操作时间',
  `spend_time` bigint(20) DEFAULT NULL COMMENT '耗时，毫秒',
  `admin_name` varchar(50) DEFAULT '' COMMENT '管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11514 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `permission_name` varchar(50) DEFAULT NULL COMMENT '权限名',
  `permission_type` int(10) DEFAULT '0' COMMENT '权限类型0.菜单，1.功能',
  `visit_url` varchar(50) DEFAULT NULL COMMENT '访问路径',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级id',
  `create_name` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_name` varchar(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (1, '系统管理', 0, 'system', 'dashboard', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (2, '权根分配', 0, 'permission', 'component', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (48, '管理员管理', 0, 'admin', 'peoples', 0, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (49, '系统角色', 0, 'role', 'people', 1, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (50, '数据字典', 0, 'dictionary', 'documentation', 2, 1, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名',
  `description` varchar(1000) DEFAULT '' COMMENT '描述',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '超级管理员', '拥有至高无上的权限！！！', '2017-05-03 15:51:30');
INSERT INTO `sys_role` VALUES (2, '管理员', '没什么权限', '2017-05-05 14:34:06');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_per
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_per`;
CREATE TABLE `sys_role_per` (
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `per_id` int(11) DEFAULT NULL COMMENT '根权id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_per
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_per` VALUES (1, 1);
INSERT INTO `sys_role_per` VALUES (1, 2);
INSERT INTO `sys_role_per` VALUES (1, 48);
INSERT INTO `sys_role_per` VALUES (1, 49);
INSERT INTO `sys_role_per` VALUES (1, 50);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
