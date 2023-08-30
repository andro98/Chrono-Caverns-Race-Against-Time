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
package com.aman.payment.auth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aman.payment.auth.service.CryptoMngrAuthService;
import com.aman.payment.auth.service.impl.CustomUserDetailsService;
import com.aman.payment.auth.service.impl.RefreshTokenService;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = Logger.getLogger(JwtAuthenticationFilter.class);

    @Value("${app.jwt.header}")
    private String tokenRequestHeader;

    @Value("${app.jwt.header.prefix}")
    private String tokenRequestHeaderPrefix;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtTokenValidator jwtTokenValidator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @Autowired
    private CryptoMngrAuthService cryptoMngrAuthService;
    
    /**
     * Filter the incoming request for a valid token in the request header
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
        	
//        	response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
//            response.setHeader("Access-Control-Max-Age", "3600");            
            
            String jwt = getJwtFromRequest(request);
            if(jwt != null)
            	jwt = cryptoMngrAuthService.decrypt(jwt);
            
            if (StringUtils.hasText(jwt) && jwtTokenValidator.validateToken(jwt)) {
                String userId = jwtTokenProvider.getUserIdFromJWT(jwt);
					if(userId != null && !userId.equals("")) { 
//							&& refreshTokenService.findByToken(jwt.trim()).get().getUser().getId().equals(Long.valueOf(cryptoMngrAuthService.decrypt(userId)))
//							&& refreshTokenService.findByToken(jwt.trim()).get().getUser().getActive()) {	
						UserDetails userDetails = customUserDetailsService.loadUserById(Long.valueOf(userId));//cryptoMngrAuthService.decrypt(userId)));
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, jwt, userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
            }
        } catch (Exception ex) {
            log.error("Failed to set user authentication in security context: ", ex);
            throw ex;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extract the token from the Authorization request header
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(tokenRequestHeader);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenRequestHeaderPrefix)) {
            return bearerToken.replace(tokenRequestHeaderPrefix, "");
        }
        return null;
    }
}
