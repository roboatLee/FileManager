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
     *     "title": "设函数$y = y(x)$是微分方程\n\n$$\ny^{\\prime} + \\frac{1}{2 \\sqrt{x}} y = 2 +\\sqrt{x}\n$$\n\n的满足$y(1)=3$的解，求曲线y的渐近线\n\n",
     *     "type": "math",
     *     "difficulty": 5,
     *     "categoryId": 1,
     *     "options": [],
     *     "answer": "",
     *     "analysis": "暂无解析\n\n",
     *     "tags": [
     *         "数学"
     *     ],
     *     "isPublic": true
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
        this.save(question);

    }

    @Override
    public List<QuestionVo> getAllQuestion() {
        return null;
    }


}
