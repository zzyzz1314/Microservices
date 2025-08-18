package cn.zwh.ymcc;

import cn.zwh.ymcc.service.ICourseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {
    @Autowired
    private ICourseService courseService;
    @Test
    public void test() {
    }
}
