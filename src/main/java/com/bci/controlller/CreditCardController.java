package com.bci.controlller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bci.service.CreditCardService;

@RestController
@RequestMapping("/creditcard")
public class CreditCardController {
    private final CreditCardService service;

    public CreditCardController(CreditCardService service) {
        this.service = service;
    }

    @PostMapping("/store")
    public String storeCard(@RequestParam String cardNumber) throws Exception {
        return "Tarjeta almacenada con token: " + service.storeCard(cardNumber);
    }

    @GetMapping("/get/{token}")
    public String getCard(@PathVariable String token) throws Exception {
        return "Tarjeta: " + service.getCard(token);
    }
}


