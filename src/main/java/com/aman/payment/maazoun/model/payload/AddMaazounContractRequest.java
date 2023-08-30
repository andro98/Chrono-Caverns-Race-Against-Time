package com.aman.payment.maazoun.model.payload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class AddMaazounContractRequest {

	private Long subServiceId;
	private String subServiceName;
	//private String subServiceFees;
	//private String amount;
	private Long maazounId;
	//private String feeAmount;
	private double contractPaidAmount;
	private double contractPaidAdvanceFees;
	private double contractPaidDelayedFees;
	private double contractPaidConditionsFees;
	private double contractPaidBetweenusFees;
	
	private String contractDate;
	private String bookSerialNumber;
	private String bookFinancialNumber;
	private String contractNumber;
	private String contractFinancialNumber;
	private String husbandName;
	private String wifeName;
	private String paymentTypeId;
	private String husbandNationalId;
	private String wifeNationalId;
	private String husbandNationalIdExpiryDate;
	private String wifeNationalIdExpiryDate;
    private Long sectorId; 
    private String status;
    private String imageUrl;
	private MultipartFile attContract;
	private String maazouniaChurchNameType;
	private Long bookWarehouseId;
	private String talakStatus;
	private String talakCount;
 	private String wifeRepresentedStatus;
	private String talakType;
  
	

	private List<ContractChecklistValidationRequest> contractChecklistValidationRequest;

	public AddMaazounContractRequest() {
	}

	public Long getMaazounId() {
		return maazounId;
	}

	public void setMaazounId(Long maazounId) {
		this.maazounId = maazounId;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public String getBookSerialNumber() {
		return bookSerialNumber;
	}

	public void setBookSerialNumber(String bookSerialNumber) {
		this.bookSerialNumber = bookSerialNumber;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getHusbandName() {
		return husbandName;
	}

	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}

	public String getWifeName() {
		return wifeName;
	}

	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}

	public Long getSubServiceId() {
		return subServiceId;
	}

	public void setSubServiceId(Long subServiceId) {
		this.subServiceId = subServiceId;
	}

	public List<ContractChecklistValidationRequest> getContractChecklistValidationRequest() {
		return contractChecklistValidationRequest;
	}

	public void setContractChecklistValidationRequest(
			List<ContractChecklistValidationRequest> contractChecklistValidationRequest) {
		this.contractChecklistValidationRequest = contractChecklistValidationRequest;
	}

	public String getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getHusbandNationalId() {
		return husbandNationalId;
	}

	public void setHusbandNationalId(String husbandNationalId) {
		this.husbandNationalId = husbandNationalId;
	}

	public String getWifeNationalId() {
		return wifeNationalId;
	}

	public void setWifeNationalId(String wifeNationalId) {
		this.wifeNationalId = wifeNationalId;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getContractPaidAmount() {
			contractPaidAmount = getContractPaidAdvanceFees() + getContractPaidDelayedFees() + 
					getContractPaidConditionsFees() + getContractPaidBetweenusFees(); 
		return contractPaidAmount;
	}

	public void setContractPaidAmount(double contractPaidAmount) {
		this.contractPaidAmount = contractPaidAmount;
	}

	public double getContractPaidAdvanceFees() {
		return contractPaidAdvanceFees;
	}

	public void setContractPaidAdvanceFees(double contractPaidAdvanceFees) {
		this.contractPaidAdvanceFees = contractPaidAdvanceFees;
	}

	public double getContractPaidDelayedFees() {
		return contractPaidDelayedFees;
	}

	public void setContractPaidDelayedFees(double contractPaidDelayedFees) {
		this.contractPaidDelayedFees = contractPaidDelayedFees;
	}

	public double getContractPaidConditionsFees() {
		return contractPaidConditionsFees;
	}

	public void setContractPaidConditionsFees(double contractPaidConditionsFees) {
		this.contractPaidConditionsFees = contractPaidConditionsFees;
	}

	public double getContractPaidBetweenusFees() {
		return contractPaidBetweenusFees;
	}

	public void setContractPaidBetweenusFees(double contractPaidBetweenusFees) {
		this.contractPaidBetweenusFees = contractPaidBetweenusFees;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSubServiceName() {
		return subServiceName;
	}

	public void setSubServiceName(String subServiceName) {
		this.subServiceName = subServiceName;
	}

	public String getContractFinancialNumber() {
		return contractFinancialNumber;
	}

	public void setContractFinancialNumber(String contractFinancialNumber) {
		this.contractFinancialNumber = contractFinancialNumber;
	}

	public MultipartFile getAttContract() {
		return attContract;
	}

	public void setAttContract(MultipartFile attContract) {
		this.attContract = attContract;
	}

	public String getMaazouniaChurchNameType() {
		return maazouniaChurchNameType;
	}

	public void setMaazouniaChurchNameType(String maazouniaChurchNameType) {
		this.maazouniaChurchNameType = maazouniaChurchNameType;
	}

	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}

	public Long getBookWarehouseId() {
		return bookWarehouseId;
	}

	public void setBookWarehouseId(Long bookWarehouseId) {
		this.bookWarehouseId = bookWarehouseId;
	}

	public String getHusbandNationalIdExpiryDate() {
		return husbandNationalIdExpiryDate;
	}

	public void setHusbandNationalIdExpiryDate(String husbandNationalIdExpiryDate) {
		this.husbandNationalIdExpiryDate = husbandNationalIdExpiryDate;
	}

	public String getWifeNationalIdExpiryDate() {
		return wifeNationalIdExpiryDate;
	}

	public void setWifeNationalIdExpiryDate(String wifeNationalIdExpiryDate) {
		this.wifeNationalIdExpiryDate = wifeNationalIdExpiryDate;
	}

	public String getTalakStatus() {
		return talakStatus;
	}

	public void setTalakStatus(String talakStatus) {
		this.talakStatus = talakStatus;
	}

	public String getTalakCount() {
		return talakCount;
	}

	public void setTalakCount(String talakCount) {
		this.talakCount = talakCount;
	}
 
	public String getWifeRepresentedStatus() {
		return wifeRepresentedStatus;
	}

	public void setWifeRepresentedStatus(String wifeRepresentedStatus) {
		this.wifeRepresentedStatus = wifeRepresentedStatus;
	}

	public String getTalakType() {
		return talakType;
	}

	public void setTalakType(String talakType) {
		this.talakType = talakType;
	}
	
	
  
}
