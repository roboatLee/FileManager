package trustfulIP.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import trustfulIP.Dto.entity.Messagecrop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kittenLee
 * @since 2025-12-29
 */
@Mapper
public interface MessagecropMapper extends BaseMapper<Messagecrop> {
    @Select("SELECT * FROM messagecrop WHERE message_store_id = #{storeId}")
    List<Messagecrop> selectByStoreId(@Param("storeId") Integer storeId);

}
