package com.lee.service;

import com.lee.dao.ChatMessageDto;

/**
 * @author KitenLee
 * * @date 2026/2/11
 */
public interface ChatMessageService {

    /**
     * todo: 发消息
     * */
    void  sendMessage(ChatMessageDto chatMessageDto);

    /**
     * todo: 读取之前的消息列表
     * */
    void listMessage();


}
