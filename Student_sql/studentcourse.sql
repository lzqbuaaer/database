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
-- Table structure for studentcourse
-- ----------------------------
DROP TABLE IF EXISTS `studentcourse`;
CREATE TABLE `studentcourse`  (
  `scId` int(10) NOT NULL AUTO_INCREMENT,
  `sno` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cno` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `grade` float(3, 1) NULL DEFAULT NULL,
  PRIMARY KEY (`scId`) USING BTREE,
  INDEX `cno`(`cno`) USING BTREE,
  INDEX `sno`(`sno`) USING BTREE,
  CONSTRAINT `cno` FOREIGN KEY (`cno`) REFERENCES `tbcourse` (`cno`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sno` FOREIGN KEY (`sno`) REFERENCES `tbstudent` (`sno`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of studentcourse
-- ----------------------------
INSERT INTO `studentcourse` VALUES (8, 'S01', 'C03', 98.0);
INSERT INTO `studentcourse` VALUES (13, 'S01', 'C06', NULL);
INSERT INTO `studentcourse` VALUES (14, 'S01', 'C05', 93.0);
INSERT INTO `studentcourse` VALUES (16, 'S02', 'C05', 95.0);
INSERT INTO `studentcourse` VALUES (17, 'S02', 'C03', 96.0);
INSERT INTO `studentcourse` VALUES (18, 'S03', 'C06', NULL);
INSERT INTO `studentcourse` VALUES (19, 'S03', 'C04', 93.0);
INSERT INTO `studentcourse` VALUES (20, 'S03', 'C02', NULL);
INSERT INTO `studentcourse` VALUES (21, 'S03', 'C01', 96.5);
INSERT INTO `studentcourse` VALUES (22, 'S04', 'C06', NULL);
INSERT INTO `studentcourse` VALUES (23, 'S04', 'C04', 94.0);
INSERT INTO `studentcourse` VALUES (24, 'S04', 'C02', NULL);
INSERT INTO `studentcourse` VALUES (25, 'S05', 'C06', NULL);
INSERT INTO `studentcourse` VALUES (26, 'S05', 'C04', 94.5);
INSERT INTO `studentcourse` VALUES (27, 'S05', 'C02', NULL);
INSERT INTO `studentcourse` VALUES (28, 'S06', 'C06', NULL);
INSERT INTO `studentcourse` VALUES (29, 'S06', 'C04', 96.5);
INSERT INTO `studentcourse` VALUES (30, 'S06', 'C02', NULL);
INSERT INTO `studentcourse` VALUES (31, 'S07', 'C06', NULL);
INSERT INTO `studentcourse` VALUES (32, 'S07', 'C04', 97.5);
INSERT INTO `studentcourse` VALUES (33, 'S07', 'C01', 97.0);
INSERT INTO `studentcourse` VALUES (34, 'S08', 'C06', NULL);
INSERT INTO `studentcourse` VALUES (35, 'S08', 'C04', 98.5);
INSERT INTO `studentcourse` VALUES (36, 'S08', 'C01', 98.0);
INSERT INTO `studentcourse` VALUES (37, 'S01', 'C04', NULL);

SET FOREIGN_KEY_CHECKS = 1;
