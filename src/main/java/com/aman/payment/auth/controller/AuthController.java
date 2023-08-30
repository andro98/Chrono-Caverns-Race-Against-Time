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
package com.aman.payment.auth.controller;

import javax.validation.Valid;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aman.payment.annotation.CurrentUser;
import com.aman.payment.auth.event.OnUserLogoutSuccessEvent;
import com.aman.payment.auth.management.AuthJsonObjectFactoryImpl;
import com.aman.payment.auth.management.AuthManagement;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.dto.ApiResponseDTO;
import com.aman.payment.auth.model.dto.JwtAuthenticationResponse;
import com.aman.payment.auth.model.payload.LoginRequest;
import com.aman.payment.auth.model.payload.ValidPasswordRequest;
import com.aman.payment.auth.service.CryptoMngrAuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Api(value = "Authorization Rest API", description = "Defines endpoints that can be hit only when the user is not logged in. It's not secured by default.")

public class AuthController extends AuthJsonObjectFactoryImpl{

	private static final Logger logger = Logger.getLogger(AuthController.class);
	private final AuthManagement authManagement;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final CryptoMngrAuthService cryptoMngrAuthService;

	@Autowired
	public AuthController(AuthManagement authManagement, ApplicationEventPublisher applicationEventPublisher,
			CryptoMngrAuthService cryptoMngrAuthService) {
		this.authManagement = authManagement;
		this.applicationEventPublisher = applicationEventPublisher;
		this.cryptoMngrAuthService = cryptoMngrAuthService;
	}

	/**
	 * Entry point for the user log in. Return the jwt auth token and the refresh
	 * token
	 */
	@PostMapping("/login")
	@ApiOperation(value = "Authenticate user", notes = "Authenticate user and generate an access token")
	public ResponseEntity<String> login(
			@ApiParam(value = "The ApplicantRequest payload") @Valid @RequestBody String jsonString) {
		LoginRequest decryptLoginRequest = 
				convertJsonStringToObject(jsonString, LoginRequest.class);
		JwtAuthenticationResponse result = authManagement.authenticateUser(decryptLoginRequest);
		return ResponseEntity.ok(cryptoMngrAuthService.encrypt(result.toString()));

	}

	/**
	 * Log the user out from the app/device. Release the refresh token associated
	 * with the user device.
	 */
	@PostMapping("/logout")
	@ApiOperation(value = "Logout user", notes = "Logout currently authenticated user")
	public void logoutUser(@CurrentUser CustomUserDetails customUserDetails) {

		Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();

		OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(customUserDetails.getUsername(),
				credentials.toString());
		applicationEventPublisher.publishEvent(logoutSuccessEvent);
	}

