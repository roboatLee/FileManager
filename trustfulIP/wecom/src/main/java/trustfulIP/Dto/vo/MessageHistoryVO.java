package trustfulIP.Dto.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2025/12/29
 */
@Data
public class MessageHistoryVO {
    private Integer messageStoreId;
    private String message;
    private LocalDateTime createTime;
    private List<CompanyVO> companyList;
}
