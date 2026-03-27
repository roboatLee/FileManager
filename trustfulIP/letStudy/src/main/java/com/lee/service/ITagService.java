package com.lee.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lee.entity.QuestionTag;
import com.lee.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2026-03-26
 */
public interface ITagService extends IService<Tag> {

/**
 * 处理标签并建立 QuestionTag 关联
 * @param questionId 问题ID
 * @param tagNames 标签名称列表
 */
 void handleTags(Long questionId, List<String> tagNames);

}
