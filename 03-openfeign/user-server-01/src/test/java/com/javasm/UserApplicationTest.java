package com.javasm;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author：MoDebing
 * @Version：1.0
 * @Date：2022-10-10-01:07
 * @Description:
 */
@SpringBootTest(classes = UserApplication.class)
public class UserApplicationTest {

    /**
     *
     * feign核心源码
     */
    //@Test
    //public void openfeign(){
    //    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(UserApplication.class);
    //    RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);
    //    UserOrderFeign userOrderFeign = (UserOrderFeign) Proxy.newProxyInstance(UserApplicationTest.class.getClassLoader(), new Class[]{UserOrderFeign.class}, new InvocationHandler() {
    //        @Override
    //        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    //            // 拿到服务提供方的ip 和 端口，然后发起远程调用
    //            String methodName = method.getName();
    //            GetMapping annotation = method.getAnnotation(GetMapping.class);
    //            String path = annotation.value()[0];  // GetMapping上的url
    //            // 根据方法获取接口
    //            Class<?> aClass = method.getDeclaringClass();
    //            // 再根据接口获取feign注解，然后解析这个注解拿到服务名
    //            FeignClient feignClient = aClass.getAnnotation(FeignClient.class);
    //            String serverName = feignClient.value();
    //            String url = "http://" + serverName + "/" + path;
    //            // 发起请求
    //            String response = restTemplate.getForObject(url, String.class);
    //            return response;
    //        }
    //    });
    //    String response = userOrderFeign.doOrder();
    //    System.out.println(response);
    //}
}
