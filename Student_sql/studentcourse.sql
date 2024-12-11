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

 Date: 10/12/2024 20:43:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for studentcourse
-- ----------------------------
DROP TABLE IF EXISTS `studentcourse`;
CREATE TABLE `studentcourse`  (
  `scId` int NOT NULL AUTO_INCREMENT,
  `sno` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `cno` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `grade` float(3, 1) NULL DEFAULT NULL,
  PRIMARY KEY (`scId`) USING BTREE,
  INDEX `cno`(`cno` ASC) USING BTREE,
  INDEX `sno`(`sno` ASC) USING BTREE,
  CONSTRAINT `cno` FOREIGN KEY (`cno`) REFERENCES `tbcourse` (`cno`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sno` FOREIGN KEY (`sno`) REFERENCES `tbstudent` (`sno`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of studentcourse
-- ----------------------------
INSERT INTO `studentcourse` VALUES (8, 'S01', 'C03', 98.0);
INSERT INTO `studentcourse` VALUES (14, 'S01', 'C05', 93.0);
INSERT INTO `studentcourse` VALUES (16, 'S02', 'C05', 95.0);
INSERT INTO `studentcourse` VALUES (17, 'S02', 'C03', 96.0);
INSERT INTO `studentcourse` VALUES (19, 'S03', 'C04', 93.0);
INSERT INTO `studentcourse` VALUES (20, 'S03', 'C02', NULL);
INSERT INTO `studentcourse` VALUES (23, 'S04', 'C04', 94.0);
INSERT INTO `studentcourse` VALUES (24, 'S04', 'C02', NULL);
INSERT INTO `studentcourse` VALUES (26, 'S05', 'C04', 94.5);
INSERT INTO `studentcourse` VALUES (27, 'S05', 'C02', NULL);
INSERT INTO `studentcourse` VALUES (29, 'S06', 'C04', 96.5);
INSERT INTO `studentcourse` VALUES (30, 'S06', 'C02', NULL);
INSERT INTO `studentcourse` VALUES (32, 'S07', 'C04', 97.5);
INSERT INTO `studentcourse` VALUES (33, 'S07', 'C01', 97.0);
INSERT INTO `studentcourse` VALUES (35, 'S08', 'C04', 98.5);
INSERT INTO `studentcourse` VALUES (36, 'S08', 'C01', 98.0);
INSERT INTO `studentcourse` VALUES (37, 'S01', 'C04', NULL);
INSERT INTO `studentcourse` VALUES (38, 'S01', 'C01', 88.0);
INSERT INTO `studentcourse` VALUES (44, 'S03', 'C01', 96.5);
INSERT INTO `studentcourse` VALUES (45, 'S01', 'C02', 0.0);
INSERT INTO `studentcourse` VALUES (47, 'S01', 'C07', 0.0);
INSERT INTO `studentcourse` VALUES (48, 'S01', 'C08', 0.0);

-- ----------------------------
-- Triggers structure for table studentcourse
-- ----------------------------
DROP TRIGGER IF EXISTS `t1`;
delimiter ;;
CREATE TRIGGER `t1` BEFORE INSERT ON `studentcourse` FOR EACH ROW BEGIN
  DECLARE conflict INT;

  -- 检查学生是否已经在同一时间选修了其他课程
  SELECT COUNT(*) INTO conflict
  FROM studentcourse sc
  JOIN tbcourse c ON sc.cno = c.cno
  WHERE sc.sno = NEW.sno
    AND c.cday = (SELECT cday FROM tbcourse WHERE cno = NEW.cno)
    AND c.ctime = (SELECT ctime FROM tbcourse WHERE cno = NEW.cno);

  -- 如果找到冲突，抛出错误
  IF conflict > 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '学生在同一时间已经选修了其他课程';
  END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table studentcourse
-- ----------------------------
DROP TRIGGER IF EXISTS `t2`;
delimiter ;;
CREATE TRIGGER `t2` BEFORE UPDATE ON `studentcourse` FOR EACH ROW BEGIN
  DECLARE conflict INT;

  -- 检查学生是否已经在同一时间选修了其他课程
  SELECT COUNT(*) INTO conflict
  FROM studentcourse sc
  JOIN tbcourse c ON sc.cno = c.cno
  WHERE sc.sno = NEW.sno
    AND c.cday = (SELECT cday FROM tbcourse WHERE cno = NEW.cno)
    AND c.ctime = (SELECT ctime FROM tbcourse WHERE cno = NEW.cno)
    AND sc.scId != OLD.scId;  -- 排除正在更新的记录

  -- 如果找到冲突，抛出错误
  IF conflict > 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '学生在同一时间已经选修了其他课程';
  END IF;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
