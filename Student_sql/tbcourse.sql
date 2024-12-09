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

 Date: 09/12/2024 10:17:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbcourse
-- ----------------------------
DROP TABLE IF EXISTS `tbcourse`;
CREATE TABLE `tbcourse`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `cno` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `tno` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `cname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `ccredit` float(3, 1) NULL DEFAULT NULL,
  `cdescribe` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  `cday` int NULL DEFAULT NULL,
  `ctime` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `cno`) USING BTREE,
  INDEX `tno`(`tno` ASC) USING BTREE,
  INDEX `cno`(`cno` ASC) USING BTREE,
  CONSTRAINT `tno` FOREIGN KEY (`tno`) REFERENCES `tbteacher` (`tno`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tbcourse
-- ----------------------------
INSERT INTO `tbcourse` VALUES (1, 'C01', 'T01', '高等数学', 3.0, '高等数学上', 1, 1);
INSERT INTO `tbcourse` VALUES (2, 'C02', 'T02', 'C语言程序设计', 3.0, 'c语言', 2, 2);
INSERT INTO `tbcourse` VALUES (4, 'C03', 'T02', '计算机网络', 3.0, '计网12345', 3, 1);
INSERT INTO `tbcourse` VALUES (5, 'C04', 'T01', '数据结构', 5.0, '数据结构与算法C++', 4, 2);
INSERT INTO `tbcourse` VALUES (7, 'C05', 'T01', '计算机操作系统', 4.0, '123456', 5, 3);
INSERT INTO `tbcourse` VALUES (11, 'C06', 'T02', '概率论', 3.0, '123', 3, 2);
INSERT INTO `tbcourse` VALUES (26, 'C07', 'T02', '数据库', 4.0, '123', 4, 4);
INSERT INTO `tbcourse` VALUES (27, 'C08', 'T01', '数据库', 4.0, '1234', 4, 4);
INSERT INTO `tbcourse` VALUES (28, 'C09', 'T01', '编译技术', 4.5, '编译', 3, 5);

SET FOREIGN_KEY_CHECKS = 1;
