/*
 Navicat Premium Data Transfer

 Source Server         : Students
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : student

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbstudent
-- ----------------------------
DROP TABLE IF EXISTS `tbstudent`;
CREATE TABLE `tbstudent`  (
  `sno` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ssex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sclass` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sdept` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbstudent
-- ----------------------------
INSERT INTO `tbstudent` VALUES ('S01', '学生一', '男', '20212831', '计算机科学与技术', '62bff9c5b8f865bdd70c588ce84158e0', 'STUDENT');
INSERT INTO `tbstudent` VALUES ('S02', '学生二', '女', '20212831', '计算机科学与技术', '206a97ee23118b6b34f00502ea434a74', 'STUDENT');
INSERT INTO `tbstudent` VALUES ('S03', '学生三', '女', '20212831', '计算机科学与技术', '206a97ee23118b6b34f00502ea434a74', 'STUDENT');
INSERT INTO `tbstudent` VALUES ('S04', '学生四', '女', '20212731', '光电信息', '206a97ee23118b6b34f00502ea434a74', 'STUDENT');
INSERT INTO `tbstudent` VALUES ('S05', '学生五', '男', '20212731', '光电信息', '206a97ee23118b6b34f00502ea434a74', 'STUDENT');
INSERT INTO `tbstudent` VALUES ('S06', '学生六', '女', '20212731', '光电信息', '206a97ee23118b6b34f00502ea434a74', 'STUDENT');
INSERT INTO `tbstudent` VALUES ('S07', '学生七', '男', '20212731', '光电信息', '206a97ee23118b6b34f00502ea434a74', 'STUDENT');
INSERT INTO `tbstudent` VALUES ('S08', '学生八', '男', '20212831', '计算机科学与技术', '206a97ee23118b6b34f00502ea434a74', 'STUDENT');

SET FOREIGN_KEY_CHECKS = 1;
