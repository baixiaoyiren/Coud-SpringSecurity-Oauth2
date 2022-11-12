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
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * @Author：MoDebing
// * @Version：1.0
// * @Date：2022-10-10-13:52
// * @Description: 网关层面的 ip拦截
// *
// * 请求  -----> gateway ------> service
// *
// * 黑名单 blank list
// * 白名单 white list
// *
// * 根据数量
// *
// */
//@Component
//public class IpCheckFilter implements GlobalFilter, Ordered {
//
//
//    public static final List<String> BLAK_LIST = Arrays.asList("127.0.0.1","192.168.88.88");
//
//    /**
//     * 拿到ip
//     *
//     * 校验
//     *
//     * 拦截或放行
//     *
//     * @param exchange
//     * @param chain
//     * @return
//     */
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        String ip = request.getHeaders().getHost().getHostString();
//
//        // 查询数据库 如果这个ip是在黑名单里面  则不放行
//        // 因为网关的并发量比较高，不会在网关层面操作数据库
//        // 并发不大的可以查询后台
//        // 并发大的直接使用redis
//        if (!BLAK_LIST.contains(ip)){
//            return chain.filter(exchange);
//        }
//        // 拦截
//        ServerHttpResponse response = exchange.getResponse();
//        // 设置拦截信息
//        response.getHeaders().set("content-type","application/json;charset=utf-8");
//        HashMap<String,Object> map = new HashMap<>(4);
//        map.put("code", HttpStatus.BAD_REQUEST.value());
//        map.put("msg","非法请求，你已被拉黑，请联系管理员");
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
//    }
//
//    @Override
//    public int getOrder() {
//        return -5;
//    }
//}
