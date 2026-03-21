/*
 Navicat Premium Data Transfer

 Source Server         : ai_project
 Source Server Type    : MySQL
 Source Server Version : 80040
 Source Host           : localhost:3306
 Source Schema         : trustful_platform

 Target Server Type    : MySQL
 Target Server Version : 80040
 File Encoding         : 65001

 Date: 21/03/2026 17:39:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '题目主键',
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目标题',
  `type` enum('single_choice','multiple_choice','judge','fill','math','subjective') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目类型 单选，多选，判断，填空，计算，主观题',
  `category_id` bigint NULL DEFAULT NULL COMMENT '所属领域id',
  `difficulty` tinyint NULL DEFAULT NULL COMMENT '难度1-5',
  `analysis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '大难',
  `is_public` tinyint(1) NULL DEFAULT 0 COMMENT '是否分析',
  `author_id` bigint NOT NULL COMMENT '创建者i',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
