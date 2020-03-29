package com.dhcc.gateway.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 处理crsf跨站访问
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange swe, WebFilterChain wfc) {
        System.out.println("====================csrf=====================");
        ServerHttpRequest request = swe.getRequest();
        System.out.println("------------hehe ------"+CorsUtils.isCorsRequest(request));
        if (CorsUtils.isCorsRequest(request)) {
            System.out.println("------------哈哈------");
            ServerHttpResponse response = swe.getResponse();
            HttpHeaders headers = response.getHeaders();
            headers.add("Access-Control-Allow-Credentials","true");
            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Access-Control-Allow-Headers", "*");
            headers.add("Access-Control-Allow-Methods", "*");
            headers.add("Access-Control-Max-Age", "18000L");
            headers.add("Access-Control-Allow-Headers", "my-token");
            headers.add("Access-Control-Allow-Headers", "Content-Type");
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
        }
        return wfc.filter(swe);
    }

}
