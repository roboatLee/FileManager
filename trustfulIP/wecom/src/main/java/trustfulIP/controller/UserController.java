package trustfulIP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trustfulIP.Service.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author KitenLee
 * * @date 2025/12/24
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public Object me(HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        Object userid = session.getAttribute("WE_COM_USER");
        if (userid == null) {
            result.put("login", false);
            return result;
        }
        result.put("login", true);
        result.put("userid", userid);

        return result;
    }

    /** 获取当前用户企业微信完整信息 */
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpSession session) {
        String userid = (String) session.getAttribute("WE_COM_USER");
        if (userid == null) {
            System.out.println("用户ID为空");
        }

        Map<String, Object> userInfo = userService.getUserInfo(userid);
        return ResponseEntity.ok(userInfo);
    }



}
