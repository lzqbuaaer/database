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

 Date: 10/12/2024 20:43:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `cno` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `userid` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 'C08', 'admin', '管理员', '数据库', '2024/12/10 19:48:49');
INSERT INTO `comment` VALUES (2, 'C04', 'T01', '李一', '数据结构', '2024/12/10 20:04:13');
INSERT INTO `comment` VALUES (3, 'C04', 'T01', '李一', '第二次测试', '2024/12/10 20:05:50');
INSERT INTO `comment` VALUES (4, 'C04', 'T01', '李一', '第三次测试', '2024/12/10 20:07:08');
INSERT INTO `comment` VALUES (5, 'C08', 'admin', '管理员', '第二次测试', '2024/12/10 20:08:39');
INSERT INTO `comment` VALUES (6, 'C08', 'admin', '管理员', '第三次测试', '2024/12/10 20:11:18');
INSERT INTO `comment` VALUES (7, 'C08', 'admin', '管理员', '第四个', '2024/12/10 20:21:27');
INSERT INTO `comment` VALUES (8, 'C08', 'admin', '管理员', '第五个', '2024/12/10 20:33:01');
INSERT INTO `comment` VALUES (9, 'C08', 'admin', '管理员', '第六个', '2024/12/10 20:33:09');
INSERT INTO `comment` VALUES (10, 'C04', 'S01', '学生一', '学生测试', '2024/12/10 20:36:59');
INSERT INTO `comment` VALUES (11, 'C08', 'S01', '学生一', '学生测试', '2024/12/10 20:37:34');
INSERT INTO `comment` VALUES (12, 'C08', 'S01', '学生一', '学生测试2', '2024/12/10 20:37:41');

SET FOREIGN_KEY_CHECKS = 1;
