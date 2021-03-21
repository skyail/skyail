package com.skyail.gateway.handler;

import com.alibaba.fastjson.JSONObject;
import com.skyail.common.constant.HttpConstant;
import com.skyail.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 对于无权访问时的自定义处理逻辑
 */
@Component
@Slf4j
public class CustomServerAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        log.error("访问失败：",e);
        R res = R.fail(HttpStatus.UNAUTHORIZED.value(),"请求未被授权");
        ServerHttpResponse response=serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, HttpConstant.APPLICATION_JSON_VALUE);
        //response.getHeaders().set("Access-Control-Allow-Origin","*");
        //response.getHeaders().set("Cache-Control","no-cache");
        DataBuffer buffer =  response.bufferFactory().wrap(JSONObject.toJSONString(res).getBytes(Charset.forName("UTF-8")));
        return response.writeWith(Mono.just(buffer));
    }
}
