package com.example.currencyexchangeservice;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CurrencyExchangeController {

    private final Environment environment;
    private final CurrencyExchangeRepo currencyExchangeRepo;

    public CurrencyExchangeController(Environment environment, CurrencyExchangeRepo currencyExchangeRepo) {
        this.environment = environment;
        this.currencyExchangeRepo = currencyExchangeRepo;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveCurrencyExchange(
            @PathVariable String from,
            @PathVariable String to) {
        String port = environment.getProperty("local.server.port");
//        CurrencyExchange currencyExchange =
//                new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(65), port);
        Optional<CurrencyExchange> optionalCurrencyExchange = currencyExchangeRepo.findByFromAndTo(from, to);
        if(optionalCurrencyExchange.isPresent()) {
            CurrencyExchange currencyExchange = optionalCurrencyExchange.get();
            currencyExchange.setEnvironment(port);
            return currencyExchange;
        } else {
            throw new RuntimeException("No such exchange present!");
        }
    }
}
