/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aman.payment.auth.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.exception.InvalidTokenRequestException;
import com.aman.payment.auth.model.EmailVerificationToken;
import com.aman.payment.auth.model.User;
import com.aman.payment.auth.model.lookup.TokenStatus;
import com.aman.payment.auth.repository.EmailVerificationTokenRepository;

@Service
public class EmailVerificationTokenService {

    private static final Logger logger = Logger.getLogger(EmailVerificationTokenService.class);
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    @Value("${app.token.email.verification.duration}")
    private Long emailVerificationTokenExpiryDuration;

    @Autowired
    public EmailVerificationTokenService(EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    /**
     * Create an email verification token and persist it in the database which will be
     * verified by the user
     */
    public void createVerificationToken(User user, String token) {
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(token);
        emailVerificationToken.setTokenStatus(TokenStatus.STATUS_PENDING);
        emailVerificationToken.setUser(user);
        emailVerificationToken.setCreatedAt(Date.from(Instant.now()));
        emailVerificationToken.setCreatedBy("system");
        emailVerificationToken.setExpiryDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));
        logger.info("Generated Email verification token [" + emailVerificationToken + "]");
        emailVerificationTokenRepository.save(emailVerificationToken);
    }

    /**
     * Updates an existing token in the database with a new expiration
     */
    public EmailVerificationToken updateExistingTokenWithNameAndExpiry(EmailVerificationToken existingToken) {
        existingToken.setTokenStatus(TokenStatus.STATUS_PENDING);
        existingToken.setExpiryDate(Instant.now().plusMillis(emailVerificationTokenExpiryDuration));
        logger.info("Updated Email verification token [" + existingToken + "]");
        return save(existingToken);
    }

    /**
     * Finds an email verification token by the @NaturalId token
     */
    public Optional<EmailVerificationToken> findByToken(String token) {
        return emailVerificationTokenRepository.findByToken(token);
    }

    /**
     * Saves an email verification token in the repository
     */
    public EmailVerificationToken save(EmailVerificationToken emailVerificationToken) {
        return emailVerificationTokenRepository.save(emailVerificationToken);
    }

    /**
     * Generates a new random UUID to be used as the token for email verification
     */
    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * Verify whether the token provided has expired or not on the basis of the current
     * server time and/or throw error otherwise
     */
    public void verifyExpiration(EmailVerificationToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new InvalidTokenRequestException("Email Verification Token", token.getToken(), "Expired token. Please issue a new request");
        }
    }

}
