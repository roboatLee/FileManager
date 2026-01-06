package trustfulIP.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import trustfulIP.Dto.entity.Messagecrop;
import trustfulIP.Dto.entity.Messagecropuser;
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
public interface MessagecropuserMapper extends BaseMapper<Messagecropuser> {
    @Select("SELECT * FROM messagecropuser WHERE message_store_id = #{storeId}")
    List<Messagecropuser> selectByStoreId(@Param("storeId") Integer storeId);

}
