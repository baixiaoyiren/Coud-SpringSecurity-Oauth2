//package com.javasm.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.javasm.utils.RedisCache;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
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
// * @Date：2022-10-10-14:28
// * @Description: token校验
// */
//@Component
//@AllArgsConstructor
//public class TokenChainFilter implements GlobalFilter, Ordered {
//
//    /**
//     * 指定好放行的路径
//     */
//    public static final List<String> ALLOW_URL = Arrays.asList("/login/doLogin", "myUrl");
//
//    private RedisCache redisCache;
//
//    /**
//     * 1、前提是约定好放在哪里带过来 一般放在headers里面  一般key为Authorization value 为 bearer token
//     * <p>
//     * 2/拿到请求的url
//     * 3拿到请求头
//     * 4拿到toekn
//     * 5校验
//     * 6放行
//     *
//     * @param exchange
//     * @param chain
//     * @return
//     */
//    @SneakyThrows
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        String path = request.getURI().getPath();
//        if (ALLOW_URL.contains(path)) {
//            return chain.filter(exchange);
//        }
//        // 检查
//        HttpHeaders headers = request.getHeaders();
//        List<String> authorization = headers.get("Authorization");
//        if (!CollectionUtils.isEmpty(authorization)){
//            String token = authorization.get(0);
//            // 约定好前缀
//            String realToken = token.replace("bearer ", "");
//            if (redisCache.hasKey(realToken)){
//                // redis中存在校验信息
//                return chain.filter(exchange);
//            }
//        }
//        // 拦截
//        // 设置拦截信息
//        ServerHttpResponse response = exchange.getResponse();
//        response.getHeaders().set("content-type","application/json;charset=utf-8");
//        HashMap<String,Object> map = new HashMap<>(4);
//        // 返回401
//        map.put("code", HttpStatus.UNAUTHORIZED.value());
//        map.put("msg","未授权");
//        ObjectMapper objectMapper = new ObjectMapper();
//        byte[] bytes = objectMapper.writeValueAsBytes(map);
//        DataBuffer wrap = response.bufferFactory().wrap(bytes);
//        return response.writeWith(Mono.just(wrap));
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
