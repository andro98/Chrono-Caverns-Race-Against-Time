package com.aman.payment.auth.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

 
public class WebSocketHandler extends TextWebSocketHandler {
    
	private static final String UNDER_REVIWED_CONTRACTS_TOPIC = "/topic/underReviewedContracts";

	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Add the session to the session manager
    	//System.out.println("session ===== "+session);
        WebSocketSessionManager.addSession(session);
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages from the client, if needed
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove the session from the session manager
        WebSocketSessionManager.removeSession(session);
    }
    
    public static void sendUnderReviewedContracts(String payload) {
        // Send the badge number to all connected clients
        WebSocketSessionManager.broadcast(UNDER_REVIWED_CONTRACTS_TOPIC, payload);
    }
   
}
