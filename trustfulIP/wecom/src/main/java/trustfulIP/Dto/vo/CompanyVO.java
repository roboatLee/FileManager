package trustfulIP.Dto.vo;

import lombok.Data;

import java.util.List;

/**
 * @author KitenLee
 * * @date 2025/12/29
 */
@Data
public class CompanyVO {
    private String cropName;
    private String agentId;
    private List<UserVO> userList;
}
