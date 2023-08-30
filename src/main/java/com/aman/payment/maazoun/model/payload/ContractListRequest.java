package com.aman.payment.maazoun.model.payload;

import com.aman.payment.maazoun.model.MaazounContractQuota;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ContractListRequest", description = "Add Book List Request DTO Payload")
public class ContractListRequest extends MaazounContractQuota{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long collectionInfoId;
	private String typeId;
	private String typeName;
	private String transactionCode;

	public ContractListRequest() {
		super();
	}

	public ContractListRequest(Long collectionInfoId) {
		super();
		this.collectionInfoId = collectionInfoId;
	}

	public Long getCollectionInfoId() {
		return collectionInfoId;
	}

	public void setCollectionInfoId(Long collectionInfoId) {
		this.collectionInfoId = collectionInfoId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}


}