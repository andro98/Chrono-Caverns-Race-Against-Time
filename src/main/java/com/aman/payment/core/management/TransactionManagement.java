package com.aman.payment.core.management;

import java.io.InputStream;
import java.util.Date;
import java.util.Set;

import com.aman.payment.core.model.TransactionECR;
import com.aman.payment.core.model.dto.TransactionDTO;
import com.aman.payment.core.model.dto.TransactionPagingDTO;
import com.aman.payment.core.model.dto.TransactionResponseDTO;
import com.aman.payment.core.model.payload.TransactionCodeRequest;
import com.aman.payment.core.model.payload.TransactionDurationRequest;
import com.aman.payment.core.model.payload.TransactionPaymentRequest;
import com.aman.payment.core.model.payload.TransactionRefundRequest;
import com.aman.payment.core.model.payload.URLRequest;

public interface TransactionManagement {

	public TransactionResponseDTO createTransaction(TransactionPaymentRequest transactionPaymentRequest);
	
	public TransactionResponseDTO createExternalTransaction(TransactionPaymentRequest transactionPaymentRequest);
	
	public TransactionResponseDTO refundTransaction(TransactionRefundRequest transactionRefundRequest);
	
	public TransactionDTO findTransactionByCode(TransactionCodeRequest transactionCodeRequest);
	
	public Set<TransactionDTO> findTransactionByPosId(TransactionCodeRequest transactionCodeRequest);
	
	public TransactionPagingDTO findTransactionByDuration(TransactionDurationRequest transactionDurationRequest);

	public InputStream downloadRefundAttFile(URLRequest uRLRequest);
	
	public void deleteTransaction(String transactionCode);
	
	public void updateTransactionStatus(String transactionCode, String updatedBy, Date updatedAt, String statusFk);
	
	public void updateTransactionPaymentCodeAndVisaNumber(String transactionCode, String paymentCode, 
			TransactionECR transactionECRFk, String visaNumber, String updatedBy, Date updatedAt);
	
}
