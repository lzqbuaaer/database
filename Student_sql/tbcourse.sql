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

 Date: 09/12/2024 23:15:44
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
INSERT INTO `tbcourse` VALUES (26, 'C07', 'T02', '数据库', 4.0, '123', 4, 4);
INSERT INTO `tbcourse` VALUES (27, 'C08', 'T01', '数据库', 4.0, '1234', 4, 3);

-- ----------------------------
-- Triggers structure for table tbcourse
-- ----------------------------
DROP TRIGGER IF EXISTS `course-t1`;
delimiter ;;
CREATE TRIGGER `course-t1` BEFORE INSERT ON `tbcourse` FOR EACH ROW BEGIN
  DECLARE conflict INT;

  -- 检查是否同一老师在同一天同一时间已经安排了课程
  SELECT COUNT(*) INTO conflict
  FROM tbcourse c
  WHERE c.tno = NEW.tno  -- 判断同一个老师
    AND c.cday = NEW.cday -- 判断是否在同一天
    AND c.ctime = NEW.ctime; -- 判断是否在同一时间

  -- 如果发现冲突，抛出错误，阻止插入
  IF conflict > 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '该老师在同一时间已经安排了课程';
  END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tbcourse
-- ----------------------------
DROP TRIGGER IF EXISTS `course-t2`;
delimiter ;;
CREATE TRIGGER `course-t2` BEFORE UPDATE ON `tbcourse` FOR EACH ROW BEGIN
  DECLARE conflict INT;

  -- 检查是否同一老师在同一天同一时间已经安排了课程
  SELECT COUNT(*) INTO conflict
  FROM tbcourse c
  WHERE c.tno = NEW.tno  -- 判断同一个老师
    AND c.cday = NEW.cday -- 判断是否在同一天
    AND c.ctime = NEW.ctime
    AND c.id != OLD.id; -- 排除正在更新的记录

  -- 如果发现冲突，抛出错误，阻止更新
  IF conflict > 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '该老师在同一时间已经安排了课程';
  END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tbcourse
-- ----------------------------
DROP TRIGGER IF EXISTS `delete`;
delimiter ;;
CREATE TRIGGER `delete` BEFORE DELETE ON `tbcourse` FOR EACH ROW DELETE FROM studentcourse WHERE cno = OLD.cno
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
