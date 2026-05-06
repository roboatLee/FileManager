package com.lee.entity;

import lombok.Data;

import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/5/6
 */
@Data
public class QuestionDetailVo {
    private Long id;
    private String title;
    private String type;
    private Long categoryId;
    private Integer difficulty;
    private List<OptionDTO> options;
    private Object answer;
    private String analysis;
    private List<String> tags;
    private Boolean isPublic;
    private Long authorId;
    private Integer submitCount;
    private Integer correctCount;
}
