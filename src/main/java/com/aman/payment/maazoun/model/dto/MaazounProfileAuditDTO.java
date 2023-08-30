package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class MaazounProfileAuditDTO {

	private Long bookZawagMuslimSoldCountSerial;
	private Long bookTalakSoldCountSerial;
	private Long bookTasadokSoldCountSerial;
	private Long bookZawagMelalySoldCountSerial;
	private Long bookMoragaaSoldCountSerial;
	
	private Long bookZawagMuslimSoldCountCustody;
	private Long bookTalakSoldCountCustody;
	private Long bookTasadokSoldCountCustody;
	private Long bookZawagMelalySoldCountCustody;
	private Long bookMoragaaSoldCountCustody;
	
	// Contract Count = contract_count in warehouse * soldCount
	private Long bookZawagMuslimContractCount;
	private Long bookTalakContractCount;
	private Long bookTasadokContractCount;
	private Long bookZawagMelalyContractCount;
	private Long bookMoragaaContractCount;
	
	private Long contractZawagMuslimCollectedCount;
	private Long contractTalakCollectedCount;
	private Long contractTasadokCollectedCount;
	private Long contractZawagMelalyCollectedCount;
	private Long contractMoragaaCollectedCount;
	
	private Long contractZawagMuslimReceivedCount;
	private Long contractTalakReceivedCount;
	private Long contractTasadokReceivedCount;
	private Long contractZawagMelalyReceivedCount;
	private Long contractMoragaaReceivedCount;
	
	// Contract Count WithMaazoun = Contract Count - Received Count
	private Long contractZawagMuslimWithMaazounCount;
	private Long contractTalakWithMaazounCount;
	private Long contractTasadokWithMaazounCount;
	private Long contractZawagMelalyWithMaazounCount;
	private Long contractMoragaaWithMaazounCount;
	
	private Long totalBookSoldCountSerial;
	private Long totalBookSoldCountCustody;
	private Long totalBookContractCount;
	private Long totalContractCollectedCount;
	private Long totalContractReceivedCount;
	private Long totalContractWithMaazounCount;

	private String maazounName;
	private String maazounNationalId;
	private String maazounId;
	private String custody;
	private Long typeId;

	public MaazounProfileAuditDTO() {
		super();
	}

	
	public Long getBookZawagMuslimContractCount() {
		return bookZawagMuslimContractCount;
	}

	public void setBookZawagMuslimContractCount(Long bookZawagMuslimContractCount) {
		this.bookZawagMuslimContractCount = bookZawagMuslimContractCount;
	}

	public Long getBookTalakContractCount() {
		return bookTalakContractCount;
	}

	public void setBookTalakContractCount(Long bookTalakContractCount) {
		this.bookTalakContractCount = bookTalakContractCount;
	}

	public Long getBookTasadokContractCount() {
		return bookTasadokContractCount;
	}

	public void setBookTasadokContractCount(Long bookTasadokContractCount) {
		this.bookTasadokContractCount = bookTasadokContractCount;
	}

	public Long getBookZawagMelalyContractCount() {
		return bookZawagMelalyContractCount;
	}

	public void setBookZawagMelalyContractCount(Long bookZawagMelalyContractCount) {
		this.bookZawagMelalyContractCount = bookZawagMelalyContractCount;
	}

	public Long getBookMoragaaContractCount() {
		return bookMoragaaContractCount;
	}

	public void setBookMoragaaContractCount(Long bookMoragaaContractCount) {
		this.bookMoragaaContractCount = bookMoragaaContractCount;
	}

	public Long getContractZawagMuslimCollectedCount() {
		return contractZawagMuslimCollectedCount;
	}

	public void setContractZawagMuslimCollectedCount(Long contractZawagMuslimCollectedCount) {
		this.contractZawagMuslimCollectedCount = contractZawagMuslimCollectedCount;
	}

	public Long getContractTalakCollectedCount() {
		return contractTalakCollectedCount;
	}

	public void setContractTalakCollectedCount(Long contractTalakCollectedCount) {
		this.contractTalakCollectedCount = contractTalakCollectedCount;
	}

	public Long getContractTasadokCollectedCount() {
		return contractTasadokCollectedCount;
	}

	public void setContractTasadokCollectedCount(Long contractTasadokCollectedCount) {
		this.contractTasadokCollectedCount = contractTasadokCollectedCount;
	}

	public Long getContractZawagMelalyCollectedCount() {
		return contractZawagMelalyCollectedCount;
	}

	public void setContractZawagMelalyCollectedCount(Long contractZawagMelalyCollectedCount) {
		this.contractZawagMelalyCollectedCount = contractZawagMelalyCollectedCount;
	}

	public Long getContractMoragaaCollectedCount() {
		return contractMoragaaCollectedCount;
	}

	public void setContractMoragaaCollectedCount(Long contractMoragaaCollectedCount) {
		this.contractMoragaaCollectedCount = contractMoragaaCollectedCount;
	}

	public Long getContractZawagMuslimReceivedCount() {
		return contractZawagMuslimReceivedCount;
	}

	public void setContractZawagMuslimReceivedCount(Long contractZawagMuslimReceivedCount) {
		this.contractZawagMuslimReceivedCount = contractZawagMuslimReceivedCount;
	}

	public Long getContractTalakReceivedCount() {
		return contractTalakReceivedCount;
	}

	public void setContractTalakReceivedCount(Long contractTalakReceivedCount) {
		this.contractTalakReceivedCount = contractTalakReceivedCount;
	}

	public Long getContractTasadokReceivedCount() {
		return contractTasadokReceivedCount;
	}

	public void setContractTasadokReceivedCount(Long contractTasadokReceivedCount) {
		this.contractTasadokReceivedCount = contractTasadokReceivedCount;
	}

	public Long getContractZawagMelalyReceivedCount() {
		return contractZawagMelalyReceivedCount;
	}

	public void setContractZawagMelalyReceivedCount(Long contractZawagMelalyReceivedCount) {
		this.contractZawagMelalyReceivedCount = contractZawagMelalyReceivedCount;
	}

	public Long getContractMoragaaReceivedCount() {
		return contractMoragaaReceivedCount;
	}

	public void setContractMoragaaReceivedCount(Long contractMoragaaReceivedCount) {
		this.contractMoragaaReceivedCount = contractMoragaaReceivedCount;
	}

	public Long getContractZawagMuslimWithMaazounCount() {
		contractZawagMuslimWithMaazounCount = bookZawagMuslimContractCount - contractZawagMuslimReceivedCount;
		return contractZawagMuslimWithMaazounCount;
	}

	public void setContractZawagMuslimWithMaazounCount(Long contractZawagMuslimWithMaazounCount) {
		this.contractZawagMuslimWithMaazounCount = contractZawagMuslimWithMaazounCount;
	}

	public Long getContractTalakWithMaazounCount() {
		contractTalakWithMaazounCount = bookTalakContractCount - contractTalakReceivedCount;
		return contractTalakWithMaazounCount;
	}

	public void setContractTalakWithMaazounCount(Long contractTalakWithMaazounCount) {
		this.contractTalakWithMaazounCount = contractTalakWithMaazounCount;
	}

	public Long getContractTasadokWithMaazounCount() {
		contractTasadokWithMaazounCount = bookTasadokContractCount - contractTasadokReceivedCount;
		return contractTasadokWithMaazounCount;
	}

	public void setContractTasadokWithMaazounCount(Long contractTasadokWithMaazounCount) {
		this.contractTasadokWithMaazounCount = contractTasadokWithMaazounCount;
	}

	public Long getContractZawagMelalyWithMaazounCount() {
		contractZawagMelalyWithMaazounCount = bookZawagMelalyContractCount - contractZawagMelalyReceivedCount;
		return contractZawagMelalyWithMaazounCount;
	}

	public void setContractZawagMelalyWithMaazounCount(Long contractZawagMelalyWithMaazounCount) {
		this.contractZawagMelalyWithMaazounCount = contractZawagMelalyWithMaazounCount;
	}

	public Long getContractMoragaaWithMaazounCount() {
		contractMoragaaWithMaazounCount = bookMoragaaContractCount - contractMoragaaReceivedCount;
		return contractMoragaaWithMaazounCount;
	}

	public void setContractMoragaaWithMaazounCount(Long contractMoragaaWithMaazounCount) {
		this.contractMoragaaWithMaazounCount = contractMoragaaWithMaazounCount;
	}

	public Long getTotalBookSoldCountSerial() {
		totalBookSoldCountSerial = bookMoragaaSoldCountSerial + bookTalakSoldCountSerial + bookTasadokSoldCountSerial + 
				bookZawagMelalySoldCountSerial + bookZawagMuslimSoldCountSerial;
		return totalBookSoldCountSerial;
	}

	public void setTotalBookSoldCountSerial(Long totalBookSoldCountSerial) {
		this.totalBookSoldCountSerial = totalBookSoldCountSerial;
	}

	public Long getTotalBookContractCount() {
		totalBookContractCount = bookMoragaaContractCount + bookTalakContractCount + bookTasadokContractCount + 
				bookZawagMelalyContractCount + bookZawagMuslimContractCount;
		return totalBookContractCount;
	}

	public void setTotalBookContractCount(Long totalBookContractCount) {
		this.totalBookContractCount = totalBookContractCount;
	}

	public Long getTotalContractCollectedCount() {
		totalContractCollectedCount = contractMoragaaCollectedCount + contractTalakCollectedCount + contractTasadokCollectedCount +
				contractZawagMelalyCollectedCount + contractZawagMuslimCollectedCount;
		return totalContractCollectedCount;
	}

	public void setTotalContractCollectedCount(Long totalContractCollectedCount) {
		this.totalContractCollectedCount = totalContractCollectedCount;
	}

	public Long getTotalContractReceivedCount() {
		totalContractReceivedCount = contractMoragaaReceivedCount + contractTalakReceivedCount +
				contractTasadokReceivedCount + contractZawagMelalyReceivedCount + contractZawagMuslimReceivedCount;
		return totalContractReceivedCount;
	}

	public void setTotalContractReceivedCount(Long totalContractReceivedCount) {
		this.totalContractReceivedCount = totalContractReceivedCount;
	}

	public Long getTotalContractWithMaazounCount() {
		totalContractWithMaazounCount = contractMoragaaWithMaazounCount + contractTalakWithMaazounCount +
				contractTasadokWithMaazounCount + contractZawagMelalyWithMaazounCount + contractZawagMuslimWithMaazounCount;
		return totalContractWithMaazounCount;
	}

	public void setTotalContractWithMaazounCount(Long totalContractWithMaazounCount) {
		this.totalContractWithMaazounCount = totalContractWithMaazounCount;
	}

	public String getMaazounName() {
		return maazounName;
	}

	public void setMaazounName(String maazounName) {
		this.maazounName = maazounName;
	}

	public String getMaazounNationalId() {
		return maazounNationalId;
	}

	public void setMaazounNationalId(String maazounNationalId) {
		this.maazounNationalId = maazounNationalId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getMaazounId() {
		return maazounId;
	}

	public void setMaazounId(String maazounId) {
		this.maazounId = maazounId;
	}

	public String getCustody() {
		return custody;
	}

	public void setCustody(String custody) {
		this.custody = custody;
	}

	public Long getBookZawagMuslimSoldCountSerial() {
		return bookZawagMuslimSoldCountSerial;
	}


	public void setBookZawagMuslimSoldCountSerial(Long bookZawagMuslimSoldCountSerial) {
		this.bookZawagMuslimSoldCountSerial = bookZawagMuslimSoldCountSerial;
	}


	public Long getBookTalakSoldCountSerial() {
		return bookTalakSoldCountSerial;
	}


	public void setBookTalakSoldCountSerial(Long bookTalakSoldCountSerial) {
		this.bookTalakSoldCountSerial = bookTalakSoldCountSerial;
	}


	public Long getBookTasadokSoldCountSerial() {
		return bookTasadokSoldCountSerial;
	}


	public void setBookTasadokSoldCountSerial(Long bookTasadokSoldCountSerial) {
		this.bookTasadokSoldCountSerial = bookTasadokSoldCountSerial;
	}


	public Long getBookZawagMelalySoldCountSerial() {
		return bookZawagMelalySoldCountSerial;
	}


	public void setBookZawagMelalySoldCountSerial(Long bookZawagMelalySoldCountSerial) {
		this.bookZawagMelalySoldCountSerial = bookZawagMelalySoldCountSerial;
	}


	public Long getBookMoragaaSoldCountSerial() {
		return bookMoragaaSoldCountSerial;
	}


	public void setBookMoragaaSoldCountSerial(Long bookMoragaaSoldCountSerial) {
		this.bookMoragaaSoldCountSerial = bookMoragaaSoldCountSerial;
	}


	public Long getBookZawagMuslimSoldCountCustody() {
		return bookZawagMuslimSoldCountCustody;
	}


	public void setBookZawagMuslimSoldCountCustody(Long bookZawagMuslimSoldCountCustody) {
		this.bookZawagMuslimSoldCountCustody = bookZawagMuslimSoldCountCustody;
	}


	public Long getBookTalakSoldCountCustody() {
		return bookTalakSoldCountCustody;
	}


	public void setBookTalakSoldCountCustody(Long bookTalakSoldCountCustody) {
		this.bookTalakSoldCountCustody = bookTalakSoldCountCustody;
	}


	public Long getBookTasadokSoldCountCustody() {
		return bookTasadokSoldCountCustody;
	}


	public void setBookTasadokSoldCountCustody(Long bookTasadokSoldCountCustody) {
		this.bookTasadokSoldCountCustody = bookTasadokSoldCountCustody;
	}


	public Long getBookZawagMelalySoldCountCustody() {
		return bookZawagMelalySoldCountCustody;
	}


	public void setBookZawagMelalySoldCountCustody(Long bookZawagMelalySoldCountCustody) {
		this.bookZawagMelalySoldCountCustody = bookZawagMelalySoldCountCustody;
	}


	public Long getBookMoragaaSoldCountCustody() {
		return bookMoragaaSoldCountCustody;
	}


	public void setBookMoragaaSoldCountCustody(Long bookMoragaaSoldCountCustody) {
		this.bookMoragaaSoldCountCustody = bookMoragaaSoldCountCustody;
	}


	public Long getTotalBookSoldCountCustody() {
		totalBookSoldCountCustody = bookMoragaaSoldCountCustody + bookTalakSoldCountCustody + bookTasadokSoldCountCustody + 
				bookZawagMelalySoldCountCustody + bookZawagMuslimSoldCountCustody;
		return totalBookSoldCountCustody;
	}


	public void setTotalBookSoldCountCustody(Long totalBookSoldCountCustody) {
		this.totalBookSoldCountCustody = totalBookSoldCountCustody;
	}


	@Override
	public String toString() {
		return new JSONObject(this).toString();
	}

	

}