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
-- Table structure for tbcourse
-- ----------------------------
DROP TABLE IF EXISTS `tbcourse`;
CREATE TABLE `tbcourse`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `cno` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tno` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ccredit` float(3, 1) NULL DEFAULT NULL,
  `cdescribe` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`, `cno`) USING BTREE,
  INDEX `tno`(`tno`) USING BTREE,
  INDEX `cno`(`cno`) USING BTREE,
  CONSTRAINT `tno` FOREIGN KEY (`tno`) REFERENCES `tbteacher` (`tno`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbcourse
-- ----------------------------
INSERT INTO `tbcourse` VALUES (1, 'C01', 'T01', '高等数学', 3.0, '高等数学上');
INSERT INTO `tbcourse` VALUES (2, 'C02', 'T02', 'C语言程序设计', 3.0, 'c语言');
INSERT INTO `tbcourse` VALUES (4, 'C03', 'T02', '计算机网络', 3.0, '计网12345');
INSERT INTO `tbcourse` VALUES (5, 'C04', 'T01', '数据结构', 5.0, '数据结构与算法C++');
INSERT INTO `tbcourse` VALUES (7, 'C05', 'T01', '计算机操作系统', 4.0, '123456');
INSERT INTO `tbcourse` VALUES (11, 'C06', 'T02', '概率论', 3.0, '123');

SET FOREIGN_KEY_CHECKS = 1;
