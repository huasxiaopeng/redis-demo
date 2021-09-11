package lktbz.springboot.redis.demo.lettuce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/8/11
 * @desc Lettuce 客户端使用
 */
@RestController
public class LettuceControllerDemo {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/set")
    public String setName() {
        //直接set
//        redisTemplate.opsForValue().set("b", "456");// 重复调用，直接替换值
//        redisTemplate.opsForValue().set("b", "1234");
//        Set value of key and return its old value.
//        String b = redisTemplate.opsForValue().getAndSet("b", "789");
//        System.out.println("返回的旧值为：" + b);
//        Boolean b = redisTemplate.opsForValue().setIfAbsent("b", "000"); //key 不存在，则设置，存在返回false
//        System.out.println("设置存在的值返回结果为：" + b);
//        Boolean c = redisTemplate.opsForValue().setIfAbsent("c", "000");
//        System.out.println("设置不存在的值返回结果为：" + c);
//        Boolean c = redisTemplate.opsForValue().setIfPresent("c", "222"); //存在则进行更新数据
//        System.out.println("设置存在的值返回结果为：" + c);
//        Boolean d = redisTemplate.opsForValue().setIfPresent("d", "222");
//        System.out.println("设置不存在的值返回结果为：" + d);
          Map<String,String> maps=new HashMap<>();
          maps.put("l","l");
          maps.put("g","g");
          maps.put("k","k");

//        redisTemplate.opsForValue().multiSet(maps); //批量添加

//          key 过期设置
//         redisTemplate.opsForValue().set("a","bbb",2, TimeUnit.SECONDS);
//
//         while (redisTemplate.hasKey("a")){//key存在就一直循环查询
//             try {
//                 TimeUnit.MILLISECONDS.sleep(20);
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//             String a = redisTemplate.opsForValue().get("a");
//             System.out.println("获取到的值为："+a);
//         }

//        Integer g = redisTemplate.opsForValue().append("g", "777"); //key 存在则追加，不存在则添加
//        Integer k = redisTemplate.opsForValue().append("gg", "999");
//        System.out.println(g);
//        System.out.println(k);
        //key 自增
//        redisTemplate.opsForValue().set("ex", String.valueOf(10));
//        String l = redisTemplate.opsForValue().get("ex");
//        System.out.println("调用increment 之前："+l);
//        for (int i = 0; i <10 ; i++) {
//            Long ex = redisTemplate.opsForValue().increment("ex");
//            System.out.println("调用执行："+ex);
//        }
//
        return "添加成功";
    }

    /**
     * get
     */
    @GetMapping("/get")
    public String getName() {
        String order = redisTemplate.opsForValue().get("l");
        System.out.println("查询到的数据为：" + order);
        return order;
    }

    /**
     *   list操作
     * @return
     */
    @GetMapping("/list")
    public String setList(){
//        redisTemplate.opsForList().leftPush("list","zs");
//        redisTemplate.opsForList().leftPush("list","jiji");
//        redisTemplate.opsForList().leftPush("list","jiji"); //添加重复的key 允许添加重复的
        Random random=new Random();
        //随机生成10个数，测试其是否排序，根据结果发现没有排序
//        for (int i = 0; i < 10; i++) {
//            int i1 = random.nextInt(100);
//            redisTemplate.opsForList().leftPush("range", String.valueOf(i1)); //从表头进行插入
//        }

        Long range = redisTemplate.opsForList().size("range");
        System.out.println("range 的大小为："+range);

        //从列表尾部插入数据
        for (int i = 0; i < 10; i++) {
            int i1 = random.nextInt(100);
            redisTemplate.opsForList().rightPush("range", String.valueOf(i1)); //从表头进行插入
        }
        range = redisTemplate.opsForList().size("range");
        System.out.println("range 的大小为："+range);

        return "添加成功";
    }

    /**
     *   hash 类型
     * @return
     */
    @GetMapping("/hash")
    public String setHash(){
//        redisTemplate.opsForHash().put("hash","person","hash"); //单次插入
        Map<String,String>hmaps=new HashMap<>();
        hmaps.put("pp","p");
        hmaps.put("jk","jk");
        hmaps.put("kk","k");
        hmaps.put("gg","gg");
//        redisTemplate.opsForHash().putAll("pall",hmaps); //多值插入
//        Map<Object, Object> pall = redisTemplate.opsForHash().entries("pall"); //根据key 获取值
//        System.out.println(pall);
//        Set<Object> pall = redisTemplate.opsForHash().keys("pall"); //返回key
//        System.out.println(pall);


//        Boolean aBoolean = redisTemplate.opsForHash().putIfAbsent("pall", "user", "lktbz");
//        System.out.println("真假判断："+aBoolean);
//        Boolean  aBoolean = redisTemplate.opsForHash().putIfAbsent("user", "user", "lktbz");
//        System.out.println("真假判断："+aBoolean);
//        Boolean  aBoolean = redisTemplate.opsForHash().putIfAbsent("user", "user", "yan"); //不存在则添加，存在则不做任何操作
//        System.out.println("真假判断："+aBoolean);
        //取key
//        List<Object> pall = redisTemplate.opsForHash().values("pall");
//        System.out.println(pall);
        return "添加成功";
    }
    @GetMapping("/sets")
    public String setSet(){
//        Long add = redisTemplate.opsForSet().add("se", "v1", "v2", "v3");
//        System.out.println("set添加返回的值为："+add);
//        Long add = redisTemplate.opsForSet().add("se","v1","v4");
//        System.out.println("测试添加重复的元素，以及新值："+add);
//        Long se = redisTemplate.opsForSet().size("se");
//        System.out.println("元素的个数为："+se);
//        String se = redisTemplate.opsForSet().pop("se");
//        System.out.println("弹出的元素为："+se);
           //集合交并集操作，暂时没有练习
        return "添加成功";
    }
}
