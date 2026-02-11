package com.lee.controller;

import com.lee.service.repository.ChatMessageRepositoryImpl;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/2/10
 */
@RestController
@RequestMapping("/chat")
public class WeMessageController {


    // 获取所有消息
    @GetMapping("/messages")
    public List<String> getMessages() {
        return messages;
    }

    // 发送消息
    @PostMapping("/send")
    public void sendMessage(@RequestParam String msg) {
        messages.add(msg);
    }

}
