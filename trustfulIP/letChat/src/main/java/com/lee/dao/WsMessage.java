package com.lee.dao;

import lombok.Data;

/**
 * @author KitenLee
 * * @date 2026/2/28
 */
@Data
public class WsMessage<T> {
    private String type;

    private T data;

    private Long time;
}
