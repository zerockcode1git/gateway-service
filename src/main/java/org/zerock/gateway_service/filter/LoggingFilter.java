package org.zerock.gateway_service.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class LoggingFilter implements GlobalFilter, Ordered {

   @Override
   public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
       
       ServerHttpRequest request = exchange.getRequest();
       Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
       String routeId = route != null ? route.getId() : "unknown";

       log.info("Request: {} {} -> route={}", request.getMethod(), request.getURI(), routeId);

       return chain.filter(exchange).then(Mono.fromRunnable(() -> {
           ServerHttpResponse response = exchange.getResponse();
           log.info("Response: {} for {} {} -> route={}", response.getStatusCode(), request.getMethod(), request.getURI(), routeId);
       }));
   }

   @Override
   public int getOrder() {
       return Ordered.LOWEST_PRECEDENCE;
   }
}
