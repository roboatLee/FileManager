package com.lee.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 题库表（完整版）
 * </p>
 *
 * @author lee
 * @since 2026-03-22
 */
@Getter
@Setter
@ToString
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 题目ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题干（支持Markdown/HTML/LaTeX）
     */
    private String title;

    /**
     * 题目类型
     */
    private String type;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 难度(1-5)
     */
    private Byte difficulty;

    /**
     * 选项（数组结构）
     */
    private String options;

    /**
     * 标准答案（统一JSON结构）
     */
    private String answer;

    /**
     * 解析（支持Markdown/HTML/公式）
     */
    private String analysis;

    /**
     * 标签 ["线代","矩阵"]
     */
    private String tags;

    /**
     * 是否公开
     */
    private Boolean isPublic;

    /**
     * 创建者ID
     */
    private Long authorId;

    /**
     * 提交次数
     */
    private Integer submitCount;

    /**
     * 正确次数
     */
    private Integer correctCount;

    /**
     * 创建时间
     * */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     * */
    private LocalDateTime updatedAt;
}
