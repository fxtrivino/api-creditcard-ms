package com.bci.service;

import org.springframework.stereotype.Service;

import com.bci.entity.CreditCard;
import com.bci.repository.CreditCardRepository;
import com.bci.util.CreditCardUtil;
import com.bci.util.EncryptionUtil;

import java.util.UUID;

@Service
public class CreditCardService {
    private final CreditCardRepository repository;

    public CreditCardService(CreditCardRepository repository) {
        this.repository = repository;
    }

    public String storeCard(String cardNumber) throws Exception {
        String encryptedCard = EncryptionUtil.encrypt(cardNumber);
        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        CreditCard creditCard = new CreditCard();
        creditCard.setToken(token);
        creditCard.setEncryptedCard(encryptedCard);
        repository.save(creditCard);

        return token;
    }

    public String getCard(String token) throws Exception {
        return repository.findByToken(token)
                .map(card -> {
                    try {
                        return CreditCardUtil.maskCardNumber(EncryptionUtil.decrypt(card.getEncryptedCard()));
                    } catch (Exception e) {
                        throw new RuntimeException("Error al desencriptar la tarjeta", e);
                    }
                })
                .orElse("Tarjeta no encontrada");
    }
}

