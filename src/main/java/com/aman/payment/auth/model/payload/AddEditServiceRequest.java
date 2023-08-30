package com.aman.payment.auth.model.payload;

import java.util.HashSet;
import java.util.Set;

import com.aman.payment.auth.service.CryptoMngrAuthService;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "AddEditServiceRequest", description = "Add/Edit Service Request DTO Payload")
public class AddEditServiceRequest implements AuthBasePayload<AddEditServiceRequest>{

	private String id;

    private String name;

    private String description;

    private String status;
    
    private String serviceType; 
    
    private String fees;
    
    private String tax;
    
    private String mainServiceId;

    private String merchantId;
    
    private Set<String> locationIds;
    
    private String requiredService;

    public AddEditServiceRequest() {
		super();
	}

	  
    public String getRequiredService() {
		return requiredService;
	}



	public void setRequiredService(String requiredService) {
		this.requiredService = requiredService;
	}



	
    public AddEditServiceRequest(String id, String name, String description, String status, String serviceType,
			String fees, String tax, String mainServiceId, String merchantId, Set<String> locationIds,
			String requiredService) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.serviceType = serviceType;
		this.fees = fees;
		this.tax = tax;
		this.mainServiceId = mainServiceId;
		this.merchantId = merchantId;
		this.locationIds = locationIds;
		this.requiredService = requiredService;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}


	public String getMainServiceId() {
		return mainServiceId;
	}


	public void setMainServiceId(String mainServiceId) {
		this.mainServiceId = mainServiceId;
	}


	public String getMerchantId() {
		return merchantId;
	}


	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}


	public Set<String> getLocationIds() {
		return locationIds;
	}


	public void setLocationIds(Set<String> locationIds) {
		this.locationIds = locationIds;
	}


	 


	@Override
	public AddEditServiceRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		Set<String> eLocationIds = new HashSet<String>();
		if(locationIds!=null) {
			locationIds.stream().forEach(s -> {
				eLocationIds.add(cryptoMngrAuthService.decrypt(s));
			});	
		}
		
		return new AddEditServiceRequest(cryptoMngrAuthService.decrypt(id), 
				name!=null? cryptoMngrAuthService.decrypt(name):null, 
				description !=null? cryptoMngrAuthService.decrypt(description):null, 
				status!=null? cryptoMngrAuthService.decrypt(status):null, 
				serviceType !=null?cryptoMngrAuthService.decrypt(serviceType):null, 
				fees !=null?cryptoMngrAuthService.decrypt(fees):null, 
				tax !=null?cryptoMngrAuthService.decrypt(tax):null, 
				mainServiceId !=null?cryptoMngrAuthService.decrypt(mainServiceId):null, 
			    merchantId !=null?cryptoMngrAuthService.decrypt(merchantId):null, 
				eLocationIds, 
				cryptoMngrAuthService.decrypt(requiredService));
	}


	
	
}