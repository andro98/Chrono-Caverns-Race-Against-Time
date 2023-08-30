package com.aman.payment.auth.model.payload;

import java.util.HashSet;
import java.util.Set;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class AddEditRoleRequest implements AuthBasePayload<AddEditRoleRequest>{

	private String id;
    private String name;
    private String comment;
    private Set<String> menuIds;
    
	public AddEditRoleRequest() {
		super();
	}
	public AddEditRoleRequest(String id, String name, String comment, Set<String> menuIds) {
		super();
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.menuIds = menuIds;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Set<String> getMenuIds() {
		return menuIds;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMenuIds(Set<String> menuIds) {
		this.menuIds = menuIds;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public AddEditRoleRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		Set<String> eMenuIds = new HashSet<String>();
		menuIds.stream().forEach(s -> {
			eMenuIds.add(cryptoMngrAuthService.decrypt(s));
		});
		return new AddEditRoleRequest(cryptoMngrAuthService.decrypt(id), 
				cryptoMngrAuthService.decrypt(name), 
				cryptoMngrAuthService.decrypt(comment), 
				eMenuIds);
	}

	

   
}