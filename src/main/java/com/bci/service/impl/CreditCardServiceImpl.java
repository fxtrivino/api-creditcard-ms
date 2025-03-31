package com.bci.service.impl;

import org.springframework.stereotype.Service;

import com.bci.entity.CreditCard;
import com.bci.entity.CreditCardDto;
import com.bci.exception.InvalidDecryptDataException;
import com.bci.repository.CreditCardRepository;
import com.bci.service.CreditCardService;
import com.bci.util.CreditCardUtil;
import com.bci.util.EncryptionUtil;

import java.util.UUID;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository repository;

    public CreditCardServiceImpl(CreditCardRepository repository) {
        this.repository = repository;
    }

    public String storeCard(CreditCardDto creditCardDto) {
        String encryptedCard = EncryptionUtil.encrypt(creditCardDto.getCardNumber());
        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        CreditCard creditCard = new CreditCard();
        creditCard.setToken(token);
        creditCard.setEncryptedCard(encryptedCard);
        repository.save(creditCard);

        return token;
    }

    public String getCard(String token) {
        return repository.findByToken(token)
                .map(card -> {
                    try {
                        return CreditCardUtil.maskCardNumber(EncryptionUtil.decrypt(card.getEncryptedCard()));
                    } catch (Exception e) {
                        throw new InvalidDecryptDataException("Error al desencriptar la tarjeta " + e.getMessage());
                    }
                })
                .orElse("Tarjeta no encontrada");
    }
}

