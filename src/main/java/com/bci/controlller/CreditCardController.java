package com.bci.controlller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bci.entity.CreditCardDto;
import com.bci.service.CreditCardService;

@RestController
@RequestMapping("/creditcard")
public class CreditCardController {
    private final CreditCardService service;

    public CreditCardController(CreditCardService service) {
        this.service = service;
    }
    
    @PostMapping("/store")
    public ResponseEntity<Object> storeCard(@RequestBody CreditCardDto creditCardDto) {
    	String token = service.storeCard(creditCardDto);
    	return new ResponseEntity<>("Tarjeta almacenada con token: " + token, HttpStatus.CREATED);
    }

    @GetMapping("/get/{token}")
    public ResponseEntity<Object> getCard(@PathVariable String token) {
    	String tarjeta = service.getCard(token);
        return new ResponseEntity<>("Tarjeta: " + tarjeta, HttpStatus.OK);
    }
}


