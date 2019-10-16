/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : lpmweb

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 16/10/2019 15:37:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lpm_course
-- ----------------------------
DROP TABLE IF EXISTS `lpm_course`;
CREATE TABLE `lpm_course`  (
  `course_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `course_id` int(11) NOT NULL,
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '任课教师',
  `course_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程链接',
  `course_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程信息',
  `domain_id` int(11) NULL DEFAULT NULL COMMENT '课程所属领域',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开课时间',
  PRIMARY KEY (`course_id`) USING BTREE,
  INDEX `domain_id`(`domain_id`) USING BTREE,
  CONSTRAINT `lpm_course_ibfk_1` FOREIGN KEY (`domain_id`) REFERENCES `lpm_domain` (`domain_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lpm_course
-- ----------------------------
INSERT INTO `lpm_course` VALUES ('计算机网络', 1, 1, NULL, NULL, 1, '2019-03-26 13:19:08');
INSERT INTO `lpm_course` VALUES ('数据结构', 2, 2, NULL, NULL, 1, '2019-03-26 13:19:08');

-- ----------------------------
-- Table structure for lpm_domain
-- ----------------------------
DROP TABLE IF EXISTS `lpm_domain`;
CREATE TABLE `lpm_domain`  (
  `domain_id` int(11) NOT NULL COMMENT '主键',
  `domain_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '领域名称',
  `domain_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '领域详细信息',
  PRIMARY KEY (`domain_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lpm_domain
-- ----------------------------
INSERT INTO `lpm_domain` VALUES (1, '计算机', '计算机科学，研究计算机及其周围各种现象和规律的科学，亦即研究计算机系统结构、程序系统（即软件）、人工智能以及计算本身的性质和问题的学科');
INSERT INTO `lpm_domain` VALUES (2, '心理学', '心理学包括基础心理学与应用心理学两大领域，其研究涉及知觉、认知、情绪、思维、人格、行为习惯、人际关系、社会关系等许多领域，也与日常生活的许多领域——家庭、教育、健康、社会等发生关联。');

-- ----------------------------
-- Table structure for lpm_knowledge_node
-- ----------------------------
DROP TABLE IF EXISTS `lpm_knowledge_node`;
CREATE TABLE `lpm_knowledge_node`  (
  `node_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点名称',
  `parent_id` int(32) NULL DEFAULT NULL COMMENT '父知识点id',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '知识点详细信息',
  PRIMARY KEY (`node_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lpm_knowledge_node
-- ----------------------------
INSERT INTO `lpm_knowledge_node` VALUES (1, '计算机网络', 0, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (2, '计算机网络结构', 1, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (3, '网络核心', 2, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (5, '网络应用', 1, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (6, '应用层', 5, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (7, 'web应用', 5, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (8, 'email应用', 5, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (9, 'DNS应用', 5, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (10, 'P2P应用', 5, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (11, 'Socket编程', 5, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (12, '传输层', 1, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (13, '滑动窗口协议', 12, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (14, '可靠数据传输', 12, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (15, 'TCP协议', 12, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (16, 'UDP协议', 12, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (17, '拥塞控制原理', 12, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (18, '网络层', 5, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (19, '网络层服务', 18, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (20, '虚电路网络', 18, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (21, '数据报网络', 18, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (22, 'IPV4协议', 18, NULL);
INSERT INTO `lpm_knowledge_node` VALUES (23, '知识结构根节点', NULL, NULL);

-- ----------------------------
-- Table structure for lpm_student
-- ----------------------------
DROP TABLE IF EXISTS `lpm_student`;
CREATE TABLE `lpm_student`  (
  `student_id` int(11) NOT NULL COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '系统登录用户',
  `student_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生姓名',
  PRIMARY KEY (`student_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `lpm_student_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lpm_student
-- ----------------------------
INSERT INTO `lpm_student` VALUES (1, 3, '郭阳');

-- ----------------------------
-- Table structure for lpm_student_course_relation
-- ----------------------------
DROP TABLE IF EXISTS `lpm_student_course_relation`;
CREATE TABLE `lpm_student_course_relation`  (
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `sc_id` int(11) NOT NULL,
  `score` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生对课程感兴趣程度',
  PRIMARY KEY (`sc_id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  CONSTRAINT `lpm_student_course_relation_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `lpm_student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lpm_student_course_relation_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `lpm_course` (`course_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lpm_student_course_relation
-- ----------------------------
INSERT INTO `lpm_student_course_relation` VALUES (1, 1, 1, '50');
INSERT INTO `lpm_student_course_relation` VALUES (1, 2, 2, '60');

-- ----------------------------
-- Table structure for lpm_student_knowledge_relation
-- ----------------------------
DROP TABLE IF EXISTS `lpm_student_knowledge_relation`;
CREATE TABLE `lpm_student_knowledge_relation`  (
  `sk_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NULL DEFAULT NULL,
  `node_id` int(11) NULL DEFAULT NULL,
  `score` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '知识点掌握程度',
  PRIMARY KEY (`sk_id`) USING BTREE,
  INDEX `lpm_student_knowledge_relation_ibfk_1`(`student_id`) USING BTREE,
  INDEX `lpm_student_knowledge_relation_ibfk_2`(`node_id`) USING BTREE,
  CONSTRAINT `lpm_student_knowledge_relation_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `lpm_student` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `lpm_student_knowledge_relation_ibfk_2` FOREIGN KEY (`node_id`) REFERENCES `lpm_knowledge_node` (`node_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lpm_student_knowledge_relation
-- ----------------------------
INSERT INTO `lpm_student_knowledge_relation` VALUES (1, 1, 2, '10');
INSERT INTO `lpm_student_knowledge_relation` VALUES (2, 1, 3, '20');
INSERT INTO `lpm_student_knowledge_relation` VALUES (4, 1, 5, '40');
INSERT INTO `lpm_student_knowledge_relation` VALUES (5, 1, 6, '50');
INSERT INTO `lpm_student_knowledge_relation` VALUES (6, 1, 7, '70');
INSERT INTO `lpm_student_knowledge_relation` VALUES (7, 1, 8, '20');
INSERT INTO `lpm_student_knowledge_relation` VALUES (8, 1, 9, '30');

-- ----------------------------
-- Table structure for lpm_student_topic_relation
-- ----------------------------
DROP TABLE IF EXISTS `lpm_student_topic_relation`;
CREATE TABLE `lpm_student_topic_relation`  (
  `student_id` int(11) NOT NULL,
  `topic_id` int(11) NULL DEFAULT NULL,
  `st_id` int(11) NOT NULL AUTO_INCREMENT,
  `score` int(255) NULL DEFAULT NULL COMMENT '学生对话题的感兴趣程度',
  PRIMARY KEY (`st_id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE,
  INDEX `topic_id`(`topic_id`) USING BTREE,
  CONSTRAINT `lpm_student_topic_relation_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `lpm_student` (`student_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `lpm_student_topic_relation_ibfk_2` FOREIGN KEY (`topic_id`) REFERENCES `lpm_topic` (`topic_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lpm_student_topic_relation
-- ----------------------------
INSERT INTO `lpm_student_topic_relation` VALUES (1, 1, 1, 10);
INSERT INTO `lpm_student_topic_relation` VALUES (1, 2, 2, 10);
INSERT INTO `lpm_student_topic_relation` VALUES (1, 3, 3, 10);
INSERT INTO `lpm_student_topic_relation` VALUES (1, 4, 4, 10);
INSERT INTO `lpm_student_topic_relation` VALUES (1, 5, 5, 10);
INSERT INTO `lpm_student_topic_relation` VALUES (1, 6, 6, 10);
INSERT INTO `lpm_student_topic_relation` VALUES (1, 7, 7, 10);
INSERT INTO `lpm_student_topic_relation` VALUES (1, 8, 8, 10);

-- ----------------------------
-- Table structure for lpm_teacher
-- ----------------------------
DROP TABLE IF EXISTS `lpm_teacher`;
CREATE TABLE `lpm_teacher`  (
  `teacher_id` int(11) NOT NULL COMMENT '主键',
  `teacher_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师姓名',
  `teacher_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '教师信息',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '系统登录用户',
  PRIMARY KEY (`teacher_id`) USING BTREE,
  INDEX `登录`(`user_id`) USING BTREE,
  CONSTRAINT `登录` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lpm_teacher
-- ----------------------------
INSERT INTO `lpm_teacher` VALUES (1, '李全龙', '主要教授计算机网络、物联网工程等课程', NULL);
INSERT INTO `lpm_teacher` VALUES (2, '黄虎杰', '主要教授数据结构、操作系统等课程', NULL);

-- ----------------------------
-- Table structure for lpm_topic
-- ----------------------------
DROP TABLE IF EXISTS `lpm_topic`;
CREATE TABLE `lpm_topic`  (
  `topic_id` int(255) NOT NULL,
  `topic_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '话题名称',
  `domain_id` int(255) NULL DEFAULT NULL COMMENT '话题所属领域',
  `topic_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '话题详细信息',
  PRIMARY KEY (`topic_id`) USING BTREE,
  INDEX `domain_id`(`domain_id`) USING BTREE,
  CONSTRAINT `lpm_topic_ibfk_1` FOREIGN KEY (`domain_id`) REFERENCES `lpm_domain` (`domain_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lpm_topic
-- ----------------------------
INSERT INTO `lpm_topic` VALUES (1, '计算机网络', 1, NULL);
INSERT INTO `lpm_topic` VALUES (2, '红黑树', 1, NULL);
INSERT INTO `lpm_topic` VALUES (3, '多线程', 1, NULL);
INSERT INTO `lpm_topic` VALUES (4, 'http', 1, NULL);
INSERT INTO `lpm_topic` VALUES (5, '虚拟地址', 1, NULL);
INSERT INTO `lpm_topic` VALUES (6, '银行家算法', 1, NULL);
INSERT INTO `lpm_topic` VALUES (7, '孤独', 2, NULL);
INSERT INTO `lpm_topic` VALUES (8, '焦虑', 2, NULL);

-- ----------------------------
-- Table structure for oauth_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_token`;
CREATE TABLE `oauth_token`  (
  `token_id` int(11) NOT NULL AUTO_INCREMENT,
  `access_token` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `permissions` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `roles` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `refresh_token` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `expire_time` datetime(0) NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`token_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_token
-- ----------------------------
INSERT INTO `oauth_token` VALUES (14, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNTcxMDI5Njg4fQ.gwSQxiwuPn1jiT5cmSbSqLeRQWJCVlfhqBBF-fHcNys', '1', '[\"delete:/v1/role/{id}\",\"get:/v1/menu\",\"post:/v1/menu\",\"put:/v1/menu\",\"delete:/v1/user/{id}\",\"delete:/v1/menu/{id}\",\"get:/v1/user\",\"get:/v1/user/info\",\"post:/v1/user/login\",\"get:/v1/user/menu\",\"delete:/v1/authorities/role\",\"put:/v1/user/state\",\"post:/v1/authorities/sync\",\"get:/v1/role\",\"get:/v1/authorities\",\"post:/v1/role\",\"post:/v1/user\",\"put:/v1/role\",\"put:/v1/user\",\"put:/v1/user/psw\",\"put:/v1/user/psw/{id}\",\"post:/v1/authorities/role\"]', '[\"1\"]', NULL, '2019-10-14 13:08:09', '2019-10-13 13:08:08', '2019-10-13 13:08:08');
INSERT INTO `oauth_token` VALUES (15, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNTcxMDI5NzQ5fQ.Bdcadi0Y-K7HCCD8j9vXbEixpEzDe4w4RiKMiMbGUr8', '1', '[\"delete:/v1/role/{id}\",\"get:/v1/menu\",\"post:/v1/menu\",\"put:/v1/menu\",\"delete:/v1/user/{id}\",\"delete:/v1/menu/{id}\",\"get:/v1/user\",\"get:/v1/user/info\",\"post:/v1/user/login\",\"get:/v1/user/menu\",\"delete:/v1/authorities/role\",\"put:/v1/user/state\",\"post:/v1/authorities/sync\",\"get:/v1/role\",\"get:/v1/authorities\",\"post:/v1/role\",\"post:/v1/user\",\"put:/v1/role\",\"put:/v1/user\",\"put:/v1/user/psw\",\"put:/v1/user/psw/{id}\",\"post:/v1/authorities/role\"]', '[\"1\"]', NULL, '2019-10-14 13:09:09', '2019-10-13 13:09:09', '2019-10-13 13:09:09');
INSERT INTO `oauth_token` VALUES (17, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiZXhwIjoxNTcxMDYzODUyfQ.EqWbmOlNG_S0OTIRcaFY2ZbVSy7VyPkXH3RNgku9r7k', '3', '[]', '[\"3\"]', NULL, '2019-10-14 22:37:33', '2019-10-13 22:37:32', '2019-10-13 22:37:32');
INSERT INTO `oauth_token` VALUES (18, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNTcxMDYzODYyfQ.-CxdDeXm5lRmltNuduN-rr7cSi_gCXJldYN4Rqw_-UU', '1', '[\"delete:/v1/role/{id}\",\"get:/v1/menu\",\"post:/v1/menu\",\"put:/v1/menu\",\"delete:/v1/user/{id}\",\"delete:/v1/menu/{id}\",\"get:/v1/user\",\"get:/v1/user/info\",\"post:/v1/user/login\",\"get:/v1/user/menu\",\"delete:/v1/authorities/role\",\"put:/v1/user/state\",\"post:/v1/authorities/sync\",\"get:/v1/role\",\"get:/v1/authorities\",\"post:/v1/role\",\"post:/v1/user\",\"put:/v1/role\",\"put:/v1/user\",\"put:/v1/user/psw\",\"put:/v1/user/psw/{id}\",\"post:/v1/authorities/role\"]', '[\"1\"]', NULL, '2019-10-14 22:37:42', '2019-10-13 22:37:42', '2019-10-13 22:37:42');
INSERT INTO `oauth_token` VALUES (19, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiZXhwIjoxNTcxMDYzOTMwfQ.qltoKOPDtawVBqJ81yrrbkPKKYmmatdRtTZ25hLZvFg', '3', '[\"get:/v1/authorities\",\"post:/v1/authorities/role\",\"delete:/v1/authorities/role\",\"post:/v1/authorities/sync\",\"get:/v1/menu\",\"post:/v1/menu\",\"put:/v1/menu\",\"delete:/v1/menu/{id}\",\"get:/v1/role\",\"post:/v1/role\",\"put:/v1/role\",\"delete:/v1/role/{id}\",\"get:/v1/user\",\"post:/v1/user\",\"put:/v1/user\",\"get:/v1/user/info\",\"post:/v1/user/login\",\"get:/v1/user/menu\",\"put:/v1/user/psw\",\"put:/v1/user/psw/{id}\",\"put:/v1/user/state\",\"delete:/v1/user/{id}\"]', '[\"3\"]', NULL, '2019-10-14 22:38:50', '2019-10-13 22:38:50', '2019-10-13 22:38:50');
INSERT INTO `oauth_token` VALUES (20, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiZXhwIjoxNTcxMjExNjUzfQ.nWR3o_6rukR7MCKRNqpSgpDKWM1YsyKX8nbqx2Xx-lo', '3', '[\"get:/v1/authorities\",\"post:/v1/authorities/role\",\"delete:/v1/authorities/role\",\"post:/v1/authorities/sync\",\"get:/v1/menu\",\"post:/v1/menu\",\"put:/v1/menu\",\"delete:/v1/menu/{id}\",\"get:/v1/role\",\"post:/v1/role\",\"put:/v1/role\",\"delete:/v1/role/{id}\",\"get:/v1/user\",\"post:/v1/user\",\"put:/v1/user\",\"get:/v1/user/info\",\"post:/v1/user/login\",\"get:/v1/user/menu\",\"put:/v1/user/psw\",\"put:/v1/user/psw/{id}\",\"put:/v1/user/state\",\"delete:/v1/user/{id}\"]', '[\"3\"]', NULL, '2019-10-16 15:40:53', '2019-10-15 15:40:53', '2019-10-15 15:40:53');

-- ----------------------------
-- Table structure for oauth_token_key
-- ----------------------------
DROP TABLE IF EXISTS `oauth_token_key`;
CREATE TABLE `oauth_token_key`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '生成token时的key',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_token_key
-- ----------------------------
INSERT INTO `oauth_token_key` VALUES (1, '97cf6f3ff5e0e01c1d08bc2bddf1e40f965518f58c3465ed79750d4d24aa3274', '2019-09-15 17:06:18');

-- ----------------------------
-- Table structure for sys_authorities
-- ----------------------------
DROP TABLE IF EXISTS `sys_authorities`;
CREATE TABLE `sys_authorities`  (
  `authority` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权标识',
  `authority_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `parent_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序号',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`authority`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_authorities
-- ----------------------------
INSERT INTO `sys_authorities` VALUES ('delete:/v1/authorities/role', '移除角色权限', '权限管理', 3, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('delete:/v1/menu/{id}', '删除菜单', '菜单管理', 8, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('delete:/v1/role/{id}', '删除角色', '角色管理', 12, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('delete:/v1/user/{id}', '删除用户', '用户管理', 22, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('get:/v1/authorities', '查询所有权限', '权限管理', 1, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('get:/v1/menu', '查询所有菜单', '菜单管理', 5, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('get:/v1/role', '查询所有角色', '角色管理', 9, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('get:/v1/user', '查询所有用户', '用户管理', 13, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('get:/v1/user/info', '获取个人信息', '个人信息', 16, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('get:/v1/user/menu', '获取所有菜单', '个人信息', 18, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('post:/v1/authorities/role', '给角色添加权限', '权限管理', 2, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('post:/v1/authorities/sync', '同步权限', '权限管理', 4, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('post:/v1/menu', '添加菜单', '菜单管理', 6, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('post:/v1/role', '添加角色', '角色管理', 10, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('post:/v1/user', '添加用户', '用户管理', 14, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('post:/v1/user/login', '用户登录', '个人信息', 17, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('put:/v1/menu', '修改菜单', '菜单管理', 7, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('put:/v1/role', '修改角色', '角色管理', 11, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('put:/v1/user', '修改用户', '用户管理', 15, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('put:/v1/user/psw', '修改自己密码', '用户管理', 19, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('put:/v1/user/psw/{id}', '重置密码', '用户管理', 20, '2019-03-29 13:07:48');
INSERT INTO `sys_authorities` VALUES ('put:/v1/user/state', '修改用户状态', '用户管理', 21, '2019-03-29 13:07:48');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `parent_id` int(11) NOT NULL DEFAULT -1 COMMENT '父级id',
  `menu_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单url',
  `menu_icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `sort_number` int(11) NOT NULL DEFAULT 0 COMMENT '排序号',
  `authority` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对应权限',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, -1, '系统管理', '', 'layui-icon layui-icon-set', 2, '', '2019-03-26 13:19:08', '2019-09-18 16:20:49');
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', '#/system/user', '', 3, 'get:/v1/user', '2019-03-26 16:21:17', '2019-03-29 13:59:13');
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', '#/system/role', '', 4, 'get:/v1/role', '2019-03-26 16:22:03', '2019-03-29 13:59:13');
INSERT INTO `sys_menu` VALUES (4, 1, '权限管理', '#/system/authorities', '', 5, 'get:/v1/authorities', '2019-03-26 16:22:28', '2019-03-29 13:59:14');
INSERT INTO `sys_menu` VALUES (5, 1, '菜单管理', '#/system/menu', '', 6, 'get:/v1/menu', '2019-03-26 16:28:14', '2019-03-29 13:59:14');
INSERT INTO `sys_menu` VALUES (6, -1, '系统功能', '', 'layui-icon layui-icon-engine', 7, '', '2019-03-28 16:44:19', '2019-03-29 13:59:15');
INSERT INTO `sys_menu` VALUES (7, 6, 'Druid监控', '#/tpl/iframe/id=druid', '', 8, '', '2019-03-28 16:45:34', '2019-03-29 13:59:19');
INSERT INTO `sys_menu` VALUES (8, 6, '接口文档', '#/tpl/iframe/id=swagger', '', 9, '', '2019-03-28 16:47:56', '2019-03-29 13:59:20');
INSERT INTO `sys_menu` VALUES (9, -1, '知识背景', '#/lpm/knowledge', '', 1, '', '2019-09-16 15:22:51', '2019-09-18 16:20:30');
INSERT INTO `sys_menu` VALUES (10, -1, '兴趣意图', '#/lpm/topic', '', 1, '', '2019-09-16 15:24:20', '2019-09-18 16:20:38');
INSERT INTO `sys_menu` VALUES (11, -1, '学习风格', '#/lpm/style', '', 1, '', '2019-09-16 15:28:37', '2019-09-18 16:20:34');
INSERT INTO `sys_menu` VALUES (12, -1, '学习状态', '#/lpm/state', '', 1, '', '2019-09-16 15:29:37', '2019-09-18 16:20:41');
INSERT INTO `sys_menu` VALUES (14, -1, '系统知识图谱', '#/lpm/graph', '', 1, '', '2019-09-16 19:04:52', '2019-09-18 16:21:16');
INSERT INTO `sys_menu` VALUES (15, -1, '数据管理', '#/lpm/portrait', '', 0, '', '2019-09-18 16:20:21', '2019-10-13 13:18:59');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `comments` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '管理员', '2018-12-19 23:11:29', '2018-12-19 23:11:29');
INSERT INTO `sys_role` VALUES (2, '普通用户', '普通用户', '2018-12-19 23:12:09', '2018-12-19 23:12:09');
INSERT INTO `sys_role` VALUES (3, '学习者', '', '2019-10-13 14:54:11', '2019-10-13 14:54:11');

-- ----------------------------
-- Table structure for sys_role_authorities
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authorities`;
CREATE TABLE `sys_role_authorities`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `authority` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_sys_role_permission_pm`(`authority`) USING BTREE,
  INDEX `FK_sys_role_permission_role`(`role_id`) USING BTREE,
  CONSTRAINT `sys_role_authorities_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_authorities
-- ----------------------------
INSERT INTO `sys_role_authorities` VALUES (1, 1, 'delete:/v1/role/{id}', '2018-12-19 23:11:36');
INSERT INTO `sys_role_authorities` VALUES (2, 1, 'get:/v1/menu', '2019-03-29 12:49:52');
INSERT INTO `sys_role_authorities` VALUES (3, 1, 'post:/v1/menu', '2019-03-29 12:49:53');
INSERT INTO `sys_role_authorities` VALUES (4, 1, 'put:/v1/menu', '2019-03-29 12:49:53');
INSERT INTO `sys_role_authorities` VALUES (5, 1, 'delete:/v1/user/{id}', '2019-03-29 12:50:04');
INSERT INTO `sys_role_authorities` VALUES (6, 1, 'delete:/v1/menu/{id}', '2019-03-29 12:50:44');
INSERT INTO `sys_role_authorities` VALUES (7, 1, 'get:/v1/user', '2019-03-29 12:50:45');
INSERT INTO `sys_role_authorities` VALUES (8, 1, 'get:/v1/user/info', '2019-03-29 13:47:08');
INSERT INTO `sys_role_authorities` VALUES (9, 1, 'post:/v1/user/login', '2019-03-29 13:47:08');
INSERT INTO `sys_role_authorities` VALUES (10, 1, 'get:/v1/user/menu', '2019-03-29 13:47:08');
INSERT INTO `sys_role_authorities` VALUES (11, 1, 'delete:/v1/authorities/role', '2018-12-26 10:32:09');
INSERT INTO `sys_role_authorities` VALUES (12, 1, 'put:/v1/user/state', '2018-12-19 23:11:50');
INSERT INTO `sys_role_authorities` VALUES (13, 1, 'post:/v1/authorities/sync', '2018-12-19 23:11:42');
INSERT INTO `sys_role_authorities` VALUES (14, 1, 'get:/v1/role', '2018-12-19 23:11:40');
INSERT INTO `sys_role_authorities` VALUES (15, 1, 'get:/v1/authorities', '2018-12-19 23:11:37');
INSERT INTO `sys_role_authorities` VALUES (16, 1, 'post:/v1/role', '2018-12-19 23:11:43');
INSERT INTO `sys_role_authorities` VALUES (17, 1, 'post:/v1/user', '2018-12-19 23:11:44');
INSERT INTO `sys_role_authorities` VALUES (18, 1, 'put:/v1/role', '2018-12-19 23:11:46');
INSERT INTO `sys_role_authorities` VALUES (19, 1, 'put:/v1/user', '2018-12-19 23:11:46');
INSERT INTO `sys_role_authorities` VALUES (20, 1, 'put:/v1/user/psw', '2018-12-19 23:11:47');
INSERT INTO `sys_role_authorities` VALUES (21, 1, 'put:/v1/user/psw/{id}', '2018-12-19 23:11:47');
INSERT INTO `sys_role_authorities` VALUES (22, 1, 'post:/v1/authorities/role', '2018-12-19 23:11:41');
INSERT INTO `sys_role_authorities` VALUES (23, 2, 'get:/v1/user/info', '2019-03-29 13:47:39');
INSERT INTO `sys_role_authorities` VALUES (24, 2, 'get:/v1/menu', '2019-03-29 13:07:36');
INSERT INTO `sys_role_authorities` VALUES (25, 2, 'get:/v1/user', '2019-03-29 12:50:56');
INSERT INTO `sys_role_authorities` VALUES (26, 2, 'get:/v1/authorities', '2018-12-19 23:12:35');
INSERT INTO `sys_role_authorities` VALUES (27, 2, 'get:/v1/role', '2018-12-19 23:13:06');
INSERT INTO `sys_role_authorities` VALUES (28, 2, 'get:/v1/user/menu', '2019-03-29 13:47:40');
INSERT INTO `sys_role_authorities` VALUES (33, 3, 'get:/v1/authorities', '2019-10-13 22:38:23');
INSERT INTO `sys_role_authorities` VALUES (34, 3, 'post:/v1/authorities/role', '2019-10-13 22:38:23');
INSERT INTO `sys_role_authorities` VALUES (35, 3, 'delete:/v1/authorities/role', '2019-10-13 22:38:24');
INSERT INTO `sys_role_authorities` VALUES (36, 3, 'post:/v1/authorities/sync', '2019-10-13 22:38:25');
INSERT INTO `sys_role_authorities` VALUES (37, 3, 'get:/v1/menu', '2019-10-13 22:38:25');
INSERT INTO `sys_role_authorities` VALUES (38, 3, 'post:/v1/menu', '2019-10-13 22:38:26');
INSERT INTO `sys_role_authorities` VALUES (39, 3, 'put:/v1/menu', '2019-10-13 22:38:26');
INSERT INTO `sys_role_authorities` VALUES (40, 3, 'delete:/v1/menu/{id}', '2019-10-13 22:38:27');
INSERT INTO `sys_role_authorities` VALUES (41, 3, 'get:/v1/role', '2019-10-13 22:38:27');
INSERT INTO `sys_role_authorities` VALUES (42, 3, 'post:/v1/role', '2019-10-13 22:38:29');
INSERT INTO `sys_role_authorities` VALUES (43, 3, 'put:/v1/role', '2019-10-13 22:38:29');
INSERT INTO `sys_role_authorities` VALUES (44, 3, 'delete:/v1/role/{id}', '2019-10-13 22:38:30');
INSERT INTO `sys_role_authorities` VALUES (45, 3, 'get:/v1/user', '2019-10-13 22:38:31');
INSERT INTO `sys_role_authorities` VALUES (46, 3, 'post:/v1/user', '2019-10-13 22:38:32');
INSERT INTO `sys_role_authorities` VALUES (47, 3, 'put:/v1/user', '2019-10-13 22:38:33');
INSERT INTO `sys_role_authorities` VALUES (48, 3, 'get:/v1/user/info', '2019-10-13 22:38:33');
INSERT INTO `sys_role_authorities` VALUES (49, 3, 'post:/v1/user/login', '2019-10-13 22:38:34');
INSERT INTO `sys_role_authorities` VALUES (50, 3, 'get:/v1/user/menu', '2019-10-13 22:38:35');
INSERT INTO `sys_role_authorities` VALUES (51, 3, 'put:/v1/user/psw', '2019-10-13 22:38:35');
INSERT INTO `sys_role_authorities` VALUES (52, 3, 'put:/v1/user/psw/{id}', '2019-10-13 22:38:37');
INSERT INTO `sys_role_authorities` VALUES (53, 3, 'put:/v1/user/state', '2019-10-13 22:38:38');
INSERT INTO `sys_role_authorities` VALUES (54, 3, 'delete:/v1/user/{id}', '2019-10-13 22:38:39');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '男' COMMENT '性别',
  `phone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `email_verified` int(1) NULL DEFAULT 0 COMMENT '邮箱是否验证，0未验证，1已验证',
  `true_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `department_id` int(11) NULL DEFAULT NULL COMMENT '部门id',
  `state` int(1) NOT NULL DEFAULT 0 COMMENT '状态，0正常，1冻结',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_account`(`username`) USING BTREE,
  INDEX `FK_sys_user`(`true_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', NULL, '男', '12345678901', NULL, 0, NULL, NULL, NULL, NULL, 0, '2018-12-19 23:30:05', '2019-03-29 13:06:48');
INSERT INTO `sys_user` VALUES (2, 'user01', 'e10adc3949ba59abbe56e057f20f883e', '用户01', NULL, '男', '12345678901', NULL, 0, NULL, NULL, NULL, NULL, 0, '2018-12-19 23:31:25', '2019-03-29 13:54:53');
INSERT INTO `sys_user` VALUES (3, '18S003084', 'e10adc3949ba59abbe56e057f20f883e', '郭阳', NULL, '女', '13242945898', NULL, 0, NULL, NULL, NULL, NULL, 0, '2019-10-13 14:54:41', '2019-10-13 14:54:41');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_sys_user_role`(`user_id`) USING BTREE,
  INDEX `FK_sys_user_role_role`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, '2018-12-19 23:30:06');
INSERT INTO `sys_user_role` VALUES (2, 2, 2, '2019-03-29 13:13:35');
INSERT INTO `sys_user_role` VALUES (3, 3, 3, '2019-10-13 14:54:41');

SET FOREIGN_KEY_CHECKS = 1;