	/**
	 * Entry point for the user log in. Return the jwt auth token and the refresh
	 * token
	 */
	@PostMapping("/checkUserAuthenticate")
	@ApiOperation(value = "Layer of secure action")
	public ResponseEntity<ApiResponseDTO> checkUserPassword(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody ValidPasswordRequest validPasswordRequest) {

		ValidPasswordRequest decryptValidPasswordRequest = validPasswordRequest.decrypt(cryptoMngrAuthService);

		Boolean checkAuth = authManagement.checkUserAuthenticate(customUserDetails.getUsername(),
				decryptValidPasswordRequest);
		if (checkAuth) {
			return ResponseEntity.ok(new ApiResponseDTO(
					cryptoMngrAuthService.encrypt(RandomStringUtils.random(10, true, true) + ":true")));
		} else {
			return ResponseEntity
					.ok(new ApiResponseDTO(cryptoMngrAuthService.encrypt(RandomStringUtils.random(10, true, true))));
		}
	}

//	/**
//	 * Checks is a given email is in use or not.
//	 */
//	@ApiOperation(value = "Checks if the given email is in use")
//	@GetMapping("/checkEmailInUse")
//	public ResponseEntity checkEmailInUse(
//			@ApiParam(value = "Email id to check against") @RequestParam("email") String email) {
//		Boolean emailExists = authService.emailAlreadyExists(email);
//		if (emailExists) {
//			return ResponseEntity
//					.ok(new ApiResponseDTO(cryptoMngrAuthService.encrypt(RandomStringUtils.random(10, true, true) + ":true")));
//		} else {
//			return ResponseEntity
//					.ok(new ApiResponseDTO(cryptoMngrAuthService.encrypt(RandomStringUtils.random(10, true, true))));
//		}
//
//	}
//
//	/**
//	 * Checks is a given username is in use or not.
//	 */
//	@ApiOperation(value = "Checks if the given username is in use")
//	@GetMapping("/checkUsernameInUse")
//	public ResponseEntity checkUsernameInUse(
//			@ApiParam(value = "Username to check against") @RequestParam("username") String username) {
//		Boolean usernameExists = authService.usernameAlreadyExists(username);
//		if (usernameExists) {
//			return ResponseEntity
//					.ok(new ApiResponseDTO(cryptoMngrAuthService.encrypt(RandomStringUtils.random(10, true, true) + ":true")));
//		} else {
//			return ResponseEntity
//					.ok(new ApiResponseDTO(cryptoMngrAuthService.encrypt(RandomStringUtils.random(10, true, true))));
//		}
//	}
//
//
//	/**
//	 * Receives the reset link request and publishes an event to send email id
//	 * containing the reset link if the request is valid. In future the deeplink
//	 * should open within the app itself.
//	 */
//	@PostMapping("/password/resetlink")
//	@ApiOperation(value = "Receive the reset link request and publish event to send mail containing the password "
//			+ "reset link")
//	public ResponseEntity resetLink(
//			@ApiParam(value = "The PasswordResetLinkRequest payload") @Valid @RequestBody PasswordResetLinkRequest passwordResetLinkRequest) {
//
//		return authService.generatePasswordResetToken(passwordResetLinkRequest).map(passwordResetToken -> {
//			// send email
////                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/password/reset");
////                    OnGenerateResetLinkEvent generateResetLinkMailEvent = new OnGenerateResetLinkEvent(passwordResetToken,
////                            urlBuilder);
////                    applicationEventPublisher.publishEvent(generateResetLinkMailEvent);
//			return ResponseEntity.ok(new ApiResponseDTO(cryptoMngrAuthService.encrypt("true")));
//		}).orElseThrow(
//				() -> new PasswordResetLinkException(passwordResetLinkRequest.getEmail(),
//						"Couldn't create a valid token"));
//	}
//
//	/**
//	 * Receives a new passwordResetRequest and sends the acknowledgement after
//	 * changing the password to the user's mail through the event.
//	 */
//
//	@GetMapping("/password/reset")
//	@ApiOperation(value = "Reset the password after verification and publish an event to send the acknowledgement "
//			+ "email")
//	public ResponseEntity resetPassword(
//			@ApiParam(value = "The PasswordResetRequest payload") @RequestParam("token") String token) {
//
//		String resetPassword = RandomStringUtils.randomAlphanumeric(10);
//		return authService.resetPassword(token, "1234567").map(changedUser -> {
//			// send email
////                    OnUserAccountChangeEvent onPasswordChangeEvent = new OnUserAccountChangeEvent(changedUser, "Reset Password",
////                            "Changed Successfully", resetPassword);
////                    applicationEventPublisher.publishEvent(onPasswordChangeEvent);
//			return ResponseEntity.ok(new ApiResponseDTO(cryptoMngrAuthService.encrypt("true")));
//		}).orElseThrow(() -> new PasswordResetException(token, "Error in resetting password"));
//	}
//
//	/**
//	 * Confirm the email verification token generated for the user during
//	 * registration. If token is invalid or token is expired, report error.
//	 */
//	@GetMapping("/registrationConfirmation")
//	@ApiOperation(value = "Confirms the email verification token that has been generated for the user during registration")
//	public ResponseEntity confirmRegistration(
//			@ApiParam(value = "the token that was sent to the user email") @RequestParam("token") String token) {
//
//		return authService.confirmEmailRegistration(token)
//				.map(user -> ResponseEntity.ok(new ApiResponseDTO(cryptoMngrAuthService.encrypt("true"))))
//				.orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", token,
//						"Failed to confirm. Please generate a new email verification request"));
//	}
//
//	/**
//	 * Resend the email registration mail with an updated token expiry. Safe to
//	 * assume that the user would always click on the last re-verification email and
//	 * any attempts at generating new token from past (possibly archived/deleted)
//	 * tokens should fail and report an exception.
//	 */
//	@GetMapping("/resendRegistrationToken")
//	@ApiOperation(value = "Resend the email registration with an updated token expiry. Safe to "
//			+ "assume that the user would always click on the last re-verification email and "
//			+ "any attempts at generating new token from past (possibly archived/deleted)"
//			+ "tokens should fail and report an exception. ")
//	public ResponseEntity resendRegistrationToken(
//			@ApiParam(value = "the initial token that was sent to the user email after registration") @RequestParam("token") String existingToken) {
//
//		EmailVerificationToken newEmailToken = authService.recreateRegistrationToken(existingToken)
//				.orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken,
//						"User is already registered. No need to re-generate token"));
//
//		return Optional.ofNullable(newEmailToken.getUser()).map(registeredUser -> {
//			UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath()
//					.path("/api/auth/registrationConfirmation");
//			OnRegenerateEmailVerificationEvent regenerateEmailVerificationEvent = new OnRegenerateEmailVerificationEvent(
//					registeredUser, urlBuilder, newEmailToken);
//			applicationEventPublisher.publishEvent(regenerateEmailVerificationEvent);
//			return ResponseEntity.ok(new ApiResponseDTO(cryptoMngrAuthService.encrypt("true")));
//		}).orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken,
//				"No user associated with this request. Re-verification denied"));
//	}
//
//	/**
//	 * Refresh the expired jwt token using a refresh token for the specific device
//	 * and return a new token to the caller
//	 */
//	@PostMapping("/refresh")
//	@ApiOperation(value = "Refresh the expired jwt authentication by issuing a token refresh request and returns the"
//			+ "updated response tokens")
//	public ResponseEntity refreshJwtToken(
//			@ApiParam(value = "The TokenRefreshRequest payload") @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
//
//		return authService.refreshJwtToken(tokenRefreshRequest).map(updatedToken -> {
//			String refreshToken = tokenRefreshRequest.getRefreshToken();
//			logger.info("Created new Jwt Auth token: " + updatedToken);
//			return ResponseEntity.ok(new JwtAuthenticationResponse(cryptoMngrAuthService.encrypt(updatedToken),
//					cryptoMngrAuthService.encrypt(refreshToken),
//					cryptoMngrAuthService.encrypt(String.valueOf(tokenProvider.getExpiryDuration()))));
//		}).orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.getRefreshToken(),
//				"Unexpected error during token refresh. Please logout and login again."));
//	}
}
