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
-- Table structure for tbteacher
-- ----------------------------
DROP TABLE IF EXISTS `tbteacher`;
CREATE TABLE `tbteacher`  (
  `tno` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tsex` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tdept` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `temail` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tpwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbteacher
-- ----------------------------
INSERT INTO `tbteacher` VALUES ('T01', '李一', '男', '数学系', '123@qq.com', '62bff9c5b8f865bdd70c588ce84158e0', 'TEACHER');
INSERT INTO `tbteacher` VALUES ('T02', '李二', '男', '计算机技术与科学', '456@qq.com', '206a97ee23118b6b34f00502ea434a74', 'TEACHER');
INSERT INTO `tbteacher` VALUES ('T03', '李三', '女', '物理系', '789@qq.com', '206a97ee23118b6b34f00502ea434a74', 'TEACHER');
INSERT INTO `tbteacher` VALUES ('T04', '李四', '男', '软件工程', '111@qq.com', '206a97ee23118b6b34f00502ea434a74', 'TEACHER');
INSERT INTO `tbteacher` VALUES ('T05', '李五', '女', '数学系', '333@qq.com', '206a97ee23118b6b34f00502ea434a74', 'TEACHER');

SET FOREIGN_KEY_CHECKS = 1;
