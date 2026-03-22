package com.lee.service;

import com.lee.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 题库表（完整版） 服务类
 * </p>
 *
 * @author lee
 * @since 2026-03-22
 */
public interface IQuestionService extends IService<Question> {

    void addQuestion();
}
