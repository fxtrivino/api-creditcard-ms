package com.bci.util;

public class CreditCardUtil {
	
	private CreditCardUtil() {}
	
    public static String maskCardNumber(String cardNumber) {
        if (cardNumber.length() < 4) return "****";
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}

