package com.lee.controller;

import com.lee.entity.QuestionDto;
import com.lee.entity.QuestionVo;
import com.lee.service.impl.QuestionServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 题库表（完整版） 前端控制器
 * </p>
 *
 * @author lee
 * @since 2026-03-22
 *
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    private QuestionServiceImpl questionService;

    public QuestionController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/addQuestion")
    public void addQuestion(@RequestBody QuestionDto questionDto,@RequestHeader("Authorization") String token){
        questionService.addQuestion(questionDto,token);
    }

    @GetMapping("/list")
    public List<QuestionVo> getListQuestionVo(){
        return  questionService.getAllQuestion();
    }

}
