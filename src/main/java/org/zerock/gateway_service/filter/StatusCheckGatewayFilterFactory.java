package org.zerock.gateway_service.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class StatusCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

   @Override
   public GatewayFilter apply(Object config) {
       return (exchange, chain) -> chain.filter(exchange).then(Mono.defer(() -> {
           HttpStatusCode statusCode = exchange.getResponse().getStatusCode();
           if (statusCode != null && statusCode.is5xxServerError()) {
               return Mono.error(new ResponseStatusException(statusCode));
           }else if(statusCode != null && statusCode.is4xxClientError()){
               return Mono.error(new ResponseStatusException(statusCode));
           }
           return Mono.empty();
       }));
    }
   }

