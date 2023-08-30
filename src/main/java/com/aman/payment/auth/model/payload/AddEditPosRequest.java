package com.aman.payment.auth.model.payload;

import java.util.HashSet;
import java.util.Set;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class AddEditPosRequest implements AuthBasePayload<AddEditPosRequest>{
    private String id;
    private String name;
    private String description;
    private String status;
    private Set<String> serviceIds;
    private Set<String> sectorIds;
    
    
	public AddEditPosRequest(String id, String name, String description, String status,
			Set<String> serviceIds, Set<String> sectorIds) {
		super();
		this.id = id;
		this.name = name;
		this.sectorIds = sectorIds;
		this.description = description;
		this.status = status;
		this.serviceIds = serviceIds;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	public String getStatus() {
		return status;
	}
	public Set<String> getServiceIds() {
		return serviceIds;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setServiceIds(Set<String> serviceIds) {
		this.serviceIds = serviceIds;
	}

	public Set<String> getSectorIds() {
		return sectorIds;
	}

	public void setSectorIds(Set<String> sectorIds) {
		this.sectorIds = sectorIds;
	}

	@Override
	public AddEditPosRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		Set<String> eServices = new HashSet<String>();
		if (serviceIds!=null) {
			serviceIds.stream().forEach(s -> {
				eServices.add(cryptoMngrAuthService.decrypt(s));
			});
			serviceIds=eServices;
		}
		
		Set<String> eSectors = new HashSet<String>();
		if (sectorIds!=null) {
			sectorIds.stream().forEach(s -> {
				eSectors.add(cryptoMngrAuthService.decrypt(s));
			});
			sectorIds=eSectors;
		}
		
		
		return new AddEditPosRequest(cryptoMngrAuthService.decrypt(id), 
				cryptoMngrAuthService.decrypt(name), 
				cryptoMngrAuthService.decrypt(description), 
				cryptoMngrAuthService.decrypt(status), 
				eServices, sectorIds);
		
	}
    
	
}