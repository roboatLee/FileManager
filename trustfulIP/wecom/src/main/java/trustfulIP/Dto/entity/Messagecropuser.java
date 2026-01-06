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
public class Messagecropuser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息发送用户主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 消息外键
     */
    private Integer messageStoreId;

    /**
     * 公司外键
     */
    private String messageCropId;

    /**
     * 发送名字
     */
    private String userName;

    /**
     * 接收者的Id
     */
    private String userId;
}
