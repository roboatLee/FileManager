package com.lee.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.entity.OptionDTO;
import com.lee.entity.Question;
import com.lee.entity.QuestionDto;
import com.lee.persistence.json.JsonUtil;

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
        q.setType(JsonUtil.toJson(dto.getType()));
        q.setCategoryId(dto.getCategoryId());
        q.setDifficulty(dto.getDifficulty().byteValue());
        q.setAnalysis(JsonUtil.toJson(dto.getAnalysis()));
        q.setIsPublic(dto.getIsPublic());

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
}
