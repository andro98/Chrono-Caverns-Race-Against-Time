package com.aman.payment.maazoun.model.payload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class EditMaazounContractRequest {

	private Long subServiceId;
	private String subServiceName;
	// private String subServiceFees;
	// private String amount;
	private Long maazounId;
	private String contractDate;
	private String id;
	private String bookSerialNumber;
	private String contractNumber;
	private String contractFinancialNumber;
	private String husbandName;
	private String wifeName;
	private String husbandNationalId;
	private String wifeNationalId;
	private Long sectorId;
	private String status;
	private String image;
	private MultipartFile attContract;

	// private List<ContractChecklistValidationRequest>
	// contractChecklistValidationRequest;

	public EditMaazounContractRequest() {
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

//	public List<ContractChecklistValidationRequest> getContractChecklistValidationRequest() {
//		return contractChecklistValidationRequest;
//	}
//
//	public void setContractChecklistValidationRequest(
//			List<ContractChecklistValidationRequest> contractChecklistValidationRequest) {
//		this.contractChecklistValidationRequest = contractChecklistValidationRequest;
//	}
 

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

	public String getContractFinancialNumber() {
		return contractFinancialNumber;
	}

	public void setContractFinancialNumber(String contractFinancialNumber) {
		this.contractFinancialNumber = contractFinancialNumber;
	}

	public String getSubServiceName() {
		return subServiceName;
	}

	public void setSubServiceName(String subServiceName) {
		this.subServiceName = subServiceName;
	}
 
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MultipartFile getAttContract() {
		return attContract;
	}

	public void setAttContract(MultipartFile attContract) {
		this.attContract = attContract;
	}

}
