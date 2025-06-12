package com.chucheka.gatewayserver.filters;

import com.chucheka.gatewayserver.utils.FilterUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class TrackingFilter implements GlobalFilter, Ordered {

    private final FilterUtils filterUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();

        if (isCorrelationIdPresent(httpHeaders)) {
            log.info("correlation-id found in tracking filter: {}", filterUtils.getCorrelationId(httpHeaders));
        } else {
            String correlationID = generateCorrelationId();

            exchange = filterUtils.setCorrelationId(exchange, correlationID);

            log.debug("correlation-id generate in tracking filter: {}", correlationID);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isCorrelationIdPresent(HttpHeaders httpHeaders) {
        return filterUtils.getCorrelationId(httpHeaders) != null;
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }
}