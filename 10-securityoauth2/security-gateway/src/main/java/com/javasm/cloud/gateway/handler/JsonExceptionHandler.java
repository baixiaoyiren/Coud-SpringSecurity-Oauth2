package com.javasm.cloud.gateway.handler;

import cn.hutool.json.JSONUtil;
import com.javasm.cloud.common.entity.Response;
import com.javasm.cloud.common.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Primary   // 同类型带有该注解的 Bean 优先背识别
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class JsonExceptionHandler implements WebExceptionHandler {

    /**
     * 所有异常都会在这里被集中处理
     *
     * @param exchange 上下文
     * @param ex       异常
     *                 SuppressWarnings("NullableProblems")：屏蔽 @NonNullApi 的警告
     */
    @Override
    @SuppressWarnings("NullableProblems")
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        ResultCode resultCode = null;
        StringBuilder message = new StringBuilder();
        if (ex instanceof ResponseStatusException && ((ResponseStatusException) ex).getStatus() == HttpStatus.UNAUTHORIZED) {
            resultCode = ResultCode.EXPIRED;
            response.setStatusCode(HttpStatus.UNAUTHORIZED);

        } else if ((ex instanceof ResponseStatusException && ((ResponseStatusException) ex).getStatus() == HttpStatus.NOT_FOUND)) {
            resultCode = ResultCode.ERROR;
            response.setStatusCode(HttpStatus.NOT_FOUND);
        } else {
            resultCode = ResultCode.BADREQUEST;
            response.setStatusCode(HttpStatus.BAD_REQUEST);

        }

        message.append(resultCode.getMsg()).append(",details:").append(ex.getMessage());
        Response resp = new Response(resultCode).msg(message.toString());
        response.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.getHeaders().set(HttpHeaders.CACHE_CONTROL, "no-cache");
        String body = JSONUtil.toJsonStr(resp);
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }


}