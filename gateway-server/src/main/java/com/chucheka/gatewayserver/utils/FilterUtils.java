package com.chucheka.gatewayserver.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class FilterUtils {

    private final String CORRELATION_ID = "correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders) {

        if (requestHeaders.containsKey(CORRELATION_ID)) {

            List<String> header = requestHeaders.get(CORRELATION_ID);

            if (Objects.nonNull(header))
                return header.get(0);
        }
        return null;
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {

        return exchange.mutate().request(
                        exchange.getRequest().mutate()
                                .header(name, value)
                                .build())
                .build();

    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

}
