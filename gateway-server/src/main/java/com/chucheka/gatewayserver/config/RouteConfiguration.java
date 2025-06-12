//package com.chucheka.gatewayserver.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.function.RouterFunction;
//import org.springframework.web.servlet.function.ServerResponse;
//
//import static com.chucheka.gatewayserver.filters.SampleHandlerFilterFunctions.instrument;
//import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
//import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
//
//@Configuration
//class RouteConfiguration {
//
//    @Bean
//    public RouterFunction<ServerResponse> instrumentRoute() {
//        return route("identity-service")
//                .filter(instrument("X-Request-Id", "X-Response-Id"))
//                .build();
//    }
//}