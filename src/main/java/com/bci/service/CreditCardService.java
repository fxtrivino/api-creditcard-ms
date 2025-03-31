package com.bci.service;

import com.bci.entity.CreditCardDto;

public interface CreditCardService {

    public String storeCard(CreditCardDto creditCardDto);
    public String getCard(String token);
}

