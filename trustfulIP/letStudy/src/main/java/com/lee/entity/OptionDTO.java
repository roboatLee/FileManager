package com.lee.entity;

import lombok.Data;

/**
 * @author KitenLee
 * * @date 2026/3/22
 */

@Data
public class OptionDTO {
    private String key;     // A / B / C
    private String content; // 选项内容
}
