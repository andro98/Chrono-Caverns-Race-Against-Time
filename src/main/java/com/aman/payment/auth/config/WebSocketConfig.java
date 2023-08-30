package com.aman.payment.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

 @Configuration
 @EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	 

	@Autowired
    public WebSocketConfig( ) {
     
    }
    
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(), "/websocket-endpoint")
                .setAllowedOrigins("*");
    }
}
 