package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.entity.QuestionTag;
import com.lee.entity.Tag;
import com.lee.mapper.TagMapper;
import com.lee.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2026-03-26
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    private QuestionTagServiceImpl questionTagService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public TagServiceImpl(QuestionTagServiceImpl questionTagService) {
        this.questionTagService = questionTagService;
    }

    /**
     * 处理标签并建立 QuestionTag 关联
     * @param questionId 问题ID
     * @param tagNames 标签名称列表
     */
    @Override
    public void handleTags(Long questionId, List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) return;

        for (String tagName : tagNames) {
            // 1️⃣ 查询标签是否存在
            Tag tag = this.getOne(new LambdaQueryWrapper<Tag>().eq(Tag::getName, tagName));
            if (tag == null) {
                // 2️⃣ 标签不存在 -> 新增
                tag = new Tag();
                tag.setName(tagName);
                this.save(tag);
            }

            // 3️⃣ 建立 QuestionTag 关系
            QuestionTag qt = new QuestionTag();
            qt.setQuestionId(questionId);
            qt.setTagId(tag.getId());
            questionTagService.save(qt);
        }
    }


}
