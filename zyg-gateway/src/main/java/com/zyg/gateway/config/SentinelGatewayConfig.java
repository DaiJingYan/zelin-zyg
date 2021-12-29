package com.zyg.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.fastjson.JSON;
import com.zyg.gateway.entity.R;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 功能: 定制网关出现异常后的错误提示
 * 参数:
 * 返回值:
 * 时间: 2021/12/29 15:17
 */
@Configuration
public class SentinelGatewayConfig {
    //TODO 响应式编程
    //GatewayCallbackManager
    public SentinelGatewayConfig(){
        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler(){
            //网关限流了请求，就会调用此回调  Mono Flux
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
                R error = R.error(100002,"请求流量过大");
                String errJson = JSON.toJSONString(error);
                Mono<ServerResponse> body = ServerResponse.ok().body(Mono.just(errJson), String.class);
                return body;
            }
        });
     }
}