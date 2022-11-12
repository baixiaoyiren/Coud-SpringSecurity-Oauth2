//package com.javasm.filter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.HashMap;
//
///**
// * @Author：MoDebing
// * @Version：1.0
// * @Date：2022-10-10-13:37
// * @Description: gateway的全局过滤器
// */
////@Component
//public class MyGlobalFilter implements GlobalFilter, Ordered {
//
//    /**
//     * 这个是过滤的方法
//     * 使用责任链模式
//     *
//     * @param exchange
//     * @param chain
//     * @return
//     *
//     * ServerHttpRequest 是响应式webflux里面的
//     * HttpServletRequest 是web里面的
//     *
//     */
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        // 针对请求的过滤 拿到请求 路径 等
//        ServerHttpRequest request = exchange.getRequest();
//        System.out.println(request.getURI().getPath());
//        System.out.println(request.getHeaders());
//        System.out.println(request.getPath());
//        System.out.println(request.getQueryParams());
//        System.out.println(request.getMethod());
//        System.out.println(request.getSslInfo());
//
//        ServerHttpResponse response = exchange.getResponse();
//        // 设置拦截信息
//        response.getHeaders().set("content-type","application/json;charset=utf-8");
//        HashMap<String,Object> map = new HashMap<>(4);
//        map.put("code", HttpStatus.UNAUTHORIZED.value());
//        map.put("msg","未授权");
//        ObjectMapper objectMapper = new ObjectMapper();
//        // 把map对象转成字节
//        byte[] bytes = new byte[0];
//        try {
//            bytes = objectMapper.writeValueAsBytes(map);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        // 将字节数组包装成一个数据包
//        DataBuffer wrap = response.bufferFactory().wrap(bytes);
//        return response.writeWith(Mono.just(wrap));
//
//
//        // 放行，到下一个过滤器
//        //return chain.filter(exchange);
//    }
//
//    /**
//     * 指定过滤器在过滤器链里面执行的方法
//     *
//     * @return
//     */
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
