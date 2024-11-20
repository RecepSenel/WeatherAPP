package com.readWise.gateway.filter;

import com.readWise.gateway.service.JweService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


import java.util.List;

@Component
@Order(-100)
public class JweFilter implements WebFilter {
    @Resource
    private JweService jweService;
    private final String LOGIN_PAGE = "/user/login";
    private final String REGISTER_PAGE = "/user";

    @Value(("${keys.api_key}"))
    private String api_key;

    @Value("${keys.api_secret}")
    private String api_secret;

/*
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // Mutate the request
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("api_key", "your_api_key_value")
                .header("header_secret", "your_secret_value")
                .build();

        // Use the mutated request
    //    return chain.filter(exchange.mutate().request(mutatedRequest).build());


        List<HttpCookie> cookie = exchange.getRequest().getCookies().get("jwe");
        String path = exchange.getRequest().getPath().toString();

        if(cookie == null || cookie.isEmpty()) {
            if(isPublicPath(path)) {
                return  chain.filter(exchange.mutate().request(mutatedRequest).build());
            }else {
                return unauthorizedResponse(exchange);
            }
        }else {
            try {
                if (jweService.isValid(cookie.getFirst())) {
                    if(isPublicPath(path)) {
                        throw new IllegalStateException("Bereits angemeldet");
                    }else {
                        return  chain.filter(exchange.mutate().request(mutatedRequest).build());
                    }
                }else {
                    return unauthorizedResponse(exchange);
                }
            } catch (Exception e) {
                return handleException(exchange, e);
            }
        }
    }

 */

    private boolean isPublicPath(String path) {
        return LOGIN_PAGE.equals(path) || REGISTER_PAGE.equals(path);
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> handleException(ServerWebExchange exchange, Exception e) {
        // Optional: Logging oder benutzerdefinierte Fehlerbehandlung
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return exchange.getResponse().setComplete();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest mutatedRequest = null;
        if(!exchange.getRequest().getHeaders().containsKey("api_key")) {
            // Mutate the request
            mutatedRequest = exchange.getRequest().mutate()
                    .header(api_key, api_key)
                    .header("api_secret", api_secret)
                    .build();
        }


        List<HttpCookie> cookie = exchange.getRequest().getCookies().get("jwe");
        String path = exchange.getRequest().getPath().toString();

        if(cookie == null || cookie.isEmpty()) {
            if(isPublicPath(path)) {
                return  chain.filter(exchange.mutate().request(mutatedRequest).build());
            }else {
                return unauthorizedResponse(exchange);
            }
        }else {
            try {
                if (jweService.isValid(cookie.getFirst())) {
                    if(isPublicPath(path)) {
                        throw new IllegalStateException("Bereits angemeldet");
                    }else {
                        return  chain.filter(exchange.mutate().request(mutatedRequest).build());
                    }
                }else {
                    return unauthorizedResponse(exchange);
                }
            } catch (Exception e) {
                return handleException(exchange, e);
            }
        }
    }
}
