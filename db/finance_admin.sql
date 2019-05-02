/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : finance_admin

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-03-22 16:41:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '1',
  `salt` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `unique_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1e67f167d99c43f897ec6566043ad6ec', 'flyshy', 'eb3a90502fbe02c2d8de91e0aa307268', '1', '5016b9942433201bf3fe61992eacba71', '0', '2016-12-07 13:24:17', '2017-11-02 09:27:37');
INSERT INTO `user` VALUES ('6222fc35aa9f43fd8f752cf50938b914', '2', '6643b025872168b199309a07789cc4cd', '1', '26f00077a39765cefb1c0eb7b0043f7f', '0', '2019-03-22 16:22:42', '2019-03-22 16:36:01');
INSERT INTO `user` VALUES ('ad313d38fe9447ce863fe8584743a010', 'user', 'c5941c5f3bc693a75e6e863bd2c55ce3', '1', '1ab6d62faa91ae7deec76d6f13ef1600', '1', '2016-12-06 11:16:51', '2017-05-11 13:59:25');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `admin_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`admin_id`,`role_id`) USING BTREE,
  KEY `admin_role_foreign` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('1e67f167d99c43f897ec6566043ad6ec', 'cbe8356d64a8433cb5dad5c7fccf8dce');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `log_id` varchar(32) NOT NULL,
  `log_user` varchar(32) DEFAULT NULL,
  `log_time` datetime DEFAULT NULL,
  `log_ip` varchar(15) DEFAULT NULL,
  `log_action` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('0165d85a678543c9beac5960e2be9a02', 'console', '2018-06-14 15:54:55', '127.0.0.1', '');
INSERT INTO `log` VALUES ('02305d9b25214197b9e12bb8f2cd6cc5', 'console', '2017-11-13 10:47:27', '127.0.0.1', '');
INSERT INTO `log` VALUES ('024021f259444cbfbee7b40a2384d43b', 'console', '2017-03-09 17:23:54', '127.0.0.1', '');
INSERT INTO `log` VALUES ('080316deca174a5ea21b4529663e7c75', 'user', '2019-03-22 12:53:39', '127.0.0.1', '');
INSERT INTO `log` VALUES ('362570545cb44213acd298a8dc466468', 'user', '2019-03-22 16:28:13', '127.0.0.1', '');
INSERT INTO `log` VALUES ('4bd963addbec4168a1a161db2208ce27', 'user', '2019-03-22 16:22:42', '127.0.0.1', '');
INSERT INTO `log` VALUES ('50c5741713894a8183442738652c0340', 'user', '2019-03-22 14:52:58', '127.0.0.1', '');
INSERT INTO `log` VALUES ('5f341a701d7d455ead33755804f884fd', 'user', '2019-03-22 10:50:03', '127.0.0.1', '');
INSERT INTO `log` VALUES ('78404a4024c24b6299cade502c4b5d15', 'user', '2019-03-22 16:36:01', '127.0.0.1', '');
INSERT INTO `log` VALUES ('816a0b242e5241649ea7116494535cb3', 'user', '2019-03-22 13:52:48', '127.0.0.1', '');
INSERT INTO `log` VALUES ('859783014d3c44939884faec3185ec8f', 'user', '2019-03-22 14:12:22', '127.0.0.1', '');
INSERT INTO `log` VALUES ('96388653c3df481186e40cc69c7cc3a0', 'user', '2019-03-22 11:18:58', '127.0.0.1', '');
INSERT INTO `log` VALUES ('a19b12f3862c4a1bb00d5dfb384760fd', 'user', '2019-03-22 11:37:27', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c4bb8029834f4483ac822bfd5eb11a27', 'user', '2019-03-22 13:20:13', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c4ea4b07350a497f9565c7418863c371', 'user', '2019-03-22 12:37:21', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d5e48c129b6948d08fd4f167e70afacc', 'user', '2019-03-22 16:18:43', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d5e9edb1bd844e8597c589cd2a3db9e3', 'user', '2019-03-22 13:17:36', '127.0.0.1', '');
INSERT INTO `log` VALUES ('dbd45c7921bd46cda2a7ea27cc4af9aa', 'user', '2019-03-22 13:29:00', '127.0.0.1', '');
INSERT INTO `log` VALUES ('dcce3de49a2848588f7eb84e098ef2ea', 'user', '2019-03-22 13:44:23', '127.0.0.1', '');
INSERT INTO `log` VALUES ('def9ff39a10b43b6bc36bff8bd59eff1', 'user', '2019-03-22 12:43:02', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e3023cf5c736409785ce5746dd71aeb4', 'user', '2019-03-22 14:10:07', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f404609f37574a5f99ce55138fe1086e', 'user', '2019-03-22 16:36:01', '127.0.0.1', '');
INSERT INTO `log` VALUES ('fa96db6ea97b44b4b664c414cae5ce1a', 'user', '2019-03-22 16:09:44', '127.0.0.1', '');
INSERT INTO `log` VALUES ('ffac4c6b49684916b47a5fb5892e4405', 'user', '2019-03-22 14:14:23', '127.0.0.1', '');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `menu_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `menu_type` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '资源类型，菜单或都按钮(menu,button)',
  `menu_url` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `menu_code` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `parent_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `parent_ids` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `child_num` int(10) NOT NULL DEFAULT '0',
  `listorder` int(10) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('00dc5c51e4824f49a30013385f680b0c', '日志管理', 'auth', '/console/log/index', 'log:index', 'e5f52fe2115e46229c60803e478d2e9a', null, '0', '1', '2017-01-06 14:11:23', '2019-03-22 13:52:48');
