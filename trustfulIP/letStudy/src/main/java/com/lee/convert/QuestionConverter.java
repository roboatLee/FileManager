package com.lee.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.entity.*;
import com.lee.persistence.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/3/26
 */
public class QuestionConverter {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     *
     * @param dto : QuestionDto
     *
     * <p>目前转换转换的有
     * 标题
     * 题目类型
     * 类型Id
     * 难度
     * 题目分析
     * 选项
     * <p/>
     * @return : Question
     * */
    public static Question toEntity(QuestionDto dto) {
        Question q = new Question();
        q.setTitle(JsonUtil.toJson(dto.getTitle()));
        q.setType(dto.getType());
        q.setCategoryId(dto.getCategoryId());
        q.setDifficulty(dto.getDifficulty().byteValue());
        q.setAnalysis(JsonUtil.toJson(dto.getAnalysis()));
        q.setIsPublic(dto.getIsPublic());
        q.setCreatedAt(LocalDateTime.now());
        q.setUpdatedAt(LocalDateTime.now());
        q.setSubmitCount(0);
        q.setCorrectCount(0);
        try {
            q.setOptions(mapper.writeValueAsString(dto.getOptions()));
            q.setAnswer(mapper.writeValueAsString(dto.getAnswer()));
            q.setTags(mapper.writeValueAsString(dto.getTags()));
        } catch (Exception e) {
            throw new RuntimeException("JSON转换失败", e);
        }

        return q;
    }

    public static QuestionDto toDto(Question q) {
        QuestionDto dto = new QuestionDto();
        dto.setTitle(q.getTitle());
        dto.setType(q.getType());
        dto.setCategoryId(q.getCategoryId());
        dto.setDifficulty((int) q.getDifficulty());
        dto.setAnalysis(q.getAnalysis());
        dto.setIsPublic(q.getIsPublic());

        try {
            dto.setOptions(
                    mapper.readValue(q.getOptions(), new TypeReference<List<OptionDTO>>() {})
            );
            dto.setAnswer(
                    mapper.readValue(q.getAnswer(), Object.class)
            );
            dto.setTags(
                    mapper.readValue(q.getTags(), new TypeReference<List<String>>() {})
            );
        } catch (Exception e) {
            throw new RuntimeException("JSON解析失败", e);
        }

        return dto;
    }
    public static QuestionVo toVo(Question q) {
        QuestionVo vo = new QuestionVo();
        vo.setId(q.getId());
        vo.setType(q.getType());
        vo.setCategoryId(q.getCategoryId());
        vo.setDifficulty(q.getDifficulty() == null ? null : (int) q.getDifficulty());
        vo.setIsPublic(q.getIsPublic());
        vo.setAuthorId(q.getAuthorId());
        vo.setSubmitCount(q.getSubmitCount());
        vo.setCorrectCount(q.getCorrectCount());
        try {
            if (q.getTitle() != null) {
                vo.setTitle(mapper.readValue(q.getTitle(), String.class));
            }
            if (q.getTags() != null) {
                vo.setTags(mapper.readValue(q.getTags(), new TypeReference<List<String>>() {}));
            }
        } catch (Exception e) {
            throw new RuntimeException("JSON解析失败", e);
        }
        return vo;
    }

    public static QuestionDetailVo toDetailVo(Question q) {
        QuestionDetailVo vo = new QuestionDetailVo();
        vo.setId(q.getId());
        vo.setType(q.getType());
        vo.setCategoryId(q.getCategoryId());
        vo.setDifficulty(q.getDifficulty() == null ? null : (int) q.getDifficulty());
        vo.setIsPublic(q.getIsPublic());
        vo.setAuthorId(q.getAuthorId());
        vo.setSubmitCount(q.getSubmitCount());
        vo.setCorrectCount(q.getCorrectCount());
        try {
            if (q.getTitle() != null) {
                vo.setTitle(mapper.readValue(q.getTitle(), String.class));
            }
            if (q.getAnalysis() != null) {
                vo.setAnalysis(mapper.readValue(q.getAnalysis(), String.class));
            }
            if (q.getOptions() != null) {
                vo.setOptions(mapper.readValue(q.getOptions(), new TypeReference<List<OptionDTO>>() {}));
            }
            if (q.getAnswer() != null) {
                vo.setAnswer(mapper.readValue(q.getAnswer(), Object.class));
            }
            if (q.getTags() != null) {
                vo.setTags(mapper.readValue(q.getTags(), new TypeReference<List<String>>() {}));
            }
        } catch (Exception e) {
            throw new RuntimeException("JSON解析失败", e);
        }
        return vo;
    }
}
