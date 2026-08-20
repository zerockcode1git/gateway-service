package org.zerock.gateway_service.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class FallbackController {

   @GetMapping("/fallback/reviews")
   public Mono<ResponseEntity<Map<String, String>>> reviewFallback() {
       return Mono.just(ResponseEntity
               .status(HttpStatus.SERVICE_UNAVAILABLE)
               .body(Map.of(
                       "message", "Review service is currently unavailable",
                       "status", "SERVICE_UNAVAILABLE"
               )));
   }

   @GetMapping("/fallback/products")
   public Mono<ResponseEntity<Map<String, String>>> productFallback() {
       return Mono.just(ResponseEntity
               .status(HttpStatus.SERVICE_UNAVAILABLE)
               .body(Map.of(
                       "message", "Product service is currently unavailable",
                       "status", "SERVICE_UNAVAILABLE"
               )));
   }
}
