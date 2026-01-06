package trustfulIP.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @author KitenLee
 * * @date 2025/12/26
 */
@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String set(String Path,String Value) {
        stringRedisTemplate.opsForValue().set(Path, Value);
        return "ok";
    }
    public String setx(String Path, String Value, long time) {
        stringRedisTemplate.opsForValue().
                set(Path, Value,Duration.ofSeconds(time));
        return "ok";
    }
    public String get(String Path) {
        String answer =  stringRedisTemplate.opsForValue().get(Path);
        return answer;
    }

}
