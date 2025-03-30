package com.bci.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bci.entity.CreditCard;

import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    Optional<CreditCard> findByToken(String token);
}

