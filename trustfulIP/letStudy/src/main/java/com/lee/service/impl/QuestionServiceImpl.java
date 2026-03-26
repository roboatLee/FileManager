package com.lee.service.impl;

import com.lee.convert.QuestionConverter;
import com.lee.entity.*;
import com.lee.mapper.QuestionMapper;
import com.lee.security.JwtUtil;
import com.lee.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 题库表（完整版） 服务实现类
 * </p>
 *
 * @author lee
 * @since 2026-03-22
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

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
     * */
    @Override
    public void addQuestion(QuestionDto questionDto, String token) {
        //todo:获得UserId
        Long userId = JwtUtil.getUserIdInt(token);

        //todo: 进行一个信息的转换
        Question question = QuestionConverter.toEntity(questionDto);
        question.setAuthorId(userId);

        //todo: 进行数据库存储

    }

    @Override
    public List<QuestionVo> getAllQuestion() {
        return null;
    }


}
