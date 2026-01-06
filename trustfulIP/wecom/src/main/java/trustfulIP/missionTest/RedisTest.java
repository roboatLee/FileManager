package trustfulIP.missionTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import trustfulIP.Service.RedisService;

/**
 * @author KitenLee
 * * @date 2025/12/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest

public class RedisTest {
    @Autowired
    private RedisService redisService;

    @Test
    public void TestRedis(){
        redisService.set("name","bruce");
    }

    @Test
    public void GetReids(){
        String test = redisService.get("name");
        System.out.println(test);
    }
}
