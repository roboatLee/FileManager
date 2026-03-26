package com.lee.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lee
 * @since 2026-03-26
 */
@Getter
@Setter
@ToString
@TableName("question_tag")
public class QuestionTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("question_id")
    private Long questionId;

    @TableId("tag_id")
    private Long tagId;
}
