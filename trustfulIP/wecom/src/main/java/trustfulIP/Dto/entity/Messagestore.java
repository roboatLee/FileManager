package trustfulIP.Dto.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Messagestore implements Serializable {

    private static final long serialVersionUID = 8L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发送的用户id
     */
    private Integer userId;

    /**
     * 发送消息
     */
    private String message;

    /**
     * 发送时间
     * */
    private LocalDateTime createTime;
}
