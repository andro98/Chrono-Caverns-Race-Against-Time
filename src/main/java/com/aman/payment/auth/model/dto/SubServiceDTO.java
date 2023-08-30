package com.aman.payment.auth.model.dto;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class SubServiceDTO implements AuthBaseDTO<SubServiceDTO>{

    private String id;
    private String name;
    private String fees;
    private String status;
    private String serviceName;
    private String description;
    private String contractCounts;
    

    public SubServiceDTO() {
		super();
	}
    
    public SubServiceDTO(String id, String name, String fees, String status, 
    		String serviceName,String description, String contractCounts) {
		super();
		this.id = id;
		this.name = name;
		this.fees = fees;
		this.status = status;
		this.serviceName = serviceName;
		this.description=description;
		this.contractCounts = contractCounts;
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

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getFees() {
        return this.fees;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContractCounts() {
		return contractCounts;
	}

	public void setContractCounts(String contractCounts) {
		this.contractCounts = contractCounts;
	}

	@Override
	public SubServiceDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new SubServiceDTO(
				cryptoMngrAuthService.encrypt(id), 
				cryptoMngrAuthService.encrypt(name), 
				cryptoMngrAuthService.encrypt(fees), 
				cryptoMngrAuthService.encrypt(status), 
				cryptoMngrAuthService.encrypt(serviceName),
				cryptoMngrAuthService.encrypt(description),
				cryptoMngrAuthService.encrypt(contractCounts));
	}

}