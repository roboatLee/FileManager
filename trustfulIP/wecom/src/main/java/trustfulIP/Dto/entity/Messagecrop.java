package trustfulIP.Dto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Messagecrop implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司消息主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 外键
     */
    private Integer messageStoreId;

    /**
     * 公司名称
     */
    private String cropName;

    /**
     * 公司应用
     */
    private String agentId;

    /**
     * wecom公司唯一标识符
     */
    private String cropId;
}
