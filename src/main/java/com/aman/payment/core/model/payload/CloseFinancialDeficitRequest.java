package com.aman.payment.core.model.payload;

import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.core.service.CryptoMngrCoreService;


public class CloseFinancialDeficitRequest implements CoreBasePayload<CloseFinancialDeficitRequest> {

	private String financialDeficitId;
	private MultipartFile fileAtt;
	private String amount;
	private String comment;
	private String username;

	public CloseFinancialDeficitRequest() {
	}

	public CloseFinancialDeficitRequest(String financialDeficitId, MultipartFile fileAtt, 
			String amount, String comment, String username) {
		super();
		this.financialDeficitId = financialDeficitId;
		this.fileAtt = fileAtt;
		this.amount = amount;
		this.comment = comment;
		this.username = username;
	}

	public String getFinancialDeficitId() {
		return financialDeficitId;
	}

	public MultipartFile getFileAtt() {
		return fileAtt;
	}

	public String getAmount() {
		return amount;
	}

	public void setFinancialDeficitId(String financialDeficitId) {
		this.financialDeficitId = financialDeficitId;
	}

	public void setFileAtt(MultipartFile fileAtt) {
		this.fileAtt = fileAtt;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public CloseFinancialDeficitRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {

		return new CloseFinancialDeficitRequest(cryptoMngrCoreService.decrypt(financialDeficitId), fileAtt,
				cryptoMngrCoreService.decrypt(amount), cryptoMngrCoreService.decrypt(comment),
				cryptoMngrCoreService.decrypt(username));
	}

}
