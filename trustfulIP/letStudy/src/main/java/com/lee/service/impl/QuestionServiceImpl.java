package com.lee.service.impl;

import com.lee.entity.Question;
import com.lee.mapper.QuestionMapper;
import com.lee.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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


    @Override
    public void addQuestion() {

    }
}
