package com.javasm;

import com.javasm.cloud.RestTemplateApplication;
import com.javasm.cloud.entity.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-09-22:55
 * @Description:
 */
@SpringBootTest(classes = RestTemplateApplication.class)
public class RestTemplateApplicationTests {

    @Test
    public void test01() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://www.baidu.com";
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
    }


    @Test
    public void testGet() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testGet?name=123";
        String resource = restTemplate.getForObject(url, String.class);
        System.out.println(resource);
    }

    @Test
    public void testGet01() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testGet?name=123";
        ResponseEntity<String> resource = restTemplate.getForEntity(url, String.class);
        System.out.println(resource);
    }


    @Test
    public void testPost1() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testPost1";
        User user = new User();
        user.setAge("12");
        user.setName("zhangsan");
        String resource = restTemplate.postForObject(url,user, String.class);
        System.out.println(resource);
    }


    @Test
    public void testPost2() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testPost2";
        Map<String,String> user = new HashMap<>();
        user.put("name","zhangsan");
        user.put("age","12");
        String resource = restTemplate.postForObject(url,null, String.class,user);
        System.out.println(resource);
    }
}
