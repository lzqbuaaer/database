/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : student

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 09/12/2024 23:15:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'log',
  `operatorName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'admin',
  `operatorRole` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `operation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (1, '管理员', 'ADMIN', '系统初始化', '2024-12-09 16:51:29');
INSERT INTO `log` VALUES (3, '管理员', 'ADMIN', 'select * from student.tbadmin where username=admin', '2024-12-09 22:41:49');
INSERT INTO `log` VALUES (4, '管理员', 'ADMIN', 'select * from notice', '2024-12-09 22:41:50');
INSERT INTO `log` VALUES (5, '管理员', 'ADMIN', 'select * from student.studentcourse where sno=null order by scId desc', '2024-12-09 22:41:52');
INSERT INTO `log` VALUES (6, '管理员', 'ADMIN', 'select * from notice where title like concat(\'%\', , \'%\')', '2024-12-09 22:41:55');
INSERT INTO `log` VALUES (7, '管理员', 'ADMIN', 'select * from student.tbteacher where tno like concat(\'%\',,\'%\') and tname like concat(\'%\',,\'%\')  order by tno desc ', '2024-12-09 22:42:23');
INSERT INTO `log` VALUES (8, NULL, 'STUDENT', 'select * from student.tbstudent where sno=S01', '2024-12-09 22:42:38');
INSERT INTO `log` VALUES (9, '管理员', 'ADMIN', 'select * from notice', '2024-12-09 22:42:38');
INSERT INTO `log` VALUES (10, '学生一', 'STUDENT', 'select * from student.studentcourse where sno=S01 order by scId desc', '2024-12-09 22:42:39');
INSERT INTO `log` VALUES (11, '学生一', 'STUDENT', 'select * from student.tbcourse where cname like concat(\'%\',,\'%\') and cno like concat(\'%\',,\'%\') and tno like concat(\'%\',,\'%\') order by id desc', '2024-12-09 22:42:40');
INSERT INTO `log` VALUES (12, NULL, 'STUDENT', 'select * from student.tbstudent where sno=S01', '2024-12-09 22:43:04');
INSERT INTO `log` VALUES (13, '管理员', 'ADMIN', 'select * from notice', '2024-12-09 22:43:04');
INSERT INTO `log` VALUES (14, '学生一', 'STUDENT', 'select * from student.tbcourse where cname like concat(\'%\',,\'%\') and cno like concat(\'%\',,\'%\') and tno like concat(\'%\',,\'%\') order by id desc', '2024-12-09 23:00:12');
INSERT INTO `log` VALUES (15, '学生一', 'STUDENT', 'select * from student.tbcourse where cname like concat(\'%\',,\'%\') and cno like concat(\'%\',,\'%\') and tno like concat(\'%\',,\'%\') order by id desc', '2024-12-09 23:00:12');
INSERT INTO `log` VALUES (16, '管理员', 'ADMIN', 'select * from student.tbadmin where username=admin', '2024-12-09 23:00:26');
INSERT INTO `log` VALUES (17, '管理员', 'ADMIN', 'select * from notice', '2024-12-09 23:00:27');
INSERT INTO `log` VALUES (18, '管理员', 'ADMIN', 'select * from notice where title like concat(\'%\', , \'%\')', '2024-12-09 23:00:29');
INSERT INTO `log` VALUES (19, '管理员', 'ADMIN', 'select * from student.tbstudent where sno like concat(\'%\',,\'%\') and sname like concat(\'%\',,\'%\')  order by sno desc ', '2024-12-09 23:00:42');
INSERT INTO `log` VALUES (20, '管理员', 'ADMIN', 'select * from student.tbteacher where tno like concat(\'%\',,\'%\') and tname like concat(\'%\',,\'%\')  order by tno desc ', '2024-12-09 23:00:42');
INSERT INTO `log` VALUES (21, NULL, 'STUDENT', 'select * from student.tbstudent where sno=S01', '2024-12-09 23:00:49');
INSERT INTO `log` VALUES (22, '管理员', 'ADMIN', 'select * from notice', '2024-12-09 23:00:49');
INSERT INTO `log` VALUES (23, '学生一', 'STUDENT', 'select * from student.studentcourse where sno=S01 order by scId desc', '2024-12-09 23:00:52');
INSERT INTO `log` VALUES (24, '管理员', 'ADMIN', 'update student.tbstudent set sno=S01 ,sname=学生一123 ,ssex=男,sclass=20212831,sdept =计算机科学与技术 where sno=S01', '2024-12-09 23:01:02');
INSERT INTO `log` VALUES (25, '管理员', 'ADMIN', 'update student.tbstudent set sno=S01 ,sname=学生一 ,ssex=男,sclass=20212831,sdept =计算机科学与技术 where sno=S01', '2024-12-09 23:01:10');
INSERT INTO `log` VALUES (26, '管理员', 'ADMIN', 'select * from student.tbadmin where username=admin', '2024-12-09 23:10:43');
INSERT INTO `log` VALUES (27, '管理员', 'ADMIN', 'select * from notice', '2024-12-09 23:10:43');
INSERT INTO `log` VALUES (28, '管理员', 'ADMIN', 'select * from student.tbcourse where cname like concat(\'%\',,\'%\') and cno like concat(\'%\',,\'%\') and tno like concat(\'%\',,\'%\') order by id desc', '2024-12-09 23:10:44');
INSERT INTO `log` VALUES (29, '管理员', 'ADMIN', 'update student.tbcourse set cno=C08 ,tno=T01 ,cname=数据库,ccredit=4.0,cdescribe=1234,cday=4,ctime=3 where cno=C08', '2024-12-09 23:11:07');
INSERT INTO `log` VALUES (30, '管理员', 'ADMIN', 'select * from student.tbcourse where cname like concat(\'%\',,\'%\') and cno like concat(\'%\',,\'%\') and tno like concat(\'%\',,\'%\') order by id desc', '2024-12-09 23:11:07');
INSERT INTO `log` VALUES (31, '管理员', 'ADMIN', 'select * from student.tbadmin where username=admin', '2024-12-09 23:14:22');
INSERT INTO `log` VALUES (32, '管理员', 'ADMIN', 'select * from notice', '2024-12-09 23:14:23');
INSERT INTO `log` VALUES (33, '管理员', 'ADMIN', 'select * from student.studentcourse where sno=null order by scId desc', '2024-12-09 23:14:24');
INSERT INTO `log` VALUES (34, '管理员', 'ADMIN', 'select * from student.tbcourse where cname like concat(\'%\',,\'%\') and cno like concat(\'%\',,\'%\') and tno like concat(\'%\',,\'%\') order by id desc', '2024-12-09 23:14:25');
INSERT INTO `log` VALUES (35, '管理员', 'ADMIN', 'select * from student.studentcourse where sno=null order by scId desc', '2024-12-09 23:14:27');
INSERT INTO `log` VALUES (36, '管理员', 'ADMIN', 'select * from student.tbcourse where cname like concat(\'%\',,\'%\') and cno like concat(\'%\',,\'%\') and tno like concat(\'%\',,\'%\') order by id desc', '2024-12-09 23:14:28');
INSERT INTO `log` VALUES (37, NULL, 'STUDENT', 'select * from student.tbstudent where sno=S01', '2024-12-09 23:14:40');
INSERT INTO `log` VALUES (38, '管理员', 'ADMIN', 'select * from notice', '2024-12-09 23:14:40');
INSERT INTO `log` VALUES (39, '学生一', 'STUDENT', 'select * from student.tbcourse where cname like concat(\'%\',,\'%\') and cno like concat(\'%\',,\'%\') and tno like concat(\'%\',,\'%\') order by id desc', '2024-12-09 23:14:42');
INSERT INTO `log` VALUES (40, '学生一', 'STUDENT', 'select * from student.studentcourse where sno=S01 order by scId desc', '2024-12-09 23:14:42');
INSERT INTO `log` VALUES (41, '学生一', 'STUDENT', 'select * from student.tbcourse where cname like concat(\'%\',,\'%\') and cno like concat(\'%\',,\'%\') and tno like concat(\'%\',,\'%\') order by id desc', '2024-12-09 23:14:49');

SET FOREIGN_KEY_CHECKS = 1;
