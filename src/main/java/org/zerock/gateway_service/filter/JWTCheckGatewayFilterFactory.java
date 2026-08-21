package org.zerock.gateway_service.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.log4j.Log4j2;


@Component
@Log4j2
public class JWTCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<JWTCheckGatewayFilterFactory.Config> {

   public JWTCheckGatewayFilterFactory() {
       super(Config.class);
   }

   @Override
   public GatewayFilter apply(Config config) {

       return (exchange, chain) -> {

           // 예: Authorization 헤더에서 JWT 토큰 추출 및 검증

           log.info("JWTAuth Gateway Filter applied");

           exchange.getRequest().getHeaders().forEach((key, value) -> {
               log.info("Header: " + key + " = " + value);
           });
           log.info("------------------------------");
     
          var req = exchange.getRequest().mutate()
                    .header("X-Authenticated", "false")
                    .build();
          return     chain.filter(exchange.mutate().request(req).build());
       };
   }

   @Data
   public static class Config {
       // 필요한 설정 필드 추가 (예: secret key, token prefix 등)
       private String role;
   }
 }
