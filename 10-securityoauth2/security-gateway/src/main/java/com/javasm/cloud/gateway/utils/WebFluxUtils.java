package com.javasm.cloud.gateway.utils;

import cn.hutool.json.JSONUtil;
import com.javasm.cloud.common.entity.ResultCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 异常响应工具类
 *
 * @Author haoxr
 * @Date 2021/01/29 13:30
 */
public class WebFluxUtils {

    public static Mono<Void> writeResponse(ServerHttpResponse response, ResultCode resultCode) {
        switch (resultCode) {
            case UNAUTHORIZED:
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                break;
            case FORBIDDEN:
                response.setStatusCode(HttpStatus.FORBIDDEN);
                break;
            default:
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                break;
        }
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        // 跨域访问
        response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.getHeaders().set(HttpHeaders.CACHE_CONTROL, "no-cache");
        String body = JSONUtil.toJsonStr(resultCode);
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }

}