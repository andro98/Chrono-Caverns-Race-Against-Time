package com.aman.payment.auth.model.dto;

import com.aman.payment.auth.model.User;

public class AgentDTO{

    private String username;
    
	public AgentDTO() {
		super();
	}
	
	public AgentDTO(User user) {
		this.username = user.getUsername();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
   
}