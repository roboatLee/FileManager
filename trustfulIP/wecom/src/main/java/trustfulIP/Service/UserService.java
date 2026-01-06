package trustfulIP.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author KitenLee
 * * @date 2025/12/24
 */

@Service
public class UserService {

    @Autowired
    private WeComService weComService;
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getUserInfo(String userid) {
        String token =weComService.getAccessToken();
        String url = String.format(
                "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=%s&userid=%s",
                token, userid
        );

        Map<String, Object> userInfo = restTemplate.getForObject(url, Map.class);
        return userInfo;
    }
}
