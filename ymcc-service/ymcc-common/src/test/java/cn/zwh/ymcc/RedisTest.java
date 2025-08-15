package cn.zwh.ymcc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CommonApplication.class)
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void test() {
        redisTemplate.opsForValue().set("name", "张敏康");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }
}
