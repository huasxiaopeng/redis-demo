package lktbz.springboot.redis.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
class LktbzSpringbootRedisDemoApplicationTests {

    private  static  Logger logger = LoggerFactory.getLogger(LktbzSpringbootRedisDemoApplicationTests.class);

    @Autowired
    private RedisTemplate template;

    @Test
    void contextLoads() {
        System.out.println("test 是否绿色。。。");
    }

    /**
     * hello redis
     */
    @Test
    public void testHello() {
        try {
            /**
             * set 方法没有返回值 ，看了下官方命令解释，说这个命令会付该旧值，永远返回ok
             */
            template.opsForValue().set("lk", "jiji");
            template.opsForValue().set("lk", "qingq");
//            Object lk = template.opsForValue().get("lk");
            /**
             * 不存在的 key 返回null
             */
            String lk = (String)template.opsForValue().get("lk"); //设置的string 类型直接强转就行
            if(StringUtils.hasText(lk)){
                log.info("查询的值为："+lk);
            }
        } catch (Exception e) {
           log.error("插入的值失败");
        }
    }

    /**
     * 设置过期时间
     */
    @Test
    public void testExpire() {
        try {
            //设置10s的过期时间
            template.opsForValue().set("ttl", "expire", 10, TimeUnit.SECONDS);
            while (true) {
                String lk = (String) template.opsForValue().get("ttl");
                System.out.println("查询到的redis数据为:" + lk);
                TimeUnit.SECONDS.sleep(11);  //11秒后在执行查询，要是查询不到数据则会抛异常
            }
        } catch (Exception e) {
            System.out.println("插入的值失败");
        }
    }
    /**
     * 批量插入操作
     */
    @Test
    public void testBatch() {
        try {
//            Map<String,Integer>maps=new HashMap<>();
//            maps.put("k1",1);
//            maps.put("k2",2);
//            maps.put("k3",3);
//            maps.put("k4",4);
//            template.opsForValue().multiSet(maps);

            List<String> keys=new ArrayList<>();
//            keys.add("k1");
//            keys.add("k2");
//            keys.add("k3");
//            keys.add("k4");
            keys.add("ju"); //不存在的值，返回的类型是List<String>=null; size =1,不打断言还以为返回size <0
            List<String> list = template.opsForValue().multiGet(keys);
            for (String o :  list) {
                if (StringUtils.isEmpty(o)) {
                    log.error("查询的值为 null");
                }
            }
        } catch (Exception e) {
            log.error( "插入的值失败");
        }
    }

    /**
     * 自增测试demo
     *
     * 抛个需求出来：
     *   一共准备了10件商品，谁抢到了商品就加1，不能超过10
     *   使用jemeter 进行压测。
     *
     *
     * 现在直接通过这个客户端去操作，并不能保证原子性需要使用redssion ,后面的demo 会解决此问题
     */
    @Test
    public void testAutoIncrement() {
        template.opsForValue().set("incr",1);
        int i=10;
        do{
            Long increment = template.opsForValue().increment("incr");
            log.info("increment",increment);
            --i;
        }while (i>1);
    }

    /**
     * 判断某个key 是否存在
     */
    @Test
    public void testKeysExists() {
        List<String> qwer1 = Collections.singletonList("k1");
        Long qwer = template.countExistingKeys(qwer1);
        System.out.println(qwer);//不存在的返回0,反之返回1
    }

    /**
     * list 列表操作
     *
     */
    @Test
    public void testList() {

        template.opsForList().leftPush("langu", "python");
        String langu = (String) template.opsForList().leftPop("langu");
        System.out.println("从redis中获取的数据为："+langu);
    }

    /**
     * 测试存入相同的结果
     */
    @Test
    public void testListSamples() {
        try {
            //特点可以存入相同的值
            template.opsForList().leftPush("langu", "python");
            template.opsForList().leftPush("langu", "python");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
