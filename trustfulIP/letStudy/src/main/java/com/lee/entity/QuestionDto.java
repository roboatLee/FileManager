package com.lee.entity;

import lombok.Data;

import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/3/22
 */
@Data
public class QuestionDto {
    // 基本信息
    private String title;
    private String type; // single_choice / multiple_choice ...
    private Long categoryId;
    private Integer difficulty;

    // 核心数据
    private List<OptionDTO> options;
    private Object answer; // 不同题型不同结构

    // 扩展
    private String analysis;
    private List<String> tags;

    // 权限
    private Boolean isPublic;
}
