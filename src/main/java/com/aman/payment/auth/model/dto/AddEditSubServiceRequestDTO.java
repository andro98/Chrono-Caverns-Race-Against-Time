package com.aman.payment.auth.model.dto;

import com.aman.payment.auth.model.SubService;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "AddEditServiceRequest", description = "Add/Edit Service Request DTO Payload")
public class AddEditSubServiceRequestDTO {

//	@NullOrNotBlank(message = "Login Username can be equal 0 in case add new service")
//    @ApiModelProperty(value = "ID Service", required = true, allowableValues = "0", allowEmptyValue = true)
	private Long id;
	
//	@NotNull(message = "Service Name cannot be blank")
//    @ApiModelProperty(value = "Valid Name", required = true, allowableValues = "NonEmpty String")
    private String name;
    
//	@NotNull(message = "Service Fees cannot be blank")
//  @ApiModelProperty(value = "Valid Fees", required = true, allowableValues = "NonEmpty Duoble 0")
    private Double fees;
	
//    @ApiModelProperty(value = "Service Description", required = false, allowableValues = "NonEmpty String")
    private String description;
	
//	@NotNull(message = "Service Status cannot be blank")
//    @ApiModelProperty(value = "Valid status", required = true, allowableValues = "NonEmpty String")
    private String status;

    private Long serviceId;
    
    public AddEditSubServiceRequestDTO() {
		super();
	}

	public AddEditSubServiceRequestDTO(SubService subService) {

		this.id = subService.getId();
    	this.name = subService.getName();
    	this.serviceId = subService.getServiceFk().getId();
    	this.description = subService.getDescription();
    	this.status = subService.getStatusFk();
    	this.fees = subService.getFees();
    	
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Double getFees() {
		return fees;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}

	

}