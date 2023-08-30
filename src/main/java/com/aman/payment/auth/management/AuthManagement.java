package com.aman.payment.auth.management;

import com.aman.payment.auth.model.dto.JwtAuthenticationResponse;
import com.aman.payment.auth.model.dto.JwtExternalAuthenticationResponse;
import com.aman.payment.auth.model.payload.LoginRequest;
import com.aman.payment.auth.model.payload.ValidPasswordRequest;

public interface AuthManagement {
	
    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);
    
    public JwtExternalAuthenticationResponse authenticateExternalUser(LoginRequest loginRequest);
    
    public Boolean checkUserAuthenticate(String username, ValidPasswordRequest validPasswordRequest);
    
}
