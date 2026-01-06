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
public class Crop implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司主键
     */
    private Integer id;

    /**
     * 公司所用的共享应用ID
     */
    private String agentId;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 企业微信ID
     */
    private String wecomId;
}