INSERT INTO `menu` VALUES ('1cc3d9ad04e4424db1bb086d1678925e', '菜单删除', 'auth', '/console/menu/delete', 'menu:delete', '736bdf0b9aec4c59928a530e34bd9aad', null, '0', '0', '2017-05-10 16:45:30', '2017-05-10 16:45:30');
INSERT INTO `menu` VALUES ('2191c9efc2fa431bb427b81ad938e8aa', '角色保存', 'auth', '/console/role/save', 'role:save', '6cda978dc9404ba2bf5854b74735b0bc', null, '0', '0', '2017-05-10 16:41:21', '2017-05-10 16:41:21');
INSERT INTO `menu` VALUES ('362923d31e064f84adb8c23ba91e54d8', '管理员编辑', 'auth', '/console/user/from', 'console:edit', 'e0dde3b9227c471eb3bd2ba0a7fab131', null, '0', '0', '2017-05-08 14:57:39', '2017-05-10 16:40:47');
INSERT INTO `menu` VALUES ('3ac96215e82f40b5bfe442e6828641df', '系统管理', 'menu', '/console/system/console', 'system:console', '0', null, '3', '1', '2016-12-07 16:00:00', '2017-05-10 16:46:27');
INSERT INTO `menu` VALUES ('6580896645d046a0acf3c1194d7bbf8e', '管理员删除', 'menu', '/console/user/delete', 'console:delete', 'e0dde3b9227c471eb3bd2ba0a7fab131', null, '0', '0', '2017-05-10 16:39:44', '2017-05-10 16:39:44');
INSERT INTO `menu` VALUES ('6cda978dc9404ba2bf5854b74735b0bc', '角色管理', 'auth', '/console/role/index', 'role:index', '3ac96215e82f40b5bfe442e6828641df', null, '4', '2', '2016-12-07 16:47:40', '2016-12-07 16:47:40');
INSERT INTO `menu` VALUES ('736bdf0b9aec4c59928a530e34bd9aad', '菜单管理', 'auth', '/console/menu/index', 'menu:index', '3ac96215e82f40b5bfe442e6828641df', null, '3', '3', '2016-12-07 16:50:17', '2016-12-07 16:50:17');
INSERT INTO `menu` VALUES ('85dad2bd9023451fab632dcfc4357d3b', '管理员保存', 'auth', '/console/user/save', 'console:save', 'e0dde3b9227c471eb3bd2ba0a7fab131', null, '0', '0', '2017-05-10 16:38:07', '2017-05-10 16:41:00');
INSERT INTO `menu` VALUES ('8a653e3fb15642d9be6aad13b02009fb', '角色授权', 'auth', '/console/role/grant', 'role:grant', '6cda978dc9404ba2bf5854b74735b0bc', null, '0', '0', '2017-05-10 16:42:37', '2017-05-10 16:42:37');
INSERT INTO `menu` VALUES ('9f41af1454d046b596023a2822c5078c', '角色编辑', 'auth', '/console/role/from', 'role:edit', '6cda978dc9404ba2bf5854b74735b0bc', null, '0', '0', '2017-05-08 14:59:25', '2017-05-08 14:59:25');
INSERT INTO `menu` VALUES ('aab7966c97db4643a36cb5afa24be38b', '角色删除', 'menu', '/console/role/delete', 'role:delete', '6cda978dc9404ba2bf5854b74735b0bc', null, '0', '0', '2017-05-10 16:43:37', '2017-05-10 16:43:37');
INSERT INTO `menu` VALUES ('c5cca135ee534bfeb482fb04b9311982', '菜单编辑', 'auth', '/console/menu/from', 'menu:from', '736bdf0b9aec4c59928a530e34bd9aad', null, '0', '0', '2016-12-07 16:51:31', '2017-05-08 15:00:02');
INSERT INTO `menu` VALUES ('e0dde3b9227c471eb3bd2ba0a7fab131', '管理员管理', 'auth', '/console/user/index', 'console:index', '3ac96215e82f40b5bfe442e6828641df', null, '3', '1', '2016-12-07 16:45:47', '2017-05-10 16:39:08');
INSERT INTO `menu` VALUES ('e5f52fe2115e46229c60803e478d2e9a', '扩展设置', 'menu', '/console/system/setting', 'system:setting', '0', null, '2', '3', '2016-12-07 16:36:42', '2017-05-10 16:50:00');
INSERT INTO `menu` VALUES ('f4237d06c0c94906bdc04f5ed19cbaeb', '菜单保存', 'auth', '/console/menu/save', 'menu:save', '736bdf0b9aec4c59928a530e34bd9aad', null, '0', '0', '2017-05-10 16:44:51', '2017-05-10 16:44:51');
INSERT INTO `menu` VALUES ('ff69a8b50ae4409bb91b1df3e1b71660', 'tst', 'menu', 'tst', 'test', 'e5f52fe2115e46229c60803e478d2e9a', null, '0', '0', '2019-03-22 16:36:01', '2019-03-22 16:36:01');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `role_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `role_desc` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE KEY `role_name_unique` (`role_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '系统管理员', '系统管理员', '1', '2016-12-07 08:53:57', '2017-05-11 13:59:03');
INSERT INTO `role` VALUES ('653071673cf64d74a63fc46e998b7d8a', '测试', '测试', '1', '2019-03-22 16:36:01', '2019-03-22 16:36:01');
INSERT INTO `role` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '普通管理员', '普通管理员', '1', '2016-12-07 13:21:21', '2017-05-05 12:58:38');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `menu_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE,
  KEY `role_menu_foreign` (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('653071673cf64d74a63fc46e998b7d8a', '00dc5c51e4824f49a30013385f680b0c');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '2191c9efc2fa431bb427b81ad938e8aa');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '3ac96215e82f40b5bfe442e6828641df');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '6cda978dc9404ba2bf5854b74735b0bc');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '8a653e3fb15642d9be6aad13b02009fb');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '9f41af1454d046b596023a2822c5078c');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'aab7966c97db4643a36cb5afa24be38b');
INSERT INTO `role_menu` VALUES ('653071673cf64d74a63fc46e998b7d8a', 'e5f52fe2115e46229c60803e478d2e9a');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'e5f52fe2115e46229c60803e478d2e9a');
