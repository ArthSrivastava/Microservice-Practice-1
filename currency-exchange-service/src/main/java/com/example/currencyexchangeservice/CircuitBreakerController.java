package com.example.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {
    Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    //Retry api calls number of times
//    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")

    //Breaks the circuit after trying some calls
//    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")

    //number of calls allowed per unit time
//    @RateLimiter(name = "default")

    //number of concurrent calls allowed
    @Bulkhead(name = "default")
    @GetMapping("/sample-api")
    public String sampleApi() {
        logger.info("\n\n Sample api url received \n\n");
//        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy-url", String.class);
//        return forEntity.getBody();
        return "sample-api";
    }

    public String hardcodedResponse(Exception ex) {
        return "Hardcoded-response!";
    }

    //For handling specific type of exceptions
//    public String hardcodedResponse(RuntimeException ex) {
//        return "Hardcoded-runtime-response!";
//    }
}
