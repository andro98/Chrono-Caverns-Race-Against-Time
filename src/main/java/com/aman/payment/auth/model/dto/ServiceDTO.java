package com.aman.payment.auth.model.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class ServiceDTO implements AuthBaseDTO<ServiceDTO>{

    private String id;
    private String name;
    private String code;
    private String status;
    private String fees;
    private String tax;
    private String merchantName;
    private String serviceType;
    private String requiredService;
    private String parentName;
    private String parentId;
    private String merchantId;
    private String description;
	private String icon;
	private String contractCount;
    
    
    

    

    public ServiceDTO() {
		super();
	}

    public ServiceDTO(String id, String name, String code, String status, String fees, 
    		String tax, String merchantName,
			String serviceType, String requiredService ,String parentName,String parentId,
			String merchantId,String description,String icon, String contractCount) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.status = status;
		this.fees = fees;
		this.tax = tax;
		this.merchantName = merchantName;
		this.serviceType = serviceType;
		this.requiredService=requiredService;
		this.parentName=parentName;
		this.parentId=parentId;
		this.merchantId=merchantId;
		this.description= description;
		this.icon=icon;
		this.contractCount = contractCount;
	}


	public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	 
 
	public String getRequiredService() {
		return requiredService;
	}

	public void setRequiredService(String requiredService) {
		this.requiredService = requiredService;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getContractCount() {
		return contractCount;
	}

	public void setContractCount(String contractCount) {
		this.contractCount = contractCount;
	}

	@Override
	public String toString() {
		
		JSONObject service = new JSONObject();
		try {
			service.put("id", id);
			service.put("name", name);
			service.put("code", code);
			service.put("fees", fees);
			service.put("tax", tax);
			service.put("merchantName", merchantName);
			service.put("status", status);
			service.put("serviceType", serviceType);
			service.put("requiredService", requiredService);
			service.put("parentName", parentName);
			service.put("parentId", parentId);
			service.put("merchantId", merchantId);
			service.put("description", description);
			service.put("contractCount", contractCount);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return service.toString();

	}

	@Override
	public ServiceDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new ServiceDTO(
				cryptoMngrAuthService.encrypt(id), 
				cryptoMngrAuthService.encrypt(name), 
				cryptoMngrAuthService.encrypt(code), 
				cryptoMngrAuthService.encrypt(status), 
				cryptoMngrAuthService.encrypt(fees), 
				cryptoMngrAuthService.encrypt(tax), 
				cryptoMngrAuthService.encrypt(merchantName), 
				cryptoMngrAuthService.encrypt(serviceType),
				cryptoMngrAuthService.encrypt(requiredService),
				cryptoMngrAuthService.encrypt(parentName),
				cryptoMngrAuthService.encrypt(parentId),
				cryptoMngrAuthService.encrypt(merchantId),
				cryptoMngrAuthService.encrypt(description),
				cryptoMngrAuthService.encrypt(icon),
				cryptoMngrAuthService.encrypt(contractCount)
				 );
	}
	
}