package cn.zwh.ymcc;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RocketMQProductTest {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Test
    public void test1() {
        rocketMQTemplate.sendOneWay("topic-test:tags-test1", "hello world");

    }

    @Test
    public void test2() {
        SendResult sendResult = rocketMQTemplate.syncSend("topic-test:tags-test2", "hello world");
        System.out.println(sendResult);
    }

    @Test
    public void test3() {
        rocketMQTemplate.asyncSend("topic-test:tags-test3","张敏康爱狗叫" ,new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                throw new RuntimeException("张敏康狗叫失败");
            }
        });
        try {
            Thread.sleep(100000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
