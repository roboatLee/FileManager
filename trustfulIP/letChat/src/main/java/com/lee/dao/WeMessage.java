package com.lee.dao;

import lombok.Data;

/**
 * @author KitenLee
 * * @date 2026/2/28
 */
@Data
public class WeMessage {
    private String type;

    private Object data;

    private Long time;
}
