package com.aman.payment.core.model.payload;

import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.core.service.CryptoMngrCoreService;


public class CloseDayPullAccountRequest implements CoreBasePayload<CloseDayPullAccountRequest> {

	private String pullAccountId;
	private MultipartFile fileBankAtt;
	private MultipartFile fileVisaAtt;
	private String userName;
	private String amountVisa;
	private String amountCash;
	private String settlementCode;

	public CloseDayPullAccountRequest() {
	}

	public CloseDayPullAccountRequest(String pullAccountId, MultipartFile fileBankAtt, MultipartFile fileVisaAtt,
			String userName, String amountVisa, String amountCash, String settlementCode) {
		super();
		this.pullAccountId = pullAccountId;
		this.fileBankAtt = fileBankAtt;
		this.fileVisaAtt = fileVisaAtt;
		this.userName = userName;
		this.amountVisa = amountVisa;
		this.amountCash = amountCash;
		this.settlementCode = settlementCode;
	}

	public String getPullAccountId() {
		return pullAccountId;
	}

	public MultipartFile getFileBankAtt() {
		return fileBankAtt;
	}

	public MultipartFile getFileVisaAtt() {
		return fileVisaAtt;
	}

	public String getUserName() {
		return userName;
	}

	public String getAmountVisa() {
		return amountVisa;
	}

	public String getAmountCash() {
		return amountCash;
	}

	public void setPullAccountId(String pullAccountId) {
		this.pullAccountId = pullAccountId;
	}

	public void setFileBankAtt(MultipartFile fileBankAtt) {
		this.fileBankAtt = fileBankAtt;
	}

	public void setFileVisaAtt(MultipartFile fileVisaAtt) {
		this.fileVisaAtt = fileVisaAtt;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAmountVisa(String amountVisa) {
		this.amountVisa = amountVisa;
	}

	public void setAmountCash(String amountCash) {
		this.amountCash = amountCash;
	}

	public String getSettlementCode() {
		return settlementCode;
	}

	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}

	@Override
	public CloseDayPullAccountRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {
		return new CloseDayPullAccountRequest(cryptoMngrCoreService.decrypt(pullAccountId), fileBankAtt, fileVisaAtt,
				cryptoMngrCoreService.decrypt(userName), cryptoMngrCoreService.decrypt(amountVisa),
				cryptoMngrCoreService.decrypt(amountCash), cryptoMngrCoreService.decrypt(settlementCode));
	}

}
