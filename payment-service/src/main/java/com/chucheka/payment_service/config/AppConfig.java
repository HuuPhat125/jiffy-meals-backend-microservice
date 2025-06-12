package com.chucheka.payment_service.config;


import com.chucheka.payment_service.exceptions.ClientErrorException;
import com.chucheka.payment_service.exceptions.ServerErrorException;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
@Slf4j
public class AppConfig {

    @Value("${payment.gateway.base.url:localhost:8080}")
    private String baseUrl;

    @Bean
    public WebClient webClient() {


        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(customClientHttpConnector())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(logRequest())
                .filter(logResponse())
                .filter(errorHandling())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info(">>>>>>>>>>>>>Request: {} {} <<<<<<<<<<<<<<<", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Response: {} {}", clientResponse.statusCode(), clientResponse.headers().asHttpHeaders());
            return Mono.just(clientResponse);
        });
    }

    private ExchangeFilterFunction errorHandling() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {

                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            String errorMessage = String.format("API returned error status %s with body %s", clientResponse.statusCode(), errorBody);
                            log.error(errorMessage);
                            if (clientResponse.statusCode().is5xxServerError()) {
                                throw new ServerErrorException("Issuer inoperative");
                            } else {
                                throw new ClientErrorException("Bad request");
                            }
                        });
            } else {
                return Mono.just(clientResponse);
            }
        });
    }

    @Bean
    public ClientHttpConnector customClientHttpConnector() {

        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                                .doOnConnected(conn -> conn
                                        .addHandlerLast(new ReadTimeoutHandler(10))
                                        .addHandlerLast(new WriteTimeoutHandler(10))));

        return new ReactorClientHttpConnector(httpClient);
    }
}
