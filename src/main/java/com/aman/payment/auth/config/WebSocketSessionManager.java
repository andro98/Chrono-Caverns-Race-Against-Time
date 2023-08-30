package com.aman.payment.auth.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketSessionManager {
    public static final Set<WebSocketSession> sessions = new HashSet<>();
    
    public static void addSession(WebSocketSession session) {
        sessions.add(session);
    }
    
    public static void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }
    
    public static void broadcast(String topic, Object payload) {
        TextMessage message = new TextMessage(payload.toString());
        for (WebSocketSession session : sessions) {
//        	 Map<String, Object> attributes = session.getAttributes();
//             Object userName = attributes.get("userName");
//             System.out.println("userName = "+userName);
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(payload.toString()));
                } catch (IOException e) {
                	e.printStackTrace();
                    // Handle exception, if needed
                }
            }
        }
    }
}
