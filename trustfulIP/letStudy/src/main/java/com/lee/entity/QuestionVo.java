package com.lee.entity;

/**
 * @author KitenLee
 * * @date 2026/3/26
 */

import lombok.Data;

import java.util.List;


@Data
public class QuestionVo {
    /**
     * 目前的json格式
     * {
     *     "title": "# 这是一个问题\n\n\n\n",
     *     "options": [
     *         {
     *             "key": "A",
     *             "content": "问题1"
     *         },
     *         {
     *             "key": "B",
     *             "content": "问题2"
     *         },
     *         {
     *             "key": "C",
     *             "content": "问题3"
     *         }
     *     ],
     *     "analysis": "这是一个典型的什么什么问题\n\n\n",
     *     "answer": "A"
     * }
     *
     */
    private Long id;
    private String title;
    private String type;
    private Long categoryId;
    private Integer difficulty;
    private List<String> tags;
    private Boolean isPublic;
    private Long authorId;
    private Integer submitCount;
    private Integer correctCount;

}
