package com.aman.payment.auth.service;

/**
 * @author Aya Esmail
 */
public interface CryptoMngrAuthService {
      String decrypt(String message);
      String encrypt(String message);
    }
