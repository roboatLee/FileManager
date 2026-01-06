package trustfulIP.Dto.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author kittenLee
 * @since 2025-12-29
 */
@Getter
@Setter
@ToString
public class Wecomuser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 微信名称
     */
    private String userName;

    /**
     * 微信加密id
     */
    private String eId;

    /**
     * 微信头像存放位置
     */
    private String useravater;
}
